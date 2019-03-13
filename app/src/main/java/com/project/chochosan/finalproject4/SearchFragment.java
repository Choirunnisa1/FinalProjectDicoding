package com.project.chochosan.finalproject4;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.project.chochosan.finalproject4.adapter.MovieAdapter;
import com.project.chochosan.finalproject4.model.MovieModel;
import com.project.chochosan.finalproject4.model.ResultModel;
import com.project.chochosan.finalproject4.retrofit.ApiService;
import com.project.chochosan.finalproject4.retrofit.BaseUrl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SearchView searchViewMovie;
    RecyclerView rv;
    ApiService apiService;
    List<MovieModel> listMovie ;
    MovieAdapter movieAdapter;
    ProgressDialog mProgress;
    private final String api_key = BuildConfig.MOVIE_API_KEY;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.film_search_rv);
        listMovie = new ArrayList<>();
        apiService = BaseUrl.getAPIService();


        load();

        searchViewMovie = (SearchView) view.findViewById(R.id.search_film);
        searchViewMovie.setQueryHint("Insert Keyword");
        searchViewMovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovie(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    load();
                }
                return false;
            }
        });


    }
    public void searchMovie(String key) {
        mProgress = ProgressDialog.show(getContext(), null, "Please wait...", true, false);
        apiService.getSearch(api_key, "en-US", key).enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if (response.isSuccessful()) {
                    mProgress.dismiss();
                    listMovie = response.body().getResults();
                    movieAdapter = new MovieAdapter(getActivity(), listMovie);
                    rv.setAdapter(movieAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(new MovieAdapter(getContext(), listMovie));
                    movieAdapter.notifyDataSetChanged();
                } else {
                    mProgress.dismiss();
                    Toast.makeText(getContext(), "Failed Get Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                mProgress.dismiss();
                Toast.makeText(getContext(), "Check Your Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void load() {
        mProgress = ProgressDialog.show(getContext(), null, "Please wait...", true, false);
        apiService.getMovies(api_key, "en-US").enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if (response.isSuccessful()) {
                    mProgress.dismiss();
                    movieAdapter = new MovieAdapter(getActivity(), listMovie);
                    rv.setAdapter(movieAdapter);
                    listMovie = response.body().getResults();
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(new MovieAdapter(getContext(), listMovie));
                    movieAdapter.notifyDataSetChanged();
                } else {
                    mProgress.dismiss();
                    Toast.makeText(getContext(), "Failed Get Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                mProgress.dismiss();
                Toast.makeText(getContext(), "Failed Get Data", Toast.LENGTH_SHORT).show();

            }


        });
    }
}
