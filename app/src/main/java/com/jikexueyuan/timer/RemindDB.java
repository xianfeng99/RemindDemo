package com.jikexueyuan.timer;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lixianfeng on 16/3/14.
 */
public class RemindDB extends SQLiteOpenHelper {

    public static String DBNAME = "rdb";
    public static String TABLENAME = "remind";

    public static String NOTE_TIME = "time";
    public static String NOTE_CONTENT = "content";
    public static String NOTE_ID = "id";

    public RemindDB(Context context) {
        super(context, "rdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLENAME + "("+   //创建用户表
                "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "time INTEGER," +
                "content TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
