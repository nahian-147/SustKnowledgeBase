package com.example.sustknowledgebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sustknowledgebase.models.RetrofitManager;
import com.example.sustknowledgebase.networking.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        TextView tvWelcome = findViewById(R.id.tvWelcome);
//        tvWelcome.setText(token);
        ApiClient apiClient = retrofit.create(ApiClient.class);
        apiClient.getProfile(Collections.singletonMap("Authorization", "Token " + token)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        try {
                            JSONObject profile = new JSONObject(response.body());
                            JSONObject participantData = profile.getJSONObject("participant_data");
                            JSONArray teamList = profile.getJSONArray("teams");
                            String welcomeText = "Hi, " + participantData.get("full_name");
                            tvWelcome.setText(welcomeText);
                            StringBuilder teamInfo = new StringBuilder();
                            for (int i = 0; i < teamList.length(); i++) {
                                JSONObject team = teamList.getJSONObject(i);
                                teamInfo.append("Name: ").append(team.getString("name")).append("\n");
                                teamInfo.append("Year: ").append(team.getInt("year")).append("\n");
                                teamInfo.append("Course: ").append(team.getString("course")).append("\n");
                                teamInfo.append("Title: ").append(team.getString("project_thesis_title")).append("\n");
                                teamInfo.append("Status: ").append(team.getString("status")).append("\n\n");
                            }
                            tvProfile.setText(teamInfo.toString());

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Error parsing profile", Toast.LENGTH_SHORT).show();

                            throw new RuntimeException(e);
                        }
                    }
                } else if (response.code() == 449) {
//                    openActivityStReg();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.192:8080/api/register-role/"));
                    Bundle bundle = new Bundle();
                    bundle.putString("Authorization", "Token "+token);
                    browserIntent.putExtra(Browser.EXTRA_HEADERS, bundle);
                    startActivity(browserIntent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail in profile fretch", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void openActivityStReg () {
        startActivity(new Intent(this, StudentRegistration.class));
    }
}

