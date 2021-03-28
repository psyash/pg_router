package com.example.minor2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Fragment1";
    EditText sname, smobile, fname, fmobile, semail, spassword, scollege;
    Button b1;
    private Firebase mRef;
    Firebase mChildNameRef;
    SharedPreferences sp;
    private FirebaseAuth mAuth;
    ProgressBar mprogressBar;
    private int emailFalg = 0;
    private String[] emailDomains = {"gmail","rediffmail","yahoo","ddn.upes.ac.in","stu.upes.ac.in"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        sname = (EditText)view.findViewById(R.id.name);
        smobile = (EditText)view.findViewById(R.id.phone);
        fname = (EditText)view.findViewById(R.id.father);
        fmobile = (EditText)view.findViewById(R.id.father_mobile);
        semail = (EditText)view.findViewById(R.id.mail);
        spassword = (EditText)view.findViewById(R.id.password);
        scollege = (EditText)view.findViewById(R.id.college);
        b1 = (Button)view.findViewById(R.id.upload_final);
        mprogressBar = (ProgressBar)view.findViewById(R.id.progressBar4);
        mAuth = FirebaseAuth.getInstance();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getActivity().getSharedPreferences("Uploaded", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String uploaded = sp.getString("Done","");
                final String sname1 = sname.getText().toString();
                final String smobile1 = smobile.getText().toString();
                final String fname1 = fname.getText().toString();
                final String fmobile1 = fmobile.getText().toString();
                final String semail1 = semail.getText().toString();
                final String spassword1 = spassword.getText().toString();
                final String scollege1 = scollege.getText().toString();
                if (semail1.contains("gmail") || semail1.contains("yahoo") || semail1.contains("rediffmail") || semail1.contains("ddn.upes.ac.in") || semail1.contains("stu.upes.ac.in")){
                    emailFalg=1;
                    Log.d("email","ok");
                }
                else{
                    emailFalg=0;
                    Log.d("email","not ok");
                }
                if (sname1.isEmpty()||smobile1.isEmpty()||fname1.isEmpty()||fmobile1.isEmpty()||semail1.isEmpty()
                ||spassword1.isEmpty()||scollege1.isEmpty()
                ){
                    Toasty.error(getContext(),"Enter Missing Details",Toasty.LENGTH_LONG).show();
                }
                else if (smobile1.length()<10||smobile1.length()>10){
                    Toasty.error(getContext(),"Phone Number Wrong",Toasty.LENGTH_LONG).show();
                }
                else if (fmobile1.length()<10||fmobile1.length()>10){
                    Toasty.error(getContext(),"Mobile Number Wrong",Toasty.LENGTH_LONG).show();
                }
                else if (spassword1.length()<8){
                    Toasty.error(getContext(),"Password Size is Small",Toasty.LENGTH_LONG).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(semail1).matches()){
                    Toasty.error(getContext(),"Enter a Vaild E-mail",Toasty.LENGTH_LONG).show();
                }
                else if(emailFalg==0){
                    Toasty.error(getContext(),"Enter a Vaild E-mail Domain",Toasty.LENGTH_SHORT).show();
                }
                else if(!uploaded.equals("Ok")){
                    Toasty.error(getContext(),"Upload ID Card First",Toasty.LENGTH_LONG).show();
                }

                else {

                    b1.setVisibility(View.INVISIBLE);
                    mprogressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(semail1,spassword1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mprogressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = firebaseDatabase.getReference("Users/"+mAuth.getUid());
                                UserProfile userProfile = new UserProfile();
                                myRef.setValue(userProfile);
                                b1.setVisibility(View.INVISIBLE);
                                Toasty.success(getContext(), "Registered Successfully", Toasty.LENGTH_LONG).show();
                                startActivity(new Intent(getContext(),MainActivity.class));
                                getActivity().finish();
                            }
                            else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toasty.error(getContext(), "Account with same Email Exists!", Toasty.LENGTH_LONG).show();
                                    b1.setVisibility(View.VISIBLE);
                                }
                                else{
                                    Toasty.error(getContext(),"Contact IT",Toasty.LENGTH_LONG).show();
                                    b1.setVisibility(View.VISIBLE);
                                }
                            }

                        }
                    });

                }

            }
        });



        return view;

    }
}
