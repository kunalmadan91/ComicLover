package com.kunalmadan.android.comiclover.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.kunalmadan.android.comiclover.R;
import com.kunalmadan.android.comiclover.database.SuperHeroColumns;
import com.kunalmadan.android.comiclover.database.SuperHeroProvider;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by KUNAL on 15-Sep-16.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    private Intent mIntent;


    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;

    }

    @Override
    public void onCreate() {
        //initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public RemoteViews getViewAt(int position) {


        String heroName = "";
        String heroId = "";
        String thumbnail = "";
        String url = "";
        int isUp = 1;

        if (mCursor.moveToPosition(position)) {
            heroName = mCursor.getString(mCursor.getColumnIndex(SuperHeroColumns.NAME));

            heroId = mCursor.getString(mCursor.getColumnIndex(SuperHeroColumns.ID));

            thumbnail = mCursor.getString(mCursor.getColumnIndex(SuperHeroColumns.THUMBNAIL));

            url = thumbnail+"/portrait_fantastic.jpg";

        }

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_item_comic);

        remoteViews.setTextViewText(R.id.text1, heroName);
        Bitmap b = null;
        try {

            b = Picasso.with(mContext).load(url).get();


            Bitmap circleBitmap = Bitmap.createBitmap(b.getWidth(), b.getHeight(), Bitmap.Config.ARGB_8888);

            BitmapShader shader = new BitmapShader (b,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setShader(shader);

            Canvas c = new Canvas(circleBitmap);
            c.drawCircle(b.getWidth()/2, b.getHeight()/2, b.getWidth()/2, paint);

            remoteViews.setImageViewBitmap(R.id.imagewidget, circleBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent fillInIntent = new Intent();


        fillInIntent.putExtra("HERO_ID", heroId);

        remoteViews.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void initData() {

        // Refresh the cursor
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = mContext.getContentResolver().query(SuperHeroProvider.SuperHero.CONTENT_URI,
                null,
                null,
                null,
                null,
                null);

    }
}
