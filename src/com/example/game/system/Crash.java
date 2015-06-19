package com.example.game.system;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ION on 2015-06-18.
 */
public class Crash {
    private Context context;

    public ArrayList<CrashEffect> crashEffects = new ArrayList<CrashEffect>();

    public Crash(Context context) {
        this.context = context;
    }

    public void enemyDetection(ArrayList<Enemy> enemies) {
        double tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY;
        int crashEffectCount = 20;

        for (int main = 0; main < enemies.size() - 1; main++) {
            Enemy mainEnemy = enemies.get(main);
            tempMainEnemyX = mainEnemy.enemyX;
            tempMainEnemyY = mainEnemy.enemyY;

            switch (mainEnemy.DIRECTION % 2) {
                case 0:
                    for (int target = main + 1; target < enemies.size(); target++) {
                        Enemy targetEnemy = enemies.get(target);
                        tempTargetEnemyX = targetEnemy.enemyX;
                        tempTargetEnemyY = targetEnemy.enemyY;

                        switch (targetEnemy.DIRECTION % 2) {
                            case 0:
                                if (Math.abs(tempMainEnemyX - tempTargetEnemyX) < (mainEnemy.enemyW + targetEnemy.enemyW) && Math.abs(tempMainEnemyY - tempTargetEnemyY) < (mainEnemy.enemyH + targetEnemy.enemyH)) {
                                    mainEnemy.crash(targetEnemy);
                                    for (int i = 0; i < crashEffectCount; i++) {
                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                    }
                                }
                                break;
                            case 1:
                                if (Math.abs(tempMainEnemyX - tempTargetEnemyX) < (mainEnemy.enemyW + targetEnemy.enemyW / 3) && Math.abs(tempMainEnemyY - tempTargetEnemyY) < (mainEnemy
                                        .enemyH + targetEnemy.enemyH / 3)) {
                                    mainEnemy.crash(targetEnemy);
                                    for (int i = 0; i < crashEffectCount; i++) {
                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                    }
                                    break;
                                }

                                switch (targetEnemy.DIRECTION) {
                                    case 1:
                                    case 5:
                                        if (Math.abs(tempMainEnemyX - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW + targetEnemy.enemyW / 2) && Math.abs(tempMainEnemyY -
                                                (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH + targetEnemy.enemyH / 2)) {
                                            mainEnemy.crash(targetEnemy);
                                            for (int i = 0; i < crashEffectCount; i++) {
                                                crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                            }
                                            break;
                                        }

                                        if (Math.abs(tempMainEnemyX - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW + targetEnemy.enemyW / 2) && Math.abs(tempMainEnemyY -
                                                (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH + targetEnemy.enemyH / 2)) {
                                            mainEnemy.crash(targetEnemy);
                                            for (int i = 0; i < crashEffectCount; i++) {
                                                crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                            }
                                            break;
                                        }
                                    case 3:
                                    case 7:
                                        if (Math.abs(tempMainEnemyX - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW + targetEnemy.enemyW / 2) && Math.abs(tempMainEnemyY -
                                                (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH + targetEnemy.enemyH / 2)) {
                                            mainEnemy.crash(targetEnemy);
                                            for (int i = 0; i < crashEffectCount; i++) {
                                                crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                            }
                                            break;
                                        }

                                        if (Math.abs(tempMainEnemyX - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW + targetEnemy.enemyW / 2) && Math.abs(tempMainEnemyY -
                                                (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH + targetEnemy.enemyH / 2)) {
                                            mainEnemy.crash(targetEnemy);
                                            for (int i = 0; i < crashEffectCount; i++) {
                                                crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                            }
                                            break;
                                        }
                                }
                        }
                    }
                    break;
                case 1:
                    for (int target = main + 1; target < enemies.size(); target++) {
                        Enemy targetEnemy = enemies.get(target);
                        tempTargetEnemyX = targetEnemy.enemyX;
                        tempTargetEnemyY = targetEnemy.enemyY;

                        switch (targetEnemy.DIRECTION % 2) {
                            case 0:
                                if (Math.abs(tempMainEnemyX - tempTargetEnemyX) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW) && Math.abs(tempMainEnemyY - tempTargetEnemyY) < (mainEnemy
                                        .enemyH / 3 + targetEnemy.enemyH)) {
                                    mainEnemy.crash(targetEnemy);
                                    for (int i = 0; i < crashEffectCount; i++) {
                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                    }
                                    break;
                                }
                            case 1:
                                // Main = Middle, Target = Middle;
                                if (Math.abs(tempMainEnemyX - tempTargetEnemyX) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW / 3) && Math.abs(tempMainEnemyY - tempTargetEnemyY) < (mainEnemy.enemyH
                                        / 3 + targetEnemy.enemyH / 3)) {
                                    mainEnemy.crash(targetEnemy);
                                    for (int i = 0; i < crashEffectCount; i++) {
                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                    }
                                    break;
                                }

                                switch (mainEnemy.DIRECTION) {
                                    case 1:
                                    case 5:
                                        // Main = Left_Top , Target = Middle
                                        if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2) - tempTargetEnemyX)) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW / 3) && Math.abs
                                                ((tempMainEnemyY - (mainEnemy.enemyH / 2) - tempTargetEnemyY)) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW / 3)) {
                                            mainEnemy.crash(targetEnemy);
                                            for (int i = 0; i < crashEffectCount; i++) {
                                                crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                            }
                                            break;
                                        }

                                        // Main = Right_Bottom, Target = Middle
                                        if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2) - tempTargetEnemyX)) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW / 3) && Math.abs
                                                ((tempMainEnemyY + (mainEnemy.enemyH / 2) - tempTargetEnemyY)) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW / 3)) {
                                            mainEnemy.crash(targetEnemy);
                                            for (int i = 0; i < crashEffectCount; i++) {
                                                crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                            }
                                            break;
                                        }

                                        switch (targetEnemy.DIRECTION) {
                                            case 1:
                                            case 5:
                                                // Main = Left_Top
                                                // Target = Left_Top
                                                if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2)) - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY - (mainEnemy.enemyH / 2)) - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Right_Bottom
                                                if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2)) - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY - (mainEnemy.enemyH / 2)) - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Main = Middle
                                                // Target = Left_Top
                                                if (Math.abs(tempMainEnemyX - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW / 2) && Math.abs
                                                        (tempMainEnemyY - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 3 + targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Right_Bottom
                                                if (Math.abs(tempMainEnemyX - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW / 2) && Math.abs
                                                        (tempMainEnemyY - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 3 + targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Main = Right_Bottom
                                                // Target = Left_Top
                                                if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2)) - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY + (mainEnemy.enemyH / 2)) - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Right_Bottom
                                                if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2)) - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY + (mainEnemy.enemyH / 2)) - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }
                                                break;

                                            case 3:
                                            case 7:
                                                // Main = Left_Top
                                                // Target = Right_Top
                                                if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2)) - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY - (mainEnemy.enemyH / 2)) - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Left_Bottom
                                                if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2)) - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY - (mainEnemy.enemyH / 2)) - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Main = Middle
                                                // Target = Right_Top
                                                if (Math.abs(tempMainEnemyX - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW / 2) && Math.abs
                                                        (tempMainEnemyY - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 3 + targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Left_Bottom
                                                if (Math.abs(tempMainEnemyX - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW / 2) && Math.abs
                                                        (tempMainEnemyY - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 3 + targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Main = Right_Bottom
                                                // Target = Right_Top
                                                if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2)) - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY + (mainEnemy.enemyH / 2)) - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Left_Bottom
                                                if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2)) - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY + (mainEnemy.enemyH / 2)) - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 3:
                                    case 7:
                                        // Main = Right_Top, Target = Middle
                                        if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2) - tempTargetEnemyX)) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW / 3) && Math.abs
                                                ((tempMainEnemyY - (mainEnemy.enemyH / 2) - tempTargetEnemyY)) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW / 3)) {
                                            mainEnemy.crash(targetEnemy);
                                            for (int i = 0; i < crashEffectCount; i++) {
                                                crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                            }
                                            break;
                                        }

                                        // Main = Left_Bottom, Target = Middle
                                        if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2) - tempTargetEnemyX)) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW / 3) && Math.abs
                                                ((tempMainEnemyY + (mainEnemy.enemyH / 2) - tempTargetEnemyY)) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW / 3)) {
                                            mainEnemy.crash(targetEnemy);
                                            for (int i = 0; i < crashEffectCount; i++) {
                                                crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                            }
                                            break;
                                        }

                                        switch (targetEnemy.DIRECTION) {
                                            case 1:
                                            case 5:
                                                // Main = Right_top
                                                // Target = Left_Top
                                                if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2)) - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY - (mainEnemy.enemyH / 2)) - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Right_Bottom
                                                if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2)) - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY - (mainEnemy.enemyH / 2)) - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Main = Middle
                                                // Target = Left_Top
                                                if (Math.abs(tempMainEnemyX - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW / 2) && Math.abs
                                                        (tempMainEnemyY - (tempTargetEnemyY - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyH / 3 + targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Right_Bottom
                                                if (Math.abs(tempMainEnemyX - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW / 2) && Math.abs
                                                        (tempMainEnemyY - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 3 + targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Main = Left_bottom
                                                // Target = Left_Top
                                                if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2)) - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY + (mainEnemy.enemyH / 2)) - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Right_Bottom
                                                if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2)) - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY + (mainEnemy.enemyH / 2)) - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }
                                                break;
                                            case 3:
                                            case 7:
                                                // Main = Right_top
                                                // Target = Right_top
                                                if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2)) - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY - (mainEnemy.enemyH / 2)) - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Left_bottom
                                                if (Math.abs((tempMainEnemyX + (mainEnemy.enemyW / 2)) - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY - (mainEnemy.enemyH / 2)) - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Main = Middle
                                                // Target = Right_top
                                                if (Math.abs(tempMainEnemyX - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW / 2) && Math.abs
                                                        (tempMainEnemyY - (tempTargetEnemyY - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyH / 3 + targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Left_bottom
                                                if (Math.abs(tempMainEnemyX - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 3 + targetEnemy.enemyW / 2) && Math.abs
                                                        (tempMainEnemyY - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 3 + targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Main = Left_bottom
                                                // Target = Right_top
                                                if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2)) - (tempTargetEnemyX + (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY + (mainEnemy.enemyH / 2)) - (tempTargetEnemyY - (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }

                                                // Target = Left_bottom
                                                if (Math.abs((tempMainEnemyX - (mainEnemy.enemyW / 2)) - (tempTargetEnemyX - (targetEnemy.enemyW / 2))) < (mainEnemy.enemyW / 2 + targetEnemy.enemyW
                                                        / 2) && Math.abs((tempMainEnemyY + (mainEnemy.enemyH / 2)) - (tempTargetEnemyY + (targetEnemy.enemyH / 2))) < (mainEnemy.enemyH / 2 +
                                                        targetEnemy.enemyH / 2)) {
                                                    mainEnemy.crash(targetEnemy);
                                                    for (int i = 0; i < crashEffectCount; i++) {
                                                        crashEffects.add(new CrashEffect(context, tempMainEnemyX, tempMainEnemyY, tempTargetEnemyX, tempTargetEnemyY));
                                                    }
                                                    break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                    }
            }
            break;
        }
    }

    public void playerDetection(Player player, ArrayList<Enemy> enemies) {
        double tempTargetEnemyX, tempTargetEnemyY;
        int crashEffectCount = 20;

        switch (player.DIRECTION % 2) {
            case 0:
                for (Enemy enemy : enemies) {
                    tempTargetEnemyX = enemy.enemyX;
                    tempTargetEnemyY = enemy.enemyY;

                    switch (enemy.DIRECTION % 2) {
                        case 0:
                            if (Math.abs(player.playerX - tempTargetEnemyX) < (player.playerW + enemy.enemyW) && Math.abs(player.playerY - tempTargetEnemyY) < (player.playerH + enemy.enemyH)) {
                                player.crash(enemy);
                                for (int i = 0; i < crashEffectCount; i++) {
                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                }
                            }
                            break;
                        case 1:
                            if (Math.abs(player.playerX - tempTargetEnemyX) < (player.playerW + enemy.enemyW / 3) && Math.abs(player.playerY - tempTargetEnemyY) < (player.playerH + enemy.enemyH / 3)) {
                                player.crash(enemy);
                                for (int i = 0; i < crashEffectCount; i++) {
                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                }
                                break;
                            }

                            switch (enemy.DIRECTION) {
                                case 1:
                                case 5:
                                    if (Math.abs(player.playerX - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW + enemy.enemyW / 2) && Math.abs(player.playerY - (tempTargetEnemyY -
                                            (enemy.enemyH / 2))) < (player.playerH + enemy.enemyH / 2)) {
                                        player.crash(enemy);
                                        for (int i = 0; i < crashEffectCount; i++) {
                                            crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                        }
                                        break;
                                    }

                                    if (Math.abs(player.playerX - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW + enemy.enemyW / 2) && Math.abs(player.playerY - (tempTargetEnemyY +
                                            (enemy.enemyH / 2))) < (player.playerH + enemy.enemyH / 2)) {
                                        player.crash(enemy);
                                        for (int i = 0; i < crashEffectCount; i++) {
                                            crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                        }
                                        break;
                                    }
                                case 3:
                                case 7:
                                    if (Math.abs(player.playerX - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW + enemy.enemyW / 2) && Math.abs(player.playerY - (tempTargetEnemyY -
                                            (enemy.enemyH / 2))) < (player.playerH + enemy.enemyH / 2)) {
                                        player.crash(enemy);
                                        for (int i = 0; i < crashEffectCount; i++) {
                                            crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                        }
                                        break;
                                    }

                                    if (Math.abs(player.playerX - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW + enemy.enemyW / 2) && Math.abs(player.playerY - (tempTargetEnemyY +
                                            (enemy.enemyH / 2))) < (player.playerH + enemy.enemyH / 2)) {
                                        player.crash(enemy);
                                        for (int i = 0; i < crashEffectCount; i++) {
                                            crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                        }
                                        break;
                                    }
                            }
                    }
                }
                break;
            case 1:
                for (Enemy enemy : enemies) {
                    tempTargetEnemyX = enemy.enemyX;
                    tempTargetEnemyY = enemy.enemyY;

                    switch (enemy.DIRECTION % 2) {
                        case 0:
                            if (Math.abs(player.playerX - tempTargetEnemyX) < (player.playerW / 3 + enemy.enemyW) && Math.abs(player.playerY - tempTargetEnemyY) < (player.playerH / 3 + enemy
                                    .enemyH)) {
                                player.crash(enemy);
                                for (int i = 0; i < crashEffectCount; i++) {
                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                }
                                break;
                            }
                        case 1:
                            // Main = Middle, Target = Middle;
                            if (Math.abs(player.playerX - tempTargetEnemyX) < (player.playerW / 3 + enemy.enemyW / 3) && Math.abs(player.playerY - tempTargetEnemyY) < (player.playerH / 3 + enemy
                                    .enemyH / 3)) {
                                player.crash(enemy);
                                for (int i = 0; i < crashEffectCount; i++) {
                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                }
                                break;
                            }

                            switch (player.DIRECTION) {
                                case 1:
                                case 5:
                                    // Main = Left_Top , Target = Middle
                                    if (Math.abs((player.playerX - (player.playerW / 2) - tempTargetEnemyX)) < (player.playerW / 2 + enemy.enemyW / 3) && Math.abs((player.playerY - (player.playerH
                                            / 2) - tempTargetEnemyY)) < (player.playerW / 2 + enemy.enemyW / 3)) {
                                        player.crash(enemy);
                                        for (int i = 0; i < crashEffectCount; i++) {
                                            crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                        }
                                        break;
                                    }

                                    // Main = Right_Bottom, Target = Middle
                                    if (Math.abs((player.playerX + (player.playerW / 2) - tempTargetEnemyX)) < (player.playerW / 2 + enemy.enemyW / 3) && Math.abs((player.playerY + (player.playerH
                                            / 2) - tempTargetEnemyY)) < (player.playerW / 2 + enemy.enemyW / 3)) {
                                        player.crash(enemy);
                                        for (int i = 0; i < crashEffectCount; i++) {
                                            crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                        }
                                        break;
                                    }

                                    switch (enemy.DIRECTION) {
                                        case 1:
                                        case 5:
                                            // Main = Left_Top
                                            // Target = Left_Top
                                            if (Math.abs((player.playerX - (player.playerW / 2)) - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY - (player.playerH / 2)) - (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Right_Bottom
                                            if (Math.abs((player.playerX - (player.playerW / 2)) - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY - (player.playerH / 2)) - (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Main = Middle
                                            // Target = Left_Top
                                            if (Math.abs(player.playerX - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 3 + enemy.enemyW / 2) && Math.abs(player.playerY -
                                                    (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 3 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Right_Bottom
                                            if (Math.abs(player.playerX - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 3 + enemy.enemyW / 2) && Math.abs(player.playerY -
                                                    (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 3 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Main = Right_Bottom
                                            // Target = Left_Top
                                            if (Math.abs((player.playerX + (player.playerW / 2)) - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY + (player.playerH / 2)) - (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Right_Bottom
                                            if (Math.abs((player.playerX + (player.playerW / 2)) - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY + (player.playerH / 2)) - (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }
                                            break;

                                        case 3:
                                        case 7:
                                            // Main = Left_Top
                                            // Target = Right_Top
                                            if (Math.abs((player.playerX - (player.playerW / 2)) - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY - (player.playerH / 2)) - (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Left_Bottom
                                            if (Math.abs((player.playerX - (player.playerW / 2)) - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY - (player.playerH / 2)) - (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Main = Middle
                                            // Target = Right_Top
                                            if (Math.abs(player.playerX - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 3 + enemy.enemyW / 2) && Math.abs(player.playerY -
                                                    (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 3 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Left_Bottom
                                            if (Math.abs(player.playerX - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 3 + enemy.enemyW / 2) && Math.abs(player.playerY -
                                                    (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 3 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Main = Right_Bottom
                                            // Target = Right_Top
                                            if (Math.abs((player.playerX + (player.playerW / 2)) - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY + (player.playerH / 2)) - (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Left_Bottom
                                            if (Math.abs((player.playerX + (player.playerW / 2)) - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY + (player.playerH / 2)) - (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }
                                            break;
                                    }
                                    break;
                                case 3:
                                case 7:
                                    // Main = Right_Top, Target = Middle
                                    if (Math.abs((player.playerX + (player.playerW / 2) - tempTargetEnemyX)) < (player.playerW / 2 + enemy.enemyW / 3) && Math.abs((player.playerY - (player.playerH
                                            / 2) - tempTargetEnemyY)) < (player.playerW / 2 + enemy.enemyW / 3)) {
                                        player.crash(enemy);
                                        for (int i = 0; i < crashEffectCount; i++) {
                                            crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                        }
                                        break;
                                    }

                                    // Main = Left_Bottom, Target = Middle
                                    if (Math.abs((player.playerX - (player.playerW / 2) - tempTargetEnemyX)) < (player.playerW / 2 + enemy.enemyW / 3) && Math.abs((player.playerY + (player.playerH
                                            / 2) - tempTargetEnemyY)) < (player.playerW / 2 + enemy.enemyW / 3)) {
                                        player.crash(enemy);
                                        for (int i = 0; i < crashEffectCount; i++) {
                                            crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                        }
                                        break;
                                    }

                                    switch (enemy.DIRECTION) {
                                        case 1:
                                        case 5:
                                            // Main = Right_top
                                            // Target = Left_Top
                                            if (Math.abs((player.playerX + (player.playerW / 2)) - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY - (player.playerH / 2)) - (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 2 +
                                                    enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Right_Bottom
                                            if (Math.abs((player.playerX + (player.playerW / 2)) - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY - (player.playerH / 2)) - (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Main = Middle
                                            // Target = Left_Top
                                            if (Math.abs(player.playerX - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 3 + enemy.enemyW / 2) && Math.abs(player.playerY -
                                                    (tempTargetEnemyY - (enemy.enemyW / 2))) < (player.playerH / 3 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Right_Bottom
                                            if (Math.abs(player.playerX - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 3 + enemy.enemyW / 2) && Math.abs(player.playerY -
                                                    (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 3 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Main = Left_bottom
                                            // Target = Left_Top
                                            if (Math.abs((player.playerX - (player.playerW / 2)) - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY + (player.playerH / 2)) - (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Right_Bottom
                                            if (Math.abs((player.playerX - (player.playerW / 2)) - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY + (player.playerH / 2)) - (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }
                                            break;
                                        case 3:
                                        case 7:
                                            // Main = Right_top
                                            // Target = Right_top
                                            if (Math.abs((player.playerX + (player.playerW / 2)) - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY - (player.playerH / 2)) - (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Left_bottom
                                            if (Math.abs((player.playerX + (player.playerW / 2)) - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY - (player.playerH / 2)) - (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Main = Middle
                                            // Target = Right_top
                                            if (Math.abs(player.playerX - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 3 + enemy.enemyW / 2) && Math.abs(player.playerY -
                                                    (tempTargetEnemyY - (enemy.enemyW / 2))) < (player.playerH / 3 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Left_bottom
                                            if (Math.abs(player.playerX - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 3 + enemy.enemyW / 2) && Math.abs(player.playerY -
                                                    (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 3 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Main = Left_bottom
                                            // Target = Right_top
                                            if (Math.abs((player.playerX - (player.playerW / 2)) - (tempTargetEnemyX + (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY + (player.playerH / 2)) - (tempTargetEnemyY - (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }

                                            // Target = Left_bottom
                                            if (Math.abs((player.playerX - (player.playerW / 2)) - (tempTargetEnemyX - (enemy.enemyW / 2))) < (player.playerW / 2 + enemy.enemyW / 2) && Math.abs(
                                                    (player.playerY + (player.playerH / 2)) - (tempTargetEnemyY + (enemy.enemyH / 2))) < (player.playerH / 2 + enemy.enemyH / 2)) {
                                                player.crash(enemy);
                                                for (int i = 0; i < crashEffectCount; i++) {
                                                    crashEffects.add(new CrashEffect(context, player.playerX, player.playerY, tempTargetEnemyX, tempTargetEnemyY));
                                                }
                                                break;
                                            }
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                }
        }
    }
}
