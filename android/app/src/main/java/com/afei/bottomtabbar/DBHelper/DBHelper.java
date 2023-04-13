package com.afei.bottomtabbar.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;


public class DBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "userinfo";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "user";

    //创建数据库，里面添加了3个参数，分别是：Msgone VARCHAR类型，30长度当然这了可以自定义
    //Msgtwo VARCHAR(20)   Msgthree VARCHAR(30))  NOT NULL不能为空
    String sql = "CREATE TABLE " + TABLE_NAME
            + "(_id INTEGER PRIMARY KEY,"
            + " address VARCHAR(255)  NOT NULL,"
            + " password VARCHAR(255),"
            + " privateKey VARCHAR(255))";
    //构造函数，创建数据库
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
    public Map<String,String> select() {
        Map<String,String> result=new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (!cursor.moveToNext())
            return null;
        result.put("address",cursor.getString(cursor.getColumnIndex("address")));
        result.put("password",cursor.getString(cursor.getColumnIndex("password")));
        result.put("privateKey",cursor.getString(cursor.getColumnIndex("privateKey")));
        return result;
    }
    public long insert(String msg1,String msg2,String msg3 ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("address", msg1);
        cv.put("password", msg2);
        cv.put("privateKey", msg3);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    public void updatePrivateKey(String privateKey,String address)
    {
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("privateKey", privateKey);
        System.out.println(address);
        sqliteDatabase.update(TABLE_NAME, values, "address=?", new String[] {address});
    }
    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME+" where 1=1");
    }

}