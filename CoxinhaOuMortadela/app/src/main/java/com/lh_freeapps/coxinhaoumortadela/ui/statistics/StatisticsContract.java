package com.lh_freeapps.coxinhaoumortadela.ui.statistics;

import com.lh_freeapps.coxinhaoumortadela.data.model.GlobalData;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BasePresenter;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;

/**
 * Created by Leandro on 04/05/2017.
 */

public interface StatisticsContract {

    interface View extends BaseView {
        void onGetGlobalDataSucceed(GlobalData globalData);
        void onGetGlobalDataFailed(String error);
    }

    interface Presenter extends BasePresenter {
        void callGetGlobalData();
    }


}
