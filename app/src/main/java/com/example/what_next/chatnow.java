package com.example.what_next;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chatnow extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager myviewPager;
    private TabLayout myTabLayout;
    private TabsAccessorAdapter myTabsAccessorAdapter;
    private FirebaseUser currentuser;
    private DatabaseReference RootRef;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatnow);

        toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat Now!");

        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference("Groups");

        myviewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        myviewPager.setAdapter(myTabsAccessorAdapter);

        myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myviewPager);



        BottomNavigationView btmNav = (BottomNavigationView)findViewById(R.id.btnav);
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {

                    case R.id.menu_add1:
                        Intent i = new Intent(chatnow.this,second.class);
                        startActivity(i);
                        finish();
                        break;

                    case R.id.menu_add2:
                        Intent j = new Intent(chatnow.this,addproject.class);
                        startActivity(j);
                        finish();
                        break;

                    case R.id.menu_add3:
                        Intent k = new Intent(chatnow.this,profile.class);
                        startActivity(k);
                        finish();
                        break;

                    case R.id.menu_add4:
                        Intent l = new Intent(chatnow.this,event.class);
                        startActivity(l);
                        finish();
                        break;

                    case R.id.menu_add5:
                        Intent m = new Intent(chatnow.this,chatnow.class);
                        startActivity(m);
                        finish();
                        break;

                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){

            case    R.id.main_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(chatnow.this,login.class));
                finish();
                return true;

            case    R.id.main_settings_option:
                Intent profile=new Intent(chatnow.this, com.example.what_next.profile.class);
                startActivity(profile);
                finish();
                break;

            case    R.id.find_New_buddy:
                Intent findnewfriend=new Intent(chatnow.this, com.example.what_next.LinkFriendsActivity.class);
                startActivity(findnewfriend);


            case    R.id.main_create_group_option:
                RequestNewGroup();

        }
        return  false;
    }

    private void RequestNewGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(chatnow.this,R.style.AlertDialog);
        builder.setTitle("Enter Group Name :");

        final EditText groupNameField = new EditText(chatnow.this);
        groupNameField.setHint("e.g INNOTECH");
        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName = groupNameField.getText().toString();

                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(chatnow.this, "Please enter a group name", Toast.LENGTH_SHORT).show();
                }
                else {
                    CreateNewGroup(groupName);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    private void CreateNewGroup(final String groupName) {
        RootRef.child(groupName).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(chatnow.this,groupName +" group is Created Successfully..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
