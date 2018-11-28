package com.tpv.mantis.accessdata;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

public class StuContentProvider extends ContentProvider {
    private static final String TAG = "StuContentProvider";

    private StuDBHelper stuDBHelper;
    private SQLiteDatabase studentDB;
    public StuContentProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        studentDB = stuDBHelper.getWritableDatabase();
        long rowId = studentDB.insert(StuDBHelper.TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(uri.buildUpon(), rowId).build();
//            if (getContext() != null) {
//                getContext().getContentResolver().notifyChange(rowUri, null);
//            }
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        stuDBHelper = new StuDBHelper(getContext());
        return null != stuDBHelper;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        studentDB = stuDBHelper.getReadableDatabase();
        qb.setTables(StuDBHelper.TABLE_NAME);
        Cursor cursor = qb.query(studentDB, projection, selection, selectionArgs, null,null, sortOrder);
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

}
