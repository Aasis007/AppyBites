package com.example.roshan.appybites.Image_Slider;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.roshan.appybites.R;

/**
 * Created by roshan on 1/4/17.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    int[] ArrayImage= {R.drawable.veg, R.drawable.drink,R.drawable.food,R.drawable.breakfast,R.drawable.rice,R.drawable.chicken,R.drawable.salad,R.drawable.desert};

    public CustomSwipeAdapter(Context ctx){
        this.ctx=ctx;
    }

    @Override
    public int getCount() {
        return ArrayImage.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe,container,false);
        ImageView  imageView = (ImageView) item_view.findViewById(R.id.imageview);
        imageView.setImageResource(ArrayImage[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
