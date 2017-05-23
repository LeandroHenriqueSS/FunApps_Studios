package com.lh_freeapps.coxinhaoumortadela;

import android.app.Application;

import com.lh_freeapps.coxinhaoumortadela.data.model.DaoMaster;
import com.lh_freeapps.coxinhaoumortadela.data.model.DaoMaster.DevOpenHelper;
import com.lh_freeapps.coxinhaoumortadela.data.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Leandro on 03/05/2017.
 */

public class CoxinhaOuMortadela extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DevOpenHelper helper = new DevOpenHelper(this, "coxinha-ou-mortadela-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
