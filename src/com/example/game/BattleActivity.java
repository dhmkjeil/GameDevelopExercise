package com.example.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.game.thread.BattleThread;

public class BattleActivity extends Activity implements SurfaceHolder.Callback {
    public final static int BATTLE_ACT = 1000;

    public static int STATE_FLAG = 0;

    public final static int BOAT_MOVING = 1;
    public final static int BOAT_SHOOTING = 2;

    public final static int IN_GAME = 10;
    public final static int STAGE_CLEAR = 20;
    public final static int GAME_OVER = 30;

    public final static String ENEMY_NUMBER = "ENEMY_NUMBER";

    long beforeTouchTime = 0, nowTouchTime = 0;

    SurfaceView battleFieldSurface;
    SurfaceHolder surfaceHolder;
    Intent intent;
    static BattleThread battleThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);
        intent = getIntent();

        battleFieldSurface = (SurfaceView) findViewById(R.id.battleFieldSurface);
        surfaceHolder = battleFieldSurface.getHolder();
        surfaceHolder.addCallback(this);

        battleThread = new BattleThread(surfaceHolder, BattleActivity.this, intent.getIntExtra(ENEMY_NUMBER, 1));
    }

    @Override
    public void onBackPressed() {
        pauseBattle();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BattleActivity.this);
        alertDialog.setMessage(R.string.finish_battle);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopBattle();
                finish();
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resumeBattle();
            }
        });
        alertDialog.show();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        battleThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean done = true;
        while (done) {
            try {
                battleThread.join();
                done = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int nowTouchX = (int) event.getX();
        int nowTouchY = (int) event.getY();

        if (battleThread.status == IN_GAME) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (isBoatRange(nowTouchX, nowTouchY)) {
                    STATE_FLAG = BOAT_MOVING;
                    beforeTouchTime = System.currentTimeMillis();
                } else {
                    STATE_FLAG = BOAT_SHOOTING;
                }
            }
        } else {
            STATE_FLAG = battleThread.status == STAGE_CLEAR ? STAGE_CLEAR : battleThread.status == GAME_OVER ? GAME_OVER : 0;
        }

        if (STATE_FLAG == BOAT_MOVING) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    battleThread.player.destinationX = nowTouchX;
                    battleThread.player.destinationY = nowTouchY;
                    battleThread.player.isDestination = false;
                    break;
                case MotionEvent.ACTION_UP:
                    nowTouchTime = System.currentTimeMillis();
                    battleThread.player.crashed = false;
                    battleThread.player.wayPointX = nowTouchX;
                    battleThread.player.wayPointY = nowTouchY;
                    battleThread.player.getSpeed(beforeTouchTime, nowTouchTime);
                    battleThread.player.getDiagonal();
                    STATE_FLAG = 0;
                    break;
            }
        }

        if (STATE_FLAG == BOAT_SHOOTING) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    battleThread.makePlayerShell(nowTouchX, nowTouchY);
                    break;
            }
        }

        if (STATE_FLAG == STAGE_CLEAR) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isStageClearButtonRange(nowTouchX, nowTouchY)) {
                        stopBattle();
                        finish();
                    }
                    break;
            }
        }

        if (STATE_FLAG == GAME_OVER) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    switch (gameOverButton(nowTouchX, nowTouchY)) {
                        case 1:
                            stopBattle();
                            finish();
                            break;
                        case 2:
                            restartGame();
                            break;
                    }
            }
        }

        return true;
    }

    public boolean isBoatRange(int x, int y) {
        if ((battleThread.player.playerX - (battleThread.player.playerW + 25)) < x && x < (battleThread.player.playerX + (battleThread.player.playerW + 25))) {
            if ((battleThread.player.playerY - (battleThread.player.playerH + 25)) < y && y < (battleThread.player.playerY + (battleThread.player.playerH + 25))) {
                return true;
            }
        }
        return false;
    }

    public boolean isStageClearButtonRange(int x, int y) {
        if (battleThread.stageClear.listButtonX < x && x < battleThread.stageClear.listButtonX + battleThread.stageClear.listButtonW) {
            if (battleThread.stageClear.listButtonY < y && y < battleThread.stageClear.listButtonY + battleThread.stageClear.listButtonH) {
                return true;
            }
        }
        return false;
    }

    public int gameOverButton(int x, int y) {
        if (battleThread.gameOver.listButtonX < x && x < battleThread.gameOver.listButtonX + battleThread.gameOver.listButtonW) {
            if (battleThread.gameOver.listButtonY < y && y < battleThread.gameOver.listButtonY + battleThread.gameOver.listButtonH) {
                return 1;
            }
        }
        if (battleThread.gameOver.restartButtonX < x && x < battleThread.gameOver.restartButtonX + battleThread.gameOver.restartButtonW) {
            if (battleThread.gameOver.restartButtonY < y && y < battleThread.gameOver.restartButtonY + battleThread.gameOver.restartButtonH) {
                return 2;
            }
        }
        return 0;
    }

    public static void stopBattle() {
        battleThread.stopBattle();
    }

    public static void pauseBattle() {
        battleThread.pauseBattle(true);
    }

    public static void resumeBattle() {
        battleThread.pauseBattle(false);
    }

    public void restartGame() {
        stopBattle();
        battleThread = null;
        battleThread = new BattleThread(surfaceHolder, BattleActivity.this, intent.getIntExtra(ENEMY_NUMBER, 1));
        battleThread.start();
    }
}
