package com.example.zhao_.mrcheck1;

import android.app.Application;

public class GetActivity extends Application {
    private String username,password;
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public void setUsername(String a){
        username=a;
    }
    public void setPassword(String a){
        password=a;
    }
    @Override
    public void onCreate(){
        super.onCreate();
    }
}
