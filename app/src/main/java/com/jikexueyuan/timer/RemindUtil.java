package com.jikexueyuan.timer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lixianfeng on 16/3/14.
 */
public class RemindUtil {

    public static RemindDB remindDB;
    public static SQLiteDatabase database;

    public static void initDataBase(Context context){

        remindDB = new RemindDB(context);

    }

    public static SQLiteDatabase getRemindDatabase(){
        if(database == null){
            database = remindDB.getWritableDatabase();
        }
        return database;
    }

    private static long addRemindBean(int time, String content){

        ContentValues value = new ContentValues();
        value.put(RemindDB.NOTE_TIME, time);
        value.put(RemindDB.NOTE_CONTENT, content);

        return database.insert(RemindDB.DBNAME, RemindDB.TABLENAME, value);
    }

    //添加提醒
    public static void addRemind(Context context, int time, String content){

        //计算时间
        long curTime = System.currentTimeMillis();
        Date date = new Date(curTime);

        int hour = date.getHours();
        int min = date.getMinutes();
        int second = date.getSeconds();

        Log.e("Remind Time----->", "hour:min:second=" + hour + ":" + min + ":" + second);
        if(time < hour + 1){
            Toast.makeText(context, "提醒时间已经超过现在时间", Toast.LENGTH_SHORT).show();
            return;
        }

        //剩余时间计算
        long endTime = ((time - hour - 1) * 60 + (60 - min)) * 60 + (60 - second);

        //添加到数据库
        long id = addRemindBean(time, content);
        if(id == -1){
            Toast.makeText(context, "添加提醒失败，请查看数据库", Toast.LENGTH_SHORT).show();
            return;
        }

        //添加定时器
        // 设置提醒
        Intent brodcast = new Intent();
        brodcast.setAction(RemindBroadcastReceiver.REMIND_BRODCAST_ACTION);
        //添加需要设置的值
        brodcast.putExtra("content", content);

        PendingIntent pend = PendingIntent.getBroadcast(context,
                (int) id, brodcast,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP,
                endTime, pend);
        Log.i("REMIND_TIME", endTime / 1000 + "秒后提醒  "+(new Date(curTime+endTime).toLocaleString()));

    }

    //删除提醒
    public static void canelRemind(Context context, int id, String content){

        //从数据库删除内容

        database.delete(RemindDB.TABLENAME, "id=" + id, null);

        //取消闹钟
        AlarmManager alarm = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent brodcast = new Intent();
        brodcast.setAction(RemindBroadcastReceiver.REMIND_BRODCAST_ACTION);
        brodcast.putExtra("content", content);
        PendingIntent pend = PendingIntent.getBroadcast(context,
                id, brodcast,
                PendingIntent.FLAG_CANCEL_CURRENT);
        alarm.cancel(pend);

    }

    //获取提醒列表
    public static ArrayList<RemindBean> getRemindList(){
        ArrayList<RemindBean> list = new ArrayList<>();
        if(database == null){
            database = getRemindDatabase();
        }

        Cursor cursor = database.query(RemindDB.TABLENAME, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            RemindBean remind = new RemindBean();

            int id = cursor.getInt(cursor.getColumnIndex(RemindDB.NOTE_ID));
            int time = cursor.getInt(cursor.getColumnIndex(RemindDB.NOTE_TIME));
            String content = cursor.getString(cursor.getColumnIndex(RemindDB.NOTE_CONTENT));
            remind.setId(id);
            remind.setTime(time);
            remind.setContent(content);
            list.add(remind);

        }

        cursor.close();
        return list;
    }
}
