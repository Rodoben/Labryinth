package com.example.ronaldbenjamin.labryinth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupPage extends AppCompatActivity {

    private EditText reg_email_field,reg_pass_field,reg_cnf_pass_field;
    private Button Signup;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar3);

        reg_email_field=(EditText)findViewById(R.id.editText9);
        reg_cnf_pass_field=(EditText)findViewById(R.id.editText11);
        reg_pass_field=(EditText)findViewById(R.id.editText10);
        Signup=(Button)findViewById(R.id.button5);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();

            }
        });


    }

    private void signup() {
        String email=reg_email_field.getText().toString().trim();
        String pass=reg_pass_field.getText().toString().trim();
        String cnfpass=reg_cnf_pass_field.getText().toString().trim();


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)&& !TextUtils.isEmpty(cnfpass)){
            Toast.makeText(SignupPage.this,"No fields empty",Toast.LENGTH_LONG).show();

            if (pass.equals(cnfpass)){
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(SignupPage.this,"Password matched",Toast.LENGTH_LONG).show();

                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignupPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(SignupPage.this,"Sign up done",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignupPage.this,Acc_Setup.class));

                        }else {
                            String errormessage = task.getException().getMessage();
                            Toast.makeText(SignupPage.this, "Error:" + errormessage, Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
            else {
                Toast.makeText(SignupPage.this,"Error:Password didnt match",Toast.LENGTH_LONG).show();

            }

        }



    }

   @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentuser=mAuth.getCurrentUser();
        if (currentuser!=null){

            sendToMain();

        }
    }

    private void sendToMain() {
        Toast.makeText(SignupPage.this,"Sign up",Toast.LENGTH_LONG).show();
        startActivity(new Intent(SignupPage.this,HomePage.class));
        finish();
    }

}
