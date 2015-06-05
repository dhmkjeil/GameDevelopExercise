package com.example.game.system;

import android.content.Context;
import android.graphics.*;
import com.example.game.R;

/**
 * Created by ION on 2015-05-22.
 */
public class GameOver {
    private int messageBackgroundX, messageBackgroundY, messageX, messageY;
    private int messageBackgroundW, messageBackgroundH, messageW, messageH;
    public int listButtonX, listButtonY, restartButtonX, restartButtonY;
    public int listButtonW, listButtonH, restartButtonW, restartButtonH;
    private Bitmap battleBackground, messageBackground, message, listButton, restartButton;
    private Paint paint;

    public GameOver(Context context, Bitmap battleBackground) {
        messageBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.message_background);
        message = BitmapFactory.decodeResource(context.getResources(), R.drawable.message_defeat);
        listButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.list_button);
        restartButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.restart_button);
        this.battleBackground = battleBackground;
        messageBackgroundW = messageBackground.getWidth();
        messageBackgroundH = messageBackground.getHeight();
        messageBackgroundX = (this.battleBackground.getWidth() - messageBackgroundW) / 2;
        messageBackgroundY = (this.battleBackground.getHeight() - messageBackgroundH) / 2;
        messageW = message.getWidth();
        messageH = message.getHeight();
        messageX = (this.battleBackground.getWidth() - messageW) / 2;
        messageY = messageBackgroundY + (messageH / 2);
        restartButtonW = restartButton.getWidth();
        restartButtonH = restartButton.getHeight();
        restartButtonX = (this.battleBackground.getWidth() + 20) / 2;
        restartButtonY = this.battleBackground.getHeight() - (this.battleBackground.getHeight() - messageBackgroundH);
        listButtonW = listButton.getWidth();
        listButtonH = listButton.getHeight();
        listButtonX = (this.battleBackground.getWidth() - (listButtonW + restartButtonW + 20)) / 2;
        listButtonY = this.battleBackground.getHeight() - (this.battleBackground.getHeight() - messageBackgroundH);
        paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
    }

    public void setOver(Canvas canvas, int score) {
        int tempScore = score, tempCount = 0;
        while (tempScore > 0) {
            tempScore /= 10;
            tempCount++;
        }
        canvas.drawBitmap(battleBackground, 0, 0, null);
        canvas.drawBitmap(messageBackground, messageBackgroundX, messageBackgroundY, null);
        canvas.drawBitmap(message, messageX, messageY, null);
        canvas.drawBitmap(listButton, listButtonX, listButtonY, null);
        canvas.drawBitmap(restartButton, restartButtonX, restartButtonY, null);
        canvas.drawText("Score : " + score, listButtonX + (listButtonW / 2) - (25 * tempCount), listButtonY - listButton.getHeight(), paint);
    }
}
