package com.androidwidgetpoc;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListProvider implements RemoteViewsFactory {
    private ArrayList<ListItem> listItemList = new ArrayList<ListItem>();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        Bundle extras = intent.getExtras();
        String array = intent.getStringExtra("array");
        populateListItem(array);
    }

    private void populateListItem(String array){
      JSONArray arr = null;
      try {
          arr = new JSONArray(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <  arr.length(); i++) {
            ListItem listItem = new ListItem();
            try {
            JSONObject o = arr.getJSONObject(i);
            listItem.heading = o.getString("heading");
            listItem.content = o.getString("content");
            listItem.ch = o.getString("ch");
          } catch (JSONException e) {
              e.printStackTrace();
          }
            listItemList.add(listItem);
        }
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.list_row);
        ListItem listItem = listItemList.get(position);
        URL url = null;
        try {
            url = new URL(listItem.ch);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        remoteView.setImageViewBitmap(R.id.ch, bmp);
        remoteView.setTextViewText(R.id.heading, listItem.heading);
        remoteView.setTextViewText(R.id.content, listItem.content);

        Intent intent = new Intent(context, CustomReactActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);  // Identifies the particular widget...
        intent.putExtra("module", "androidWidgetPoc");

        Bundle b = new Bundle();
        b.putString("navigationKey","MedicationScreen");

        Bundle med = new Bundle();
        med.putString("medId", listItem.heading);
        med.putString("medName", listItem.content);

        b.putBundle("medication",med);

        intent.putExtra("data",b);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        remoteView.setOnClickFillInIntent(R.id.widgetItemContainer, intent);
        return remoteView;
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
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {}

    @Override
    public void onDestroy() {}

}
