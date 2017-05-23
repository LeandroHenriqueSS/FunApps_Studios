package com.lh_freeapps.coxinhaoumortadela.ui.result;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lh_freeapps.coxinhaoumortadela.ui.base.BasePresenter;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;

/**
 * Created by Leandro on 28/04/2017.
 */

public interface ResultContract {

    interface View extends BaseView {
        void showPercentage();
    }

    interface Presenter extends BasePresenter {

        void setUserParams(int coxinhaLevel, int mortadelaLevel, boolean isOnCloud);

        int getImageID();

        String getPercentage();

        String getDescription();

        void showResult(ViewGroup viewGroup, android.view.View resultLine, WindowManager windowManager);

        void shareImage(ViewGroup viewGroup, android.view.View view);
    }

}
