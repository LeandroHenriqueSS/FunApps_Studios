package com.lh_freeapps.coxinhaoumortadela.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Get a recommended text size depending on the screen density
 *
 * Created by Leandro on 21/04/2017.
 */
public final class TextSizeUtility {


    public static int getRecommendedTextSize(Context context) {

        int result;

        switch (context.getResources().getConfiguration().densityDpi) {
            case 120: // ldpi
                result = 19;
                break;
            case 160: // mdpi
                result = 20;
                break;
            case 240: // hdpi
                result = 21;
                break;
            case 320: // xhdpi
                result = 22;
                break;
            case 480: // xxhdpi
                result = 23;
                break;
            case 640: // xxxhdpi
                result = 24;
                break;
            default:
                result = 20;
                break;
        }

        return result;

    }

}
