package com.kunalmadan.android.comiclover;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kunalmadan.android.comiclover.asyncTask.FetchSuperHeroData;
import com.kunalmadan.android.comiclover.database.SuperHeroColumns;
import com.kunalmadan.android.comiclover.database.SuperHeroProvider;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int CURSOR_LOADER_ID = 0;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private HeroCursorAdapter mCursorAdapter;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(isConnectingToInternet()) {
            FetchSuperHeroData fetchSuperHeroData = new FetchSuperHeroData(this);

            fetchSuperHeroData.execute();
        }

        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);



        View view1 = findViewById(R.id.grid);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        mCursorAdapter = new HeroCursorAdapter(getApplicationContext(), null,0 , CURSOR_LOADER_ID);

        gridView.setAdapter(mCursorAdapter);



            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                    int heroId = cursor.getColumnIndex(SuperHeroColumns.ID);

                    String heroIid = cursor.getString(heroId);
                    Log.v(LOG_TAG,"HERO_ID"+cursor.getString(heroId));

                    Intent intent = new Intent(MainActivity.this,HeroComicDetail.class);

                    intent.putExtra("HERO_ID",heroIid);

                    startActivity(intent);
                }
            });


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


       ((MyApplication) getApplication()).startTracking();
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/




        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(MainActivity.this, SuperHeroProvider.SuperHero.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mCursorAdapter.swapCursor(null);
    }

    public boolean isConnectingToInternet(){

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
