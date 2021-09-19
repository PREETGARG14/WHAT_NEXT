package com.example.what_next;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup extends AppCompatActivity {
    Button button3;
    Animation frombottom,fromtop;
    private TextView textView2;
    private EditText username,password,email;
    private Button register,alreadyuser;

    private FirebaseAuth auth;
    DatabaseReference reference;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        register=(Button) findViewById(R.id.button3);
        textView2=(TextView) findViewById(R.id.textView2);
        username=(EditText) findViewById(R.id.textView3);
        password=(EditText) findViewById(R.id.textView4);
        email=(EditText) findViewById(R.id.textView6);
        alreadyuser=(Button)findViewById(R.id.button8);
        loadingBar = new ProgressDialog(this);

        register.startAnimation(frombottom);
        alreadyuser.startAnimation(frombottom);
        textView2.startAnimation(fromtop);
        username.startAnimation(fromtop);
        password.startAnimation(fromtop);
        email.startAnimation(fromtop);

        auth = FirebaseAuth.getInstance();

        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(signup.this,login.class);
                startActivity(c);
                finish();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validate()){
                   final String txt_username =username.getText().toString();

                    String txt_password = password.getText().toString();
                    String txt_email = email.getText().toString();

                    auth.createUserWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                if (firebaseUser != null){
                                    String userid = firebaseUser.getUid();

                                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", userid);
                                hashMap.put("name",txt_username);

                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            loadingBar.dismiss();
                                            Intent intent = new Intent(signup.this, second.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                        }
                                    }
                                });
                            }
                            } else {
                                Toast.makeText(signup.this, "You Can't Register With This Email or Password", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }


    private void setupUIViews(){
        username= findViewById(R.id.textView3);
        password=(EditText) findViewById(R.id.textView4);
        email=(EditText) findViewById(R.id.textView6);
        register=(Button) findViewById(R.id.button3);

    }

    private Boolean validate(){
        Boolean result=false;


        String txt_username = username.getText().toString();
        String txt_password = password.getText().toString();
        String txt_email = email.getText().toString();


        if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
            Toast.makeText(signup.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
        }
        else{
        if (password.length() < 5) {
            Toast.makeText(this, "Password Must Be At Least 5 Characters Long", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
                loadingBar.setTitle("Creating New Account");
                loadingBar.setMessage("Please wait,while we are creating new account for you...");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();
        }
        }

        return result;
    }

}
