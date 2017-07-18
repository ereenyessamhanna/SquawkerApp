package com.example.ereenyessam.squawker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ereenyessam.squawker.following.FollowingPreferenceActivity;
import com.example.ereenyessam.squawker.following.FollowingPreferenceFragment;
import com.example.ereenyessam.squawker.provider.SquawkContract;
import com.example.ereenyessam.squawker.provider.SquawkProvider;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int LOADER_ID_MESSAGES = 0;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    SquawkAdapter mAdapter;

    static final String[] MESSAGES_PROJECTION = {
            SquawkContract.COLUMN_AUTHOR,
            SquawkContract.COLUMN_MESSAGE,
            SquawkContract.COLUMN_DATE,
            SquawkContract.COLUMN_AUTHOR_KEY
    };

    static final int COL_NUM_AUTHOR = 0;
    static final int COL_NUM_MESSAGE = 1;
    static final int COL_NUM_DATE = 2;
    static final int COL_NUM_AUTHOR_KEY = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.squawks_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // seprate between items
        DividerItemDecoration mDividerItemDecoration= new DividerItemDecoration(mRecyclerView.getContext(),mLinearLayoutManager.getOrientation());

        mRecyclerView.addItemDecoration(mDividerItemDecoration);


        mAdapter = new SquawkAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //start loader from here

        getSupportLoaderManager().initLoader(LOADER_ID_MESSAGES, null, this);


       /* ContentValues values = new ContentValues();
        values.put(SquawkContract.COLUMN_DATE, 1487968810557L);
        values.put(SquawkContract.COLUMN_AUTHOR, "Ereeny");
        values.put(SquawkContract.COLUMN_MESSAGE, "Hi");
        values.put(SquawkContract.COLUMN_AUTHOR_KEY, SquawkContract.EREENY_KEY);
        getContentResolver().insert(SquawkProvider.SquawkMessages.CONTENT_URI, values);

        ContentValues contentValues = new ContentValues();
        contentValues.put(SquawkContract.COLUMN_DATE, 1487968860559L);
        contentValues.put(SquawkContract.COLUMN_AUTHOR, "Mina");
        contentValues.put(SquawkContract.COLUMN_MESSAGE, "Hello");
        contentValues.put(SquawkContract.COLUMN_AUTHOR_KEY, SquawkContract.MINA_KEY);
        getContentResolver().insert(SquawkProvider.SquawkMessages.CONTENT_URI, contentValues);*/




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_following_preferences) {

            Intent startFollowingActivity = new Intent(MainActivity.this, FollowingPreferenceActivity.class);
            startActivity(startFollowingActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String mSelection = SquawkContract.createSelectionForCurrentFollowers(PreferenceManager.getDefaultSharedPreferences(this));

        Log.d(LOG_TAG, "Selection is " + mSelection);

        return new  CursorLoader(this, SquawkProvider.SquawkMessages.CONTENT_URI,MESSAGES_PROJECTION,mSelection,null,SquawkContract.COLUMN_DATE +" DESC ");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }
}
