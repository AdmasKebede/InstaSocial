package com.example.instasocial.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instasocial.MainActivity;
import com.example.instasocial.Post;
import com.example.instasocial.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComposeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE=42;
    private EditText description;
    private Button captureImage;
    private ImageView postImage;
    private Button submit;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    public static final String TAG = "ComposeFragment";

    public ComposeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComposeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComposeFragment newInstance(String param1, String param2) {
        ComposeFragment fragment = new ComposeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        description=view.findViewById(R.id.description);
        captureImage=view.findViewById(R.id.btnTakePicture);
        postImage=view.findViewById(R.id.postImage);
        submit=view.findViewById(R.id.btnSubmit);


        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera(v);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descrip = description.getText().toString();
                if(descrip.isEmpty() ) {
                    Toast.makeText(getContext(), "Description can not be empty", Toast.LENGTH_SHORT).show();
                    return;}
                if(postImage.getDrawable()==null || photoFile == null){
                    Toast.makeText(getContext(), "no picture", Toast.LENGTH_SHORT).show();
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(descrip,currentUser,photoFile);
            }
        });
    }
    private void savePost(String text, ParseUser currentUser,File photoFile) {
        Post post = new Post();
        post.setDescription(text);
        post.setUser(currentUser);
        post.setImage(new ParseFile(photoFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Log.d(TAG,"Error trying to save the post");

                }
                description.setText("");
                postImage.setImageResource(0);
            }
        });

    }
    private void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUrl(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(getContext(),"com.codepath.fileprovider",photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider);
        if(intent.resolveActivity(getContext().getPackageManager())!=null){
            startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            //if(resultCode == RESULT_OK){
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                postImage.setImageBitmap(takenImage);
           // }else{
                Toast.makeText(getContext(), "Failed to get the picture taken"+requestCode, Toast.LENGTH_SHORT).show();
           // }
        }

    }
    private File getPhotoFileUrl(String fileName) {

        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),TAG);
        if(!mediaStorageDir.exists() && !mediaStorageDir.mkdir()){
            Log.d(TAG,"failed to create directory");
        }

        return  new File(mediaStorageDir.getPath()+File.separator+fileName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }
}