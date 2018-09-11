package com.example.ivana.citybikeapp2;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.example.ivana.citybikeapp2.other.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Settings extends AppCompatActivity {

    @BindView(R.id.numberPicker)
    NumberPicker limit;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);


        i = PreferencesManager.getLimit(Settings.this);

       limit.setMaxValue(60);
       limit.setMinValue(15);
       limit.setWrapSelectorWheel(true);

       if (i != 0 ) {
           limit.setValue(i);
       }

       limit.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
           @Override
           public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

               i = newVal;
               PreferencesManager.setLimit(i, Settings.this);

           }
       });




        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Settings.this, BikeCompanyList.class);
        startActivity(i);
        finish();
    }
}
