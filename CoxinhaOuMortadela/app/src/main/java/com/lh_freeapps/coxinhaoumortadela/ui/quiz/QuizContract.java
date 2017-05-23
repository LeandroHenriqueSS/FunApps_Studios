package com.lh_freeapps.coxinhaoumortadela.ui.quiz;

import com.lh_freeapps.coxinhaoumortadela.ui.base.BasePresenter;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;

/**
 * Created by Leandro on 28/04/2017.
 */

public interface QuizContract {

    interface View extends BaseView {

        void updateSentence(int currentSentenceNumber, int maxSentenceNumber, String sentence);

    }

    interface Presenter extends BasePresenter {

        void onClickYesButton();

        void onClickNoButton();

    }
}
