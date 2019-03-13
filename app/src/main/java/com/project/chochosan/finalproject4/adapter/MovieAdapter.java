package com.project.chochosan.finalproject4.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import com.project.chochosan.finalproject4.BuildConfig;
import com.project.chochosan.finalproject4.R;
import com.project.chochosan.finalproject4.activity.DetailActivity;
import com.project.chochosan.finalproject4.model.MovieModel;
import com.squareup.picasso.Picasso;


import java.util.List;


/**
 * Created by Nisa on 20/09/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<MovieModel> listMovies;
    public static String URL = "https://www.themoviedb.org/movie/";

    public MovieAdapter(Context context, List<MovieModel> listMovies){
        this.context = context;
        this.listMovies = listMovies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String posterUrl =  "http://image.tmdb.org/t/p/w185";
        final MovieModel movieModel = listMovies.get(position);
        Picasso.with(context)
                .load(BuildConfig.Image+listMovies.get(position).getPosterPath())
                .into(holder.gmb);
        holder.judul.setText(movieModel.getTitle());
        holder.desc.setText(movieModel.getOverview());

        holder.tgl.setText(movieModel.getReleaseDate());

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", movieModel.getTitle());
                intent.putExtra("poster_path", movieModel.getPosterPath());
                intent.putExtra("overview", movieModel.getOverview());
                intent.putExtra("release_date", movieModel.getReleaseDate());
                intent.putExtra("popularity", movieModel.getPopularity());
                intent.putExtra("backdrop_path",movieModel.getBackdropPath());
                intent.putExtra("original_language",movieModel.getOriginalLanguage());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = movieModel.getTitle().replaceAll("","-");
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String shareBody = URL+movieModel.getId()+"-"+title;
                share.putExtra(Intent.EXTRA_TEXT, shareBody);
                view.getContext().startActivity(Intent.createChooser(share,"Share Movie"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView gmb;
        private Button btn_detail, btn_share;
        private TextView judul, tgl, desc;

        public ViewHolder(View itemView) {
            super(itemView);

            btn_detail = itemView.findViewById(R.id.detail);
            gmb = itemView.findViewById(R.id.img_poster);
            judul = itemView.findViewById(R.id.tv_tittle);
            desc = itemView.findViewById(R.id.tv_overview);
            tgl = itemView.findViewById(R.id.tv_release);
            btn_share = itemView.findViewById(R.id.share);
        }
    }

}
