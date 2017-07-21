package com.lh_freeapps.coxinhaoumortadela.util;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Leandro on 07/05/2017.
 *
 * Class used for retrieving the hexadecimal value of the user's device that will represent the user ID in the database.
 * That number is unique for each android device
 *
 */

public final class DeviceIdUtility {

    public static long getSimplifiedDeviceID(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        // just get a simplified version of androidID
        String simplified = (androidID.length() > 6) ? androidID.substring(0, 5) : androidID;

        return hex2decimal(simplified);
    }


    private static long hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

}
