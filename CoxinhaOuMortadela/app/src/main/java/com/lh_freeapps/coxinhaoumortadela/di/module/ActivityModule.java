package com.lh_freeapps.coxinhaoumortadela.di.module;

import com.lh_freeapps.coxinhaoumortadela.di.scope.PerActivity;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;
import com.lh_freeapps.coxinhaoumortadela.ui.first_screen.FirstScreenContract;
import com.lh_freeapps.coxinhaoumortadela.ui.first_screen.FirstScreenPresenter;
import com.lh_freeapps.coxinhaoumortadela.ui.quiz.QuizContract;
import com.lh_freeapps.coxinhaoumortadela.ui.quiz.QuizPresenter;
import com.lh_freeapps.coxinhaoumortadela.ui.result.ResultContract;
import com.lh_freeapps.coxinhaoumortadela.ui.result.ResultPresenter;
import com.lh_freeapps.coxinhaoumortadela.ui.statistics.StatisticsContract;
import com.lh_freeapps.coxinhaoumortadela.ui.statistics.StatisticsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leandro
 */

@Module
public class ActivityModule {

    private BaseView view;

    public ActivityModule(BaseView view) {
        this.view = view;
    }


    @Provides
    @PerActivity
    public FirstScreenContract.Presenter provideFirsScreenPresenter() {
        return new FirstScreenPresenter(view);
    }

    @Provides
    @PerActivity
    public QuizContract.Presenter provideQuizPresenter() {
        return new QuizPresenter(view);
    }

    @Provides
    @PerActivity
    public ResultContract.Presenter provideResultPresenter() {
        return new ResultPresenter(view);
    }

    @Provides
    @PerActivity
    public StatisticsContract.Presenter provideStatisticsPresenter() {
        return new StatisticsPresenter(view);
    }


}
