package com.project.chochosan.favorite;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import static com.project.chochosan.favorite.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static java.util.Objects.requireNonNull;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private MovieAdapter adapterFavorite;

    private final int LOAD_NOTES_ID = 110;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requireNonNull(getSupportActionBar()).setTitle("List Favorite Movies");

        ListView lvMovies = findViewById(R.id.lv_movies);
        adapterFavorite = new MovieAdapter(this, null, true);
        lvMovies.setAdapter(adapterFavorite);

        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapterFavorite.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapterFavorite.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_NOTES_ID);
    }

}
