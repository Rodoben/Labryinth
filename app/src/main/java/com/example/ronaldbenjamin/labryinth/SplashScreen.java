package com.example.ronaldbenjamin.labryinth;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class SplashScreen extends AppCompatActivity {
    LinearLayout l1, l2;
    Button button;
    Animation uptodown, downtoup;
    LinearLayout activity_splash_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        button = (Button) findViewById(R.id.button1);
        l1 = (LinearLayout) findViewById(R.id.l1);
        activity_splash_screen = (LinearLayout) findViewById(R.id.linerlayout);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        l1.setAnimation(uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        l2.setAnimation(downtoup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashScreen.this, LoginPage.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            //  Toast.makeText(HomePage.this,"Homepage",Toast.LENGTH_LONG).show();
            startActivity(new Intent(SplashScreen.this, LoginPage.class));

            finish();
        } else {
            startActivity(new Intent(SplashScreen.this, HomePage.class));
            finish();


        }


    }
}
