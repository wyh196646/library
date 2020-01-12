package com.example.library;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.Toast;
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public Context mContext;

    //创建书籍表
    public static final String CREATE_book = "create table book ("
            +"book_id integer primary key autoincrement, "
            + "book_name text, "
            + "book_pages text,"
            + "book_rank text)";
    //抽象类 必须定义显示的构造函数 重写方法
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
        arg0.execSQL(CREATE_book );
        Toast.makeText(mContext, "Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        arg0.execSQL("drop table if exists book");
        onCreate(arg0);
        Toast.makeText(mContext, "Upgraged", Toast.LENGTH_SHORT).show();
    }
}