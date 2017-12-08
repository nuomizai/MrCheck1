package com.example.zhao_.mrcheck1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import android.widget.AdapterView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.SimpleAdapter;
import java.util.Objects;


public class MyinforActivity extends AppCompatActivity
{

    private TextView uname;
    private EditText pwd;
    private EditText intro;
    private String name1,pwd1,intro1;
    private TextView text1;
    private Button submmit,back;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0){
                String qq="修改成功";
                Log.i("MyinforActivity",qq);
                text1.setText(qq);
            }
            if(msg.what==1){
                String qq="修改失败";
                Log.i("MyinforActivity",qq);
                text1.setText(qq);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfor);
        uname = (TextView)findViewById(R.id.edit_username);
        uname.setText(Data.username);
        pwd=(EditText)findViewById(R.id.edit_password);
        pwd.setText(Data.password);
        //pwd.setKeyListener(null);
        pwd.setFocusable(false);
        pwd.setFocusableInTouchMode(false);
        pwd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                pwd.setFocusableInTouchMode(true);
                pwd.setFocusable(true);
                pwd.requestFocus();
            }
        });


        intro=(EditText)findViewById(R.id.edit_message);
        intro.setFocusable(false);
        intro.setFocusableInTouchMode(false);
        intro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                intro.setFocusableInTouchMode(true);
                intro.setFocusable(true);
                intro.requestFocus();
            }
        });
        text1=(TextView)findViewById(R.id.edit_response);
        submmit=(Button)findViewById(R.id.submmitButton);
        back=(Button)findViewById(R.id.back);

        name1=Data.username;
        Log.i("MyinforActivity", "name" + name1);
        postForintro();


        submmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name1 = uname.getText().toString().trim();
                pwd1 = pwd.getText().toString().trim();
                intro1 = intro.getText().toString().trim();
                Log.d("MyinforActivity", "name" + name1);
                Log.d("MyinforActivity", "password" + pwd1);
                Log.d("MyinforActivity", "introduction" + intro1);
                postRequest();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MyinforActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void postRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody formBody= new FormBody.Builder()
                        .add("uname",name1)
                        .add("pwd",pwd1)
                        .add("introduction",intro1)
                        .build();
                final Request request=new Request.Builder()
                        .url("http://111.230.237.110/Changemessage.php")
                        .post(formBody)
                        .build();
                try{
                    OkHttpClient client= new OkHttpClient();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJSONWithJSONObject(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONObject jsonObject=new JSONObject(jsonData);
            final String msg=jsonObject.getString("Msg");
            Log.d("MyinforActivity","msg is"+msg);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message=new Message();
                    if(msg.equals("0"))
                    {
                        message.what=0;
                    }
                    if(msg.equals("1")){
                        message.what=1;
                    }
                    mHandler.sendMessage(message);
                }
            }).start();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //获取个性签名
    private void postForintro(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody formBody= new FormBody.Builder()
                        .add("uname",name1)
                        .build();
                final Request request=new Request.Builder()
                        .url("http://111.230.237.110/Returnmessage.php")
                        .post(formBody)
                        .build();
                try{
                    OkHttpClient client= new OkHttpClient();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJSONWithJSONObjectFORINTRO(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObjectFORINTRO(String jsonData){
        try{
            JSONObject jsonObject=new JSONObject(jsonData);

            String introduction=jsonObject.getString("Introduction");
            Log.d("MyinforActivity",introduction);
            intro.setText(introduction);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
