package com.project.chochosan.finalproject4.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.project.chochosan.finalproject4.database.DatabaseContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.BaseColumns._ID;
import static com.project.chochosan.finalproject4.database.DatabaseContract.getColumnInt;
import static com.project.chochosan.finalproject4.database.DatabaseContract.getColumnString;

/**
 * Created by Nisa on 20/09/2018.
 */

public class FavoriteModel implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String date;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("id")
    private Integer id;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String language;

    protected FavoriteModel(Parcel in) {
        id = in.readInt();
        popularity = in.readString();
        backdropPath = in.readString();
        language = in.readString();
        title = in.readString();
        poster = in.readString();
        date = in.readString();
        overview = in.readString();
    }

    public static final Parcelable.Creator<FavoriteModel> CREATOR = new Parcelable.Creator<FavoriteModel>() {
        @Override
        public FavoriteModel createFromParcel(Parcel in) {
            return new FavoriteModel(in);
        }

        @Override
        public FavoriteModel[] newArray(int size) {
            return new FavoriteModel[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop() {
        return backdropPath;
    }

    public void setBackdrop(String backdrop) {
        this.backdropPath = backdrop;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return overview;
    }

    public void setDescription(String description) {
        this.overview = description;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(backdropPath);
        parcel.writeString(date);
        parcel.writeString(overview);
        parcel.writeString(popularity);
        parcel.writeString(language);
    }



public FavoriteModel(){

}

public FavoriteModel(Cursor cursor){
    this.id = getColumnInt(cursor, _ID);
    this.title = getColumnString(cursor, DatabaseContract.FavoriteColumns.TITLE);
    this.language = getColumnString(cursor, DatabaseContract.FavoriteColumns.LANGUANGE);
    this.popularity = getColumnString(cursor, DatabaseContract.FavoriteColumns.POPULARITY);
    this.poster = getColumnString(cursor, DatabaseContract.FavoriteColumns.POSTER);
    this.date = getColumnString(cursor, DatabaseContract.FavoriteColumns.RELEASE_DATE);
    this.backdropPath = getColumnString(cursor, DatabaseContract.FavoriteColumns.BACKDROP);
    this.overview = getColumnString(cursor, DatabaseContract.FavoriteColumns.DESCRIPTION);
}

}
