package funappsstudios.com.testlibrasparayoutubeenetflix.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Leandro on 12/06/2017.
 */

public interface Service {

    String BASE_URL =  "http://video.google.com/";

    @GET("timedtext")
    Call<ResponseBody> getYouTubeCaption(@QueryMap Map<String, String> options);

}
