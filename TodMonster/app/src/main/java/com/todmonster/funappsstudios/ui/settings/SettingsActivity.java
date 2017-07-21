package com.todmonster.funappsstudios.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.todmonster.funappsstudios.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.rv_settings) RecyclerView recyclerView;

    private List<String> settings = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);

        if (pref.getBoolean(Preferences.DARK_THEME.name(), false))
            setTheme(R.style.AppThemeDark);

        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        SettingsAdapter sAdapter = new SettingsAdapter(this, Settings.getSettingsList());
        recyclerView.setAdapter(sAdapter);





    }
}
