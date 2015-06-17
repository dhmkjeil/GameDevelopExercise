package com.example.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import com.example.game.system.ApplicationData;

public class MenuActivity extends Activity {

    Button startButton, exitButton, settingButton;
    SharedPreferences settings;
    private ApplicationData applicationData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        startButton = (Button) findViewById(R.id.startButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        settingButton = (Button) findViewById(R.id.settingButton);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        applicationData = (ApplicationData) getApplicationContext();

        if (settings.getBoolean(SettingPreference.BGM_SOUND_ON, true)) {
            if (applicationData.menuBGM == null) {
                applicationData.menuBGM = MediaPlayer.create(MenuActivity.this, R.raw.menu_bgm);
            }
        } else {
            if (applicationData.menuBGM != null && applicationData.menuBGM.isPlaying()) {
                applicationData.menuBGM.stop();
            }
            applicationData.menuBGM = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (applicationData.menuBGM != null && !applicationData.menuBGM.isPlaying()) {
            applicationData.menuBGM.start();
            applicationData.menuBGM.setLooping(true);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, LevelActivity.class));
                finish();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, SettingPreference.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuActivity.this);
        alertDialog.setMessage(R.string.check_exit);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                applicationData.menuBGM.stop();
                applicationData.menuBGM = null;
                finish();
            }
        });
        alertDialog.setNegativeButton(R.string.no, null);
        alertDialog.show();
    }
}
