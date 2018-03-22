package com.example.ronaldbenjamin.labryinth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {
    TextView textView;
   ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        textView=(TextView)findViewById(R.id.text2);
        imageButton=(ImageButton)findViewById(R.id.imageButton1);
        textView.setText("In Greek mythology, Daedalus, the smartest of inventors, created an impenetrable maze. It was rumored that once a person entered the maze, nobody would be able to escape. But finally, a group of intelligent demigods entered the maze and were able to navigate their way through it.\n" +
                "\n" +
                "The maze was dynamic, ever-changing, evolving and they needed to be able to adapt to it.\n" +
                "\n" +
                "This isn’t very different from computers today. Nothing ever stays static, except a java main function. Computer Science people have a key to the world written in 1’s & 0’s which is noncomprehensible by most other people. \n" +
                "\n" +
                "While the CS department's Computer Academy has been around since 1997 organizing all of the fests (Gateways, Interface, Techleons) behind the scenes, Labyrinth was created in order to give this academy a front end. Labyrinth, the Computer Science Club of Christ University was created solely to give students a platform to display their talents as well as grow while having the opportunity of exploring various topics in the Computer Science world. Labyrinth holds a variety of events every semester, ranging from technical events like coding" +
                " & debugging competitions, logical puzzle challenges, web development/designing contests as well as non-technical events such as fastest fingers," +
                " gaming and so on. The club can also hold workshops or seminars, conducted by industry professionals, on topics based on student suggestions.\n" +
                " \n" +
                "Lastly, open source projects are a huge challenge to every individual computer fanatic. However, it's often difficult for the average student to try and contribute to an open source project as much as they'd like to. Labyrinth gather students with the will to learn and contribute towards different open source projects and have them work as a team towards the same goal.");

              imageButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      startActivity(new Intent(AboutUs.this,HomePage.class));
                      finish();
                  }
              });
    }
}
