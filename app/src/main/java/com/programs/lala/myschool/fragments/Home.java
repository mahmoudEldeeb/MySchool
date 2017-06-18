package com.programs.lala.myschool.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.programs.lala.myschool.adapter.PostsAdapter;
import com.programs.lala.myschool.R;
import com.programs.lala.myschool.assest.Check;
import com.programs.lala.myschool.interfaces.GetData;
import com.programs.lala.myschool.modeld.PostModel;
import com.programs.lala.myschool.modeld.ResultPostModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    RecyclerView recyclerView;
    PostsAdapter adapter;
    List<PostModel> postsList = new ArrayList<>();

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View row = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = (RecyclerView) row.findViewById(R.id.posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (new Check(getContext()).isNetworkAvailable()) {
            getPosts();
        } else
            Toast.makeText(getContext(), getText(R.string.no_network), Toast.LENGTH_SHORT).show();

        return row;
    }


    public void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BAS_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Call<ResultPostModel> connection;
        GetData loginObgect = retrofit.create(GetData.class);

        connection = loginObgect.getPosts();
        connection.enqueue(new Callback<ResultPostModel>() {
            @Override
            public void onResponse(Call<ResultPostModel> call, Response<ResultPostModel> response) {
                postsList = response.body().getPost();

                adapter = new PostsAdapter(getContext(), postsList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResultPostModel> call, Throwable t) {
                Toast.makeText(getContext(), getText(R.string.something_wrong), Toast.LENGTH_SHORT).show();


            }
        });


    }


}
