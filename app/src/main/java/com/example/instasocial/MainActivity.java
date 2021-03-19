package com.example.instasocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.instasocial.fragment.ComposeFragment;
import com.example.instasocial.fragment.PostsFragment;
import com.example.instasocial.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // toolbar = findViewById(R.id.toolbar);
       // toolbar.setTitle("");
        //setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment;
                fragment = new ComposeFragment();
                switch(item.getItemId()){
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        //compose here
                        break;
                    case R.id.action_home:
                        //home here
                        fragment = new PostsFragment();
                        break;
                    case R.id.action_profile:
                        //profile here
                        fragment = new ProfileFragment();
                        break;
                    default:
                        fragment = new ComposeFragment();
                }
                fragmentManager.beginTransaction().replace(R.id.simpleContainer,fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}