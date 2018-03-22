package com.example.ronaldbenjamin.labryinth;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class PostActivity extends AppCompatActivity {

    private static final int MAX_LENGTH = 100 ;
    private ImageButton newPostImage;
    private EditText newPostDesc,newPostTitle;
    private Button newPostBtn;
    private Uri postImageUri=null;
    private ProgressBar progressBar;
    private Bitmap compressedImageFile;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
          private FirebaseAuth firebaseAuth;
          private  String current_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
       firebaseAuth=FirebaseAuth.getInstance();
       current_user_id=firebaseAuth.getCurrentUser().getUid();
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        newPostImage=findViewById(R.id.imageButton);
        newPostDesc=findViewById(R.id.editText2);
        newPostTitle=findViewById(R.id.editText);
        newPostBtn=findViewById(R.id.button2);
        progressBar=findViewById(R.id.progressBar4);


        newPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(PostActivity.this);

            }
        });
        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String desc=newPostDesc.getText().toString().trim();
                final String title=newPostDesc.getText().toString().trim();
                    if (!TextUtils.isEmpty(desc) && !TextUtils.isEmpty(title) && postImageUri!=null){
                        Toast.makeText(getApplicationContext(),"utils checked",Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.VISIBLE);

                              final String randomName= UUID.randomUUID().toString();
                        StorageReference filepath=storageReference.child("post_images").child(randomName+".jpg");
                        Toast.makeText(getApplicationContext(),"path checked",Toast.LENGTH_SHORT).show();

                        filepath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                                final String downloadUri=task.getResult().getDownloadUrl().toString();


                                if (task.isSuccessful()){

                                    File newImageFile=new File(postImageUri.getPath());
                                    try {
                                        compressedImageFile = new Compressor(PostActivity.this)
                                                .setMaxHeight(100)
                                                .setMaxWidth(100)
                                                .setQuality(2)

                                                .compressToBitmap(newImageFile);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    byte[] thumbData = baos.toByteArray();

                                    UploadTask uploadTask=storageReference.child("post_images/thumbs")
                                            .child(randomName +".jpg").putBytes(thumbData);
                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            Toast.makeText(getApplicationContext(),"filepath checked",Toast.LENGTH_SHORT).show();
                                            String downloadthumb=taskSnapshot.getDownloadUrl().toString();
                                            Map<String,Object> postMap=new HashMap<>();
                                            postMap.put("title",title);
                                            postMap.put("image_url",downloadUri);
                                            postMap.put("image_thumb",downloadthumb);
                                            postMap.put("desc",desc);
                                            postMap.put("user_id",current_user_id);
                                            postMap.put("timestamp",FieldValue.serverTimestamp());
                                            Toast.makeText(getApplicationContext(),"postMap checked",Toast.LENGTH_SHORT).show();




                                            firebaseFirestore.collection("Post").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(PostActivity.this,"Post was Added",Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(PostActivity.this,HomePage.class));
                                                        finish();


                                                    }else {
                                                        String erroe=task.getException().getMessage();
                                                        Toast.makeText(getApplicationContext(),"Error:"+erroe,Toast.LENGTH_LONG).show();


                                                    }
                                                    progressBar.setVisibility(View.INVISIBLE);



                                                }
                                            });


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {


                                        }
                                    });





                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    String erroe=task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(),"Error2:"+erroe,Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                    }

            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                postImageUri=result.getUri();
                newPostImage.setImageURI(postImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }

    }

    //random string creation.
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
