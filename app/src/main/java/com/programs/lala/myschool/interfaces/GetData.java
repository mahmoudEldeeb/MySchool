package com.programs.lala.myschool.interfaces;

import com.programs.lala.myschool.modeld.ResultAccountModel;
import com.programs.lala.myschool.modeld.ResultPostModel;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by melde on 6/12/2017.
 */

public interface GetData {
    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("job") String job,
            @Field("type") String type,
            @Field("children") String children);
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> login(
            @Field("email") String email,
            @Field("password") String password
      );


    @GET("getposts.php")
    Call<ResultPostModel> getPosts(
    );
    @FormUrlEncoded
    @POST("getAccounts.php")
    Call<ResultAccountModel> getAccounts(
            @Field("type") String type
    );


    @GET("getLocation.php")
    Call<ResponseBody> getLocationBus(
    );
    @FormUrlEncoded
    @POST("updateLocation.php")
    Call<ResponseBody> sendLocation(
            @Field("longtiude") double longitude,
            @Field("latitude") double latitude
    );
    @FormUrlEncoded
    @POST("sendMessage.php")
    Call<ResponseBody> sendMessage(
            @Field("senderId") String senderId,
            @Field("receiveId") String receiveId,
            @Field("message") String message
    );
    @POST("updateToken.php")
    Call<ResponseBody> updateToken(
            @Field("id") String id,
            @Field("token") String token
    );

}
