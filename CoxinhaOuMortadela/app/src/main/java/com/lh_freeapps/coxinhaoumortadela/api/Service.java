package com.lh_freeapps.coxinhaoumortadela.api;

import com.lh_freeapps.coxinhaoumortadela.data.model.GlobalData;
import com.lh_freeapps.coxinhaoumortadela.data.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Leandro on 04/05/2017.
 */

public interface Service {

    String BASE_URL = "http://funappsstudios.host22.com/rest/package/ctrl/";

    @GET("{ctrlGlobalData}")
    Call<GlobalData> getGlobalData(@Path("ctrlGlobalData") String ctrl);

    @POST("CtrlUser.php")
    Call<String> postUserData(@Body User userData);

}
