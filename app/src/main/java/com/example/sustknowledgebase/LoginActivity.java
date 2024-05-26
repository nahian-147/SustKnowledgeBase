package com.example.sustknowledgebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sustknowledgebase.models.RetrofitManager;
import com.example.sustknowledgebase.networking.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                if (validateInputs(username, password)){


                    ApiClient apiClient = RetrofitManager.getRetrofitManagerSingletonInstance().getRetrofit().create(ApiClient.class);

                    apiClient.getAuthToken(username, password).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                if (response.code() == 200) {
                                    try {
                                        JSONObject token = new JSONObject(response.body());
                                        RetrofitManager retrofitManager = RetrofitManager.getRetrofitManagerSingletonInstance();
                                        retrofitManager.setToken(token.getString("token"));
                                        Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                                        openActivityMain();
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.192:8080/register/"));
                startActivity(browserIntent);            }
        });

    }

    private boolean validateInputs(String username, String password) {

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

    public void openActivityMain(){
        startActivity(new Intent(this, MainActivity.class));
    }

}
