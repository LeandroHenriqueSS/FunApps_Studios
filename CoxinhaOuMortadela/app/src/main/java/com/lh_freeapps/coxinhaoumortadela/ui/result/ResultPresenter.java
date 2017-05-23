package com.lh_freeapps.coxinhaoumortadela.ui.result;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.google.gson.GsonBuilder;
import com.lh_freeapps.coxinhaoumortadela.CoxinhaOuMortadela;
import com.lh_freeapps.coxinhaoumortadela.R;
import com.lh_freeapps.coxinhaoumortadela.api.Service;
import com.lh_freeapps.coxinhaoumortadela.data.model.DaoSession;
import com.lh_freeapps.coxinhaoumortadela.data.model.User;
import com.lh_freeapps.coxinhaoumortadela.data.model.UserDao;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;
import com.lh_freeapps.coxinhaoumortadela.util.DeviceIdUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lh_freeapps.coxinhaoumortadela.data.model.ResultType.*;


/**
 * Created by Leandro on 28/04/2017.
 */

public class ResultPresenter implements ResultContract.Presenter {

    private ResultContract.View resultView;

    private User user;

    private Context context;


    public ResultPresenter(BaseView resultView) {
        this.resultView = (ResultContract.View) resultView;
        this.context    = (Context) resultView;
    }

    @Override
    public void setUserParams(int coxinhaLevel, int mortadelaLevel, boolean isOnCloud) {
        user = new User();
        user.setUserId(DeviceIdUtility.getSimplifiedDeviceID(context));
        user.setCoxinhaLevel(coxinhaLevel);
        user.setMortadelaLevel(mortadelaLevel);
        user.setResultType(getTypeByCoxinhaLevel(coxinhaLevel));

        if (isOnCloud == false)
            saveUserIntoServer();
    }




    @Override
    public String getPercentage() {
        return Math.max(user.getCoxinhaLevel(), user.getMortadelaLevel()) + "%";
    }


    @Override
    public String getDescription() {

        switch (user.getResultType()) {

            case COXINHA:
                return context.getString(R.string.result_coxinha);

            case SUPER_COXINHA:
                return context.getString(R.string.result_super_coxinha);

            case MORTADELA:
                return context.getString(R.string.result_mortadela);

            case SUPER_MORTADELA:
                return context.getString(R.string.result_super_mortadela);

            default: // COXINHA_DE_MORTADELA
                return context.getString(R.string.result_draw);
        }

    }

    @Override
    public void showResult(final ViewGroup viewGroup, final View resultLine, final WindowManager windowManager) {
        int xOrig, xDest;

        // measure layout
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics( dm );
        resultLine.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        // origin starting at left
        if (user.getCoxinhaLevel() >= user.getMortadelaLevel()) {
            xOrig = 0;
            xOrig -= resultLine.getMeasuredWidth()/2;
        // origin starting at right
        } else {
            xOrig = dm.widthPixels;
            xOrig -= (resultLine.getMeasuredWidth());
        }


        // get destine
        xDest = (int) (dm.widthPixels*user.getCoxinhaLevel()/100f);
        xDest -= resultLine.getMeasuredWidth()*0.75;


        // avoid resultline disappear at the edges
        if (user.getCoxinhaLevel() == 100)
            xDest -= resultLine.getMeasuredWidth()/4;
        if (user.getMortadelaLevel() == 100)
            xDest += resultLine.getMeasuredWidth()/4;


        // create animation
        TranslateAnimation anim = new TranslateAnimation(xOrig, xDest, 0, 0);
        anim.setDuration(3000);
        anim.setStartOffset(500);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                resultView.showPercentage();
            }
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}

        });

        // show animation
        resultLine.startAnimation(anim);

        //TODO: maybe create an animation for the result line when it starts and end to move
        // like make it bigger for a while
    }


    @Override
    public int getImageID() {

        switch (user.getResultType()) {
            case COXINHA: case SUPER_COXINHA:
                return R.drawable.coxinha;

            case MORTADELA: case SUPER_MORTADELA:
                return R.drawable.mortadela;

            default: // COXINHA_DE_MORTADELA
                return R.drawable.coxinha_de_mortadela;
        }

    }


    private void saveUserIntoServer() {
        // create dao
        DaoSession daoSession = ((CoxinhaOuMortadela) context.getApplicationContext()).getDaoSession();
        final UserDao userDao = daoSession.getUserDao();

        // create retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        // create callback
        Service service = retrofit.create(Service.class);
        Call<String> callSend = service.postUserData(user);

        callSend.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("ResultPresenter", "Data saved into server");
                user.setOnTheCloud(true);
                userDao.insertOrReplace(user);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("ResultPresenter", "It was not possible to save data into server " + t.getMessage());
            }

        });
    }


    @Override
    public void shareImage(ViewGroup viewGroup, View view) {
        new ShareResultImageUtil(context).shareResult(viewGroup, view);
    }

}
