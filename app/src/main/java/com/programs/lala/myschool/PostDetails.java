package com.programs.lala.myschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.programs.lala.myschool.modeld.PostModel;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PostDetails extends AppCompatActivity {

    @InjectView(R.id.titleDetails) TextView titleDetails;
    @InjectView(R.id.descriptionDetail)
    TextView descriptionDetail;
    PostModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        ButterKnife.inject(this);

        model= (PostModel) getIntent().getSerializableExtra("postModel");
        titleDetails.setText(model.getTitile());
        descriptionDetail.setText(model.getDescription());

    }
}
