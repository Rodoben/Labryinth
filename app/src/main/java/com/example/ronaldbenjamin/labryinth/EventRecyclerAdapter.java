package com.example.ronaldbenjamin.labryinth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {

    public List<BlogPost> blog_List;
    public Context context;
    private FirebaseFirestore firebaseFirestore;

    public EventRecyclerAdapter(List<BlogPost> blog_List) {
        this.blog_List = blog_List;
    }

    @NonNull
    @Override
    public EventRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new EventRecyclerAdapter.ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final EventRecyclerAdapter.ViewHolder holder, int position) {

        String desc_data = blog_List.get(position).getDesc();
        holder.setDesc(desc_data);

        String image_url = blog_List.get(position).getImage_url();
        holder.setBlogImage(image_url);

        String user_id = blog_List.get(position).getUser_id();
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    String userName = task.getResult().getString("name");
                    String userImage = task.getResult().getString("image");
                 //   holder.setUserData(userName,userImage);

                }
                else {
                    //
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return blog_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public ImageView blogImageView;




        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDesc(String descText) {
            TextView descView = mView.findViewById(R.id.blog_desc);
            descView.setText(descText);

        }

        public void setBlogImage(String downloadUri) {

            blogImageView = mView.findViewById(R.id.blog_image);
            Glide.with(context.getApplicationContext()).load(downloadUri).into(blogImageView);
        }

      //  public void setUserData(String name, String image) {

         //   blogUserImage = mView.findViewById(R.id.blog_user_image);
           //   blogUserName = mView.findViewById(R.id.blog_user_name);



           // RequestOptions placeholderOption = new RequestOptions();
        //    placeholderOption.placeholder(R.drawable.picture_box);

        //    Glide.with(context.getApplicationContext()).applyDefaultRequestOptions(placeholderOption).load(image).into(blogUserImage);


      //  }
    }
}
