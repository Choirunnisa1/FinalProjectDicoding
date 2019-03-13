package com.project.chochosan.finalproject4.retrofit;

/**
 * Created by Nisa on 20/09/2018.
 */

public class BaseUrl {
    public static final String BASE_URL_API = "https://api.themoviedb.org/3/";

    public static ApiService getAPIService(){
        return Client.getClient(BASE_URL_API).create(ApiService.class);
    }
}
