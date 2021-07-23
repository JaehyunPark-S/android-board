package com.inhatc.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

//로그인 Activity
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    SQLiteDatabase myDB;
    Button btn_login, btn_register;
    EditText edtId, edtPw;
    String strSQL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtId = (EditText)findViewById(R.id.et_id);
        edtPw = (EditText)findViewById(R.id.et_pass);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_register = (Button)findViewById(R.id.btn_write2);
        btn_register.setOnClickListener(this);

        mContext = this;

        String text = PreferenceManager.getString(mContext, "logininfo");
        if(!text.equals("")){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    public void onClick(View v) {
        myDB = this.openOrCreateDatabase("Main", MODE_PRIVATE, null);

        if(v == btn_login) {
            strSQL = "SELECT * FROM Users WHERE id='" + edtId.getText().toString() + "' AND password='" + edtPw.getText().toString() + "';";
            try{
                Cursor cur = myDB.rawQuery(strSQL, null);
                if(cur != null && cur.moveToNext()){
                    Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_LONG).show();
                    PreferenceManager.setString(mContext, "logininfo", edtId.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){
                throw e;
            } finally {
                strSQL = "SELECT * FROM Users WHERE id='"+edtId.getText().toString()+"';";
                Cursor cur = myDB.rawQuery(strSQL, null);
                if(cur != null && cur.moveToNext()){
                    Toast.makeText(getApplicationContext(), "잘못된 비밀번호 입니다.", Toast.LENGTH_LONG).show();
                    edtPw.setText("");
                }else{
                    Toast.makeText(getApplicationContext(), "존재하지 않는 아이디입니다.", Toast.LENGTH_LONG).show();
                    edtId.setText("");
                    edtPw.setText("");
                }
            }
        } else if(v == btn_register) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }
    }

}
