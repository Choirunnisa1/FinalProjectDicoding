package com.project.chochosan.finalproject4.retrofit;

import com.project.chochosan.finalproject4.model.ResultModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nisa on 20/09/2018.
 */

public interface ApiService {
    @GET("discover/movie")
    Call<ResultModel> getMovies(@Query("api_key") String api_key,
                                   @Query("language") String language);
    @GET("search/movie")
    Call<ResultModel> getSearch(@Query("api_key") String api_key,
                                @Query("language") String language,
                                @Query("query") String query);

    @GET("movie/now_playing")
    Call<ResultModel> getNowPlaying(@Query("api_key") String api_key,
                                    @Query("language") String language);

    @GET("movie/upcoming")
    Call<ResultModel> getUpcoming(@Query("api_key") String api_key,
                                  @Query("language") String language);

}
