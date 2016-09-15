package com.kunalmadan.android.comiclover;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComicDetail extends AppCompatActivity {


    @Bind(R.id.detail_title)
    TextView titleView;

    @Bind(R.id.onsaledate)
    TextView onSaleDate;

    @Bind(R.id.diamondCode)
    TextView diamondCode;

    @Bind(R.id.issueNumber)
    TextView issueNumber;

    @Bind(R.id.desc)
    TextView description;

    @Bind(R.id.img)
    ImageView mainImage;

    CollapsingToolbarLayout collapsingToolbarLayout;

    Toolbar toolbar;

    String IMAGE_SCALE = "/landscape_xlarge.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


        Intent intent = getIntent();

        Comic comicData = intent.getExtras().getParcelable("COMIC_OBJ");

        ButterKnife.bind(this);

        try {
            if (comicData.getOnSaleDate().equals("null") || comicData.getOnSaleDate() == "0") {
                onSaleDate.setText(getResources().getString(R.string.text_not_available));
            } else {
                String dateParse = dateParser(comicData.getOnSaleDate());
                onSaleDate.setText(dateParse);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        titleView.setText(comicData.getTitle());
        collapsingToolbarLayout.setTitle(comicData.getTitle());


        if (comicData.getDescription().equals("null") || comicData.getDescription() == "0") {

            description.setText(getResources().getString(R.string.description_not_available));
        } else {
            description.setText(comicData.getDescription());
        }


        if (comicData.getIssueNumber().equals("null") || comicData.getDiamondCode().equals("")) {
            issueNumber.setText(getResources().getString(R.string.text_not_available));
        }

        else {
            issueNumber.setText(comicData.getIssueNumber());
        }

        issueNumber.setText(comicData.getIssueNumber());


        if(comicData.getDiamondCode().equals("null") || comicData.getDiamondCode().equals("")) {
            diamondCode.setText(getResources().getString(R.string.text_not_available));
        }
        else {
            diamondCode.setText(comicData.getDiamondCode());
        }


        String url = comicData.getImage();

        String imgUrl = url + IMAGE_SCALE;

        Picasso.with(this)
                .load(imgUrl).placeholder(R.drawable.icon)
                .error(R.drawable.icon)
                .into(mainImage);
        mainImage.setContentDescription(comicData.getTitle());

       // Toast.makeText(ComicDetail.this, "hey" + cmc.getDiamondCode(), Toast.LENGTH_SHORT).show();
    }

    public boolean isConnectingToInternet() {

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public String dateParser(String date) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date dateStr = formatter.parse(date);

        String formattedDate = formatter.format(dateStr);

        return formattedDate;
    }


}
