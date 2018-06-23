package com.androidwidgetpoc;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;
import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.widget.*;

public class BackgroundTaskBridge extends ReactContextBaseJavaModule {

    public BackgroundTaskBridge(final ReactApplicationContext reactContext) { super(reactContext); }

    @Override
    public String getName() { return "BackgroundTaskBridge"; }

    @ReactMethod
    public void initializeWidgetBridge(ReadableArray starredCharms) {
        RemoteViews widgetView = new RemoteViews(this.getReactApplicationContext().getPackageName(), R.layout.appwidget);
        widgetView.removeAllViews(R.id.charms_layout);
        JSONArray obj = new JSONArray();
        for (int i = 0; i < starredCharms.size(); i++) {
          ReadableMap charm = starredCharms.getMap(i);
          JSONObject el = new JSONObject();
          String bio = charm.getString("bio").replace("\r","").replace("\n","").replace("\t","");
          try {
          el.put("heading",charm.getString("name"));
          el.put("content",bio);
          el.put("ch",charm.getString("imageurl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
          obj.put(el);
          //updateView(widgetView, charm, R.layout.list_row);
        }

        Intent svcIntent = new Intent(this.getReactApplicationContext(), WidgetService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, (new ComponentName(this.getReactApplicationContext(), WidgetProvider.class)));
        svcIntent.putExtra("array", obj.toString());
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        widgetView.setRemoteAdapter(R.id.listViewWidget, svcIntent);

        AppWidgetManager.getInstance(this.getReactApplicationContext()).updateAppWidget(new ComponentName(this.getReactApplicationContext(), WidgetProvider.class), widgetView);
    }

    /*private void updateView(RemoteViews widgetView, ReadableMap charm, Integer layout) {
        String bio = charm.getString("bio").replace("\r","").replace("\n","").replace("\t","");
        RemoteViews charm_view = new RemoteViews(this.getReactApplicationContext().getPackageName(), layout);
        URL url = null;
        try {
            url = new URL(charm.getString("imageurl"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        charm_view.setImageViewBitmap(R.id.ch, bmp);
        charm_view.setTextViewText(R.id.heading, charm.getString("name"));
        charm_view.setTextViewText(R.id.content,bio);
        widgetView.addView(R.id.charms_layout, charm_view);
    }*/

}
