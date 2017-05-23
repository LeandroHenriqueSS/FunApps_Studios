package com.lh_freeapps.coxinhaoumortadela.di.component;

import com.lh_freeapps.coxinhaoumortadela.di.module.ActivityModule;
import com.lh_freeapps.coxinhaoumortadela.di.scope.PerActivity;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;
import com.lh_freeapps.coxinhaoumortadela.ui.first_screen.FirstScreenActivity;
import com.lh_freeapps.coxinhaoumortadela.ui.first_screen.FirstScreenContract;
import com.lh_freeapps.coxinhaoumortadela.ui.quiz.QuizContract;
import com.lh_freeapps.coxinhaoumortadela.ui.result.ResultContract;
import com.lh_freeapps.coxinhaoumortadela.ui.statistics.StatisticsContract;

import dagger.Component;

/**
 * Created by Leandro
 */

@PerActivity
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    FirstScreenContract.Presenter getFirstScreenPresenter();

    QuizContract.Presenter        getQuizPresenter();

    ResultContract.Presenter      getResultPresenter();

    StatisticsContract.Presenter  getStatisticsPresenter();
}
