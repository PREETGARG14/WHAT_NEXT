package com.example.what_next;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    Button Register;
    Animation frombottom,fromtop;
    private TextView textView2,ForgetPassword,LoginUsingYour;
    private EditText Name;
    private EditText Password;
    private Button Login,PhoneLogin;
    private ProgressDialog loadingBar;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference RootRef,UsersRef;

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseUser != null){
            loadingBar.setTitle("Logging In");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            verifyuserexistance();
        }
    }

    private void verifyuserexistance() {
        String currentUserID = auth.getCurrentUser().getUid();
        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.child("name").exists())){
                    Toast.makeText(login.this,"Welcome To App",Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent d = new Intent(login.this,second.class);
                    startActivity(d);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference("Users");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        Name=(EditText)findViewById(R.id.textView3);
        Password=(EditText)findViewById(R.id.textView4);
        Login=(Button)findViewById(R.id.button2);
        Register = (Button) findViewById(R.id.button5) ;
        ForgetPassword = (TextView)findViewById(R.id.forgot_password_link);
        loadingBar = new ProgressDialog(this);
        PhoneLogin = (Button)findViewById(R.id.phone_login_button);
        LoginUsingYour = (TextView)findViewById(R.id.login_with_other);



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_Name = Name.getText().toString() ;
                String txt_Password = Password.getText().toString();

                if (TextUtils.isEmpty(txt_Name) || TextUtils.isEmpty(txt_Password)){
                    Toast.makeText(login.this, "All Details Are Required", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("Logging In");
                    loadingBar.setMessage("Please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    auth.signInWithEmailAndPassword(txt_Name, txt_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(login.this,"Logged in Successful...",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(login.this,second.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(login.this, "You Are Not Registered", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                }
            }

        });

        PhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginusingphone = new Intent(login.this, PhoneLogin.class);
                startActivity(loginusingphone);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(login.this, signup.class);
                startActivity(b);
                finish();
            }
        });

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        Login=(Button) findViewById(R.id.button2);
        Register=(Button) findViewById(R.id.button5);
        textView2=(TextView) findViewById(R.id.textView2);
        Name=(EditText) findViewById(R.id.textView3);
        Password=(EditText) findViewById(R.id.textView4);

        Login.startAnimation(frombottom);
        Register.startAnimation(frombottom);
        textView2.startAnimation(fromtop);
        Name.startAnimation(fromtop);
        Password.startAnimation(fromtop);
        PhoneLogin.startAnimation(frombottom);
        LoginUsingYour.startAnimation(frombottom);
        ForgetPassword.startAnimation(fromtop);

    }
}
