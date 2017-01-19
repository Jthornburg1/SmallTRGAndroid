package com.example.jon_thornburg.smalltrgandroid.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.jon_thornburg.smalltrgandroid.R;
import com.squareup.picasso.Picasso;

/**
 * Created by jon_thornburg on 1/11/17.
 */

public class PicassoManager {

    public final static String TAG = PicassoManager.class.getSimpleName();

    public void loadEmployeePicasso(ImageView view, String urlString, Context context) {

        Drawable placeholder = context.getResources().getDrawable(R.drawable.place_holder);
        Picasso.with(context)
                .load(urlString)
                .placeholder(placeholder)
                .into(view);
    }

    public void loadRoomPicasso(ImageView view, String urlString, Context context) {

        Drawable placeholder = context.getResources().getDrawable(R.drawable.generic_room_placeholder);
        Picasso.with(context)
                .load(urlString)
                .placeholder(placeholder)
                .into(view);
    }
}
