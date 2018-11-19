package com.tpv.mantis.accessdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class StuDBHelper extends SQLiteOpenHelper {

    public static final String TAG = "TestSQLite";
    public static final int VERSION = 1;
    public static final int SECOND_VERSION = 2;

    // constructor is a must
    public StuDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSql = "create table stu_table(id int, sname varchar(20), sage int, sgender varchar(10), sheight real)";
        Log.d(TAG, "==>create table");
        sqLiteDatabase.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "==>update database");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
