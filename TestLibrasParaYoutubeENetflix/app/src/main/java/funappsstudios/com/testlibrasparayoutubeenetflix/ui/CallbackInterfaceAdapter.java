package funappsstudios.com.testlibrasparayoutubeenetflix.ui;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.unity3d.player.UnityPlayer;

/**
 * HAND TALK - A Translator Plataform from Spoken and
 * Written Languages to Sign languages.
 * http://www.handtalk.me
 *
 * Created by carloswanderlan on 3/6/17.
 */

public class CallbackInterfaceAdapter extends YouTubeBaseActivity {

    protected static OnUnityListener mOUL;

    static String TAG = "TAG";

    public static void _HandTalkInit() {
        try {
            Log.i(TAG,"_HandTalkInit() called | mOUL: " + mOUL);
            if (mOUL != null) {
                mOUL.OnUnityStarted();
                Log.i(TAG,"mOUL.OnUnityStarted();");
            }
        } catch (Exception localException) {
            Log.e(TAG, "_HandTalkInit ERROR: " + localException.getMessage());
        }
    }

    public static void _carregandoDados() {
        try {
            if (mOUL != null) {
                mOUL.OnLoadingDatas();
            }
        } catch (Exception localException) {
            Log.e(TAG, "_carregandoDados ERROR: " + localException.getMessage());
        }
    }

    public static void _dadosCarregados() {
        try {
            if (mOUL != null) {
                mOUL.OnDataLoaded();
            }
        } catch (Exception localException) {
            Log.e(TAG, "_dadosCarregados ERROR: " + localException.getMessage());
        }
    }

    public static void _fimAnimacao() {
        try {
            if (mOUL != null) {
                mOUL.OnAnimationEnd();
            }
        } catch (Exception localException) {
            Log.e(TAG, "_fimAnimacao ERROR: " + localException.getMessage());
        }
    }

    public static void _stopAnimacao() {
        try {
            if (mOUL != null) {
                mOUL.OnStopAnimation();
            }
        } catch (Exception localException) {
            Log.e(TAG, "_stopAnimacao ERROR: " + localException.getMessage());
        }
    }

    private static void _hideShareButton() { }
    private static void _saveInHistory(String paramString1, String paramString2) {}
    private static void _showShareButton() {}

    public void setUserID(String token) {
        UnityPlayer.UnitySendMessage("Apresentacao", "setUserUID", token);
        Log.i(TAG, "Hand Talk uid: " + token);
    }

    public static void playHugo (String phrase){
        try {
            Log.i(TAG,"phrase played: "+phrase);
            UnityPlayer.UnitySendMessage("Apresentacao", "PlayFrase", phrase);
        }catch (Exception e){
            Log.e(TAG,"phrase: "+phrase+" | playHugo error: "+e.getMessage());
        }
    }

    public interface OnUnityListener {
        void OnAnimationEnd();
        void OnDataLoaded();
        void OnLoadingDatas();
        void OnStopAnimation();
        void OnUnityStarted();
    }
}