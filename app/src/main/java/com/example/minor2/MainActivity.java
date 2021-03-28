package com.example.minor2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

     EditText e1, e2;
     TextView p1;
     ImageButton b1;
     Button signup;
    FirebaseAuth mAuth;
     ProgressBar progressBar;
    TextView emailVerify;
     Switch change1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        change1 = (Switch)findViewById(R.id.to_owner);
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        p1 = (TextView)findViewById(R.id.forgot_password_user);
        mAuth = FirebaseAuth.getInstance();
        emailVerify = (TextView)findViewById(R.id.emailVerification);
        emailVerify.setVisibility(View.INVISIBLE);
        signup = (Button)findViewById(R.id.signup);
        e1 = (EditText)findViewById(R.id.email_user);
        e2 = (EditText)findViewById(R.id.password_user);
        b1 = (ImageButton)findViewById(R.id.signin);
        change1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                startActivity(new Intent(MainActivity.this,owner_login.class));
                change1.setChecked(true);
                change1.setChecked(false);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(e2.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                userLogin();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FinalUserRegister.class));
                finish();
            }
        });
        FirebaseUser firebaseUser2 = mAuth.getCurrentUser();
        if (firebaseUser2!=null && firebaseUser2.isEmailVerified()){//already logged in
            Toasty.info(getApplicationContext(),"Already Logged in",Toasty.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,UserHomePage.class));
        }
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e1.getText().toString();
                if (email.isEmpty()){
                    Toasty.error(getApplicationContext(),"Enter Email First",Toasty.LENGTH_LONG).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toasty.error(getApplicationContext(),"Enter a Vaild E-mail",Toasty.LENGTH_LONG).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toasty.success(getApplicationContext(),"Link is sent to your Email",Toasty.LENGTH_LONG).show();
                            }
                            else{
                                Toasty.error(getApplicationContext(),"Contact IT",Toasty.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
    public void userLogin(){
        final String email = e1.getText().toString();
        final String password = e2.getText().toString();
        if (email.isEmpty()||password.isEmpty()){
            Toasty.error(this,"Enter Missing Details",Toasty.LENGTH_LONG).show();
        }
        else if (password.length()<8){
            Toasty.error(this,"Password Size is Small",Toasty.LENGTH_LONG).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toasty.error(this,"Enter a Vaild E-mail",Toasty.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            b1.setVisibility(View.INVISIBLE);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    if (task.isSuccessful()){
                        //Toasty.success(getApplicationContext(),"Hello Owner",Toasty.LENGTH_LONG).show();
                        if (firebaseUser.isEmailVerified()){
                            //Intent intent = new Intent(MainActivity.this,ownerHomePage1.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //startActivity(intent);
                            //startActivity(new Intent(owner_login.this,ownerHomePage1.class));
                            //finish();
                            startActivity(new Intent(MainActivity.this,UserHomePage.class));
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else{
                            b1.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            emailVerify.setVisibility(View.VISIBLE);
                            emailVerify.setText("Email is not Verified (Click here)");
                            emailVerify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    firebaseUser.sendEmailVerification();
                                    Toasty.info(getApplicationContext(),"Link is Sent",Toasty.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                    else{

                        mAuth.fetchProvidersForEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                        boolean check = !task.getResult().getProviders().isEmpty();
                                        if(!check){
                                            Toasty.error(getApplicationContext(),"User Not Exists, Sign Up Today!",Toasty.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            b1.setVisibility(View.VISIBLE);
                                        }
                                        else{
                                            Toasty.warning(getApplicationContext(),"Password is Wrong",Toasty.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            b1.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });

                    }
                }
            });
        }

    }

}
