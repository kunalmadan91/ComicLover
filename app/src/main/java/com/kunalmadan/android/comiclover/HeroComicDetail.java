package com.kunalmadan.android.comiclover;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.kunalmadan.android.comiclover.asyncTask.FetchComicData;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class HeroComicDetail extends AppCompatActivity {

    public ArrayList<Comic> comicArrayList = new ArrayList<Comic>();
    ;
    RecyclerView recyclerView = null;


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(HeroComicDetail.this);
        super.onStop();
    }

    public void onEvent(DataEvent event) {
        // your implementation
        if (isConnectingToInternet()) {
            comicArrayList = event.getmList();
            View brokenLinkView = findViewById(R.id.link_broken);
            Log.v("oneventlist", "oneventlist" + comicArrayList);
            if(comicArrayList.equals(null) || comicArrayList.size() == 0) {
                brokenLinkView.setVisibility(View.VISIBLE);
            }
            recyclerView.setAdapter(new ComicsAdapter(getApplicationContext(), comicArrayList));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(HeroComicDetail.this);
        setContentView(R.layout.activity_hero_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        String id = intent.getStringExtra("HERO_ID");

        Log.v("heroidis", "heroidis" + id);
        View errorMsgView = findViewById(R.id.error_message);


        if (isConnectingToInternet()) {
            FetchComicData fetchComicData = new FetchComicData(this);

            fetchComicData.execute(id);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }

        else {
            errorMsgView.setVisibility(View.VISIBLE);
        }

    }

    public boolean isConnectingToInternet() {

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
