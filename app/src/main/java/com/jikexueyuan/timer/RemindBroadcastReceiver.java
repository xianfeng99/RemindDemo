package com.jikexueyuan.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lixianfeng on 16/3/14.
 */
public class RemindBroadcastReceiver extends BroadcastReceiver {

    public static String REMIND_BRODCAST_ACTION = "com.jikexueyuan.timer.remind";

    @Override
    public void onReceive(Context context, Intent intent) {

        //发送通知栏通知
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //如何存在需要提醒的记事，弹出通知
        Notification notification = Notification.Builder(context)
                .setContentTitle("记事提醒")
                .setContentText(intent.getStringExtra("content"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        nm.notify(1, notification);

    }
}
