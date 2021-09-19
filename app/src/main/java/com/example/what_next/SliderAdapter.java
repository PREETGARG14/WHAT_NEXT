package com.example.what_next;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import static com.example.what_next.R.*;
import static com.example.what_next.R.drawable.*;
import static com.example.what_next.R.drawable.starting_page_3;
import static com.example.what_next.R.drawable.starting_page_1;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Arrays
    public int[] slide_images = {

            drawable.starting_page_3,
            drawable.starting_page_1,
            drawable.starting_page_2
    };

    public String[] slide_headings = {

            "Concurrency",
            "Enlightenment",
            " Approachable"


    };

    public String[] slide_descs= {

            "Reminder for you to take your next step.",
            "An informative source aimed to resolve your difficulty. ",
            "Enabling you towards desired learning goals."

    };




    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
