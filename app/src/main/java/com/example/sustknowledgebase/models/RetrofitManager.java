package com.example.sustknowledgebase.models;

import com.example.sustknowledgebase.networking.ApiClient;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManager {

    static final String base_url = "http://192.168.0.192:8080/";
    private static RetrofitManager retrofitManager = null;
    private static Retrofit retrofit;
    private static String token;

    private RetrofitManager(){
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public static RetrofitManager getRetrofitManagerSingletonInstance(){
        if (retrofitManager != null){
            return retrofitManager;
        }else{
            return new RetrofitManager();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
