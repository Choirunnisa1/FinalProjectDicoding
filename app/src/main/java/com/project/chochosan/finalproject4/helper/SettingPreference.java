package com.project.chochosan.finalproject4.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

/**
 * Created by Nisa on 03/10/2018.
 */

public class SettingPreference {

    private final static String PREF_NAME = "reminderMoviePreferences";
    private final static String KEY_REMINDER_MOVIE_TIME = "reminderTime";
    private final static String KEY_REMINDER_MOVIE_MESSAGE = "reminderMessage";
    private final static String KEY_FIELD_UPCOMING_REMINDER = "checkedUpcoming";
    private final static String KEY_FIELD_DAILY_REMINDER = "checkedDaily";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SettingPreference (Context context){
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void setReminderTime (String time){
        editor.putString(KEY_REMINDER_MOVIE_TIME, time);
        editor.commit();
    }

    public String getReminderTime(){
        return preferences.getString(KEY_REMINDER_MOVIE_TIME, "");
    }

    public void setReminderMessage(String message) {
        editor.putString(KEY_REMINDER_MOVIE_MESSAGE, message);
        editor.commit();
    }

    public String getReminderMessage(){
        return preferences.getString(KEY_REMINDER_MOVIE_MESSAGE, "");
    }

    public void delete(){
        editor.clear();
        editor.commit();
    }
    public void setUpcomingReminder(Boolean status) {
        editor.putBoolean(KEY_FIELD_UPCOMING_REMINDER, status);
        editor.commit();
    }

    public void setDailyReminder(Boolean status) {
        editor.putBoolean(KEY_FIELD_DAILY_REMINDER, status);
        editor.commit();
    }

    public Boolean getUpcomingReminder() {
        return preferences.getBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
    }

    public Boolean getDailyReminder() {
        return preferences.getBoolean(KEY_FIELD_DAILY_REMINDER, false);
    }


    public static String getColomnString(Cursor cursor, String colomnName) {
        return cursor.getString(cursor.getColumnIndex(colomnName));
    }


    public static int getColomnInt(Cursor cursor, String colomnName) {
        return cursor.getInt(cursor.getColumnIndex(colomnName));
    }
}
