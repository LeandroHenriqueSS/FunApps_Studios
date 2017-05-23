package com.lh_freeapps.coxinhaoumortadela.util;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by L. G.
 * http://stackoverflow.com/questions/4131656/how-to-make-button-highlight/14278790#14278790
 *
 * Adapted by Leandro Henrique
 *
 */

public class ButtonHighlighterOnTouchListener implements View.OnTouchListener {

    private static final int TRANSPARENT_GREY = Color.argb(0, 185, 185, 185);
    private static final int FILTERED_GREY = Color.argb(155, 185, 185, 185);

    public boolean onTouch(final View view, final MotionEvent motionEvent) {

        if (view instanceof ImageView) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ((ImageButton) view).setColorFilter(FILTERED_GREY, PorterDuff.Mode.SRC_ATOP);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                ((ImageButton) view).setColorFilter(TRANSPARENT_GREY, PorterDuff.Mode.SRC_ATOP);
            }
        }

        if (view instanceof Button) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                view.getBackground().setColorFilter(FILTERED_GREY, PorterDuff.Mode.SRC_ATOP);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getBackground().setColorFilter(TRANSPARENT_GREY, PorterDuff.Mode.SRC_ATOP);
            }
        }

        return false;
    }

}
