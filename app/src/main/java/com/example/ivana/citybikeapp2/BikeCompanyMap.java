package com.example.ivana.citybikeapp2;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.ivana.citybikeapp2.models.Networks;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BikeCompanyMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Networks networkMap;
    String company_name;
    String company_city;
    String country_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_company_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        networkMap = (Networks) getIntent().getSerializableExtra("NAME");
        company_name = networkMap.getName();
        company_city = networkMap.getCity();
        country_code = networkMap.getCountry().toUpperCase();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng company = new LatLng(networkMap.getLan(), networkMap.getLong());
        mMap.addMarker(new MarkerOptions().position(company).title(" Company: "+ company_name +
               ", " + "City: " +  company_city +  ", " + "Country: " +  country_code));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(company));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(BikeCompanyMap.this, BikeCompanyList.class);
        startActivity(i);
        finish();
    }
}
