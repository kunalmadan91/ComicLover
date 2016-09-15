package com.kunalmadan.android.comiclover.asyncTask;

import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

import com.kunalmadan.android.comiclover.HeroCursorAdapter;
import com.kunalmadan.android.comiclover.database.SuperHeroColumns;
import com.kunalmadan.android.comiclover.database.SuperHeroProvider;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by KUNAL on 25-08-2016.
 */
public class FetchSuperHeroData extends AsyncTask<Void, Void, String> {

    private static final String LOG_TAG = FetchSuperHeroData.class.getSimpleName();
    Context mContext;
    private ProgressDialog progressDialog;
    HeroCursorAdapter heroCursorAdapter;

    public FetchSuperHeroData(Context context) {
        mContext = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(Void... voids) {

        String requestUrl = "http://gateway.marvel.com:80/v1/public/characters?" +
                "limit=100&ts=1&apikey=3b9f480b29e4101e40fddf857851a27b" +
                "&hash=bd3fe11cc3a47a90501a11360b644ec2";

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        Response response = null;
        String responseStr = "";


        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response.code() == 200) {


        try {
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("response", "response" + responseStr);


        return getSuperHeroDataFromJson(responseStr);
        }
        return null;
    }

    public String getSuperHeroDataFromJson(String data) {
        String nameComic = "";
        String uri = "";

        try {
            JSONObject jsonObject = new JSONObject(data);

            JSONObject outputData = jsonObject.getJSONObject("data");

            JSONArray jsonArray = outputData.getJSONArray("results");

            ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();

            Log.v("MyHero", "MyHero" + jsonArray);

           Cursor c =
                    mContext.getContentResolver().query(SuperHeroProvider.SuperHero.CONTENT_URI,
                            new String[]{SuperHeroColumns._ID},
                            null,
                            null,
                            null);

            if (c.getCount() == 0) {

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);

                int id = obj.getInt("id");
                String name = obj.getString("name");
                String description = obj.getString("description");

                JSONObject thumb = jsonArray.getJSONObject(i).getJSONObject("thumbnail");

                String thumbnail = thumb.getString("path");

                String ext = thumb.getString("extension");

                JSONObject comicsData = jsonArray.getJSONObject(i).getJSONObject("comics");

                JSONArray comicsArray = comicsData.getJSONArray("items");

                    ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                            SuperHeroProvider.SuperHero.CONTENT_URI);

                    builder.withValue(SuperHeroColumns.NAME, name);
                    builder.withValue(SuperHeroColumns.ID, id);
                    builder.withValue(SuperHeroColumns.THUMBNAIL, thumbnail);

                    batchOperations.add(builder.build());

                    for (int j = 0; j < comicsArray.length(); j++) {

                        JSONObject comic = comicsArray.getJSONObject(j);

                        nameComic = comic.getString("name");
                        uri = comic.getString("resourceURI");
                        builder.withValue(SuperHeroColumns.COMIC_NAME, nameComic);
                        builder.withValue(SuperHeroColumns.RESOURCE_URI, uri);

                    }
                    Log.v("insertdata", "insertdata>>" + name + "<>" + id + "<>" + thumbnail + "<>" + nameComic + "<>" + uri);

                }

            try {
                mContext.getContentResolver().applyBatch(SuperHeroProvider.AUTHORITY, batchOperations);
            } catch (RemoteException | OperationApplicationException e) {
                Log.e(LOG_TAG, "Error applying batch insert", e);
            }

           }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        progressDialog.dismiss();
    }
}
