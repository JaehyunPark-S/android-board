package com.inhatc.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

//게시판 메인Activity
public class MainActivity extends AppCompatActivity {

    private ListViewAdapter adapter;
    SQLiteDatabase myDB;
    ContentValues insertValue;
    String strSQL = null;
    private MenuItem stateCheck;
    private Context mContext;
    String text;

    String title;
    String content;
    String writer;
    String regdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar time = Calendar.getInstance();
        String time_now = format.format(time.getTime());

        myDB = this.openOrCreateDatabase("Main", MODE_PRIVATE, null);
        // DB유지해서 사용하려면 myDB.insert() 까지 삭제
//        myDB.execSQL("Drop table if exists Boards");
//        myDB.execSQL("Create table Boards (" + " _id integer primary key autoincrement, " + "title text not null, " + "content text not null, " + "writer text not null, " + "regdate text not null);");
//        insertValue = new ContentValues();
//        insertValue.put("title", "Admin");
//        insertValue.put("content", "Admin");
//        insertValue.put("writer", "Admin");
//        insertValue.put("regdate", time_now);
//        myDB.insert("Boards", null, insertValue);
        adapter = new ListViewAdapter();

        Button move_write = (Button)findViewById(R.id.btn_write);
        move_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });

        ListView lst_boards = (ListView)findViewById(R.id.lst_board);
        lst_boards.setAdapter(adapter);

        strSQL = "SELECT * FROM Boards ORDER BY regdate DESC;";
        Cursor cur = myDB.rawQuery(strSQL, null);

        while(cur.moveToNext()){
            title = cur.getString(1);
            content = cur.getString(2);
            writer = cur.getString(3);
            regdate = cur.getString(4);

            adapter.addItem(title, content, writer, regdate);
        }

        adapter.notifyDataSetChanged();
        if (myDB != null) myDB.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        mContext = this;
        text = PreferenceManager.getString(mContext, "logininfo");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        if(!text.equals("")){
            stateCheck = menu.findItem(R.id.state);
            stateCheck.setTitle("Logout");
        }else {
            stateCheck = menu.findItem(R.id.state);
            stateCheck.setTitle("Login");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.state:
                PreferenceManager.clear(mContext);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.map:
                Toast.makeText(this, "Google Map", Toast.LENGTH_LONG).show();
                intent = new Intent(getApplicationContext(), MyMapActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}