package com.example.ivana.citybikeapp2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.ivana.citybikeapp2.adapter.ListAdapter;
import com.example.ivana.citybikeapp2.api.RestApi;
import com.example.ivana.citybikeapp2.models.NetworksModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class BikeCompanyList extends AppCompatActivity {

    RestApi api;
    ListAdapter listAdapter;
    @BindView(R.id.list_rec_view)
    RecyclerView recyclerView;
    NetworksModel networksModels;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_company_list);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        api = new RestApi(this);
        networksModels = new NetworksModel();
        listAdapter = new ListAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(listAdapter);

        loadList();


    }

    public void loadList() {

        retrofit2.Call<NetworksModel> call = api.getNetList();
        call.enqueue(new Callback<NetworksModel>() {
            @Override
            public void onResponse(retrofit2.Call<NetworksModel> call, Response<NetworksModel> response) {
                if (response.isSuccessful()) {

                    networksModels = response.body();
                    listAdapter.setNetworksList(BikeCompanyList.this,networksModels.getNetworks());

                    recyclerView.setAdapter(listAdapter);

                }
            }

            @Override
            public void onFailure(retrofit2.Call<NetworksModel> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.action_search);


        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                listAdapter.getFilter().filter(query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(BikeCompanyList.this, Settings.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        }
    }

    }










