package com.example.game.thread;

import android.content.Context;
import android.graphics.*;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.example.game.R;
import com.example.game.system.*;

import java.util.ArrayList;
import java.util.Random;

public class BattleThread extends Thread {

    public final static int IN_GAME = 10;
    public final static int STAGE_CLEAR = 20;
    public final static int GAME_OVER = 30;
    public int status = IN_GAME;

    SurfaceHolder holder;
    Context context;
    public StageClear stageClear;
    public GameOver gameOver;

    private int width, height;
    public int enemyNumber;
    private long lastFireTime;
    private Bitmap background;
    private boolean isStopBattle = false;
    private boolean isPauseBattle = false;

    ArrayList<Shell> playerShells = new ArrayList<Shell>();
    ArrayList<Shell> enemiesShells = new ArrayList<Shell>();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    public Player player;

    public BattleThread(SurfaceHolder holder, Context context, int enemyNumber) {
        this.holder = holder;
        this.context = context;
        this.enemyNumber = enemyNumber;
        Display display = ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        background = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.sea);
        background = Bitmap.createScaledBitmap(background, width, height, false);

        stageClear = new StageClear(context, background);
        gameOver = new GameOver(context, background);
        player = new Player(context, width, height);
        makeEnemy(this.enemyNumber);
        status = IN_GAME;
    }

    public void makePlayerShell(double touchX, double touchY) {
        long fireTime = System.currentTimeMillis();
        if (fireTime - lastFireTime >= 3000) {
            playerShells.add(new Shell(context, player.playerX, player.playerY, width, height, touchX, touchY));
            lastFireTime = fireTime;
        }
    }


    public void makeEnemy(int enemyNumber) {
        this.enemyNumber = enemyNumber;
        Random randomPosition = new Random();
        for (int i = 0; i < enemyNumber; i++) {
            enemies.add(new Enemy(context, randomPosition.nextInt(width), randomPosition.nextInt(height), width, height));
        }
    }

    public void makeEnemyShell() {
        for (Enemy enemy : enemies) {
            enemy.makeShell(enemy.enemyX, enemy.enemyY, player.playerX, player.playerY, enemiesShells);
        }
    }

    public void moveItems() {

        for (int i = playerShells.size() - 1; i >= 0; i--) {
            playerShells.get(i).moveShell();
            if (playerShells.get(i).dead) playerShells.remove(i);
        }

        for (int i = enemiesShells.size() - 1; i >= 0; i--) {
            enemiesShells.get(i).moveShell();
            if (enemiesShells.get(i).dead) enemiesShells.remove(i);
        }

        player.movePlayer();

        for (int i = enemies.size() - 1; i >= 0; i--) {
            enemies.get(i).moveEnemy();
            if (enemies.get(i).dead) enemies.remove(i);
        }
    }

    public void checkEnemyHit() {
        double tempShellX, tempEnemyX, tempShellY, tempEnemyY;
        for (Shell shell : playerShells) {
            tempShellX = shell.shellX;
            tempShellY = shell.shellY;
            for (Enemy enemy : enemies) {
                tempEnemyX = enemy.enemyX;
                tempEnemyY = enemy.enemyY;
                if (Math.abs(tempEnemyX - tempShellX) < enemy.enemyW && Math.abs(tempEnemyY - tempShellY) < enemy.enemyH) {
                    if (enemy.hitPoint > 1) {
                        enemy.hitPoint -= 1;
                    } else {
                        enemy.dead = true;
                    }
                    shell.dead = true;
                    break;
                }
            }
        }
    }

    public void checkPlayerHit() {
        double tempShellX, tempShellY;
        for (Shell shell : enemiesShells) {
            tempShellX = shell.shellX;
            tempShellY = shell.shellY;
            if (Math.abs(player.playerX - tempShellX) < player.playerW && Math.abs(player.playerY - tempShellY) < player.playerH) {
                if (player.playerHitPoint > 1) {
                    player.playerHitPoint -= 1;
                } else {
                    status = GAME_OVER;
                }
                shell.dead = true;
                break;
            }
        }
    }

    public void checkBoatCrash() {
        double tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY;

        for (int main = 0; main < enemies.size() - 1; main++) {
            Enemy mainEnemy = enemies.get(main);
            tempMainEnemyX = mainEnemy.enemyX;
            tempMainEnemyY = mainEnemy.enemyY;
            for (int target = main + 1; target < enemies.size(); target++) {
                Enemy targetEnemy = enemies.get(target);
                tempTargetEnemyX = targetEnemy.enemyX;
                tempTargetEnemyY = targetEnemy.enemyY;

                if (Math.abs(tempMainEnemyX - tempTargetEnemyX) < (mainEnemy.enemyW + targetEnemy.enemyW) && Math.abs(tempMainEnemyY - tempTargetEnemyY) < (mainEnemy.enemyH + targetEnemy.enemyH)) {
                    if (!mainEnemy.crashed) {
                        mainEnemy.crash(targetEnemy);
                    }
                }
            }
        }

        for (Enemy enemy : enemies) {
            if (Math.abs(player.playerX - enemy.enemyX) < (player.playerW + enemy.enemyW) && Math.abs(player.playerY - enemy.enemyY) < (player.playerH + enemy.enemyH)) {
                if (!player.crashed) {
                    player.crash(enemy);
                }
            }
        }
    }

    public void drawItems(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        for (Enemy enemy : enemies) {
            canvas.drawBitmap(enemy.enemy, (int) (enemy.enemyX - enemy.enemyW), (int) (enemy.enemyY - enemy.enemyH), null);
        }

        for (Shell shell : enemiesShells) {
            canvas.drawBitmap(shell.shell, (int) (shell.shellX - shell.shellW), (int) (shell.shellY - shell.shellH), null);
        }

        for (Shell shell : playerShells) {
            canvas.drawBitmap(shell.shell, (int) (shell.shellX - shell.shellW), (int) (shell.shellY - shell.shellH), null);
        }

        canvas.drawBitmap(player.playerBoat, (int) (player.playerX - player.playerW), (int) (player.playerY - player.playerH), null);
        player.moveTrack(canvas);
    }

    public void run() {
        Canvas canvas = null;
        while (!isStopBattle) {
            canvas = holder.lockCanvas();

            synchronized (this) {
                if (isPauseBattle) {
                    try {
                        wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                synchronized (holder) {
                    switch (status) {
                        case STAGE_CLEAR:
                            stageClear.setClear(canvas);
                            break;
                        case GAME_OVER:
                            gameOver.setOver(canvas);
                            break;
                        case IN_GAME:
                            makeEnemyShell();
                            checkBoatCrash();
                            moveItems();
                            checkEnemyHit();
                            checkPlayerHit();
                            drawItems(canvas);
                            break;
                    }
                }
            } finally {
                if (enemies.size() == 0 && player.playerHitPoint > 0) {
                    status = STAGE_CLEAR;
                }
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void stopBattle() {
        isStopBattle = true;
        synchronized (this) {
            this.notify();
        }
    }

    public void pauseBattle(boolean pause) {
        isPauseBattle = pause;
        synchronized (this) {
            this.notify();
        }
    }
}
