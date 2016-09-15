package com.kunalmadan.android.comiclover.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kunalmadan.android.comiclover.Comic;
import com.kunalmadan.android.comiclover.DataEvent;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by KUNAL on 02-09-2016.
 */
public class FetchComicData extends AsyncTask<String, Void, ArrayList<Comic>> {
    private ProgressDialog progressDialog;
    private Context mContext;
    private static final String LOG_TAG = FetchComicData.class.getSimpleName();
    public static ArrayList<Comic> comicList;

    public FetchComicData(Context context) {
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
    protected ArrayList<Comic> doInBackground(String... params) {
        String idhero = params[0];

        String url = "http://gateway.marvel.com:80/v1/public/characters/" + idhero + "/comics?ts=1&apikey=3b9f480b29e4101e40fddf857851a27b" +
                "&hash=bd3fe11cc3a47a90501a11360b644ec2";

        Log.v("urll", "urll" + url);

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        String res = "";

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("responsecomic", "responsecomic" + res);

        return getsuperHeroDatafromJson(res);
    }


    public ArrayList<Comic> getsuperHeroDatafromJson(String data) {
        Comic comic = null;

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject outputData = jsonObject.getJSONObject("data");

            JSONArray jsonArray = outputData.getJSONArray("results");
              int counter = 0;

            comicList = new ArrayList<>();
           // Comic comic;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String title1 = obj.getString("title");
                String description = obj.getString("description");
                String diamondCode = obj.getString("diamondCode");
                String issueNumber = Integer.toString(obj.getInt("issueNumber"));


                JSONArray jsonArray1 = obj.getJSONArray("dates");

                JSONObject dateobj = jsonArray1.getJSONObject(0);
                //JSONObject digitalPurchaseDateObj = jsonArray1.getJSONObject(2);

                String date = dateobj.getString("date");

                //String digitalDate = digitalPurchaseDateObj.getString("date");

                JSONArray jsonArrayPrice = obj.getJSONArray("prices");

                JSONObject priceobj = jsonArrayPrice.getJSONObject(0);
                //JSONObject digitalPurchase = jsonArrayPrice.getJSONObject(0);

                String price = priceobj.getString("price");

               // String digitalPurchasePrice = digitalPurchase.getString("price");

                String thumbnail = obj.getString("thumbnail");

                JSONArray imgarray = obj.getJSONArray("images");

                JSONObject img = imgarray.getJSONObject(0);
                String image = img.getString("path");


                Log.v("jsonparse","jsonparse "+title1+description+price+image+thumbnail+date);

                comic = new Comic(title1, description, price, image, thumbnail, date, diamondCode, issueNumber);

                comicList.add(comic);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return comicList;
    }

    @Override
    protected void onPostExecute(ArrayList<Comic> comicList) {
        super.onPostExecute(comicList);
        progressDialog.dismiss();
        EventBus.getDefault().post(new DataEvent(comicList));

    }
}
