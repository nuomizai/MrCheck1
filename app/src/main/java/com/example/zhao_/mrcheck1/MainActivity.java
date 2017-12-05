package com.example.zhao_.mrcheck1;

/**
 * Created by 万珂嘉 on 2017/12/3.
 */
import android.content.Intent;
import android.os.Message;
import java.util.Calendar;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.AdapterView;
import org.json.*;

import okhttp3.*;
import java.io.IOException;

public class MainActivity  extends  AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private Button add,btn;
    private EditText money,ps;
    private String money1,ps1,spstr;
    private TextView text1,dateDisplay;
    private Spinner spin;

    private int mYear;
    private int mMonth;
    private int mDay;
    private String nYear;
    private String nMonth;
    private String nDay;
    static final int DATE_DIALOG_ID = 0;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq=(String)msg.obj;
                Log.i("RegistActivity",qq);
                text1.setText(qq);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
/**
 * 初始化数据
 */
        money= (EditText) findViewById(R.id.editmoney);
        ps= (EditText) findViewById(R.id.editps);
        add= (Button) findViewById(R.id.add);
        text1= (TextView) findViewById(R.id.return0);
        spin= (Spinner) findViewById(R.id.classifyspin);
        spstr = (String) spin.getSelectedItem();
        btn = (Button) findViewById(R.id.dateChoose);
        dateDisplay= (TextView) findViewById(R.id.dateDisplay);

/**
 * 注册按钮监听
 */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取相关参数
                money1=money.getText().toString().trim();
                ps1=ps.getText().toString().trim();
                nYear=String.valueOf(mYear);
                nMonth=String.valueOf(mMonth);
                Log.d("money",money1);
                Log.d("ps",ps1);
                Log.d("Year",nYear);
                Log.d("Month",nMonth);
                Log.d("Day",nDay);
                //通过okhttp发起post请求
                postRequest();

            }
        });
        btn .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        spin.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //拿到被选择项的值
                spstr = (String) spin.getSelectedItem();
                Log.d("type",spstr);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        } );
        //获得当前时间
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //显示当前时间
        updateDisplay();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_checkbook) {

            // Handle the camera action
        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_report) {

        } else if (id == R.id.nav_message) {
            Intent intent = new Intent();
            intent.setClass(this, MyinforActivity.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_quit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    // updates the date we display in the TextView
    private void updateDisplay() {
        dateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    /**
     */
    private void postRequest()  {
        //建立请求表单，添加上传服务器的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody formBody = new FormBody.Builder()
                        .add("uname", "1")
                        .add("Type", spstr)
                        .add("Money", money1)
                        .add("Remark", ps1)
                        .add("Year",nYear)
                        .add("Month", nMonth)
                        .add("Day", nDay)
                        .build();
                //发起请求
                final Request request = new Request.Builder()
                        .url("http://111.230.237.110/Addbill.php")
                        .post(formBody)
                        .build();

                try {
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}).start();}

    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONArray jsonArray=new JSONArray(jsonData);
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            String msg=jsonObject.getString("msg");
            Log.d("mainActivity","msg is"+msg);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
