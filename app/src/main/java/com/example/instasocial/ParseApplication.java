package com.example.instasocial;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("QhcPEoqJ6pk1lCtWAQ5Jvl7SoD8fLwbpcYfQ9RiQ")
                .clientKey("zdt6rRQvCqlm0Zyf3YcG3qVWcDcaHhV7Z86kja2N")
                        .server("https://parseapi.back4app.com").build());
    }
}
