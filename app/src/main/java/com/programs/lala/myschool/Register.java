package com.programs.lala.myschool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.programs.lala.myschool.interfaces.GetData;
import com.programs.lala.myschool.services.FCMRegistrationService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    @InjectView(R.id.name) EditText name;
    @InjectView(R.id.email) EditText email;
    @InjectView(R.id.password) EditText password;
    @InjectView(R.id.childrenName) EditText childrenName;
    @InjectView(R.id.register) Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();
            }
        });
    }
    public void register (){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BAS_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Call<ResponseBody> connection;
        GetData registerFunction = retrofit.create(GetData.class);

        connection = registerFunction.register(name.getText().toString(),email.getText().toString(),password.getText().toString(),"parent of ("+childrenName.getText().toString()+")","parent",childrenName.getText().toString());
        connection.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                String RegisterResult = null;
                try {
                    RegisterResult = response.body().string();
                    JSONObject jso = new JSONObject(RegisterResult);
                    String succee=jso.getString("succes");
                    if(succee.equals("true")){
                        SharedPreferences.Editor editor = getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit();
                        editor.putString("id", jso.getString("type"));
                        editor.putString("type", "parent of ("+childrenName.getText().toString()+")");
                        editor.putBoolean("logined", true);


                        startService(new Intent(Register.this, FCMRegistrationService.class));
                        Intent intent=new Intent(Register.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        String wrong =jso.getString("type");
                        if(wrong.equals("email")){
                            email.setTextColor(getResources().getColor(R.color.colorAccent));
                        email.setText(getString(R.string.wrong_email));}
                        else if(wrong.equals("child")){

                            childrenName.setTextColor(getResources().getColor(R.color.colorAccent));
                            childrenName.setText(getString(R.string.no_child_name));}
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Toast.makeText(getBaseContext(),getText(R.string.something_wrong) , Toast.LENGTH_SHORT).show();}




        });



    }
    }

