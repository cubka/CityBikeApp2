package com.example.ivana.citybikeapp2.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.example.ivana.citybikeapp2.R;

public class CheckNetwork  {


        public static Boolean CheckInternetCon (Context context) {
    try {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeConnection = connectivityManager.getActiveNetworkInfo();

        if (activeConnection == null)
            throw new Exception();
        if (!activeConnection.isConnectedOrConnecting())
            throw new Exception();
        return true;
    } catch (Exception ex) {

        return false;
    }
}

}
