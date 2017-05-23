package com.lh_freeapps.coxinhaoumortadela.ui.first_screen;

import com.lh_freeapps.coxinhaoumortadela.ui.base.BasePresenter;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;

/**
 * Created by Leandro on 28/04/2017.
 */

public interface FirstScreenContract {

    interface View extends BaseView {

    }


    interface Presenter extends BasePresenter {

        void startQuizActivity();

        void startResultActivity();

        void startStatisticsActivity();

    }

}
