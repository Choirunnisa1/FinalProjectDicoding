package com.project.chochosan.finalproject4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.project.chochosan.finalproject4.model.FavoriteModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

import static com.project.chochosan.finalproject4.database.DatabaseContract.FavoriteColumns.BACKDROP;
import static com.project.chochosan.finalproject4.database.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.project.chochosan.finalproject4.database.DatabaseContract.FavoriteColumns.LANGUANGE;
import static com.project.chochosan.finalproject4.database.DatabaseContract.FavoriteColumns.POPULARITY;
import static com.project.chochosan.finalproject4.database.DatabaseContract.FavoriteColumns.POSTER;
import static com.project.chochosan.finalproject4.database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.project.chochosan.finalproject4.database.DatabaseContract.FavoriteColumns.TITLE;
import static com.project.chochosan.finalproject4.database.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {

    private static String DATABASE_TABLE = TABLE_FAVORITE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context){
        this.context = context;
    }


    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }



    public ArrayList<FavoriteModel> query() {
        ArrayList<FavoriteModel> arrayList = new ArrayList<FavoriteModel>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        FavoriteModel favorite;
        if (cursor.getCount() > 0) {
            do {

                favorite = new FavoriteModel();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favorite.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                favorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favorite.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                favorite.setDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                favorite.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));
                favorite.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUANGE)));

                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                , new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }


    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
