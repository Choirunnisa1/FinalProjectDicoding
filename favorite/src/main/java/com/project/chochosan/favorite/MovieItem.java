package com.project.chochosan.favorite;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.BACKDROP;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.LANGUANGE;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.POPULARITY;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.POSTER;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.TITLE;
import static com.project.chochosan.favorite.DatabaseContract.getColumnInt;
import static com.project.chochosan.favorite.DatabaseContract.getColumnString;

public class MovieItem implements Parcelable {

    private int id;
    private String title;
    private String poster;
    private String date;
    private String overview;
    private String backdrop;
    private String language;
    private String popularity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.date);
        dest.writeString(this.language);
        dest.writeString(this.backdrop);
        dest.writeString(this.popularity);
        dest.writeString(this.poster);
    }

    public MovieItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, TITLE);
        this.overview = getColumnString(cursor, DESCRIPTION);
        this.date = getColumnString(cursor, RELEASE_DATE);
        this.poster = getColumnString(cursor, POSTER);
        this.popularity = getColumnString(cursor, POPULARITY);
        this.backdrop = getColumnString(cursor, BACKDROP);
        this.language = getColumnString(cursor, LANGUANGE);
    }

    private MovieItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.date = in.readString();
        this.poster = in.readString();
        this.language = in.readString();
        this.backdrop = in.readString();
        this.popularity = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

}