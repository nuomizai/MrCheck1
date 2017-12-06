package com.example.zhao_.mrcheck1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReportActivity extends AppCompatActivity {
    private Button Btmonth,Btyear,back,Btquery;
    private Spinner Spyear,Spmonth;
    Calendar c= Calendar.getInstance();
    private String year;
    private String month1;
    private String month2;
    private String day1;
    private String day2;
    private String name;
    private ArrayAdapter<String> adapteryear;
    private ArrayAdapter<String> adaptermonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_form);

        back=(Button)findViewById(R.id.back);
        Btyear=(Button)findViewById(R.id.report_form_year);
        Btmonth=(Button)findViewById(R.id.report_form_month);
        Btquery=(Button)findViewById(R.id.report_form_query);
        Spyear=(Spinner)findViewById(R.id.report_form_history_year);
        Spmonth=(Spinner)findViewById(R.id.report_form_history_month);

        Spyear.setAdapter(adapteryear);
        Spmonth.setAdapter(adaptermonth);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(ReportActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Btmonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                year=String.valueOf(c.get(Calendar.YEAR));
                month1=String.valueOf(c.get(Calendar.MONTH));
                month2=month1;
                day1="1";
                day2="31";
                postRequest();
            }
        });

        Btyear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                year=String.valueOf(c.get(Calendar.YEAR));
                month1="1";
                month2=String.valueOf(c.get(Calendar.MONTH));
                day1="1";
                day2=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                postRequest();
            }
        });

        /*Spyear.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected( adapteryear<?> arg0, View arg1, int arg2, long arg3) {
                year = Activity.this.getResources().getStringArray(R.array.year)[arg2];
            }
        });*/
    }

    private void postRequest(){
        name=((GetActivity)getApplicationContext()).getUsername();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody formBody= new FormBody.Builder()
                        .add("Year1",year)
                        .add("Month1",month1)
                        .add("Day1",day1)
                        .add("Year2",year)
                        .add("Month2",month2)
                        .add("Day2",day2)
                        .build();
                final Request request=new Request.Builder()
                        .url("http://111.230.237.110/Statementbill.php")
                        .post(formBody)
                        .build();
                try{
                    OkHttpClient client= new OkHttpClient();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
