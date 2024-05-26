package com.example.sustknowledgebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sustknowledgebase.models.RetrofitManager;
import com.example.sustknowledgebase.models.StudentInfo;
import com.example.sustknowledgebase.networking.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StudentRegistration extends AppCompatActivity {

    private EditText etFName,etRegNo,etDept;
    private TextView tvFName,tvRegNo,tvDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        etFName = findViewById(R.id.etFName);
        etRegNo = findViewById(R.id.etRegNo);
        etDept = findViewById(R.id.etDept);
        tvFName = findViewById(R.id.tvFName);
        tvRegNo = findViewById(R.id.tvRegNo);
        tvDept = findViewById(R.id.tvDept);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fName = etFName.getText().toString();
                Integer regNo = Integer.parseInt(etRegNo.getText().toString());
                String dept = etDept.getText().toString();

                if (validateInputs(fName, regNo, dept)){

                    RetrofitManager retrofitManager = RetrofitManager.getRetrofitManagerSingletonInstance();
                    Retrofit retrofit = retrofitManager.getRetrofit();
                    String token = retrofitManager.getToken();
                    ApiClient apiClient = retrofit.create(ApiClient.class);

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("full_name", fName);
                        jsonObject.put("registration", regNo);
                        jsonObject.put("department", dept);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    String studentInfo = jsonObject.toString();
                    apiClient.postStudentInfo(Collections.singletonMap("Authorization", "Token "+token),"STUDENT", studentInfo).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()){
                                if (response.code() == 200) {
                                    Toast.makeText(StudentRegistration.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(StudentRegistration.this, "Error", Toast.LENGTH_SHORT).show();
//                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.192:8080/api/register-role/"));
//                                Bundle bundle = new Bundle();
//                                bundle.putString("Authorization", "Token "+token);
//                                browserIntent.putExtra(Browser.EXTRA_HEADERS, bundle);
//                                startActivity(browserIntent);
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(StudentRegistration.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean validateInputs(String fName, Integer reg, String dept) {

        if (fName.isEmpty()){
            Toast.makeText(this, "Please Give your Full Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (reg < 2016331000){
            Toast.makeText(this, "Registration Number is a must !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dept.isEmpty()){
            Toast.makeText(this, "What is Your Department ?", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}