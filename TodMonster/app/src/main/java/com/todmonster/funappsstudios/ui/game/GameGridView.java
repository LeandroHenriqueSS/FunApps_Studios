package com.todmonster.funappsstudios.ui.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.todmonster.funappsstudios.R;
import com.todmonster.funappsstudios.model.scenario.Location;
import com.todmonster.funappsstudios.model.scenario.TodMonster;
import com.todmonster.funappsstudios.ui.settings.Preferences;

import java.util.List;


public class GameGridView extends View {

    // grid 10x6
    public static int NUM_VERTICAL_CELLS   = 10;
    public static int NUM_HORIZONTAL_CELLS = 06;
    public static final int CELL_BORDER_PX       = 05;

    private volatile List<TodMonster> todMonsters;
    private Bitmap goodTod;
    private Bitmap badTod;

    private boolean penalty;
    private TodMonster monsterPenalty;


    public GameGridView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);

        SharedPreferences pref = getContext().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        if (pref.getBoolean(Preferences.SMALL_SCREENS.name(), false)) {
            NUM_HORIZONTAL_CELLS--;
            NUM_VERTICAL_CELLS--;
        }

    }


    public void setTodMonsters(List<TodMonster> todMonsters) {
        this.todMonsters = todMonsters;
    }


    public void setPenalty(boolean penalty, TodMonster monsterPenalty) {
        this.penalty = penalty;
        this.monsterPenalty = monsterPenalty;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        float thickness = (right/(float)NUM_HORIZONTAL_CELLS) - 3*CELL_BORDER_PX;

        goodTod = getResizedBitmap(thickness, thickness, R.drawable.good_tod);
        badTod  = getResizedBitmap(thickness, thickness, R.drawable.bad_tod );
    }

    /** @see android.view.View#onDraw(android.graphics.Canvas) */
    @Override
    protected void onDraw(final Canvas canvas) {

        final Paint paint = new Paint();
        paint.setStyle(Style.FILL_AND_STROKE);
        paint.setColor(Color.rgb(48, 67, 135));

        SharedPreferences pref = getContext().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        if (pref.getBoolean(Preferences.DARK_THEME.name(), false))
            paint.setColor(Color.BLACK);


        float vertThickness = canvas.getHeight()/(float)(NUM_VERTICAL_CELLS  );
        float horiThickness = canvas.getWidth() /(float)(NUM_HORIZONTAL_CELLS);
        int offset = 0;


        // draw horizontal lines
        for (int i = 0; i < (NUM_VERTICAL_CELLS + 1); i++) {

            // for bug fix on some devices
            if (i == NUM_VERTICAL_CELLS)
                offset = 1;

            canvas.drawLine(
                    /*startX:*/ 0,
                    /*startY:*/ i*vertThickness - offset,
                    /*stopX: */ (NUM_VERTICAL_CELLS + 1)*horiThickness,
                    /*stopY: */ i*vertThickness - offset,
                    paint);
        }


        offset = 0;


        // draw vertical lines
        for (int i = 0; i < (NUM_HORIZONTAL_CELLS + 1); i++) {

            // for bug fix on some devices
            if (i == NUM_HORIZONTAL_CELLS )
                offset = 1;


            canvas.drawLine(
                    /*startX:*/ i*horiThickness - offset,
                    /*startY:*/ 0,
                    /*stopX: */ i*horiThickness - offset,
                    /*stopY: */ (NUM_VERTICAL_CELLS + 1)*vertThickness,
                    paint);
        }

        for (TodMonster tm : todMonsters) {

            int left   = (int) (tm.getColumn() *horiThickness                 + CELL_BORDER_PX);
            int top    = (int) (tm.getLine()   *vertThickness                 + CELL_BORDER_PX);
            int right  = (int) (tm.getColumn() *horiThickness + horiThickness - CELL_BORDER_PX);
            int bottom = (int) (tm.getLine()   *vertThickness + vertThickness - CELL_BORDER_PX);

            tm.setLocation(new Location(left, top , right, bottom));


            //TODO: ele tá ficando vermelho atrás mesmo depois dele se mover
            if (penalty && tm.equals(monsterPenalty)) {
                penalty = false;

                paint.setStyle(Style.FILL_AND_STROKE);
                paint.setColor(Color.RED);
                canvas.drawRect(left, top, right, bottom, paint);
            }


            if (tm.isVulnerable()) {
                canvas.drawBitmap(badTod, left, top, paint);
            } else {
                canvas.drawBitmap(goodTod,  left, top, paint);
            }


        }

    }

    private Bitmap getResizedBitmap(float newWidth, float newHeight, int id) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), id);

        int width  = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth  = newWidth/width;
        float scaleHeight = newHeight/height;

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


}
