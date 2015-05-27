package com.example.game.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.example.game.R;

public class StageClear {
    private int messageBackgroundX, messageBackgroundY, messageX, messageY;
    private int messageBackgroundW, messageBackgroundH, messageW, messageH;
    public int listButtonX, listButtonY;
    public int listButtonW, listButtonH;
    private Bitmap battleBackground, messageBackground, message, listButton;

    public StageClear(Context context, Bitmap battleBackground) {
        messageBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.message_background);
        message = BitmapFactory.decodeResource(context.getResources(), R.drawable.message_victory);
        listButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.list_button);
        this.battleBackground = battleBackground;
        messageBackgroundW = messageBackground.getWidth();
        messageBackgroundH = messageBackground.getHeight();
        messageBackgroundX = (this.battleBackground.getWidth() - messageBackgroundW) / 2;
        messageBackgroundY = (this.battleBackground.getHeight() - messageBackgroundH) / 2;
        messageW = message.getWidth();
        messageH = message.getHeight();
        messageX = (this.battleBackground.getWidth() - messageW) / 2;
        messageY = messageBackgroundY + (messageH / 2);
        listButtonW = listButton.getWidth();
        listButtonH = listButton.getHeight();
        listButtonX = (this.battleBackground.getWidth() - listButtonW) / 2;
        listButtonY = this.battleBackground.getHeight() - (this.battleBackground.getHeight() - messageBackgroundH);
    }

    public void setClear(Canvas canvas) {
        canvas.drawBitmap(battleBackground, 0, 0, null);
        canvas.drawBitmap(messageBackground, messageBackgroundX, messageBackgroundY, null);
        canvas.drawBitmap(message, messageX, messageY, null);
        canvas.drawBitmap(listButton, listButtonX, listButtonY, null);
    }
}
