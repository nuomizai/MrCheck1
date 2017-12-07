package com.example.zhao_.mrcheck1;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReportActivity extends AppCompatActivity {
    private Button Btmonth, Btyear, back, Btquery;
    private Spinner Spyear, Spmonth;
    Calendar c = Calendar.getInstance();
    private String year;
    private String month1;
    private String month2;
    private String day1;
    private String day2;
    private String name;
    private TextView reportview;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String get = reportview.getText().toString().trim();
            String get1 = (String) msg.obj;
            reportview.setText(get + "\n" + "\n" + get1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_form);

        back = (Button) findViewById(R.id.back);
        Btyear = (Button) findViewById(R.id.report_form_year);
        Btmonth = (Button) findViewById(R.id.report_form_month);
        Btquery = (Button) findViewById(R.id.report_form_query);
        Spyear = (Spinner) findViewById(R.id.report_form_history_year);
        Spmonth = (Spinner) findViewById(R.id.report_form_history_month);
        reportview = (TextView) findViewById(R.id.report_view);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Btmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = String.valueOf(c.get(Calendar.YEAR));
                month1 = String.valueOf(c.get(Calendar.MONTH));
                month2 = month1;
                day1 = "1";
                day2 = "31";
                postRequest();
            }
        });

        Btyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = String.valueOf(c.get(Calendar.YEAR));
                month1 = "1";
                month2 = String.valueOf(c.get(Calendar.MONTH));
                day1 = "1";
                day2 = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                postRequest();
            }
        });

        Btquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = (String) Spyear.getSelectedItem();
                month1 = (String) Spmonth.getSelectedItem();
                month2 = month1;
                day1 = "1";
                day2 = "31";
                postRequest();
            }
        });
    }

    private void postRequest() {
        name = Data.username;
        new Thread(new Runnable() {
            @Override
            public void run() {
                name = Data.username;
                RequestBody formBody = new FormBody.Builder()
                        .add("uname", name)
                        .add("Year1", year)
                        .add("Month1", month1)
                        .add("Day1", day1)
                        .add("Year2", year)
                        .add("Month2", month2)
                        .add("Day2", day2)
                        .build();
                final Request request = new Request.Builder()
                        .url("http://111.230.237.110/Statementbill.php")
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
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);//读成jsonObject
            final String sum = jsonObject.getString("sum");//读取sum中的内容“5”
            //int num=Integer.parseInt(sum);将sum转成int型
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.obj = "总额：" + sum;
                    String get = (String) message.obj;
                    reportview.setText(get);
                }
            }).start();

            final String food = jsonObject.getString("food");//读取housecar后面为string型
            JSONObject jsonObject1 = new JSONObject(food);//转成object型
            final String money1 = jsonObject1.getString("money");//读取其中的money数据
            final String point1 = jsonObject1.getString("point");//读取其中的point数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.obj = "food：" + money1 + "(" + point1 + ")";
                    mHandler.sendMessage(message);
                }
            }).start();

            final String housecar = jsonObject.getString("house-car");//读取housecar后面为string型
            JSONObject jsonObject2 = new JSONObject(housecar);//转成object型
            final String money2 = jsonObject2.getString("money");//读取其中的money数据
            final String point2 = jsonObject2.getString("point");//读取其中的point数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.obj = "house-car：" + money2 + "(" + point2 + ")";
                    mHandler.sendMessage(message);
                }
            }).start();

            final String clothes = jsonObject.getString("clothes");//读取housecar后面为string型
            JSONObject jsonObject3 = new JSONObject(clothes);//转成object型
            final String money3 = jsonObject3.getString("money");//读取其中的money数据
            final String point3 = jsonObject3.getString("point");//读取其中的point数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.obj = "clothes：" + money3 + "(" + point3 + ")";
                    mHandler.sendMessage(message);
                }
            }).start();

            final String hosedu = jsonObject.getString("hos-edu");//读取housecar后面为string型
            JSONObject jsonObject4 = new JSONObject(hosedu);//转成object型
            final String money4 = jsonObject4.getString("money");//读取其中的money数据
            final String point4 = jsonObject4.getString("point");//读取其中的point数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.obj = "hos-edu：" + money4 + "(" + point4 + ")";
                    mHandler.sendMessage(message);
                }
            }).start();

            final String transport = jsonObject.getString("transport");//读取housecar后面为string型
            JSONObject jsonObject5 = new JSONObject(transport);//转成object型
            final String money5 = jsonObject5.getString("money");//读取其中的money数据
            final String point5 = jsonObject5.getString("point");//读取其中的point数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.obj = "transport：" + money5 + "(" + point5 + ")";
                    mHandler.sendMessage(message);
                }
            }).start();

            final String threeC = jsonObject.getString("3C");//读取housecar后面为string型
            JSONObject jsonObject6 = new JSONObject(threeC);//转成object型
            final String money6 = jsonObject6.getString("money");//读取其中的money数据
            final String point6 = jsonObject6.getString("point");//读取其中的point数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.obj = "3C：" + money6 + "(" + point6 + ")";
                    mHandler.sendMessage(message);
                }
            }).start();

            final String highprice = jsonObject.getString("Highprice");//读取housecar后面为string型
            JSONObject jsonObject7 = new JSONObject(highprice);//转成object型
            final String money7 = jsonObject7.getString("money");//读取其中的money数据
            final String point7 = jsonObject7.getString("point");//读取其中的point数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.obj = "Highprice：" + money7 + "(" + point7 + ")";
                    mHandler.sendMessage(message);
                }
            }).start();

            final String moneymanage = jsonObject.getString("money");//读取housecar后面为string型
            JSONObject jsonObject8 = new JSONObject(moneymanage);//转成object型
            final String money8 = jsonObject8.getString("money");//读取其中的money数据
            final String point8 = jsonObject8.getString("point");//读取其中的point数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.obj = "money：" + money8 + "(" + point8 + ")";
                    mHandler.sendMessage(message);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
