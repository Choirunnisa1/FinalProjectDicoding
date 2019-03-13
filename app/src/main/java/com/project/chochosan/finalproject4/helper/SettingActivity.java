package com.project.chochosan.finalproject4.helper;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.chochosan.finalproject4.R;
import com.project.chochosan.finalproject4.Reminder.DailyReceiver;
import com.project.chochosan.finalproject4.Reminder.SchedulerTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    private Switch switchDaily;
    private Switch switchFavorit;
    private LinearLayout divClock;
    private TextView tvTime;

    private SchedulerTask schedulerTask;
    private DailyReceiver dailyReceiver;
    private Calendar calendar;
    private SettingPreference settingPreference;

    boolean checkFavorit;
    boolean checkDaily;
    private LinearLayout divKlikTime;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle(R.string.setting);
        initView();
        schedulerTask = new SchedulerTask(SettingActivity.this);
        dailyReceiver = new DailyReceiver();
        settingPreference = new SettingPreference(SettingActivity.this);
        calendar = Calendar.getInstance();

        checkFavorit = settingPreference.getUpcomingReminder();
        checkDaily = settingPreference.getDailyReminder();

        switchFavorit.setChecked(checkFavorit);
        switchDaily.setChecked(checkDaily);

        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchDaily.isChecked()) {
                    settingPreference.setDailyReminder(true);
                    divClock.setVisibility(View.VISIBLE);
                    dailyReminderON();
                } else {
                    divClock.setVisibility(View.GONE);
                    settingPreference.setDailyReminder(false);
                    dailyReminderOFF();
                }
            }
        });
        switchFavorit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchFavorit.isChecked()) {
                    settingPreference.setUpcomingReminder(true);
                    schedulerTask.createPeriodicTask();
                    Toast.makeText(SettingActivity.this, "Reminder Created", Toast.LENGTH_SHORT).show();
                } else {
                    settingPreference.setUpcomingReminder(false);
                    schedulerTask.cancelPeriodicTask();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void dailyReminderOFF() {
        dailyReceiver.cancelAlarm(SettingActivity.this);
    }

    private void dailyReminderON() {
        divKlikTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                new TimePickerDialog(SettingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        tvTime.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true).show();
                String reminder = simpleDateFormat.format(calendar.getTime());
                String messsage = "Please back soon";
                settingPreference.setReminderTime(reminder);
                settingPreference.setReminderMessage(messsage);
                dailyReceiver.setReminder(SettingActivity.this, DailyReceiver.NOTIF_TYPE_REMINDER, reminder, messsage);
            }
        });
    }

    private void initView() {
        switchDaily = findViewById(R.id.switch_daily);
        switchFavorit = findViewById(R.id.switch_now_play);
        divClock = findViewById(R.id.clock);
        tvTime = findViewById(R.id.tv_clock);
        divKlikTime = findViewById(R.id.div_time_klik);
        button = findViewById(R.id.btn_klik_alarm);
    }
}
