package funappsstudios.com.testlibrasparayoutubeenetflix.ui;

import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;

/**
 * Created by Leandro on 12/06/2017.
 */

public class YoutubeStateListener implements YouTubePlayer.PlayerStateChangeListener {

    private final String TAG = this.getClass().getSimpleName();

    private HugoActivity hugo;

    public YoutubeStateListener(HugoActivity hugo) {
        this.hugo = hugo;
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {
        Log.d(TAG, "Video Started");
        hugo.startHugo();

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}
