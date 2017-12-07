package com.example.zhao_.mrcheck1;

import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MyinforActivity extends AppCompatActivity
{

    private TextView textView;
    private EditText editText1;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfor);

        textView = (TextView)findViewById(R.id.edit_username);
        textView.setText(Data.username);
        editText1=(EditText)findViewById(R.id.edit_password);
        editText1.setText(Data.password);
    }
}
