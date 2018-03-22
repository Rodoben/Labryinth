package com.example.ronaldbenjamin.labryinth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Acc_Setup extends AppCompatActivity {
    private CircleImageView setupimage;
    private Uri mainImageURI=null;
    private EditText setupName;
    private Button setupButton;
    private ProgressBar setupProgress;
private String user_id;
private boolean isChanged = false;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_acc__setup);
        firebaseAuth=FirebaseAuth.getInstance();
        user_id=firebaseAuth.getCurrentUser().getUid();
        storageReference= FirebaseStorage.getInstance().getReference();
               firebaseFirestore=FirebaseFirestore.getInstance();
        setupProgress=findViewById(R.id.setup_progress);

        setupProgress.setVisibility(View.VISIBLE);


        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){

                    if (task.getResult().exists()){
                        Toast.makeText(Acc_Setup.this,"data exists",Toast.LENGTH_LONG).show();
                       String name=task.getResult().getString("name");
                       String image=task.getResult().getString("image");
                       mainImageURI=Uri.parse(image);

                       setupName.setText(name);
                        RequestOptions placeholderRequest=new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.picture_box);
                        Glide.with(Acc_Setup.this).setDefaultRequestOptions(placeholderRequest).load(image).into(setupimage);


                    }else {

                        Toast.makeText(Acc_Setup.this,"data doesnt exist:",Toast.LENGTH_LONG).show();

                    }


                }else{
                    String error=task.getException().getMessage();
                    Toast.makeText(Acc_Setup.this,"Firebase retrieve Error:"+ error,Toast.LENGTH_LONG).show();
                }
                setupProgress.setVisibility(View.INVISIBLE);
                
            }
        });

        setupName=findViewById(R.id.editText8);
        setupButton=findViewById(R.id.button6);
        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name = setupName.getText().toString().trim();

                if (isChanged) {


                    if (!TextUtils.isEmpty(user_name) && mainImageURI != null) {

                        user_id = firebaseAuth.getCurrentUser().getUid();
                        setupProgress.setVisibility(View.VISIBLE);
                        StorageReference image_path = storageReference.child("Profile_images").child(user_id + ".jpg");
                        image_path.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    storeFirestore(task, user_name);

                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(Acc_Setup.this, "Error:" + error, Toast.LENGTH_LONG).show();
                                    setupProgress.setVisibility(View.INVISIBLE);
                                }


                            }
                        });

                    }
                }
                else {
                    storeFirestore(null,user_name);

                }
            }
        });
        setupimage=findViewById(R.id.setup_image);
        setupimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(Acc_Setup.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(Acc_Setup.this,"permisiion denied",Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(Acc_Setup.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1 );
                    }
                    else{
                       BringImagePicker();
                    }
                }
                else{
                        BringImagePicker();
                }
            }
        });
    }

    private void storeFirestore(@NonNull Task<UploadTask.TaskSnapshot> task, String user_name) {
    Uri download_uri;

      if (task!=null) {
          download_uri = task.getResult().getDownloadUrl();
      }else {
           download_uri=mainImageURI;

      }
        Map<String,String> userMap = new HashMap<>();
        userMap.put("name",user_name);
        userMap.put("image",download_uri.toString());
        firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Acc_Setup.this,"Updadated",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Acc_Setup.this,HomePage.class));
                    finish();


                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(Acc_Setup.this,"Firestore error :" +error,Toast.LENGTH_LONG).show();
                }
                setupProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void BringImagePicker() {
        Toast.makeText(Acc_Setup.this,"permisiion granted",Toast.LENGTH_LONG).show();
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(Acc_Setup.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainImageURI = result.getUri();
                 setupimage.setImageURI(mainImageURI);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }

    }
}
