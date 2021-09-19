package com.example.what_next;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {

    private Button UpdateAccountSettings;
    private EditText username , userstatus;
    private CircleImageView userProfileImage;
    private String currentuserID;
    private FirebaseAuth mauth;
    private DatabaseReference RootRef;

    private  static  final int GalleryPick = 1;
    private StorageReference UserProfileImageRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mauth = FirebaseAuth.getInstance();
        currentuserID = mauth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        Initializefields();

       // username.setVisibility(View.INVISIBLE);//

        UpdateAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSettings();
            }
        });

        RetrieveUserInfo();

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GalleryPick);
            }
        });

        BottomNavigationView btmNav = (BottomNavigationView)findViewById(R.id.btnav);
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {

                    case R.id.menu_add1:
                        Intent i = new Intent(profile.this,second.class);
                        startActivity(i);
                        finish();
                        break;

                    case R.id.menu_add2:
                        Intent j = new Intent(profile.this,addproject.class);
                        startActivity(j);
                        finish();
                        break;

                    case R.id.menu_add3:
                        Intent k = new Intent(profile.this,profile.class);
                        startActivity(k);
                        finish();
                        break;

                    case R.id.menu_add4:
                        Intent l = new Intent(profile.this,event.class);
                        startActivity(l);
                        finish();
                        break;

                    case R.id.menu_add5:
                        Intent m = new Intent(profile.this,chatnow.class);
                        startActivity(m);
                        finish();
                        break;

                }
                return false;
            }
        });

    }

    private void Initializefields() {

        UpdateAccountSettings = (Button) findViewById(R.id.update_profile_button);
        username = (EditText) findViewById(R.id.set_user_name);
        userstatus = (EditText) findViewById(R.id.set_profile_status);
        userProfileImage = (CircleImageView) findViewById(R.id.set_profile_image);
        loadingBar = new ProgressDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        { Uri ImageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        { CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {   loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri resultUri = result.getUri();

                StorageReference filePath = UserProfileImageRef.child(currentuserID + ".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(profile.this, "Profile Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                            final String downloaedUrl = task.getResult().getMetadata().toString();

                            RootRef.child("Users").child(currentuserID).child("image")
                                    .setValue(downloaedUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            { Toast.makeText(profile.this, "Image save in Database, Successfully...", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                            else
                                            { String message = task.getException().toString();
                                                Toast.makeText(profile.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(profile.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        }
    }



    private void UpdateSettings() {
        String setUsername = username.getText().toString();
        String setStatus = userstatus.getText().toString();

        if(TextUtils.isEmpty(setUsername)){
            Toast.makeText(profile.this, "Please write your Username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(setStatus)){
            Toast.makeText(profile.this, "Write your current status....", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String,Object> profileMap = new HashMap<>();
                profileMap.put("uid", currentuserID);
                profileMap.put("name", setUsername);
                profileMap.put("status", setStatus);
             RootRef.child("Users").child(currentuserID).updateChildren(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(profile.this, "Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                     }
                     else {
                         String message = task.getException().toString();
                         Toast.makeText(profile.this, "Error :" + message, Toast.LENGTH_SHORT).show();
                     }
                 }
             });
        }
    }

    private void RetrieveUserInfo() {
        RootRef.child("Users").child(currentuserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image")))){
                    String retreiveUserName = dataSnapshot.child("name").getValue().toString();
                    String retreiveStatus = dataSnapshot.child("status").getValue().toString();
                    String retreiveProfileImage = dataSnapshot.child("image").getValue().toString();
                    username.setText(retreiveUserName);
                    userstatus.setText(retreiveStatus);
                    Picasso.get().load(retreiveProfileImage).into(userProfileImage);
                }
                else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("status")))){
                    String retreiveUserName = dataSnapshot.child("name").getValue().toString();
                    String retreiveStatus = dataSnapshot.child("status").getValue().toString();

                    username.setText(retreiveUserName);
                    userstatus.setText(retreiveStatus);
                }
                else {
                    Toast.makeText(profile.this, "Please set & Update your Profile info", Toast.LENGTH_SHORT).show();
                    username.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
