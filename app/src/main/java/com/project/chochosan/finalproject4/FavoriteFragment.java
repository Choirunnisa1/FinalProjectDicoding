package com.project.chochosan.finalproject4;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.chochosan.finalproject4.adapter.FavoriteAdapter;
import com.project.chochosan.finalproject4.database.FavoriteHelper;
import com.project.chochosan.finalproject4.model.FavoriteModel;

import java.util.ArrayList;
import java.util.LinkedList;

import static com.project.chochosan.finalproject4.database.DatabaseContract.FavoriteColumns.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    RecyclerView rvMovies;
    private Cursor listFav;
    private FavoriteAdapter favoriteAdapter;



    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvMovies = view.findViewById(R.id.rv_movie);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setHasFixedSize(true);

        favoriteAdapter = new FavoriteAdapter(getContext());
        favoriteAdapter.setListFavorite(listFav);
        rvMovies.setAdapter(favoriteAdapter);

        new LoadFavoritAsync().execute();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class LoadFavoritAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);


            listFav = favorite;
            favoriteAdapter.setListFavorite(listFav);
            favoriteAdapter.notifyDataSetChanged();

            if (listFav.getCount() == 0) {
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rvMovies, R.string.no_favourite, Snackbar.LENGTH_SHORT).show();
    }

}


