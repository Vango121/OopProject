package com.example.Marcel.oop;

import android.os.Bundle;

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
