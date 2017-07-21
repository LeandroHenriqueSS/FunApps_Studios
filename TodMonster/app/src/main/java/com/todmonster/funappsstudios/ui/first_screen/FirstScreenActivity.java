package com.todmonster.funappsstudios.ui.first_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.todmonster.funappsstudios.R;
import com.todmonster.funappsstudios.ui.game.GameActivity;
import com.todmonster.funappsstudios.ui.settings.SettingsActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.bt_start)
    public void startGame(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    @OnClick(R.id.bt_record)
    public void openRecord(View view) {
        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.bt_settings)
    public void openSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }


}
