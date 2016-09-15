package com.kunalmadan.android.comiclover.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by KUNAL on 15-Sep-16.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
