package com.example.minor2;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class OwnerAddPhotos extends AppCompatActivity {

    ImageView i1, i2, i3;
    Button b1,b2,b3;
    EditText e1;
    FirebaseAuth mAuth;
    Uri mImageuri;
    private StorageReference mstorageReference;
    private static final int FILE_SELECT_CODE = 1;
    public static String imageURL;
    int flag1 = 0;
    int flag2 = 0;
    int flag3 = 0;
    String pgname;
    String whoCalled = null;
    private StorageTask storageTask;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_photos);
        e1 = (EditText)findViewById(R.id.pgname1);
        b1 = (Button) findViewById(R.id.selectPhoto1);
        b2 = (Button) findViewById(R.id.selectPhoto2);
        b3 = (Button) findViewById(R.id.selectPhoto3);
        i1 = (ImageView) findViewById(R.id.image1);
        i1.setImageResource(R.drawable.pg_photo_upload);
        i2 = (ImageView) findViewById(R.id.image2);
        i2.setImageResource(R.drawable.pg_photo_upload);
        i3 = (ImageView) findViewById(R.id.image3);
        i3.setImageResource(R.drawable.pg_photo_upload);
        mAuth = FirebaseAuth.getInstance();
        mstorageReference = FirebaseStorage.getInstance().getReference();
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag1==0){
                    openFileSelector();
                    whoCalled = "first";
                }
                else{
                    Toasty.error(getApplicationContext(),"Already Uploaded",Toasty.LENGTH_SHORT).show();
                }
            }


        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag2==0){
                    openFileSelector();
                    whoCalled = "second";
                }
                else{
                    Toasty.error(getApplicationContext(),"Already Uploaded",Toasty.LENGTH_SHORT).show();
                }
            }


        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag3==0){
                    openFileSelector();
                    whoCalled = "third";
                }
                else{
                    Toasty.error(getApplicationContext(),"Already Uploaded",Toasty.LENGTH_SHORT).show();
                }
            }


        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgname = e1.getText().toString();
                if (pgname.equals("")){
                    Toasty.error(getApplicationContext(),"Provide PG Name First",Toasty.LENGTH_SHORT).show();
                }
                else{
                    uploadFile1();
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgname = e1.getText().toString();
                if (pgname.equals("")){
                    Toasty.error(getApplicationContext(),"Provide PG Name First",Toasty.LENGTH_SHORT).show();
                }
                else{
                    uploadFile2();
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgname = e1.getText().toString();
                if (pgname.equals("")){
                    Toasty.error(getApplicationContext(),"Provide PG Name First",Toasty.LENGTH_SHORT).show();
                }
                else{
                    uploadFile3();
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
            if (whoCalled.equals("first")){
                Picasso.with(getApplicationContext()).load(mImageuri).into(i1);
            }
            if (whoCalled.equals("second")){
                Picasso.with(getApplicationContext()).load(mImageuri).into(i2);
            }
            if (whoCalled.equals("third")){
                Picasso.with(getApplicationContext()).load(mImageuri).into(i3);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile1(){

        if(mImageuri!=null){
            final StorageReference fileReference = mstorageReference.child(mAuth.getUid()).child("Images").child("PG1");
            try{
                Log.d("imageWorking","i worked");
                storageTask = fileReference.putFile(mImageuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful());
                                Uri downloadUrl = urlTask.getResult();
                                Log.d("Imageurl",downloadUrl.toString());
                                imageURL = downloadUrl.toString();
                                try{
                                    Log.d("pgname",pgname);
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = firebaseDatabase.getReference("Owners/"+mAuth.getUid());
                                    DatabaseReference myRef1 = myRef.child("PG's").child(pgname);
                                    DatabaseReference myRef2 = firebaseDatabase.getReference("PG's");
                                    DatabaseReference myRef3 = myRef2.child(pgname);
                                    myRef3.child("P1imageURL").setValue(imageURL);
                                    myRef1.child("P1imageURL").setValue(imageURL);
                                    flag1 = 1;
                                    b1.setVisibility(View.INVISIBLE);
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
    private void uploadFile2(){

        if(mImageuri!=null){
            final StorageReference fileReference = mstorageReference.child(mAuth.getUid()).child("Images").child("PG2");
            try{
                Log.d("imageWorking","i worked");
                storageTask = fileReference.putFile(mImageuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful());
                                Uri downloadUrl = urlTask.getResult();
                                Log.d("Imageurl",downloadUrl.toString());
                                imageURL = downloadUrl.toString();
                                try{
                                    Log.d("pgname",pgname);
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = firebaseDatabase.getReference("Owners/"+mAuth.getUid());
                                    DatabaseReference myRef1 = myRef.child("PG's").child(pgname);
                                    myRef1.child("P2imageURL").setValue(imageURL);
                                    DatabaseReference myRef2 = firebaseDatabase.getReference("PG's");
                                    DatabaseReference myRef3 = myRef2.child(pgname);
                                    myRef3.child("P2imageURL").setValue(imageURL);
                                    flag2 = 1;
                                    b2.setVisibility(View.INVISIBLE);
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
    private void uploadFile3(){

        if(mImageuri!=null){
            final StorageReference fileReference = mstorageReference.child(mAuth.getUid()).child("Images").child("PG3");
            try{
                Log.d("imageWorking","i worked");
                storageTask = fileReference.putFile(mImageuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful());
                                Uri downloadUrl = urlTask.getResult();
                                Log.d("Imageurl",downloadUrl.toString());
                                imageURL = downloadUrl.toString();
                                try{
                                    Log.d("pgname",pgname);
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = firebaseDatabase.getReference("Owners/"+mAuth.getUid());
                                    DatabaseReference myRef1 = myRef.child("PG's").child(pgname);
                                    myRef1.child("P3imageURL").setValue(imageURL);
                                    DatabaseReference myRef2 = firebaseDatabase.getReference("PG's");
                                    DatabaseReference myRef3 = myRef2.child(pgname);
                                    myRef3.child("P3imageURL").setValue(imageURL);
                                    flag3 = 1;
                                    b3.setVisibility(View.INVISIBLE);
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
