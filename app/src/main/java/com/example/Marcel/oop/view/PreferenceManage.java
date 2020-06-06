package com.example.Marcel.oop.view;

import android.os.Bundle;

import com.example.Marcel.oop.R;

import androidx.preference.PreferenceFragmentCompat;

/**
 * Class that load settings xml
 */
public class PreferenceManage extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settingspreference,rootKey);
    }
}
