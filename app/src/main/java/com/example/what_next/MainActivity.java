package com.example.what_next;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView bgone;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgone = (ImageView) findViewById(R.id.bgone);
        button1 = (Button) findViewById(R.id.button1) ;

        bgone.animate().scaleX(2).scaleY(2).setDuration(8000).start();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,StartingPageActivity.class);
                startActivity(a);
                finish();
            }
        });
    }
}
