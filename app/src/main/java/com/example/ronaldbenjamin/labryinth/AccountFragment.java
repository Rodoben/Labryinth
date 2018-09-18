package com.example.ronaldbenjamin.labryinth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AccountFragment extends Fragment {

    private DocumentSnapshot lastVisible;


    private RecyclerView blog_list_view1;

    private List<BlogPost> blog_List;
    private FirebaseFirestore firebaseFirestore;
    private  NotificationRecyclerAdapter notificationRecyclerAdapter;



    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);



        blog_List=new ArrayList<>();
        blog_list_view1= view.findViewById(R.id.blog_list_view);

        notificationRecyclerAdapter= new NotificationRecyclerAdapter(blog_List);
        blog_list_view1.setLayoutManager(new LinearLayoutManager(container.getContext()));
        blog_list_view1.setAdapter(notificationRecyclerAdapter);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Post").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {


                for (DocumentChange doc : documentSnapshots.getDocumentChanges()){


                    if (doc.getType()==DocumentChange.Type.ADDED){
                        BlogPost blogPost=doc.getDocument().toObject(BlogPost.class);


                        blog_List.add(blogPost);

                     notificationRecyclerAdapter.notifyDataSetChanged();

                    }else {
                        Log.w(TAG, "Error adding event document", e);
                        Toast.makeText(getContext(),"Event document could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                }



            }

        });



        // Inflate the layout for this fragment
        return view;
    }

}
