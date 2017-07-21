package funappsstudios.com.testlibrasparayoutubeenetflix.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.unity3d.player.UnityPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.handtalk.androidlib.HandTalkSDK;
import funappsstudios.com.testlibrasparayoutubeenetflix.R;
import funappsstudios.com.testlibrasparayoutubeenetflix.api.Service;
import funappsstudios.com.testlibrasparayoutubeenetflix.model.data.Caption;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

//TODO: fazer tela landscape
public class HugoActivity extends CallbackInterfaceAdapter implements CallbackInterfaceAdapter.OnUnityListener, YouTubePlayer.OnInitializedListener {

    protected UnityPlayer mUnityPlayer;
    protected Handler handler;
    protected String textToTranslate = "teste";
    protected boolean touchoutsideToExit;
    protected int typeOfWindow;

//    protected RelativeLayout loaderRl;


    protected HandTalkSDK htsdk;
    protected Context ctx;

//    private YouTubePlayerView youtubeView;

    private List<Caption> captions;

    static final String TAG = "Hand Talk Debug ->";

    private static final String API_KEY  = "AIzaSyCE6hryP1c5yBrmZHJa4ixxaru54VQ_65c";

    private static final String VIDEO_ID = "cPbl26Fw-dk";

    private int ind = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hugo);

//        youtubeView = (YouTubePlayerView) findViewById(youtube);
//        youtubeView.initialize(API_KEY, this);



        // create retrofit
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .build();

        // create callback
        Service service = retrofit.create(Service.class);

        Map<String, String> data = new HashMap<>();
        data.put("lang","pt");
        data.put("v",VIDEO_ID);

        Call<ResponseBody> callCaption = service.getYouTubeCaption(data);

//        callCaption.enqueue(new Callback<ResponseBody>() {
//
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("Hugo Activity", "Peguei caption");
//                try {
//
//                    captions = XmlToCaptionsConverter.convert(response.body().string());
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    if (response.errorBody() != null) {
//                        System.out.println(response.errorBody().string());
//                    }
//
//                } catch (IOException e) {
//                    System.out.println("error");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                System.out.println("deu merda");
//                t.printStackTrace();
//            }
//        });

        ctx = this;
        mOUL = this;

        handler = new Handler();

        setupUnity();
//        setConfigPopUp();

    }

    private void dismissActivity(){
        onBackPressed();
    }

    private void setConfigPopUp() {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            getWindow().setLayout((int)(width/1.3), (int)(height/1.5));
        } catch (Exception e) {
            Log.e(TAG, "setConfigPopUp() : " + e.getMessage());
        }


    }

    public void setupUnity() {

        try {
            getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy
            mUnityPlayer = new UnityPlayer(this);

//            if (mUnityPlayer.getSettings().getBoolean("hide_status_bar", true)) {
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            }

            View playerView = mUnityPlayer.getView();

//            FrameLayout layout = (FrameLayout) findViewById(br.com.handtalk.androidlib.R.id.frameUnity);
            FrameLayout layout = (FrameLayout) findViewById(R.id.frameUnity);
//            layout.addView(playerView, getWindow().getAttributes().width, getWindow().getAttributes().height);
            layout.addView(playerView, 800, 800);
//            mUnityPlayer.requestFocus();


            //added
//            setFinishOnTouchOutside(true); // not working
            mUnityPlayer.setKeepScreenOn(true);

        } catch (Exception e) {
            Log.e(TAG, "setupUnity() : " + e.getMessage());
        }
    }

    //Override METHODS

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    // Quit Unity
    @Override protected void onDestroy () {
        mUnityPlayer.quit();
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }


    // Pause Unity
    @Override protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();
        Log.i(TAG, "onPause()");

    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUnityPlayer.refreshDrawableState();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUnityPlayer.pause();
        Log.i(TAG, "onStop()");
    }


    @Override
    protected void onResume() {
        super.onResume();
        mUnityPlayer.resume();
        Log.i(TAG, "onResume()");
    }



    //UNITY CALLBACK FUCNTIONS
    public void OnAnimationEnd() {
        Log.i(TAG, "OnAnimationEnd()");

        if (captions != null) {
            textToTranslate = captions.get(ind).getText();
            ind++;
        }

        playHugo(textToTranslate);


        mUnityPlayer.setContentDescription("a");

    }

    public void OnDataLoaded() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "OnDataLoaded()");
            }
        }, 300);
    }

    public void OnLoadingDatas() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "OnLoadingDatas()");
            }
        });
    }
    public void OnStopAnimation() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "OnStopAnimation()");
            }
        });
    }

    public void OnUnityStarted() {

//        Log.i(TAG, "OnUnityStarted()");

        mUnityPlayer.requestFocus();
        String token = "2cabf73683233b2b8c9b295315d799a7";
        UnityPlayer.UnitySendMessage("Apresentacao", "setUserUID", token);

//        if (!textToTranslate.isEmpty()) {
//            playHugo(textToTranslate);
//        } else {
//            Log.e(TAG, "The 'textToTranslate' variable is empty!");
//        }

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Log.d("HugoActivity", "Success");
        youTubePlayer.cueVideo(VIDEO_ID);
        youTubePlayer.play();
        youTubePlayer.setPlayerStateChangeListener(new YoutubeStateListener(this));

    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("HugoActivity", youTubeInitializationResult.getDeclaringClass().getName());
    }


    public void startHugo() {
        if (captions != null) {
            textToTranslate = captions.get(ind).getText();
            ind++;
        }

        playHugo(textToTranslate);
    }

}