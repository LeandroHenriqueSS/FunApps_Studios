package androidlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
//import android.support.design.internal.NavigationMenuItemView;
//import android.support.design.internal.NavigationMenuView;
//import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.handtalk.androidlib.HugoActivity;
import br.com.handtalk.androidlib.R;

import static androidlib.Constants.Configurations.TAG;

/**
 * HAND TALK - A Translator Plataform from Spoken and
 * Written Languages to Sign languages.
 * http://www.handtalk.me
 *
 * Created by carloswanderlan on 3/6/17.
 */

@SuppressWarnings({"JavaDoc", "WeakerAccess"})
public class HandTalkSDK {

    @SuppressWarnings("WeakerAccess")
    public static class HTWindowType {
        private static final int HUGO_WINDOW_FLOAT_CENTERED = 10;
        public static final int HUGO_WINDOW_FLOAT_LEFT_BOTTOM = 11;
        public static final int HUGO_WINDOW_FLOAT_RIGHT_BOTTOM = 12;
        public static final int HUGO_WINDOW_FULL = 13;
    }

    @SuppressWarnings("WeakerAccess")
    public static class HTAnimationType {
        public static final int HUGO_ANIME_SLIDE_TO_LEFT = 14;
        public static final int HUGO_ANIME_SLIDE_TO_RIGHT = 15;
        public static final int HUGO_ANIME_SLIDE_FROM_LEFT = 16;
        public static final int HUGO_ANIME_SLIDE_FROM_RIGHT = 17;
    }


    @SuppressLint("StaticFieldLeak")
    private static HandTalkSDK mInstance = null;
    private static String currentClassName;

    private Context context;
    private String texttotranslate;
    private int windowtype = HTWindowType.HUGO_WINDOW_FLOAT_CENTERED;
    private boolean touchableToExit = false;

    @SuppressWarnings("FieldCanBeLocal")
    private int animation = HTAnimationType.HUGO_ANIME_SLIDE_FROM_RIGHT;

    private String _lastTitleMenuItemSelected = null;


    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            showConfirmDialog();
            Log.i(TAG, "_lastTitleMenuItemSelected: " + _lastTitleMenuItemSelected);
            return false;
        }
    };


    private HandTalkSDK(){}

    @SuppressWarnings("WeakerAccess")
    public static synchronized HandTalkSDK getInstance(Context ctx){


        if(null == mInstance || !currentClassName.equals(ctx.getClass().getName())){
            mInstance = new HandTalkSDK(ctx);
            currentClassName = ctx.getClass().getName();
            Log.i(TAG,"###### Different ActivityClass: "+ctx.getClass().getName());

        }else{
            Log.i(TAG,"###### Same ActivityClass: "+ctx.getClass().getName());
        }
        return mInstance;
    }

    /**
     * Init the Hand Talk SDK, you need to pass the Context
     * @param ctx
     */
    private HandTalkSDK(Context ctx) {
        context = ctx;
        turnAllElementsSelectable();
    }

    /**
     * Set if the user can touch outside window to finish Hugo's window.
     * @param flag
     */
    @SuppressWarnings("WeakerAccess")
    public void setTouchableToExit(boolean flag) {
        touchableToExit = flag;
    }

    /**
     * Here you will set up the animation type for window entering
     * @param anime
     */
    @SuppressWarnings("WeakerAccess")
    public void setAnimation(int anime) {
        animation = anime;
    }

    /**
     * Set the text will be translated by Hugo! ;)
     * @param str
     */
    @SuppressWarnings("WeakerAccess")
    public void setTextToTranslate(String str){
        texttotranslate = str;
    }

    /**
     * Set the way of the window will be showed.
     * @param type
     */
    @SuppressWarnings("WeakerAccess")
    public void setWindowType(int type){
        windowtype = type;
    }

    /**
     * This method will start and show the Hugo's Window and init the translation.
     */

    public void showHugo(){

        try {
            Intent intent = new Intent(context, HugoActivity.class);
            intent.putExtra("HT_StringToTranslate", texttotranslate);
            intent.putExtra("HT_TouchableToExit", touchableToExit);
            intent.putExtra("HT_WindowType", windowtype);
            context.startActivity(intent);

//            if (animation == HTAnimationType.HUGO_ANIME_SLIDE_FROM_LEFT) {
//                Log.i(TAG,"HUGO_ANIME_SLIDE_FROM_LEFT run");
//                ((Activity) context).overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right);
//            }else if (animation == HTAnimationType.HUGO_ANIME_SLIDE_FROM_RIGHT) {
//                Log.i(TAG,"HUGO_ANIME_SLIDE_FROM_RIGHT run");
//                ((Activity) context).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_from_left);
//            }

        }catch (Exception e){
            Log.e(TAG,"error: openPopUpWindow -> "+e.getMessage());
        }
    }

    public void showUXTutorial(){
        Intent intent = new Intent(context,TutorialSDK.class);
        context.startActivity(intent);
    }

    public void setToken(String token){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("HandTalkSDKToken",token).apply();
    }

    private void showConfirmDialog(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_confirm_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.ht_txt_dialog);
        text.setText(R.string.handtalk_label_confirm_dialog);

        Button dialogButtonY = (Button) dialog.findViewById(R.id.ht_btn_yes);
        dialogButtonY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextToTranslate(_lastTitleMenuItemSelected);
                showHugo();
                dialog.dismiss();
            }
        });

        Button dialogButtonN = (Button) dialog.findViewById(R.id.ht_btn_no);
        dialogButtonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public int getIconhandTalkResouces(){
        return R.drawable.ic_sign_language;
    }

    @SuppressWarnings("WeakerAccess")
    public void turnAllElementsSelectable(){
        ArrayList<Integer> ids = new ArrayList<>();
        ViewGroup viewGroup = (ViewGroup) ((Activity) context).getWindow().getDecorView();
//        findAllViews(viewGroup, ids);

    }

//    private void findAllViews(ViewGroup viewGroup,ArrayList<Integer> ids) {
//        for (int i = 0, n = viewGroup.getChildCount(); i < n; i++) {
//
//            View child = viewGroup.getChildAt(i);
//
//            //NAVIGATION VIEW - MENU VIEW
//            if((viewGroup instanceof NavigationView) && (child instanceof NavigationMenuView)) {
//
//                NavigationMenuView nmv = (NavigationMenuView) child;
//
//                for (int t = 0, p = nmv.getChildCount(); t < p; t++) {
//
//                    View childMenu = nmv.getChildAt(t);
//
//                    if (childMenu instanceof NavigationMenuItemView) {
//
//                        final NavigationMenuItemView item = (NavigationMenuItemView) childMenu;
//                        _lastTitleMenuItemSelected = item.getItemData().getTitle().toString();
//                        item.setOnLongClickListener(onLongClickListener);
//
//                    } else if (childMenu instanceof TextView) {
//
//                        TextView txt = (TextView) childMenu;
//                        makeViewIdElementToSelectableMode(txt);
//
//                    }
//                }
//
//
//            //BUTTONS
//            }else if(child instanceof Button){
//                Button bt = (Button) child;
//                _lastTitleMenuItemSelected = bt.getText().toString();
//                bt.setOnLongClickListener(onLongClickListener);
//
//
//                //ANY OTHER ITEMS
//            } else if (child instanceof ViewGroup) {
//                findAllViews((ViewGroup) child, ids);
//
//            } else {
//                if (child instanceof TextView) {
//                    TextView txt2 = (TextView) child;
//                    makeViewIdElementToSelectableMode(txt2);
//                }
//            }
//        }
//    }

    private void makeViewIdElementToSelectableMode(final TextView v) {

        v.setTextIsSelectable(true);

        v.setCustomSelectionActionModeCallback(new android.view.ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {
                ((Activity) context).getMenuInflater().inflate(R.menu.menu_translate_handtalk, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {
                //menu.removeItem(android.R.id.selectAll);
                //menu.removeItem(android.R.id.cut);
                //menu.removeItem(android.R.id.copy);
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                int i = item.getItemId();

                if (i == R.id.menu_item_translate) {

                    if (v.isFocused()) {

                        final int selStart = v.getSelectionStart();
                        final int selEnd = v.getSelectionEnd();

                        int min = Math.max(0, Math.min(selStart, selEnd));
                        int max = Math.max(0, Math.max(selStart, selEnd));
                        String selectedText = String.valueOf(v.getText().subSequence(min, max));

                        // Perform your definition lookup with the selected text
                        setTextToTranslate(selectedText);
                        setWindowType(HTWindowType.HUGO_WINDOW_FLOAT_RIGHT_BOTTOM);
                        setAnimation(HTAnimationType.HUGO_ANIME_SLIDE_TO_LEFT);
                        setTouchableToExit(true);
                        showHugo();
                    }

                    mode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode actionMode) {

            }
        });
    }
}
