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

    public int enemyNumber, totalScore;

    private int width, height, leftEnemyNumber;
    private long lastFireTime, enemyRegenTime;
    private boolean isStopBattle = false;
    private boolean isPauseBattle = false;
    private Bitmap background;
    private Bitmap life;
    private Bitmap leftEnemy;
    private Paint paint;

    ArrayList<Shell> playerShells = new ArrayList<Shell>();
    ArrayList<Shell> enemiesShells = new ArrayList<Shell>();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<Score> scores = new ArrayList<Score>();
    ArrayList<Long> enemyRegen = new ArrayList<Long>();
    ArrayList<CrashEffect> crashEffects = new ArrayList<CrashEffect>();

    public Player player;
    private Reload reload;

    public BattleThread(SurfaceHolder holder, Context context, int enemyNumber) {
        this.holder = holder;
        this.context = context;
        this.enemyNumber = enemyNumber;
        Display display = ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        background = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.sea);
        background = Bitmap.createScaledBitmap(background, width, height, false);
        life = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.player_hitpoint);
        leftEnemy = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.enemy);
        leftEnemy = Bitmap.createScaledBitmap(leftEnemy, 65, 30, false);

        totalScore = 0;
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);

        stageClear = new StageClear(context, background);
        gameOver = new GameOver(context, background);
        player = new Player(context, width, height);
        reload = new Reload(this.context);

        reload.reloadTime = player.reloadTime;
        leftEnemyNumber = enemyNumber * 5;
        enemyRegenTime = 5000;

        makeEnemy();
        status = IN_GAME;
    }

    public void makePlayerShell(double touchX, double touchY) {
        long fireTime = System.currentTimeMillis();
        if (fireTime - lastFireTime >= (player.reloadTime * 1000)) {
            playerShells.add(new Shell(context, player.playerX, player.playerY, width, height, touchX, touchY));
            lastFireTime = fireTime;
        }
    }

    public void makeEnemy() {
        Random randomPosition = new Random();
        for (int i = 0; i < enemyNumber; i++) {
            switch (leftEnemyNumber % 4) {
                case 0:
                    enemies.add(new Enemy(context, randomPosition.nextInt(width), randomPosition.nextInt((int) (player.playerY - (player.playerH * 3))), width, height));
                    break;
                case 1:
                    enemies.add(new Enemy(context, randomPosition.nextInt(Assist.randomNumIntRangeOverZero((int) (player.playerX + (player.playerW * 3)), width)), randomPosition.nextInt(height), width, height));
                    break;
                case 2:
                    enemies.add(new Enemy(context, randomPosition.nextInt(width), randomPosition.nextInt(Assist.randomNumIntRangeOverZero((int) (player.playerY + (player.playerH * 3)), height)), width, height));
                    break;
                case 3:
                    enemies.add(new Enemy(context, randomPosition.nextInt(Assist.randomNumIntRangeOverZero(0, (int) (player.playerX - (player.playerW * 3)))), randomPosition.nextInt(height), width, height));
                    break;
            }
            leftEnemyNumber -= 1;
        }
    }

    public void checkEnemyRegen() {
        if (enemyRegen.size() > 0) {
            for (int i = 0; i < enemyRegen.size(); i++) {
                if ((enemies.size() == 0 && System.currentTimeMillis() - enemyRegen.get(i) > 1000) || System.currentTimeMillis() - enemyRegen.get(i) > enemyRegenTime) {
                    Random randomPosition = new Random();
                    int randomY = 0, randomX = 0;
                    switch (leftEnemyNumber % 4) {
                        case 0:
                            if (player.playerY - (player.playerH * 3) > 0) {
                                randomY = (int) (player.playerY - (player.playerH * 3));
                            }
                            enemies.add(new Enemy(context, randomPosition.nextInt(width), randomPosition.nextInt(randomY), width, height));
                            break;
                        case 1:
                            randomX = player.playerX + (player.playerW * 3) > width ? width : (int) (player.playerX + (player.playerW * 3));
                            enemies.add(new Enemy(context, randomPosition.nextInt(Assist.randomNumIntRangeOverZero(randomX, width)), randomPosition.nextInt(height), width, height));
                            break;
                        case 2:
                            randomY = player.playerY + (player.playerH * 3) > height ? height : (int) (player.playerY + (player.playerH * 3));
                            enemies.add(new Enemy(context, randomPosition.nextInt(width), randomPosition.nextInt(Assist.randomNumIntRangeOverZero(randomY, height)), width, height));
                            break;
                        case 3:
                            if (player.playerX - (player.playerW * 3) > 0) {
                                randomX = (int) (player.playerX - (player.playerW * 3));
                            }
                            enemies.add(new Enemy(context, randomPosition.nextInt(randomX), randomPosition.nextInt(height), width, height));
                            break;
                    }
                    enemyRegen.remove(i);
                    leftEnemyNumber -= 1;
                }
            }
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

        for (int i = scores.size() - 1; i >= 0; i--) {
            scores.get(i).moveScore();
            if (scores.get(i).isMoveFinish()) scores.remove(i);
        }

        for (int i = crashEffects.size() - 1; i >= 0; i--) {
            crashEffects.get(i).makeEffect();
            if (crashEffects.get(i).isFinish) crashEffects.remove(i);
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
                        scores.add(new Score(enemy.enemyX, enemy.enemyY - enemy.enemyH, 50));
                        totalScore += 50;
                    } else {
                        enemy.dead = true;
                        scores.add(new Score(enemy.enemyX, enemy.enemyY - enemy.enemyH, 100));
                        totalScore += 100;
                        if (leftEnemyNumber > 0) {
                            enemyRegen.add(System.currentTimeMillis());
                        }
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
                if (player.hitPoint > 1) {
                    player.hitPoint -= 1;
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
        int crashEffectCount = 20;

        for (int main = 0; main < enemies.size() - 1; main++) {
            Enemy mainEnemy = enemies.get(main);
            tempMainEnemyX = mainEnemy.enemyX;
            tempMainEnemyY = mainEnemy.enemyY;
            for (int target = main + 1; target < enemies.size(); target++) {
                Enemy targetEnemy = enemies.get(target);
                tempTargetEnemyX = targetEnemy.enemyX;
                tempTargetEnemyY = targetEnemy.enemyY;

                if (Math.abs(tempMainEnemyX - tempTargetEnemyX) < (mainEnemy.enemyW + targetEnemy.enemyW) && Math.abs(tempMainEnemyY - tempTargetEnemyY) < (mainEnemy.enemyH + targetEnemy.enemyH)) {
                    mainEnemy.crash(targetEnemy);
                    for (int i = 0; i < crashEffectCount; i++) {
                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                    }
                }
            }
        }

        for (Enemy enemy : enemies) {
            if (Math.abs(player.playerX - enemy.enemyX) < (player.playerW + enemy.enemyW) && Math.abs(player.playerY - enemy.enemyY) < (player.playerH + enemy.enemyH)) {
                player.crash(enemy);
                player.getDiagonal();
                for (int i = 0; i < crashEffectCount; i++) {
                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, enemy.enemyX, enemy.enemyY));
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

        for (Score score : scores) {
            canvas.drawText("+" + score.score, (float) score.positionX, (float) score.positionY, score.paint);
        }

        for (int i = 0; i < player.hitPoint; i++) {
            canvas.drawBitmap(life, (i * life.getWidth()) + (i * 10) + 10, 10, null);
        }

        reload.isReloading(lastFireTime);
        if (reload.isReloading) {
            canvas.drawBitmap(reload.reloadBitmap, width - (reload.reloadBitmap.getWidth() + 10), 10, null);
        }

        canvas.drawBitmap(leftEnemy, 10, height - (10 + leftEnemy.getHeight()), null);
        canvas.drawText("x" + leftEnemyNumber, leftEnemy.getWidth() + 20, height - 10, paint);
        canvas.drawText("Score : " + totalScore, 10, 100, paint);
        canvas.drawBitmap(player.playerBoat, (int) (player.playerX - player.playerW), (int) (player.playerY - player.playerH), null);
        player.moveTrack(canvas);

        for (CrashEffect crashEffect : crashEffects) {
            canvas.drawBitmap(crashEffect.crashEffect, (int) crashEffect.location.x, (int) crashEffect.location.y, null);
        }
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
                            stageClear.setClear(canvas, totalScore);
                            break;
                        case GAME_OVER:
                            gameOver.setOver(canvas, totalScore);
                            break;
                        case IN_GAME:
                            makeEnemyShell();
                            checkBoatCrash();
                            checkEnemyHit();
                            checkPlayerHit();
                            moveItems();
                            checkEnemyRegen();
                            drawItems(canvas);
                            break;
                    }
                }
            } finally {
                if (leftEnemyNumber == 0 && enemies.size() == 0 && player.hitPoint > 0) {
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
