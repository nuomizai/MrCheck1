package com.example.zhao_.mrcheck1;

<<<<<<< HEAD
/**
 * Created by 万珂嘉 on 2017/12/3.
 */
import android.content.DialogInterface;
import android.os.Message;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
=======
import android.content.Intent;
>>>>>>> afe56d60f2427e8d8d66008f539c811035389fda
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
<<<<<<< HEAD
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.widget.SimpleAdapter;
import org.json.*;
import okhttp3.*;

public class MainActivity  extends  AppCompatActivity {
    private Button add,btn,recent;
    private EditText money,ps;
    private String money1,ps1,spstr;
    private TextView text1,dateDisplay;
    private Spinner spin;
    private ListView lt1;

    private int mYear;
    private int mMonth;
    private int mDay;
    private String nYear;
    private String nMonth;
    private String nDay;
    static final int DATE_DIALOG_ID = 0;
    private SimpleAdapter simplead;
    private final List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();

    private Handler lHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0){
                String qq="更新成功";
                Log.i("RegistActivity",qq);
                simplead.notifyDataSetChanged();
                text1.setText(qq);
            }
        }
    };
    private Handler dHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0){
                String qq="删除成功";
                Log.i("RegistActivity",qq);
                text1.setText(qq);
                simplead.notifyDataSetChanged();
            }
            if(msg.what==1){
                String qq="删除失败";
                Log.i("RegistActivity",qq);
                text1.setText(qq);
            }

        }
    };
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0){
                String qq="添加成功";
                Log.i("RegistActivity",qq);
                text1.setText(qq);
                simplead.notifyDataSetChanged();
            }
            if(msg.what==1){
                String qq="添加失败";
                Log.i("RegistActivity",qq);
                text1.setText(qq);
            }

        }
    };
=======
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

>>>>>>> afe56d60f2427e8d8d66008f539c811035389fda
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
<<<<<<< HEAD
/**
 * 初始化数据
 */


        simplead = new SimpleAdapter(this, listems,
                R.layout.list_item, new String[]{"mymoney", "mytype", "myps","mytime"},
                new int[]{R.id.mymoney, R.id.mytype, R.id.myps, R.id.mytime});

        money = (EditText) findViewById(R.id.editmoney);
        ps = (EditText) findViewById(R.id.editps);
        add = (Button) findViewById(R.id.add);
        text1 = (TextView) findViewById(R.id.return0);
        spin = (Spinner) findViewById(R.id.classifyspin);
        spstr = (String) spin.getSelectedItem();
        btn = (Button) findViewById(R.id.dateChoose);
        recent = (Button) findViewById(R.id.recent);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        lt1 = (ListView) findViewById(R.id.listview);
        lt1.setAdapter(simplead);

        try {
            Log.i("mainactivity","postask in");
            postAsk();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("mainactivity","postask out");
            e.printStackTrace();
        }
/**
 * 注册按钮监听
 */
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listems.clear();
                postAsk();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取相关参数
                money1=money.getText().toString().trim();
                ps1=ps.getText().toString().trim();
                money.setText("");
                ps.setText("");
                nYear=String.valueOf(mYear);
                nMonth=String.valueOf(mMonth);
                nDay=String.valueOf(mDay);
                Log.d("money",money1);
                Log.d("ps",ps1);
                Log.d("Year",nYear);
                Log.d("Month",nMonth);
                Log.d("Day",nDay);
                //通过okhttp发起post请求
                listems.clear();
                postRequest();
                postAsk();
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
        lt1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           int position, long id) {
                DeleteDialog(position);
                return true;
            }
        });
        //获得当前时间
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //显示当前时间
        updateDisplay();
=======
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
>>>>>>> afe56d60f2427e8d8d66008f539c811035389fda
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
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            this.startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent();
            intent.setClass(this, SearchActivity.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_report) {
            Intent intent = new Intent();
            intent.setClass(this, ReportActivity.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_message) {
            Intent intent = new Intent();
            intent.setClass(this, MyinforActivity.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_quit) {
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            this.startActivity(intent);

<<<<<<< HEAD
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

    private void DeleteDialog(int id) {
        AlertDialog.Builder builder = new Builder(MainActivity.this);
        final int ide=id;
        builder.setMessage("确定删除文件?");
        builder.setTitle("提示");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//这里File构造方法参数就是从你list读取的文件路径
                postDelet(ide);
//通知adapter 更新
                Log.d("postdelete","0k");
                listems.clear();
                postAsk();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
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

    private void postAsk()  {
        //建立请求表单，添加上传服务器的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody listBody = new FormBody.Builder()
                        .add("uname", "1")
                        .build();
                //发起请求
                final Request request = new Request.Builder()
                        .url("http://111.230.237.110/Searchbill.php")
                        .post(listBody)
                        .build();
                Log.d("activity","OKHttp");
                try {
                    OkHttpClient client = new OkHttpClient();
                    Response responses = client.newCall(request).execute();
                    String responseDatas = responses.body().string();
                    Log.d("mainactivity","response"+responseDatas);
                    listJSONWithJSONObject(responseDatas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}).start();}
    private void postDelet(int id)  {
        //建立请求表单，添加上传服务器的参数
        Map<String, Object> map = listems.get(id);
        Log.d("delete","0");
        Object get=map.get("Countid");
        final  String value = (String) get;    //获取指定的value值
        Log.d("mainactivity","value"+value);
        Log.d("delete","1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody listBody = new FormBody.Builder()
                        .add("Countid", value)
                        .build();
                //发起请求
                Log.d("delete","2");
                final Request request = new Request.Builder()
                        .url("http://111.230.237.110/Deletebill.php")
                        .post(listBody)
                        .build();
                Log.d("activity","OKHttp");
                try {
                    OkHttpClient client = new OkHttpClient();
                    Response responses = client.newCall(request).execute();
                    String responseDatas = responses.body().string();
                    Log.d("mainactivity","response"+responseDatas);
                    deleteJSONWithJSONObject(responseDatas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}).start();}

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

                    mHandler.sendMessage(message);
                }
            }).start();


        }catch(Exception e){
            e.printStackTrace();
=======
>>>>>>> afe56d60f2427e8d8d66008f539c811035389fda
        }
    }

    private void deleteJSONWithJSONObject(String jsonData){
        try{

<<<<<<< HEAD
            JSONObject jsonObject=new JSONObject(jsonData);
            Log.d("delete","3");
            final String msg=jsonObject.getString("Msg");
            Log.d("deleteActivity","msg is"+msg);
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
                    Log.d("delete","4");
                    dHandler.sendMessage(message);
                }
            }).start();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void listJSONWithJSONObject(String jsonData){

        try{
            int i;
            JSONObject jsonObject=new JSONObject(jsonData);
            for ( i = 0; i < jsonObject.length()-1; i++) {
                JSONObject inObject = jsonObject.getJSONObject(String.valueOf(i));
                Log.d("mainactivity","i="+String.valueOf(i));
                Map<String, Object> listem = new HashMap<String, Object>();
                String mymoney = inObject.getString("Money");
                String myps = inObject.getString("Remark");
                String mytype = inObject.getString("Type");
                String mytime = inObject.getString("Date");
                String myid = inObject.getString("Countid");
                Log.d("MainActivity", "mymoney=" + mymoney);
                listem.put("mymoney", mymoney);
                listem.put("mytype", mytype);
                listem.put("myps", myps);
                listem.put("mytime", mytime);
                listem.put("myid", myid);
                listems.add(listem);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message=new Message();
                    if(true)
                    {
                        message.what=0;
                    }
                    lHandler.sendMessage(message);
                }
            }).start();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


=======
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
>>>>>>> afe56d60f2427e8d8d66008f539c811035389fda
}
