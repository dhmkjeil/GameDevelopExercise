package com.example.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.game.select.SelectLevelAdapter;
import com.example.game.system.ApplicationData;

public class LevelActivity extends Activity {
    ApplicationData applicationData;
    SharedPreferences settings;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level);
        applicationData = (ApplicationData) getApplicationContext();
        gridView = (GridView) findViewById(R.id.levelGridView);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (settings.getBoolean(SettingPreference.BGM_SOUND_ON, true)) {
            try {
                if (!applicationData.menuBGM.isPlaying()) {
                    applicationData.menuBGM.start();
                    applicationData.menuBGM.setLooping(true);
                }
            } catch (NullPointerException ex) {
                if (applicationData.menuBGM == null) {
                    applicationData.menuBGM = MediaPlayer.create(LevelActivity.this, R.raw.menu_bgm);
                }
                applicationData.menuBGM.start();
                applicationData.menuBGM.setLooping(true);
            }
        }

        gridView.setAdapter(new SelectLevelAdapter(LevelActivity.this, 3));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (applicationData.menuBGM != null && applicationData.menuBGM.isPlaying()) {
                    applicationData.menuBGM.stop();
                }
                applicationData.menuBGM = null;
                Intent intent = new Intent(LevelActivity.this, BattleActivity.class);
                intent.putExtra(BattleActivity.LEVEL_NUMBER, position + 1);
                startActivityForResult(intent, BattleActivity.BATTLE_ACT);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LevelActivity.this);
        alertDialog.setMessage(R.string.back_to_menu);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(LevelActivity.this, MenuActivity.class));
                finish();
            }
        });
        alertDialog.setNegativeButton(R.string.no, null);
        alertDialog.show();
    }
}
