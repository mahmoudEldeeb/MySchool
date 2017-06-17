package com.programs.lala.myschool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.programs.lala.myschool.interfaces.GetData;
import com.programs.lala.myschool.services.FCMRegistrationService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    @InjectView(R.id.loginEmail)
    EditText loginEmail;
    @InjectView(R.id.loginPassword)
    EditText loginPassword;
    @InjectView(R.id.registerText)
    TextView registerText;
    @InjectView(R.id.error)
    TextView error;
    @InjectView(R.id.logiBbutton)
    Button logiBbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.inject(this);
        declareSharedPrefrenceValues();
        boolean logined = getBaseContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getBoolean("logined", false);
        if (logined) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }

        logiBbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginEmail.getText().toString().isEmpty() && !loginPassword.getText().toString().isEmpty()) {
                    login();
                } else {
                    error.setText(R.string.fill_all_record);
                    error.setVisibility(View.VISIBLE);
                }

            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    public void login() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BAS_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Call<ResponseBody> connection;
        GetData loginObgect = retrofit.create(GetData.class);

        connection = loginObgect.login(loginEmail.getText().toString(), loginPassword.getText().toString());
        connection.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                String RegisterResult = null;
                try {
                    RegisterResult = response.body().string();
                    JSONObject jso = new JSONObject(RegisterResult);
                    String succee = jso.getString("succes");
                    if (succee.equals("true")) {


                        SharedPreferences.Editor editor = getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit();
                        editor.putString("id", jso.getString("type"));
                        editor.putString("type", "staff");
                        editor.putBoolean("logined", true);
                        editor.commit();
                        startService(new Intent(Login.this, FCMRegistrationService.class));

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        error.setVisibility(View.VISIBLE);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), getText(R.string.something_wrong), Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void declareSharedPrefrenceValues() {

        SharedPreferences.Editor editor = getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit();
        editor.putString("id", "0");
        editor.putString("job", "parent");
        editor.putString("type", "parent");
        editor.putBoolean("logined", false);
        editor.commit();
    }
}