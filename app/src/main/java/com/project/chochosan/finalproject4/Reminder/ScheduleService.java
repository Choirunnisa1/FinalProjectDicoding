package com.project.chochosan.finalproject4.Reminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;
import com.project.chochosan.finalproject4.BuildConfig;
import com.project.chochosan.finalproject4.R;
import com.project.chochosan.finalproject4.activity.DetailActivity;
import com.project.chochosan.finalproject4.model.MovieModel;
import com.project.chochosan.finalproject4.model.ResultModel;
import com.project.chochosan.finalproject4.retrofit.ApiService;
import com.project.chochosan.finalproject4.retrofit.BaseUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Nisa on 03/10/2018.
 */

public class ScheduleService extends GcmTaskService {

    public static final String TAG_TASK_MOVIE_LOG = "Movie_Favorit";
    public final String TAG = "MovieFavorite";
    private final String api_key = BuildConfig.MOVIE_API_KEY;
    private final String lang = "en-US";

    public ArrayList<MovieModel> favoriteModels = new ArrayList<>();


    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_MOVIE_LOG)) {
            getCurrentFavorite();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    private void getCurrentFavorite() {
        ApiService apiService = BaseUrl.getAPIService();
        apiService.getNowPlaying(api_key, lang).enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if (response.isSuccessful()) {
                    List<MovieModel> favoritMovie = response.body().getResults();
                    //favoriteModels.addAll(favoritMovie);
                    //Log.d(TAG, String.valueOf(favoritMovie));
                    int index = new Random().nextInt(favoritMovie.size());

                    MovieModel item = favoritMovie.get(index);
                    String title = favoritMovie.get(index).getTitle();
                    String message = favoritMovie.get(index).getOverview();
                    String time = favoritMovie.get(index).getReleaseDate();
                    String poster = favoritMovie.get(index).getPosterPath();
                    int notifId = 200;

                    showNotification(getApplicationContext(), title, message, notifId, item);
                    //  showNotification(getApplicationContext(),favoriteModels );
                } else loadFailed();
            }


            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

            }
        });
    }

    private void loadFailed() {
        Toast.makeText(this, R.string.err_load_failed, Toast.LENGTH_SHORT).show();
    }

    private void showNotification(Context context, String title, String message, int notifId, MovieModel item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String time = item.getReleaseDate();
        String language = item.getOriginalLanguage();
        String popularity = item.getPopularity();
        String backdrop = item.getBackdropPath();
        String poster = item.getPosterPath();
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TITLE, title);
        intent.putExtra(DetailActivity.EXTRA_OVERVIEW, message);
        intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, time);
        intent.putExtra(DetailActivity.EXTRA_POSTER, poster);
        intent.putExtra(DetailActivity.EXTRA_LANGUAGE, language);
        intent.putExtra(DetailActivity.EXTRA_POPULARITY, popularity);
        intent.putExtra(DetailActivity.EXTRA_BACKDROP, backdrop);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_favorite)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());

    }

}

  //  private void showNotification(Context applicationContext, ArrayList<MovieModel> listMovie) {
    //    NotificationManager notificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
      //  Uri alaramSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Intent i = new Intent(applicationContext, DetailActivity.class);

        //i.putExtra(BUNDLE_TITTLE, listMovie.get(1).getTitle());
       // i.putExtra(BUNDLE_RELEASE_DATE, listMovie.get(1).getReleaseDate());
       // i.putExtra(BUNDLE_OVERVIEW, listMovie.get(1).getOverview());
       // i.putExtra(BUNDLE_POSTER_IMAGE, listMovie.get(1).getPosterPath());
      //  i.putExtra(BUNDLE_BACKDROPH_IMAGE, listMovie.get(1).getBackdropPath());
    //    i.putExtra(BUNDLE_POPULARITY, listMovie.get(1).getPopularity());
  //      i.putExtra(BUNDLE_ORIGINAL_LANGUAGE, listMovie.get(1).getOriginalLanguage());
//
     //   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
         //   int notifId = 200;
       //     PendingIntent pendingIntent = TaskStackBuilder.create(applicationContext)
             //       .addNextIntent(i)
           //         .getPendingIntent(notifId, PendingIntent.FLAG_UPDATE_CURRENT);

           // NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(applicationContext)
             //       .setContentTitle(listMovie.get(0).getTitle())
               //     .setSmallIcon(R.drawable.ic_favorite)
                 //   .setContentText("Release on : " + listMovie.get(0).getReleaseDate())
                   // .setColor(ContextCompat.getColor(applicationContext, android.R.color.black))
                   // .setVibrate(new long[]{1000, 1000, 1000, 1000, 100})
                  //  .setSound(alaramSound)
                    //.setContentIntent(pendingIntent)
                    //.setAutoCancel(true);
            //if (notificationManager != null) {
              //  notificationManager.notify(notifId, builder.build());
            //}
        //} else {
          //  Toast.makeText(applicationContext, "Not Supported", Toast.LENGTH_SHORT).show();
        //}
   // }
//}


