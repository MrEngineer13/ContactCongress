package com.mrengineer13.contact_congress.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.mrengineer13.contact_congress.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class LegislatorImageUtils {
    public static final String PIC_LARGE = "450x550";
    public static final String PIC_SMALL = "225x275";


    public static String getImageURL(String bioguideId, String size) {
        return "http://theunitedstates.io/images/congress/" + size + "/" + bioguideId + ".jpg";
    }

    public static void setImageView(String bioguideId, String imageSize, Context context, ImageView imageView) {
        String url = LegislatorImageUtils.getImageURL(bioguideId, imageSize);
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_account)
                .into(imageView);
    }

    public static void setImageView(String bioguideId, String imageSize, Context context, CircleImageView imageView) {
        String url = LegislatorImageUtils.getImageURL(bioguideId, imageSize);
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_account)
                .into(imageView);
    }

}
