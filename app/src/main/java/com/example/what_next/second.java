package com.example.what_next;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class second extends AppCompatActivity {

    private DatabaseReference RootRef;
    private String currentuserID;
    private FirebaseAuth mauth;
    private StorageReference UserProfileImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mauth = FirebaseAuth.getInstance();
        currentuserID = mauth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        BottomNavigationView btmNav = (BottomNavigationView)findViewById(R.id.btnav);
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {

                    case R.id.menu_add1:
                        Intent i = new Intent(second.this,second.class);
                        startActivity(i);
                        finish();
                        break;

                    case R.id.menu_add2:
                        Intent j = new Intent(second.this,addproject.class);
                        startActivity(j);
                        finish();
                        break;

                    case R.id.menu_add3:
                        Intent k = new Intent(second.this,profile.class);
                        startActivity(k);
                        finish();
                        break;

                    case R.id.menu_add4:
                        Intent l = new Intent(second.this,event.class);
                        startActivity(l);
                        finish();
                        break;

                    case R.id.menu_add5:
                        Intent m = new Intent(second.this,chatnow.class);
                        startActivity(m);
                        finish();
                        break;

                }
                return false;
            }
        });

    }


}
