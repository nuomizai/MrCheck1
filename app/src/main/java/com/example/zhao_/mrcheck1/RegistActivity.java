package com.example.zhao_.mrcheck1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.os.Message;
import org.json.*;

import okhttp3.*;
import java.io.IOException;

public class RegistActivity extends AppCompatActivity {
    private Button reg,back;
    private EditText uname,pwd,intro;
    private String name1,pwd1,intro1;
    private TextView text1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0){
                String qq="注册成功";
                Log.i("RegistActivity",qq);
                text1.setText(qq);
            }
            if(msg.what==1){
                String qq="用户名已被占用";
                Log.i("RegistActivity",qq);
                text1.setText(qq);
            }
            if(msg.what==2){
                String qq="用户名长度错误";
                Log.i("RegistActivity",qq);
                text1.setText(qq);
            }
            if(msg.what==3){
                String qq="密码长度错误";
                Log.i("RegistActivity",qq);
                text1.setText(qq);
            }
            if(msg.what==4){
                String qq="失败";
                Log.i("RegistActivity",qq);
                text1.setText(qq);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        uname=(EditText)findViewById(R.id.username);
        pwd=(EditText)findViewById(R.id.password);
        intro=(EditText)findViewById(R.id.introduction);
        text1=(TextView)findViewById(R.id.response);
        reg=(Button)findViewById(R.id.registButton);
        back=(Button)findViewById(R.id.back);

        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name1 = uname.getText().toString().trim();
                pwd1 = pwd.getText().toString().trim();
                intro1 = intro.getText().toString().trim();
                Log.d("RegistActivity", "name" + name1);
                Log.d("RegistActivity", "password" + pwd1);
                Log.d("RegistActivity", "introduction" + intro1);
                postRequest();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
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
                        .url("http://111.230.237.110/Signin.php")
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
            Log.d("RegistActivity","msg is"+msg);
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
                    if(msg.equals("2")){
                        message.what=2;
                    }
                    if(msg.equals("3")){
                        message.what=3;
                    }
                    if(msg.equals("4")){
                        message.what=4;
                    }
                    mHandler.sendMessage(message);
                }
            }).start();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

