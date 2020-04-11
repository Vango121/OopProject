package com.example.Marcel.oop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        PreferenceManage preferenceManage = new PreferenceManage();
        getSupportFragmentManager().beginTransaction().replace(R.id.Layout,new PreferenceManage()).commit();
        //preferenceManage.getFragmentManager().beginTransaction().replace(R.id.Layout,preferenceManage).commit();
    }
}
