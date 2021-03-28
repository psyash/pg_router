package com.example.minor2;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class FinalUserRegister extends AppCompatActivity {


    private static final int FILE_SELECT_CODE = 1;
    public static String imageURL;
    private int imageFlag = 0;
    int done = 0;
    ImageView ownerPic;
    ProgressBar mprogressBar;
    EditText sname,sphone,fname,fmobile,semail,spassword,scollege;
    Button b1;
    FirebaseAuth mAuth;
    Uri mImageuri;
    private int flag = 0;
    private int emailFalg = 0;
    private StorageReference mstorageReference;
    private StorageTask storageTask;
    private String[] emailDomains = {"gmail","rediffmail","yahoo","ddn.upes.ac.in","stu.upes.ac.in"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_user_register);
        ownerPic = (ImageView)findViewById(R.id.userImage);
        ownerPic.setImageResource(R.drawable.owner_image);
        sname = (EditText)findViewById(R.id.name);
        sphone = (EditText)findViewById(R.id.phone);
        fname = (EditText)findViewById(R.id.father);
        fmobile = (EditText)findViewById(R.id.father_mobile);
        semail = (EditText)findViewById(R.id.mail);
        spassword = (EditText)findViewById(R.id.password);
        scollege = (EditText)findViewById(R.id.college);
        b1 = (Button)findViewById(R.id.upload_final);
        mAuth = FirebaseAuth.getInstance();
        mstorageReference = FirebaseStorage.getInstance().getReference();
        mprogressBar = (ProgressBar)findViewById(R.id.progressBar4);
        ownerPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==0){
                    openFileSelector();
                }
                else{
                    Toasty.error(getApplicationContext(),"Already Uploaded",Toasty.LENGTH_SHORT).show();
                }
            }


        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                final String sname1 = sname.getText().toString();
                final String smobile1 = sphone.getText().toString();
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
                    Toasty.error(getApplicationContext(),"Enter Missing Details",Toasty.LENGTH_LONG).show();
                }
                else if (smobile1.length()<10||smobile1.length()>10){
                    Toasty.error(getApplicationContext(),"Phone Number Wrong",Toasty.LENGTH_LONG).show();
                }
                else if (fmobile1.length()<10||fmobile1.length()>10){
                    Toasty.error(getApplicationContext(),"Mobile Number Wrong",Toasty.LENGTH_LONG).show();
                }
                else if (spassword1.length()<8){
                    Toasty.error(getApplicationContext(),"Password Size is Small",Toasty.LENGTH_LONG).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(semail1).matches()){
                    Toasty.error(getApplicationContext(),"Enter a Vaild E-mail",Toasty.LENGTH_LONG).show();
                }
                else if(emailFalg==0){
                    Toasty.error(getApplicationContext(),"Enter a Vaild E-mail Domain",Toasty.LENGTH_SHORT).show();
                }
                else if (imageFlag==0){
                    Toasty.warning(getApplicationContext(),"No Image is selected",Toasty.LENGTH_SHORT).show();
                }

                else {
                    b1.setVisibility(View.INVISIBLE);
                    mprogressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(semail1,spassword1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mprogressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                //here add details to database
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = firebaseDatabase.getReference("Users/"+mAuth.getUid());
                                uploadFile();
                                UserProfile userProfile = new UserProfile(sname1,smobile1,fname1,fmobile1,semail1,spassword1,scollege1,imageURL);
                                myRef.setValue(userProfile);
                                b1.setVisibility(View.INVISIBLE);
                                Toasty.success(getApplicationContext(), "Registered Successfully", Toasty.LENGTH_SHORT).show();
                                //mAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();

                            }

                            else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toasty.error(getApplicationContext(), "Account with same Email Exists!", Toasty.LENGTH_SHORT).show();
                                    b1.setVisibility(View.VISIBLE);
                                }
                                else{
                                    Toasty.error(getApplicationContext(),"No Internet Connection Found",Toasty.LENGTH_SHORT).show();
                                    b1.setVisibility(View.VISIBLE);
                                }
                            }

                        }
                    });


                }

            }
        });
    }
    public void openFileSelector(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        try{
            startActivityForResult(
                    Intent.createChooser(i,"Select an Image to Uplaod"),
                    FILE_SELECT_CODE
            );

        }
        catch (android.content.ActivityNotFoundException e){
            Toasty.error(getApplicationContext(),"Install a File Manager", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FILE_SELECT_CODE && resultCode== RESULT_OK
                &&data!=null && data.getData()!=null){
            mImageuri = data.getData();
            Picasso.with(getApplicationContext()).load(mImageuri).into(ownerPic);
            imageFlag = 1;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){

        if(mImageuri!=null){
            final StorageReference fileReference = mstorageReference.child(mAuth.getUid()).child("Images").child("IDCARD");
            try{
                Log.d("imageWorking","i worked");
                storageTask = fileReference.putFile(mImageuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mprogressBar.setProgress(0);
                                    }
                                },5000);

                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful());
                                Uri downloadUrl = urlTask.getResult();
                                Log.d("Imageurl",downloadUrl.toString());
                                imageURL = downloadUrl.toString();
                                try{
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = firebaseDatabase.getReference("Users/"+mAuth.getUid());
                                    myRef.child("UimageURL").setValue(imageURL);
                                    flag = 1;
                                }
                                catch (NullPointerException e){
                                    Log.d("Errors",e.getMessage());
                                }




                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(getApplicationContext(),e.getMessage(),Toasty.LENGTH_LONG).show();

                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                mprogressBar.setProgress((int)progress);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Imageurl",e.getMessage());
                            }
                        });
            }
            catch (NullPointerException e){
                Log.d("Errors",e.getMessage());
            }

        }
        else{
            Toasty.error(getApplicationContext(),"No Image Selected",Toasty.LENGTH_SHORT).show();

        }
    }
}
