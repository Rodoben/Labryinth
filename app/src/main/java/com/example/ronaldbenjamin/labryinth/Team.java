package com.example.ronaldbenjamin.labryinth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Team extends AppCompatActivity {

    private  RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private DatabaseReference mBlogDatabase;
    private LinearLayoutManager linearLayoutManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        mDatabase=FirebaseDatabase.getInstance().getReference("Team");
        mBlogDatabase=FirebaseDatabase.getInstance().getReference("Blog");
        mBlogList=(RecyclerView)findViewById(R.id.Blog_list);
        mBlogList.setHasFixedSize(true);

        linearLayoutManager=(new LinearLayoutManager(this));
        linearLayoutManager.setReverseLayout(true);
        mBlogList.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Blog,BlogPage.BlogViewHolder>firebaseRecyclerAdapter1=new FirebaseRecyclerAdapter<Blog, BlogPage.BlogViewHolder>(


                Blog.class,
                R.layout.design_blog_page,
                BlogPage.BlogViewHolder.class,
                mBlogDatabase
        ) {
            @Override
            protected void populateViewHolder(BlogPage.BlogViewHolder viewHolder, Blog model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(), model.getImage());


            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter1);



        FirebaseRecyclerAdapter<Teamlist,TeamViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Teamlist, TeamViewHolder>(

                Teamlist.class,
                R.layout.design_team,
                TeamViewHolder.class,
                mDatabase



        ) {
            @Override
            protected void populateViewHolder(TeamViewHolder viewHolder, Teamlist model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setSemester(model.getSemester());
                viewHolder.setWork(model.getWork());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setImage(getApplicationContext(),model.getImage());
            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder{
                     View mView;
        public TeamViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setName(String name){
            TextView post_name=(TextView)mView.findViewById(R.id.post_name);
            post_name.setText(name);
        }
        public void setSemester(String semester){
            TextView post_semester=(TextView)mView.findViewById(R.id.post_class);
            post_semester.setText(semester);
        }
        public void setWork(String work){
            TextView post_work=(TextView)mView.findViewById(R.id.post_activity);
            post_work.setText(work);
        }
        public void setEmail(String email){
            TextView post_email=(TextView)mView.findViewById(R.id.post_email);
            post_email.setText(email);
        }
        public  void setImage(Context context,String image){
            ImageView post_image=(ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(context).load(image).into(post_image);

        }
    }



}
