package com.kunalmadan.android.comiclover.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.kunalmadan.android.comiclover.HeroComicDetail;
import com.kunalmadan.android.comiclover.R;

/**
 * Created by KUNAL on 14-Sep-16.
 */
public class ComicWidget extends AppWidgetProvider {

    Context context;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.comics_widget);
        // Instruct the widget manager to update the widget

        setRemoteAdapter(context, views);

        //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_list);
        Intent clickTemplate = new Intent(context, HeroComicDetail.class);

        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(clickTemplate)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.widget_list,pendingIntent);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_list);
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_list);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, WidgetService.class));
    }


}
