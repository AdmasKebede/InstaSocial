package com.example.instasocial;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts){
        this.context=context;
        this.posts=posts;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView userName;
        private TextView description;
        private ImageView postImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.userName);
            description = itemView.findViewById(R.id.textDescription);
            postImage = itemView.findViewById(R.id.imagePost);

        }

        public void bind(Post post) {
            userName.setText(post.getUser().getUsername());
            description.setText(post.getDescription());
            ParseFile image = post.getImage();
            if(image!=null){
            Glide.with(context).load(post.getImage().getUrl()).into(postImage);
            }
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post=posts.get(position);
        holder.bind(post);


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
