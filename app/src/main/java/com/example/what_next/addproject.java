package com.example.what_next;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class addproject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);

        BottomNavigationView btmNav = (BottomNavigationView)findViewById(R.id.btnav);
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {

                    case R.id.menu_add1:
                        Intent i = new Intent(addproject.this,second.class);
                        startActivity(i);
                        finish();
                        break;

                    case R.id.menu_add2:
                        Intent j = new Intent(addproject.this,addproject.class);
                        startActivity(j);
                        finish();
                        break;

                    case R.id.menu_add3:
                        Intent k = new Intent(addproject.this,profile.class);
                        startActivity(k);
                        finish();
                        break;

                    case R.id.menu_add4:
                        Intent l = new Intent(addproject.this,event.class);
                        startActivity(l);
                        finish();
                        break;

                    case R.id.menu_add5:
                        Intent m = new Intent(addproject.this,chatnow.class);
                        startActivity(m);
                        finish();
                        break;

                }
                return false;
            }
        });

    }
}
