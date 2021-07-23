package com.inhatc.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import android.widget.Toast;

import java.util.ArrayList;

//회원가입 Activity
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    SQLiteDatabase myDB;
    ArrayList<String> aryMBRList;
    ArrayAdapter<String> adtMembers;
    ListView lstView;
    String strRecord = null;
    ContentValues insertValue;
    Cursor allRCD;
    Button validateButton, btn_register;
    EditText edtId, edtPw, edtPwck, edtName;
    String strSQL = null;
    Boolean idCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtId = (EditText)findViewById(R.id.et_id);
        edtPw = (EditText)findViewById(R.id.et_pass);
        edtPwck = (EditText)findViewById(R.id.et_passck);
        edtName = (EditText)findViewById(R.id.et_name);

        btn_register = (Button)findViewById(R.id.btn_write2);
        btn_register.setEnabled(false);

        btn_register.setOnClickListener(this);
        validateButton = (Button)findViewById(R.id.validateButton);
        validateButton.setOnClickListener(this);

//        myDB = this.openOrCreateDatabase("Main", MODE_PRIVATE, null);

        // DB유지해서 사용하려면 myDB.insert()까지 삭제
//        myDB.execSQL("Drop table if exists Users");
//        myDB.execSQL("Create table Users (" + " id text primary key, " + "password text not null, " + "name text not null);");
//
//        ContentValues insertValue = new ContentValues();
//        insertValue.put("id", "admin");
//        insertValue.put("password", "admin");
//        insertValue.put("name", "admin");
//        myDB.insert("Users", null, insertValue);

//        if(myDB != null) myDB.close();

    }

    @Override
    public void onClick(View v) {
        myDB = this.openOrCreateDatabase("Main", MODE_PRIVATE, null);
        if(v == btn_register){
            insertValue = new ContentValues();
            if(idCheck = true && !edtId.getText().toString().equals("") && !edtName.getText().toString().equals("") && !edtPw.getText().toString().equals("") && edtPw.getText().toString().equals(edtPwck.getText().toString())){
                insertValue.put("id", edtId.getText().toString());
                insertValue.put("password", edtPw.getText().toString());
                insertValue.put("name", edtName.getText().toString());
                myDB.insert("Users", null, insertValue);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }else {
                //회원가입 비밀번호 비밀번호 확인의 불일치 및 공백이 포함되었을 경우 예외처리
                Toast.makeText(getApplicationContext(), "잘못된 정보입니다.", Toast.LENGTH_LONG).show();
            }

        }else if(v == validateButton){
            //아이디 중복확인 예외처리
            strSQL = "Select * from Users ";
            strSQL += "Where id = '" + edtId.getText().toString() + "';";
            String id = "";
            Cursor cur = myDB.rawQuery(strSQL, null);
            while(cur.moveToNext()){
                id = cur.getString(0);
            }
            if (id.equals("")) {
                Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_LONG).show();
                idCheck = true;
                this.btn_register.setEnabled(true);
            }else {
                Toast.makeText(getApplicationContext(), "이미 사용중인 아이디입니다.", Toast.LENGTH_LONG).show();
                edtId.setText("");
                idCheck = false;
                this.btn_register.setEnabled(false);
            }
        }
    }
}
