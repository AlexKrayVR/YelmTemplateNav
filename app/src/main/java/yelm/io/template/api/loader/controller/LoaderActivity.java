package yelm.io.template.api.loader.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import yelm.io.template.api.loader.settings.SharedPreferencesSetting;
import yelm.io.template.databinding.ActivityLoaderBinding;

public class LoaderActivity extends AppCompatActivity {
    ActivityLoaderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferencesSetting.initSharedPreferencesSettings(this);

    }
}
