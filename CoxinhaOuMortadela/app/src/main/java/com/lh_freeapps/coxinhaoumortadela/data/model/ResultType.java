package com.lh_freeapps.coxinhaoumortadela.data.model;

/**
 * Created by Leandro on 04/05/2017.
 */

public abstract class ResultType {

    public static final int COXINHA              = 1;
    public static final int SUPER_COXINHA        = 2;

    public static final int MORTADELA            = 3;
    public static final int SUPER_MORTADELA      = 4;

    public static final int COXINHA_DE_MORTADELA = 5;


    public static int getTypeByCoxinhaLevel(int coxinhaLevel) {

        if (coxinhaLevel > 55 && coxinhaLevel < 80)
            return COXINHA;

        else if (coxinhaLevel >= 80)
            return SUPER_COXINHA;

        else if (coxinhaLevel > 20 && coxinhaLevel < 45)
            return MORTADELA;

        else if (coxinhaLevel <= 20)
            return SUPER_MORTADELA;

        else // 45 >= coxinhaLevel <= 55
            return COXINHA_DE_MORTADELA;

    }


}
