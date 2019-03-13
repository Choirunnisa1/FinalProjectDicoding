package com.project.chochosan.finalproject4;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class NowPlayingFragment extends Fragment {
    private final String api_key = BuildConfig.MOVIE_API_KEY;
    private final String lang = "en-US";
    private RecyclerView rvNowPlaying;
    private List<MovieModel> listView;
    Activity activity;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container,false);


        getNowPlaying();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvNowPlaying = (RecyclerView) view.findViewById(R.id.rv_now_playing);
        listView = new ArrayList<>();
        getNowPlaying();

    }
    private void getNowPlaying() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), getString(R.string.Tittle_loading), getString(R.string.Message_loading), false, false);
        ApiService apiService = BaseUrl.getAPIService();
        apiService.getNowPlaying(api_key, lang).enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    listView = response.body().getResults();
                    MovieAdapter adapter = new MovieAdapter(getActivity(), listView);
                    rvNowPlaying.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvNowPlaying.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Failed, check your connection !", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
