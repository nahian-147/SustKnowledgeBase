package com.example.sustknowledgebase.networking;

import com.example.sustknowledgebase.models.AuthToken;
import com.example.sustknowledgebase.models.StudentInfo;
import com.example.sustknowledgebase.models.UserProfile;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;


public interface ApiClient {

    @FormUrlEncoded
    @POST("api-token-auth/")
    Call<String> getAuthToken(@Field("username") String username, @Field("password") String password);

    @GET("api/profile/")
    Call<String> getProfile(@HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST("api/register-role/")
    Call<String> postStudentInfo(@HeaderMap Map<String, String> headers,
                                 @Field("role_id") String roleID,
                                 @Field("student_info") String studentInfo);
}
