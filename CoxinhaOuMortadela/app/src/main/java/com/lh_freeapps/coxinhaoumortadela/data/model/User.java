package com.lh_freeapps.coxinhaoumortadela.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Leandro on 03/05/2017.
 */

@Entity
public class User {

    @Id(autoincrement = false)
    private Long userId = new Long(0);

    @NotNull
    private int coxinhaLevel;

    @NotNull
    private int mortadelaLevel;

    @NotNull
    private int resultType;

    @NotNull
    private boolean onTheCloud;

    @Generated(hash = 1804311916)
    public User(Long userId, int coxinhaLevel, int mortadelaLevel, int resultType,
            boolean onTheCloud) {
        this.userId = userId;
        this.coxinhaLevel = coxinhaLevel;
        this.mortadelaLevel = mortadelaLevel;
        this.resultType = resultType;
        this.onTheCloud = onTheCloud;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getCoxinhaLevel() {
        return this.coxinhaLevel;
    }

    public void setCoxinhaLevel(int coxinhaLevel) {
        this.coxinhaLevel = coxinhaLevel;
    }

    public int getMortadelaLevel() {
        return this.mortadelaLevel;
    }

    public void setMortadelaLevel(int mortadelaLevel) {
        this.mortadelaLevel = mortadelaLevel;
    }

    public int getResultType() {
        return this.resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public boolean getOnTheCloud() {
        return this.onTheCloud;
    }

    public void setOnTheCloud(boolean onTheCloud) {
        this.onTheCloud = onTheCloud;
    }




}
