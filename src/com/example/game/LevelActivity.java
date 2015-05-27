package com.example.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.game.select.SelectLevelAdapter;

public class LevelActivity extends Activity {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level);
        gridView = (GridView) findViewById(R.id.levelGridView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gridView.setAdapter(new SelectLevelAdapter(LevelActivity.this, 3));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LevelActivity.this, BattleActivity.class);
                intent.putExtra(BattleActivity.ENEMY_NUMBER, position + 1);
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
