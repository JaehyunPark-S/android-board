package com.inhatc.final_project;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    SQLiteDatabase myDB;
    ArrayList<String> aryMBRList;
    ArrayAdapter<String> adtMembers;
    ListView lstView;
    String strRecord = null;
    ContentValues insertValue;
    Cursor allRCD;
    Button btn_write2;
    EditText et_board_content, et_board_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        et_board_content = (EditText)findViewById(R.id.et_board_content);
        et_board_title = (EditText)findViewById(R.id.et_board_title);

        btn_write2 = (Button) findViewById(R.id.btn_write2);
        btn_write2.setOnClickListener(this);

    }

    public void getDBData(String strWhere) {
        allRCD = myDB.query("Boards", null, strWhere, null, null, null, null, null);
        aryMBRList = new ArrayList<String>();
        if (allRCD != null) {
            if (allRCD.moveToFirst()) {
                do {
                    strRecord = allRCD.getString(1) + "\t\t" + allRCD.getString(2);
                    aryMBRList.add(strRecord);
                } while (allRCD.moveToNext());
            }
        }
        adtMembers = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, aryMBRList);

        lstView.setAdapter(adtMembers);
        lstView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onClick(View v) {
        myDB = this.openOrCreateDatabase("Main", MODE_PRIVATE, null);
        mContext = this;
        String writer = PreferenceManager.getString(mContext, "logininfo");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar time = Calendar.getInstance();
        String time_now = format.format(time.getTime());

        if(!et_board_title.getText().toString().equals("") && !et_board_content.getText().toString().equals("") && !writer.equals("")){
            insertValue = new ContentValues();
            insertValue.put("title", et_board_title.getText().toString());
            insertValue.put("content", et_board_content.getText().toString());
            insertValue.put("writer", writer);
            insertValue.put("regdate", time_now);
            myDB.insert("Boards", null, insertValue);
            Toast.makeText(getApplicationContext(), "작성 완료.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(getApplicationContext(), "잘못된 정보입니다.", Toast.LENGTH_LONG).show();
        }

    }
}
