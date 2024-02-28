package com.example.sustknowledgebase;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sustknowledgebase.models.RetrofitManager;
import com.example.sustknowledgebase.networking.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitManager retrofitManager = RetrofitManager.getRetrofitManagerSingletonInstance();
        Retrofit retrofit = retrofitManager.getRetrofit();
        String token = retrofitManager.getToken();
        Toast Toast = null;
        TextView tvProfile = findViewById(R.id.tvProfile);
        TextView tvToken = findViewById(R.id.tvToken);
//        tvToken.setText(token);
        ApiClient apiClient = retrofit.create(ApiClient.class);
        apiClient.getProfile(Collections.singletonMap("Authorization", "Token "+token)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject profile = new JSONObject(response.body());
                        Toast.makeText(MainActivity.this, profile.toString(), Toast.LENGTH_LONG).show();
                        tvProfile.setText(profile.toString());
                        tvToken.setText(token);

                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, "Error parsing profile", Toast.LENGTH_SHORT).show();

                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail in profile fretch", Toast.LENGTH_SHORT).show();
            }
        });
    }
}