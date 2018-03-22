package com.example.ronaldbenjamin.labryinth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    private  EditText loginEmailText,loginPassText;
    private Button loginbtn;
   private   TextView textView;
   private ProgressBar progressBar;

 private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

      progressBar=findViewById(R.id.progressBar2);
        textView=(TextView)findViewById(R.id.textView2);
        mAuth=FirebaseAuth.getInstance();
   loginEmailText=(EditText)findViewById(R.id.editText3);
   loginPassText=(EditText)findViewById(R.id.editText4);
    loginbtn=(Button)findViewById(R.id.button);


    textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(LoginPage.this,SignupPage.class));
        }
    });

     loginbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {


             Toast.makeText(getApplication(),"clicked",Toast.LENGTH_LONG).show();

             String loginEmail=loginEmailText.getText().toString();
             String loginPass=loginPassText.getText().toString();

             if (!TextUtils.isEmpty(loginEmail)&& !TextUtils.isEmpty(loginPass)){
                 Toast.makeText(getApplication(),"Not Blank",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.VISIBLE);
                  mAuth.signInWithEmailAndPassword(loginEmail,loginPass).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                         if (task.isSuccessful()){
                             Toast.makeText(getApplication(),"Signed in",Toast.LENGTH_LONG).show();
                                sendToMain();
                         }
                         else {
                             String errormessage=task.getException().getMessage();
                             Toast.makeText(getApplication(),"Error:"+errormessage,Toast.LENGTH_LONG).show();

                         }
                         progressBar.setVisibility(View.INVISIBLE);

                     }
                 });
             }
         }
     });



    }



    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();
      if ( currentUser!=null){

          sendToMain();
      }

    }

    private void sendToMain() {
        Toast.makeText(LoginPage.this,"Login",Toast.LENGTH_LONG).show();
        startActivity(new Intent(LoginPage.this,HomePage.class));
        finish();
    }


}
