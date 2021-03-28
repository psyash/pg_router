package com.example.minor2;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class OwnerTab2Fragment extends Fragment {

    private static final int FILE_SELECT_CODE = 1;

    Button b1,b2;
    ImageView mimageView;
    ProgressBar mprogressBar;
    private StorageReference mstorageReference;
    private DatabaseReference mdatabaseReference;
    private StorageTask storageTask;
    Uri mImageuri;
    private int flag = 0;
    private Firebase mRef;
    Firebase mChildNameRef;
    SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_owner_tab2_fragment, container, false);
        b1 = (Button)view.findViewById(R.id.selectPhoto);
        b2 = (Button)view.findViewById(R.id.upload);
        mimageView = (ImageView)view.findViewById(R.id.imageView);
        mprogressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        mstorageReference = FirebaseStorage.getInstance().getReference("OwnerUploads");

        mRef = new Firebase("https://minor2-d5d31.firebaseio.com/");

        mChildNameRef = mRef.child("OwnerUploads");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==0){
                    openFileSelector();
                }
                else{
                    Toasty.error(getContext(),"Already Uploaded",Toasty.LENGTH_SHORT).show();
                }



            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storageTask!=null && storageTask.isInProgress()){
                    Toasty.warning(getContext(),"Upload in Progress",Toasty.LENGTH_LONG).show();
                }
                else{
                    uploadFile();
                }


            }
        });

        return view;
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
            Toasty.error(getContext(),"Install a File Manager", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FILE_SELECT_CODE && resultCode== RESULT_OK
                &&data!=null && data.getData()!=null){
            mImageuri = data.getData();
            Picasso.with(getContext()).load(mImageuri).into(mimageView);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){

        if(mImageuri!=null){
            final StorageReference fileReference = mstorageReference.child("OWNERPic"+System.currentTimeMillis()
                    +"."+getFileExtension(mImageuri));
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

                                Toasty.success(getContext(), "Upload Success", Toasty.LENGTH_LONG).show();
                                Upload upload = new Upload("Owner", fileReference.getDownloadUrl().toString());
                                //Firebase child_user_id = mChildNameRef.child("Owner"+date+System.currentTimeMillis());
                                Firebase child_user_id = mChildNameRef.push();
                                Firebase childOfChildFileName = child_user_id.child("mFileName");
                                Firebase childOfChildUrl = child_user_id.child("mURL");
                                childOfChildFileName.setValue("File 1 ");
                                childOfChildUrl.setValue(mstorageReference.getDownloadUrl().toString());
                                sp = getActivity().getSharedPreferences("Uploaded", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("Done1", "Ok");
                                editor.apply();
                                b2.setVisibility(View.INVISIBLE);
                                flag = 1;


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toasty.error(getContext(),e.getMessage(),Toasty.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mprogressBar.setProgress((int)progress);
                        }
                    });
        }
        else{
            Toasty.error(getContext(),"No Image Selected",Toasty.LENGTH_SHORT).show();
        }

    }
}
