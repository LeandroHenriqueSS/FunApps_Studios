package com.lh_freeapps.coxinhaoumortadela.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Leandro on 04/05/2017.
 */

@Entity
public class GlobalData {

    @Id(autoincrement = false)
    private Long id = new Long(0);

    @NotNull
    private int numCoxinha;

    @NotNull
    private int numSuperCoxinha;

    @NotNull
    private int numMortadela;

    @NotNull
    private int numSuperMortadela;

    @NotNull
    private int numCoxinhaDeMortadela;

    @NotNull
    private int totalUsers;

    @Generated(hash = 966024865)
    public GlobalData(Long id, int numCoxinha, int numSuperCoxinha,
            int numMortadela, int numSuperMortadela, int numCoxinhaDeMortadela,
            int totalUsers) {
        this.id = id;
        this.numCoxinha = numCoxinha;
        this.numSuperCoxinha = numSuperCoxinha;
        this.numMortadela = numMortadela;
        this.numSuperMortadela = numSuperMortadela;
        this.numCoxinhaDeMortadela = numCoxinhaDeMortadela;
        this.totalUsers = totalUsers;
    }

    @Generated(hash = 1173898045)
    public GlobalData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumCoxinha() {
        return this.numCoxinha;
    }

    public void setNumCoxinha(int numCoxinha) {
        this.numCoxinha = numCoxinha;
    }

    public int getNumSuperCoxinha() {
        return this.numSuperCoxinha;
    }

    public void setNumSuperCoxinha(int numSuperCoxinha) {
        this.numSuperCoxinha = numSuperCoxinha;
    }

    public int getNumMortadela() {
        return this.numMortadela;
    }

    public void setNumMortadela(int numMortadela) {
        this.numMortadela = numMortadela;
    }

    public int getNumSuperMortadela() {
        return this.numSuperMortadela;
    }

    public void setNumSuperMortadela(int numSuperMortadela) {
        this.numSuperMortadela = numSuperMortadela;
    }

    public int getNumCoxinhaDeMortadela() {
        return this.numCoxinhaDeMortadela;
    }

    public void setNumCoxinhaDeMortadela(int numCoxinhaDeMortadela) {
        this.numCoxinhaDeMortadela = numCoxinhaDeMortadela;
    }

    public int getTotalUsers() {
        return this.totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

}
