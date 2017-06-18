package com.programs.lala.myschool.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.programs.lala.myschool.adapter.AccountsAdapter;
import com.programs.lala.myschool.R;
import com.programs.lala.myschool.assest.Check;
import com.programs.lala.myschool.interfaces.GetData;
import com.programs.lala.myschool.modeld.AccountModel;
import com.programs.lala.myschool.modeld.ResultAccountModel;

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
public class Accounts extends Fragment {

    RecyclerView recyclerView;
    AccountsAdapter adapter;
    List<AccountModel> accountList = new ArrayList<>();

    public Accounts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View row = inflater.inflate(R.layout.fragment_stuff, container, false);


        recyclerView = (RecyclerView) row.findViewById(R.id.stuff_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (new Check(getContext()).isNetworkAvailable()) {
            getAccounts();
        } else
            Toast.makeText(getContext(), getText(R.string.no_network), Toast.LENGTH_SHORT).show();

        return row;
    }

    public void getAccounts() {
        String type = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getString("type", "parent");
        if (type.equals("parent")) {
            type = "staff";

        } else {
            type = "parent";
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BAS_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Call<ResultAccountModel> connection;
        GetData acounts = retrofit.create(GetData.class);

        connection = acounts.getAccounts(type);
        connection.enqueue(new Callback<ResultAccountModel>() {
            @Override
            public void onResponse(Call<ResultAccountModel> call, Response<ResultAccountModel> response) {
                try {
                    accountList = response.body().getAcount();

                    adapter = new AccountsAdapter(getContext(), accountList);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(getContext(), getText(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResultAccountModel> call, Throwable t) {
                Toast.makeText(getContext(), getText(R.string.something_wrong), Toast.LENGTH_SHORT).show();


            }
        });


    }
}
