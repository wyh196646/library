package com.example.library;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.EditText;
import android.app.Activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    //继承SQLiteOpenHelper类
    private MySQLiteOpenHelper sqlHelper;
    private ListView listview;
    private EditText edit1;
    private EditText edit2;
    private EditText edit3;
    private EditText edit4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlHelper = new MySQLiteOpenHelper(this, "bookDatabase.db", null, 2);
        //建立新表
        Button createBn = (Button) findViewById(R.id.button1);
        createBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelper.getWritableDatabase();
            }
        });
        //插入数据
        Button insertBn = (Button) findViewById(R.id.button2);
        edit1 = (EditText) findViewById(R.id.edit_id);
        edit2 = (EditText) findViewById(R.id.edit_name);
        edit3 = (EditText) findViewById(R.id.edit_tel);
        edit4 = (EditText) findViewById(R.id.edit_height);
        insertBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = sqlHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("book_id", edit1.getText().toString());
                values.put("book_name", edit2.getText().toString());
                values.put("book_pages", edit3.getText().toString());
                values.put("book_rank", edit4.getText().toString());
                db.insert("book", null, values);
                Toast.makeText(MainActivity.this, "数据插入成功", Toast.LENGTH_SHORT).show();
                edit1.setText("");
                edit2.setText("");
                edit3.setText("");
                edit4.setText("");
            }
        });
        //删除数据
        Button deleteBn = (Button) findViewById(R.id.button3);
        deleteBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = sqlHelper.getWritableDatabase();
                db.delete("book", "book_id= ?", new String[] {edit1.getText().toString()});
                Toast.makeText(MainActivity.this, "删除数据", Toast.LENGTH_SHORT).show();
    }
});
        //更新数据
        Button updateBn = (Button) findViewById(R.id.button4);
        updateBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = sqlHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("book_id", edit1.getText().toString());
                values.put("book_name", edit2.getText().toString());
                values.put("book_pages", edit3.getText().toString());
                values.put("book_rank", edit4.getText().toString());
                db.update("book", values, "book_id = ?", new String[] {edit1.getText().toString()} );
                Toast.makeText(MainActivity.this, "更新数据", Toast.LENGTH_SHORT).show();
            }
        });
        //查询数据
        listview = (ListView) findViewById(R.id.listview1);
        Button selectBn = (Button) findViewById(R.id.button5);
        selectBn.setOnClickListener(new View.OnClickListener() {
            private Object Log;

            @Override
            public void onClick(View v) {
                try {
                    SQLiteDatabase db = sqlHelper.getWritableDatabase();
                    //游标查询每条数据
                    Cursor cursor = db.query("book", null, null, null, null, null, null);
                    //定义list存储数据
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    //适配器SimpleAdapter数据绑定
                    //错误:构造函数SimpleAdapter没有定义 需把this改动为MainActivity.this
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list, R.layout.book_item,
                            new String[]{"book_id", "book_name", "book_pages", "book_rank"},
                            new int[]{R.id.stu_id, R.id.stu_name, R.id.stu_tel, R.id.stu_height});
                    //读取数据 游标移动到下一行
                    while(cursor.moveToNext()) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put( "book_id", cursor.getString(cursor.getColumnIndex("book_id")) );
                        map.put( "book_name", cursor.getString(cursor.getColumnIndex("book_name")) );
                        map.put( "book_pages", cursor.getString(cursor.getColumnIndex("book_pages")) );
                        map.put( "book_rank", cursor.getString(cursor.getColumnIndex("book_rank")) );
                        list.add(map);
                    }
                    listview.setAdapter(adapter);
                }
                catch (Exception e){

                }
            }
        });
    }
}