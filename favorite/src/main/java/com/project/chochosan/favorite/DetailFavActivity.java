package com.project.chochosan.favorite;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;


public class DetailFavActivity extends AppCompatActivity {

    String img, judul, desc, tgl, post, popular, lang;
    ImageView tvImg,tvBack;
    TextView tvJudul, tvDesc, tvTgl, pop, tvLang;
    CoordinatorLayout coordinatorLayout;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fav);
        tvImg = findViewById(R.id.poster);
        tvBack = findViewById(R.id.iv_detail_movie);
        tvJudul = findViewById(R.id.tv_tittle);
        tvDesc = findViewById(R.id.tv_detail_overview);
        tvTgl = findViewById(R.id.tv_release);
        tvLang = findViewById(R.id.language);
        pop = findViewById(R.id.popularity);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);


        Movie();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Movie");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void Movie(){
        img = getIntent().getStringExtra("poster_path");
        judul = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("overview");
        tgl = getIntent().getStringExtra("release_date");
        popular = getIntent().getStringExtra("popularity");
        post = getIntent().getStringExtra("backdrop_path");
        lang = getIntent().getStringExtra("language");

        Picasso.with(getApplicationContext())
                .load(img)
                .placeholder(R.drawable.loading)
                .into(tvImg);

       Picasso.with(getApplicationContext())
                .load(post)
                .placeholder(R.drawable.loading)
                .into(tvBack);

        tvJudul.setText(judul);
        pop.setText(popular);
        tvLang.setText(lang);
        tvDesc.setText(desc);
        tvTgl.setText(tgl);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
