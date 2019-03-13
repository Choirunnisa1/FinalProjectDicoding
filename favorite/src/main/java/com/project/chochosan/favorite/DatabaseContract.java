package com.project.chochosan.favorite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.project.chochosan.finalproject4";
    public static final String SCHEME = "content";
    public static String TABLE_FAVORITE = "favorite";

    public static final class FavoriteColumns implements BaseColumns {


        public static String TITLE = "title";
        public static String POSTER = "poster_path";
        public static String BACKDROP = "backdrop_path";
        public static String RELEASE_DATE = "date";
        public static String LANGUANGE = "original_language";
        public static String POPULARITY = "popularity";
        public static String DESCRIPTION = "description";


        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();

    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

}
