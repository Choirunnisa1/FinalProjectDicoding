package com.project.chochosan.finalproject4.Reminder;


import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.project.chochosan.finalproject4.model.MovieModel;

import java.util.ArrayList;

/**
 * Created by Nisa on 03/10/2018.
 */

public class SchedulerTask {

    private GcmNetworkManager gcmNetworkManager;

    public SchedulerTask(Context context){
        this.gcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask(){
        Task periodikTask = new PeriodicTask.Builder()
                .setService(ScheduleService.class)
                .setPeriod(30)
                .setFlex(10)
                .setTag(ScheduleService.TAG_TASK_MOVIE_LOG)
                .setPersisted(true)
                .build();
        gcmNetworkManager.schedule(periodikTask);
    }

    public void cancelPeriodicTask(){
        if (gcmNetworkManager!=null) {
            gcmNetworkManager.cancelTask(ScheduleService.TAG_TASK_MOVIE_LOG, ScheduleService.class);
        }
    }

}