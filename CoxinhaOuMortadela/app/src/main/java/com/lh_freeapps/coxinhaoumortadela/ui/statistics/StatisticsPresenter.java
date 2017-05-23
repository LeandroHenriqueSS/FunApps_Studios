package com.lh_freeapps.coxinhaoumortadela.ui.statistics;

import android.content.Context;

import com.lh_freeapps.coxinhaoumortadela.CoxinhaOuMortadela;
import com.lh_freeapps.coxinhaoumortadela.api.Service;
import com.lh_freeapps.coxinhaoumortadela.data.model.GlobalData;
import com.lh_freeapps.coxinhaoumortadela.data.model.DaoSession;
import com.lh_freeapps.coxinhaoumortadela.data.model.GlobalDataDao;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BasePresenter;
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

                statisticsView.onGetGlobalDataSucceed(response.body());

                // insert into database
                DaoSession daoSession = ((CoxinhaOuMortadela) context.getApplicationContext()).getDaoSession();
                GlobalDataDao globalDataDao = daoSession.getGlobalDataDao();
                globalDataDao.insertOrReplace(response.body());
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


}
