package com.example.instasocial.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instasocial.Post;
import com.example.instasocial.PostAdapter;
import com.example.instasocial.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {


    private RecyclerView rvPost;
    private final String TAG = "PostFragment";
    private PostAdapter adapter;
    private List<Post> posts;


    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        posts = new ArrayList<>();
        adapter = new PostAdapter(getContext(),posts);
        rvPost = view.findViewById(R.id.rvPost);
        rvPost.setAdapter(adapter);
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPost();
    }
    private void queryPost(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> igPosts, ParseException e) {
                if(e!=null){
                    Log.e(TAG,"Issue with the fetch");
                    return;
                }
                    posts.addAll(igPosts);
                    adapter.notifyDataSetChanged();
                for(Post post:posts){

                    Log.e(TAG,post.getDescription());
               }
            }
        });
    }
}