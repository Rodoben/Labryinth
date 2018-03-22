package com.example.ronaldbenjamin.labryinth;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BlogPage extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_page);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mBlogList = (RecyclerView) findViewById(R.id.Blog_list);
        mBlogList.setHasFixedSize(true);

        linearLayoutManager = (new LinearLayoutManager(this));
        linearLayoutManager.setReverseLayout(true);
        mBlogList.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.design_blog_page,
                BlogViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(), model.getImage());
            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }
        public static class BlogViewHolder extends RecyclerView.ViewHolder {

            View mView;

            public BlogViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
            }

            public void setTitle(String title) {
                TextView post_title = (TextView) mView.findViewById(R.id.post_title);
                post_title.setText(title);


            }

            public void setDesc(String desc) {
                TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
                post_desc.setText(desc);

            }

            public void setImage(Context context, String image) {
                ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
                Picasso.with(context).load(image).into(post_image);
            }

        }

}
