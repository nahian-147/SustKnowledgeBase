package com.example.sustknowledgebase.networking;

import com.example.sustknowledgebase.models.AuthToken;
import com.example.sustknowledgebase.models.UserProfile;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;


public interface ApiClient {
    static final String base_url = "http://192.168.0.192:8080/";
    public static Retrofit retrofit = null;

    String token = null;

    @FormUrlEncoded
    @POST("api-token-auth/")
    Call<String> getAuthToken(@Field("username") String username, @Field("password") String password);

    @GET("api/profile/")
    Call<String> getProfile(@HeaderMap Map<String, String> headers);
}
