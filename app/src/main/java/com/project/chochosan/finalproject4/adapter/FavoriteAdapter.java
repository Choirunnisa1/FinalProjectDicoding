package com.project.chochosan.finalproject4.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.chochosan.finalproject4.BuildConfig;
import com.project.chochosan.finalproject4.R;
import com.project.chochosan.finalproject4.activity.DetailActivity;
import com.project.chochosan.finalproject4.model.FavoriteModel;
import com.squareup.picasso.Picasso;


/**
 * Created by Nisa on 20/09/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private Cursor listFavorite;
    public static String URL = "https://www.themoviedb.org/movie/";

    public FavoriteAdapter(Context context){
        this.context = context;
    }

    public void setListFavorite(Cursor listMovies){
        this.listFavorite = listMovies;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FavoriteModel favorite = getItem(position);
        Picasso.with(context)
                .load(BuildConfig.Image+favorite.getPoster())
                .into(holder.gmb);
        holder.judul.setText(favorite.getTitle());
        holder.desc.setText(favorite.getDescription());
        holder.tgl.setText(favorite.getDate());
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("title", favorite.getTitle());
                i.putExtra("poster_path", favorite.getPoster());
                i.putExtra("overview", favorite.getDescription());
                i.putExtra("release_date", favorite.getDate());
                i.putExtra("backdrop_path", favorite.getBackdrop());
                i.putExtra("original_language", favorite.getLanguage());
                i.putExtra("popularity", favorite.getPopularity());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                String webTitle = favorite.getTitle().replaceAll("", "-");
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String shareBody = URL+favorite.getId()+"-"+webTitle;
                share.putExtra(Intent.EXTRA_TEXT, shareBody);
                vi.getContext().startActivity(Intent.createChooser(share,"Share Movie"));

            }
        });



        Log.e("DATE", ""+favorite.getDate());
    }

    @Override
    public int getItemCount() {
        if (listFavorite == null) {
            return 0;
        } return listFavorite.getCount();
    }

    private FavoriteModel getItem(int position){
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new FavoriteModel(listFavorite);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView gmb;
        TextView judul, tgl, desc;
        Button btn_detail,btn_share;

        ViewHolder(View itemView) {
            super(itemView);

            gmb = itemView.findViewById(R.id.img_poster);
            judul = itemView.findViewById(R.id.tv_tittle);
            desc = itemView.findViewById(R.id.tv_overview);
            tgl = itemView.findViewById(R.id.tv_release);
            btn_detail = itemView.findViewById(R.id.detail);
            btn_share = itemView.findViewById(R.id.share);
        }
    }

}

