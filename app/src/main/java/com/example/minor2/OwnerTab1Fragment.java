package com.example.minor2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class OwnerTab1Fragment extends Fragment {

    EditText oname, omobile, oemail, opassword;
    Button b1;
    private Firebase mRef,mRef1;
    SharedPreferences sp;
    Firebase mChildNameRef;
    private FirebaseAuth mAuth;
    ProgressBar mprogressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_owner_tab1_fragment, container, false);
        oname = (EditText)view.findViewById(R.id.name);
        omobile = (EditText)view.findViewById(R.id.phone);
        oemail = (EditText)view.findViewById(R.id.mail);
        opassword = (EditText)view.findViewById(R.id.password);
        b1 = (Button)view.findViewById(R.id.upload_final);
        mprogressBar = (ProgressBar)view.findViewById(R.id.progressBar2);
        mRef = new Firebase("https://minor2-d5d31.firebaseio.com/");
        mRef1 = new Firebase("https://minor2-d5d31.firebaseio.com/Owners");
        mAuth = FirebaseAuth.getInstance();
        mChildNameRef = mRef.child("Owners");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getActivity().getSharedPreferences("Uploaded", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String uploaded = sp.getString("Done1","");
                final String sname1 = oname.getText().toString();
                final String smobile1 = omobile.getText().toString();
                final String semail1 = oemail.getText().toString();
                final String spassword1 = opassword.getText().toString();
                if (sname1.isEmpty()||smobile1.isEmpty()||semail1.isEmpty()
                        ||spassword1.isEmpty()
                ){
                    Toasty.error(getContext(),"Enter Missing Details",Toasty.LENGTH_LONG).show();
                }
                else if (smobile1.length()<10||smobile1.length()>10){
                    Toasty.error(getContext(),"Phone Number Wrong",Toasty.LENGTH_LONG).show();
                }
                else if (spassword1.length()<8){
                    Toasty.error(getContext(),"Password Size is Small",Toasty.LENGTH_LONG).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(semail1).matches()){
                    Toasty.error(getContext(),"Enter a Vaild E-mail",Toasty.LENGTH_LONG).show();
                }
                else if(!semail1.contains("gmail")){
                    Toasty.error(getContext(),"Enter a Vaild E-mail Domain",Toasty.LENGTH_LONG).show();
                }

                else if(!uploaded.equals("Ok")){
                    Toasty.error(getContext(),"Upload Photo First",Toasty.LENGTH_LONG).show();
                }

                else {
                    b1.setVisibility(View.INVISIBLE);
                    mprogressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(semail1,spassword1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mprogressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Firebase child_user = mChildNameRef.push();
                                Firebase child_user_name = child_user.child("Name");
                                Firebase child_user_mobile = child_user.child("SMobile");
                                Firebase child_user_password = child_user.child("SPassword");
                                child_user_name.setValue(sname1);
                                child_user_mobile.setValue(smobile1);
                                child_user_password.setValue(spassword1);
                                b1.setVisibility(View.INVISIBLE);
                                Toasty.success(getContext(), "Registered Successfully", Toasty.LENGTH_LONG).show();
                                startActivity(new Intent(getContext(),owner_login.class));
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
