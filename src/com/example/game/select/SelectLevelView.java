package com.example.game.select;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.game.R;

/**
 * Created by ION on 2015-05-15.
 */
public class SelectLevelView extends ImageView {
    private Context context;
    private LayoutInflater inflater;
    private ViewGroup viewGroup;
    private ImageView levelView;
    private RelativeLayout levelLayout;
    private TextView levelNumber;
    private int level;

    public SelectLevelView(Context context, ViewGroup parent) {
        super(context);
        this.context = context;
        this.viewGroup = parent;
    }

    public ImageView init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.level_view, null);

        levelLayout = (RelativeLayout) view.findViewById(R.id.levelLayout);
        levelView = (ImageView) view.findViewById(R.id.levelView);

        levelNumber = new TextView(context);
        levelNumber.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        levelNumber.setText(Integer.toString(level));

        levelLayout.addView(levelNumber);
        levelLayout.setDrawingCacheEnabled(true);
        levelLayout.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        levelLayout.layout(0, 0, levelView.getMeasuredWidth(), levelView.getMeasuredHeight());
        levelLayout.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(levelLayout.getDrawingCache());
        levelLayout.setDrawingCacheEnabled(false);
        levelView.setImageBitmap(bitmap);

        return levelView;
    }

    public void setLevelNum(int position) {
        level = position + 1;
    }
}
