package com.example.zhao_.mrcheck1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private Button sch,back;
    private Spinner cls;
    private TextView text1;
    private ListView list1;
    private EditText syear,smonth,sdate,dyear,dmonth,ddate;
    private String cls1,year1,month1,date1,year2,month2,date2;
    private String uname;
    private SimpleAdapter simplead;
    private final List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();

    private Handler lHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0){
                //String qq="更新成功";
                //Log.i("RegistActivity",qq);
                simplead.notifyDataSetChanged();
                //text1.setText(qq);
            }
        }
    };
    //private String uname=((GetActivity)getApplicationContext()).getUsername();
//    private Handler mHandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg){
//            String get=(String)msg.obj;
//            String[] get2={get};
//            ArrayAdapter<String> adapter= new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,get2);
//            list1.setAdapter(adapter);
//        }
//    };

//    private Handler mHandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg){
//            String get = text1.getText().toString().trim();
//            String get2=(String)msg.obj;
//            text1.setText(get+"\n"+"\n"+get2);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        simplead = new SimpleAdapter(this, listems,
                R.layout.list_item, new String[]{"mymoney", "mytype", "myps","mytime"},
                new int[]{R.id.mymoney, R.id.mytype, R.id.myps, R.id.mytime});


        back = (Button) findViewById(R.id.back);
        list1=(ListView)findViewById(R.id.search_list);
        list1.setAdapter(simplead);
        text1=(TextView)findViewById(R.id.textView);
        sch=(Button)findViewById(R.id.search_button);
        cls=(Spinner) findViewById(R.id.classes);
        syear=(EditText)findViewById(R.id.year1);
        smonth=(EditText)findViewById(R.id.month1);
        sdate=(EditText)findViewById(R.id.date1);
        dyear=(EditText)findViewById(R.id.year2);
        dmonth=(EditText)findViewById(R.id.month2);
        ddate=(EditText)findViewById(R.id.date2);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cls.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                cls1 = (String) cls.getSelectedItem();
//                switch (cls1)
//                {
//                    case "食品用餐": cls1="food"; break;
//                    case "住房购车": cls1="house-car"; break;
//                    case "饰品衣物": cls1="clothes"; break;
//                    case "医疗教育": cls1="hos-edu"; break;
//                    case "交通出行": cls1="transport"; break;
//                    case "数码家电": cls1="3C"; break;
//                    case "大宗商品": cls1="highprice"; break;
//                    case "理财管理": cls1="money"; break;
//                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        sch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                listems.clear();
                simplead.notifyDataSetChanged();
                uname=Data.username;
                year1 = syear.getText().toString().trim();
                month1 =smonth.getText().toString().trim();
                date1 = sdate.getText().toString().trim();
                year2 =dyear.getText().toString().trim();
                month2 = dmonth.getText().toString().trim();
                date2 = ddate.getText().toString().trim();
                Log.d("SearchActivity", "classes" + cls1);
                Log.d("SearchActivity", "startyear" + year1);
                Log.d("SearchActivity", "startmonth" + month1);
                Log.d("SearchActivity", "startdate" + date1);
                Log.d("SearchActivity", "endyear" + year2);
                Log.d("SearchActivity", "endmonth" + month2);
                Log.d("SearchActivity", "enddate" + date2);
                //text1.setText("查询结果：");
                postRequest();
            }
        });

    }

    private void postRequest(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                RequestBody formBody=new FormBody.Builder()
                        .add("uname",uname)
                        .add("Type",cls1)
                        .add("Year1",year1)
                        .add("Month1",month1)
                        .add("Day1",date1)
                        .add("Year2",year2)
                        .add("Month2",month2)
                        .add("Day2",date2)
                        .build();
                final Request request=new Request.Builder()
                        .url("http://111.230.237.110/Searchbill.php")
                        .post(formBody)
                        .build();
                try {
                    OkHttpClient client = new OkHttpClient();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d("SearchActivity", "11");
                    parseJSONWithJSONObject(responseData);
                    //showResponse(responseData);
                } catch (Exception e)
                { e.printStackTrace();}
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONObject jsonObject=new JSONObject(jsonData);
            String count=jsonObject.getString("count");
            Log.d("SearchActivity",count);

            int num=Integer.parseInt(count);
            //if (num>0) text1.setText("查找成功");
            if (num==0) text1.setText("查找失败");
            //
            //Toast.makeText(SearchActivity.this,"没有账单",Toast.LENGTH_SHORT).show();
            for (int i=0;i<num;i++)
            {
                String s=String.valueOf(i);
                String data=jsonObject.getString(s);
                Log.d("SearchActivity",data);
                JSONObject jsonObject1=new JSONObject(data);
                final String Username=jsonObject1.getString("Username");
                Log.d("SearchActivity","Username:"+Username);
                final String Countid=jsonObject1.getString("Countid");
                Log.d("SearchActivity","Countid:"+Countid);
                final String Type=jsonObject1.getString("Type");
                Log.d("SearchActivity","Type:"+Type);
                final String Money=jsonObject1.getString("Money");
                Log.d("SearchActivity","Money:"+Money);
                final String Remark=jsonObject1.getString("Remark");
                Log.d("SearchActivity","Remark:"+Remark);
                final String Date=jsonObject1.getString("Date");
                Log.d("SearchActivity","Date:"+Date);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Message message=new Message();
//                        message.obj="用户名:"+Username+" "+"编号:"+Countid+" "+"类型:"+Type+"\n"+"金额:"+Money+" "+"备注:"+Remark+" "+"日期:"+Date;
//                        mHandler.sendMessage(message);
//                    }
//                }).start();
                Map<String, Object> listem = new HashMap<String, Object>();
                listem.put("mymoney",Money);
                listem.put("mytype", Type);
                listem.put("myps", Remark);
                listem.put("mytime", Date);
                listem.put("myid", Username);
                listems.add(listem);
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
