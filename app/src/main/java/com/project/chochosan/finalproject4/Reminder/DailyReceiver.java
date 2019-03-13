package com.project.chochosan.finalproject4.Reminder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.project.chochosan.finalproject4.R;
import com.project.chochosan.finalproject4.activity.MainActivity;

import java.util.Calendar;

/**
 * Created by Nisa on 03/10/2018.
 */

public class DailyReceiver extends BroadcastReceiver {

    public static final String NOTIF_EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String NOTIF_TYPE_MESSAGE = "TYPE_MESSAGE";
   public static final int NOTIF_ID_REMINDER  = 101;
    public static final String NOTIF_TYPE_REMINDER = "TYPE_REMINDER";



    public DailyReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(NOTIF_EXTRA_MESSAGE);

        String title = "Daily Reminder";

        showAlarmNotification(context, title, message,NOTIF_ID_REMINDER);
    }


    public void showAlarmNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            pendingIntent = TaskStackBuilder.create(context).addNextIntent(intent)
                    .getPendingIntent(NOTIF_ID_REMINDER, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);
            notificationManagerCompat.notify(notifId, builder.build());

        } else {
            Toast.makeText(context, "Tidak Support", Toast.LENGTH_SHORT).show();
        }
    }


    public void setReminder(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);
        intent.putExtra(NOTIF_EXTRA_MESSAGE, message);
        intent.putExtra(NOTIF_TYPE_MESSAGE, type);

        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REMINDER, intent, 0);
      long thirtySecondsFromNow = System.currentTimeMillis() + 30 * 1000;
      alarmManager.set(AlarmManager.RTC_WAKEUP, thirtySecondsFromNow, pendingIntent);
  }


    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REMINDER, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, "Reminder has been deleted", Toast.LENGTH_SHORT).show();
    }

}

