package com.example.ronaldbenjamin.labryinth;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {



   private   FirebaseAuth mAuth;

   private BottomNavigationView mainbottomNav;
   private HomeFragment homeFragment;
   private NotificationFragment notificationFragment;
   private ContactFragment contactFragment;
   private AccountFragment accountFragment;
   private NotiFragment notiFragment;

private  String current_user_id;
   private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainbottomNav=findViewById(R.id.navigation);

        //Fragments
        homeFragment=new HomeFragment();
        notificationFragment= new NotificationFragment();
        accountFragment=new AccountFragment();
        notiFragment=new NotiFragment();
        contactFragment=new ContactFragment();
        replaceFragment(homeFragment);

        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){

                   case R.id.action_navigation:
                       Toast.makeText(HomePage.this,"notification",Toast.LENGTH_LONG).show();
                       replaceFragment(notiFragment);
                       Toast.makeText(HomePage.this,"notification replaced",Toast.LENGTH_LONG).show();

                       return true;

                   case R.id.action_search:
                       Toast.makeText(HomePage.this,"account",Toast.LENGTH_LONG).show();

                       replaceFragment(accountFragment);
                       Toast.makeText(HomePage.this,"account replaced",Toast.LENGTH_LONG).show();

                       return  true;
                   case R.id.action_settings:
                       Toast.makeText(HomePage.this,"home",Toast.LENGTH_LONG).show();

                       replaceFragment(homeFragment);
                       Toast.makeText(HomePage.this,"home replaced",Toast.LENGTH_LONG).show();

                       return true;
                       default:
                           Toast.makeText(HomePage.this,"out",Toast.LENGTH_LONG).show();

                           return false;

               }

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            startActivity(new Intent(HomePage.this,PostActivity.class));


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
   public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_home) {



        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(HomePage.this,AboutUs.class));
            finish();

        } else if (id == R.id.nav_event) {
            startActivity(new Intent(HomePage.this,Event.class));
             finish();
        } else if (id == R.id.nav_blog) {

            startActivity(new Intent( HomePage.this,BlogPage.class));

        } else if (id == R.id.nav_team) {
            Toast.makeText(getApplicationContext(),"team entered",Toast.LENGTH_LONG).show();


            startActivity(new Intent(HomePage.this,Team.class));
            finish();

        }
        else if (id==R.id.nav_enquiry) {
           replaceFragment(contactFragment);

        }
        else if (id==R.id.nav_projects){
            startActivity(new Intent(HomePage.this,Projects.class));
            finish();


        }
        else if (id==R.id.nav_share){
            mAuth.signOut();
            startActivity(new Intent(HomePage.this,LoginPage.class));
            finish();

        }else if (id==R.id.nav_send){

            startActivity(new Intent(HomePage.this,Acc_Setup.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser==null){
            Toast.makeText(HomePage.this,"Homepage",Toast.LENGTH_LONG).show();
            startActivity(new Intent(HomePage.this,LoginPage.class));
            finish();
        }
        else {
           current_user_id=mAuth.getCurrentUser().getUid();
           firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                   if (task.isSuccessful()){
                       if (task.getResult().exists()){
                           Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_LONG).show();



                       }

                   }
                   else {
                       String erroe=task.getException().getMessage();
                       Toast.makeText(getApplicationContext(),"Error:"+erroe,Toast.LENGTH_LONG).show();

                   }
               }
           });
        }

    }
  private void replaceFragment(Fragment fragment){
     FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
     fragmentTransaction.replace(R.id.frameLayout,fragment);
     fragmentTransaction.commit();


  }

}
