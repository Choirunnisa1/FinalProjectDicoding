package com.project.chochosan.finalproject4.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.project.chochosan.finalproject4.BuildConfig;
import com.project.chochosan.finalproject4.R;
import com.project.chochosan.finalproject4.database.DatabaseContract;
import com.squareup.picasso.Picasso;


import static com.project.chochosan.finalproject4.database.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {

    String poster, title, overview, release,popularity,backdrop, language;
    ImageView tvImg, tvPath;
    FloatingActionButton fvFav;
    TextView tvJudul, tvDesc, tvRelease,tvPop, tvLanguage;
    CoordinatorLayout coordinatorLayout;

    public static String EXTRA_TITLE = "title";
    public static String EXTRA_OVERVIEW = "overview";
    public static String EXTRA_RELEASE_DATE = "release_date";
    public static String EXTRA_POSTER = "poster_path";
    public static String EXTRA_POPULARITY = "popularity";
    public static String EXTRA_BACKDROP = "backdrop_path";
    public static String EXTRA_LANGUAGE = "original_language";

    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        strucMovie();
        saveFavorite();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.detail_tab);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void strucMovie(){
        Intent intent = getIntent();
        backdrop = intent.getStringExtra(EXTRA_BACKDROP);
        poster = intent.getStringExtra(EXTRA_POSTER);
        title = intent.getStringExtra(EXTRA_TITLE);
        overview = intent.getStringExtra(EXTRA_OVERVIEW);
        popularity = intent.getStringExtra(EXTRA_POPULARITY);
        language = intent.getStringExtra(EXTRA_LANGUAGE);
        release = intent.getStringExtra(EXTRA_RELEASE_DATE);

        Picasso.with(this)
                .load(BuildConfig.Image+poster)
                .into(tvPath);

        Picasso.with(this)
                .load(BuildConfig.Image+backdrop)
                .into(tvImg);

        tvJudul.setText(title);
        tvLanguage.setText(language);
        tvPop.setText(popularity);
        tvDesc.setText(overview);
        tvRelease.setText(release);
        fvFav.setImageResource(R.drawable.ic_border_favorit);


    }
    public boolean saveFavorite(){
        Uri uri = Uri.parse(CONTENT_URI+"");
        boolean data = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String getTitle;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getLong(0);
                getTitle = cursor.getString(1);
                if (getTitle.equals(getIntent().getStringExtra("title"))){
                    fvFav.setImageResource(R.drawable.ic_favorite);
                    data = true;
                }
            } while (cursor.moveToNext());

        }

        return data;

    }

    public void favorite (View view) {
        if(saveFavorite()){
            Uri fav  = Uri.parse(CONTENT_URI+"/"+id);
            getContentResolver().delete(fav, null, null);
            fvFav.setImageResource(R.drawable.ic_border_favorit);
        }
        else{
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.FavoriteColumns.TITLE, title);
            contentValues.put(DatabaseContract.FavoriteColumns.POSTER, poster);
            contentValues.put(DatabaseContract.FavoriteColumns.RELEASE_DATE, release);
            contentValues.put(DatabaseContract.FavoriteColumns.DESCRIPTION, overview);
            contentValues.put(DatabaseContract.FavoriteColumns.BACKDROP, backdrop);
            contentValues.put(DatabaseContract.FavoriteColumns.POPULARITY, popularity);
            contentValues.put(DatabaseContract.FavoriteColumns.LANGUANGE,language );

            getContentResolver().insert(CONTENT_URI, contentValues);
            setResult(101);

            fvFav.setImageResource(R.drawable.ic_favorite);
        }
    }



    private void initView(){
        tvImg = findViewById(R.id.poster);
        tvJudul = findViewById(R.id.tv_tittle);
        tvDesc = findViewById(R.id.tv_detail_overview);
        tvRelease = findViewById(R.id.tv_release);
        tvLanguage = findViewById(R.id.language);
        fvFav = findViewById(R.id.fab);
        tvPop = findViewById(R.id.popularity);
        tvPath = findViewById(R.id.iv_detail_movie);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, R.string.detail_tab, Toast.LENGTH_SHORT).show();
    }



}
