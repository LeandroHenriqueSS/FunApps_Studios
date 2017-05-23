package com.lh_freeapps.coxinhaoumortadela.ui.first_screen;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lh_freeapps.coxinhaoumortadela.CoxinhaOuMortadela;
import com.lh_freeapps.coxinhaoumortadela.R;
import com.lh_freeapps.coxinhaoumortadela.data.model.DaoSession;
import com.lh_freeapps.coxinhaoumortadela.data.model.User;
import com.lh_freeapps.coxinhaoumortadela.data.model.UserDao;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;
import com.lh_freeapps.coxinhaoumortadela.ui.quiz.QuizActivity;
import com.lh_freeapps.coxinhaoumortadela.ui.result.ResultActivity;
import com.lh_freeapps.coxinhaoumortadela.ui.statistics.StatisticsActivity;

import java.util.List;

/**
 * Created by Leandro on 28/04/2017.
 */

public class FirstScreenPresenter implements FirstScreenContract.Presenter {

    private FirstScreenContract.View firstScreenView;

    private Context context;


    public FirstScreenPresenter(BaseView firstScreenView) {
        this.context = (Context) firstScreenView;
        this.firstScreenView = (FirstScreenContract.View) firstScreenView;
    }

    @Override
    public void startQuizActivity() {
        context.startActivity(new Intent(context, QuizActivity.class));
    }

    @Override
    public void startResultActivity() {
        // create db sessions
        DaoSession daoSession = ((CoxinhaOuMortadela) context.getApplicationContext()).getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();

        // if there is no data, return
        if (users.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.er_no_data), Toast.LENGTH_SHORT).show();
            return;
        }

        // prepare intent
        User user = users.get(0);
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("COXINHA_LEVEL"  , user.getCoxinhaLevel()  );
        intent.putExtra("MORTADELA_LEVEL", user.getMortadelaLevel());
        intent.putExtra("IS_ON_CLOUD"    , user.getOnTheCloud());

        // start activity
        context.startActivity(intent);
    }

    @Override
    public void startStatisticsActivity() {
        context.startActivity(new Intent(context, StatisticsActivity.class));
    }

}
