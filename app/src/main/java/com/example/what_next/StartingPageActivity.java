package com.example.what_next;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StartingPageActivity extends AppCompatActivity {

    private ViewPager slide_view_pager;
    private LinearLayout dots_layout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button mNextBtn;
    private Button mBackBtn;

    private int mCurrentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);


        slide_view_pager = (ViewPager)findViewById(R.id.slideViewPager);
        dots_layout = (LinearLayout)findViewById(R.id.dotsLayout);

        mNextBtn = (Button) findViewById(R.id.button4);
        mBackBtn = (Button) findViewById(R.id.button3);

        sliderAdapter = new SliderAdapter( this);

        slide_view_pager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        slide_view_pager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide_view_pager.setCurrentItem(mCurrentPage+1);
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide_view_pager.setCurrentItem(mCurrentPage-1);
            }
        });
    }


    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        dots_layout.removeAllViews();

        for( int i=0; i < mDots.length ; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorAccent));

            dots_layout.addView(mDots[i]);
        }

        if(mDots.length > 0){

            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);

            mCurrentPage=position;

            if(position == 0){

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("");
            }else if ( position == mDots.length-1){

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Finish");
                mNextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent b = new Intent(StartingPageActivity.this, login.class);
                        startActivity(b);
                        finish();
                mBackBtn.setText("Back");


            }});
            }
            else  {

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
