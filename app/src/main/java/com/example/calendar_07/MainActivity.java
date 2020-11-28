package com.example.calendar_07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    long start = System.currentTimeMillis();
    long end = System.currentTimeMillis() + 1000 * 60 * 60 * 48;

    // 2
    ContentResolver cr = getContentResolver();
    String[] projection = {
            CalendarContract.Events._ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
    };
    String selection = "(" +
            "(" + CalendarContract.Events.ACCOUNT_NAME + " = ?) AND " +
            "(" + CalendarContract.Events.ACCOUNT_TYPE + " = ?) AND " +
            "(" + CalendarContract.Events.DTSTART + " >= ?) AND " +
            "(" + CalendarContract.Events.DTEND + " <= ?) AND " +
            "(" + CalendarContract.Events.CALENDAR_ID + " = ?)" +
            ")";
    String[] selectionArgs = new String[]{"eri.hidaka24", "com.google", Long.toString(start), Long.toString(end), "2"};

    // 3
    private static String TAG;
    Cursor cursor = cr.query(CalendarContract.Events.CONTENT_URI, projection, selection, selectionArgs, null);
    if(cursor.moveToNext()){
        do {
            long eventID = cursor.getLong(0);
            String title = cursor.getString(1);
            long startSec = cursor.getLong(2);
            long endSec = cursor.getLong(3);

            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.JAPAN);

            Log.i(TAG, eventID + ":" + title);
            Log.i(TAG, format.format(startSec) + " - " + format.format(endSec));
            Log.i(TAG, "-----------------------------------");
            cursor.close();
        }while(cursor.moveToNext());
    }
}