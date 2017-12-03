package com.example.zhao_.mrcheck1;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity{
    private EditText editUserName;
    private EditText editPassWord;
    private Button LButton;
    private TextView text1;
    private String name1,pwd1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0){
                //跳转页面

            }
            if(msg.what==1){
                String qq="无该用户";
                text1.setText(qq);
            }
            if(msg.what==2){
                String qq="密码错误";
                text1.setText(qq);
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LButton = (Button) findViewById(R.id.loginButton);
        editUserName=(EditText)findViewById(R.id.edit_username);
        editPassWord=(EditText)findViewById(R.id.edit_password);
        text1=(TextView)findViewById(R.id.text1);
        LButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name1=editUserName.getText().toString().trim();
                pwd1=editPassWord.getText().toString().trim();
                postRequest();
            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
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
                        .build();
                final Request request=new Request.Builder()
                        .url("http://111.230.237.110/Login.php")
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

                    mHandler.sendMessage(message);
                }
            }).start();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
