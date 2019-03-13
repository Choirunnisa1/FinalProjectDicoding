package com.project.chochosan.favorite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import com.squareup.picasso.Picasso;



import static android.provider.BaseColumns._ID;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.BACKDROP;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.LANGUANGE;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.POPULARITY;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.POSTER;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.TITLE;
import static com.project.chochosan.favorite.DatabaseContract.getColumnString;


public class MovieAdapter extends CursorAdapter {
    public static String URL = "https://www.themoviedb.org/movie/";


    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_favorite, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null){
            TextView textViewTitle, textViewOverview, textViewRelease;
            ImageView imgPoster;
            Button btn_detail, btn_share;

            textViewTitle = view.findViewById(R.id.title);
            textViewOverview = view.findViewById(R.id.overview);
            textViewRelease = view.findViewById(R.id.release_date);
            imgPoster = view.findViewById(R.id.poster);
            btn_detail = view.findViewById(R.id.detail);
            btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailFavActivity.class);
                    i.putExtra("title", getColumnString(cursor,TITLE));
                    i.putExtra("poster_path", getColumnString(cursor,POSTER));
                    i.putExtra("overview", getColumnString(cursor,DESCRIPTION));
                    i.putExtra("release_date", getColumnString(cursor,RELEASE_DATE));
                    i.putExtra("popularity", getColumnString(cursor, POPULARITY));
                    i.putExtra("original_language", getColumnString(cursor, LANGUANGE));
                    i.putExtra("backdrop_path", getColumnString(cursor, BACKDROP));
                    context.startActivity(i);
                }
            });
            btn_share = view.findViewById(R.id.share);
            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String titleUrl = getColumnString(cursor,TITLE).replaceAll(" ","-");
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBodyText = URL+getColumnString(cursor,_ID)+"-"+titleUrl;
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                    view.getContext().startActivity(Intent.createChooser(sharingIntent, "Share Movie"));
                }
            });

            textViewTitle.setText(getColumnString(cursor,TITLE));
            textViewOverview.setText(getColumnString(cursor,DESCRIPTION));
            textViewRelease.setText(getColumnString(cursor,RELEASE_DATE));
            Picasso.with(context).load(getColumnString(cursor,POSTER))
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(imgPoster);
        }
    }
}