package com.example.what_next;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class event extends AppCompatActivity {


    Button button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        button7 = (Button) findViewById(R.id.button7) ;

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(event.this,showevents.class);
                startActivity(a);
            }
        });

        BottomNavigationView btmNav = (BottomNavigationView)findViewById(R.id.btnav);
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {

                    case R.id.menu_add1:
                        Intent i = new Intent(event.this,second.class);
                        startActivity(i);
                        finish();
                        break;

                    case R.id.menu_add2:
                        Intent j = new Intent(event.this,addproject.class);
                        startActivity(j);
                        finish();
                        break;

                    case R.id.menu_add3:
                        Intent k = new Intent(event.this,profile.class);
                        startActivity(k);
                        finish();
                        break;

                    case R.id.menu_add4:
                        Intent l = new Intent(event.this,event.class);
                        startActivity(l);
                        finish();
                        break;

                    case R.id.menu_add5:
                        Intent m = new Intent(event.this,chatnow.class);
                        startActivity(m);
                        finish();
                        break;

                }
                return false;
            }
        });
    }
}
