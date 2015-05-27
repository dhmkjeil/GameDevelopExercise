package com.example.game.select;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created by ION on 2015-05-15.
 */
public class SelectLevelAdapter extends BaseAdapter {
    private Context context;
    private int stageNumber;

    public SelectLevelAdapter(Context context, int stageNumber) {
        this.context = context;
        this.stageNumber = stageNumber;
    }

    @Override
    public int getCount() {
        return stageNumber;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        SelectLevelView selectLevelView;
        if (convertView == null) {
            selectLevelView = new SelectLevelView(context, parent);
            selectLevelView.setLevelNum(position);
            imageView = selectLevelView.init();
            imageView.setLayoutParams(new GridView.LayoutParams(375, 250));
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
            imageView.setPadding(4, 4, 4, 4);
        } else {
            imageView = (ImageView) convertView;
        }

        return imageView;
    }
}
