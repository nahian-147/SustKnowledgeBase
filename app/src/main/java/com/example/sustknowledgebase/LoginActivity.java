package com.example.sustknowledgebase;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sustknowledgebase.models.AuthToken;
import com.example.sustknowledgebase.models.LoggeInUserAuthToken;
import com.example.sustknowledgebase.models.UserProfile;
import com.example.sustknowledgebase.networking.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername,etPassword;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.tietUsername);
        etPassword = findViewById(R.id.tiePassword);
        TextView tvSignUp = findViewById(R.id.tvSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (validaInputs(username, password)){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.0.192:8080/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();

                    ApiClient apiClient = retrofit.create(ApiClient.class);

                    apiClient.getAuthToken(username, password).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                LoggeInUserAuthToken user = new LoggeInUserAuthToken();
                                try {
                                    JSONObject token = new JSONObject(response.body());
                                    user.setToken(token.getString("token"));
//                                    Toast.makeText(LoginActivity.this, "Welcome " + token.getString("token"), Toast.LENGTH_LONG).show();
                                    apiClient.getProfile(Collections.singletonMap("Authorization", "Token "+token.getString("token"))).enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.isSuccessful()){
                                                try {
                                                    JSONObject profile = new JSONObject(response.body());
                                                    Toast.makeText(LoginActivity.this, profile.toString(), Toast.LENGTH_LONG).show();
                                                } catch (JSONException e) {
                                                    Toast.makeText(LoginActivity.this, "Error parsing profile", Toast.LENGTH_SHORT).show();
                                                    tvSignUp.setText(e.toString());

                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Toast.makeText(LoginActivity.this, "Fail in profile fretch", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    finish();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            tvSignUp.setText(t.toString());
                        }
                    });
                }
            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

    private boolean validaInputs(String username, String password) {

        if (username.isEmpty()){
            Toast.makeText(this, getString(R.string.username_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()){
            Toast.makeText(this, getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
