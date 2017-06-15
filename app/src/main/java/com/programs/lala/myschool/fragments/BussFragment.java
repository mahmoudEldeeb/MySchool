package com.programs.lala.myschool.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.programs.lala.myschool.R;
import com.programs.lala.myschool.assest.Check;
import com.programs.lala.myschool.interfaces.GetData;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class BussFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    FloatingActionButton fb;
    public BussFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_buss, container, false);
fb= (FloatingActionButton) view.findViewById(R.id.fb);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();

        fragment.getMapAsync(this);
fb.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        if (new Check(getContext()).isNetworkAvailable()){
            getLocation();}
        else    Toast.makeText(getContext(),getText(R.string.no_network) , Toast.LENGTH_SHORT).show();

    }
});

        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //goToLocationZoom(30.044452, 31.235512, 17);
        getLocation();
    }

    private void goToLocationZoom(double lat, double lng,float z) {
        LatLng li=new LatLng(lat,lng);
        MarkerOptions mar=new MarkerOptions()
                .title((String) getText(R.string.buss))
                .position(li);
        mGoogleMap.addMarker(mar);
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(li,z);
        mGoogleMap.moveCamera(update);
    }
    public void getLocation(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BAS_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Call<ResponseBody> connection;
        GetData getData = retrofit.create(GetData.class);

        connection = getData.getLocationBus();
        connection.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                String RegisterResult = null;
                try {
                    RegisterResult = response.body().string();
                    JSONObject jso = new JSONObject(RegisterResult);
                    String longitude=jso.getString("longitude");

                    String latitude=jso.getString("latitude");
                    goToLocationZoom(Double.parseDouble(latitude),Double.parseDouble(longitude),17);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),getText(R.string.something_wrong) , Toast.LENGTH_SHORT).show();
            }
        });

    }
}
