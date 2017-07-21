package com.lh_freeapps.coxinhaoumortadela.ui.statistics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.lh_freeapps.coxinhaoumortadela.CoxinhaOuMortadela;
import com.lh_freeapps.coxinhaoumortadela.R;
import com.lh_freeapps.coxinhaoumortadela.api.Service;
import com.lh_freeapps.coxinhaoumortadela.data.model.DaoSession;
import com.lh_freeapps.coxinhaoumortadela.data.model.GlobalData;
import com.lh_freeapps.coxinhaoumortadela.data.model.GlobalDataDao;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leandro on 04/05/2017.
 */

public class StatisticsPresenter implements StatisticsContract.Presenter{

    private StatisticsContract.View statisticsView;

    private Context context;


    public StatisticsPresenter(BaseView statisticsView) {
        this.statisticsView = (StatisticsContract.View) statisticsView;
        this.context        = (Context) statisticsView;
    }

    @Override
    public void callGetGlobalData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<GlobalData> callGD = service.getGlobalData("CtrlGlobalData.php");

        callGD.enqueue(new Callback<GlobalData>() {
            @Override
            public void onResponse(Call<GlobalData> call, Response<GlobalData> response) {

                if (response == null || response.body() == null) {
                    getGlobalDataFromDatabase();
                    return;
                }

                // insert into database
                DaoSession daoSession = ((CoxinhaOuMortadela) context.getApplicationContext()).getDaoSession();
                GlobalDataDao globalDataDao = daoSession.getGlobalDataDao();
                globalDataDao.insertOrReplace(response.body());

                onGetGlobalDataSucceed(response.body());
            }

            @Override
            public void onFailure(Call<GlobalData> call, Throwable t) {
                getGlobalDataFromDatabase();
            }
        });
    }

    public void getGlobalDataFromDatabase() {

        // insert into database
        DaoSession daoSession = ((CoxinhaOuMortadela) context.getApplicationContext()).getDaoSession();
        GlobalDataDao globalDataDao = daoSession.getGlobalDataDao();
        GlobalData globalData = globalDataDao.load(new Long(0));

        if (globalData != null)
            statisticsView.onGetGlobalDataSucceed(globalData);
        else
            statisticsView.onGetGlobalDataFailed("Sem acesso a Internet");
    }



    private void onGetGlobalDataSucceed(final GlobalData globalData) {

        final SharedPreferences pref = context.getSharedPreferences("STATISTICS_INFO", Context.MODE_PRIVATE);
        boolean shown = pref.getBoolean("shown", false);

        // Information about where the statistics data was retrieved. That dialog is only shown once
        if (shown == false) {

            //TODO: put this alert on StaticticsActivity
            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.statistics_info_body))
                    .setCancelable(false)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // alert activity about the result
                            statisticsView.onGetGlobalDataSucceed(globalData);

                            // do not show the dialog again
                            pref.edit().putBoolean("shown", true).apply();

                        }
                    })
                    .show();
        }
        else  {
            // alert activity about the result
            statisticsView.onGetGlobalDataSucceed(globalData);
        }

    }

}
