package com.programs.lala.myschool;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.programs.lala.myschool.modeld.AccountModel;
import com.programs.lala.myschool.modeld.MessageModel;
import com.programs.lala.myschool.modeld.PostModel;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Profile extends AppCompatActivity {
AccountModel model;

    @InjectView(R.id.message)
    ImageButton message;
    @InjectView(R.id.accountProfileName)
    TextView accountProfileName;

    @InjectView(R.id.accountProfilrJob) TextView accountProfilrJob;
    @InjectView(R.id.accountProfileAge)
    TextView accountProfileAge;
    @InjectView(R.id.accountProfileImage)
    ImageView accountProfileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        model= (AccountModel) getIntent().getSerializableExtra("accountModel");
        accountProfileName.setText(model.getName());
        accountProfilrJob.setText(model.getJob());
        accountProfileAge.setText(model.getAge());
        try {
            Picasso.with(this)
                    .load(String.valueOf(this.getString(R.string.BAS_URL) + model.getImage())).fit()
                    .into(accountProfileImage);
        }
        catch (Exception E){}
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= getBaseContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                        .getString("id", "0");
                MessageModel messageModel=new MessageModel();
                messageModel.setReceiver_id(model.getId());
                messageModel.setName(model.getName());
                messageModel.setSender_id(id);
                Intent intent = new Intent(Profile.this, ChatRoom.class);
                intent.putExtra("messageModel", messageModel);

                startActivity(intent);
            }
        });
    }
}
