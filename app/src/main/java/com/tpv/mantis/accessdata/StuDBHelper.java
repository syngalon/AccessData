package com.tpv.mantis.accessdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class StuDBHelper extends SQLiteOpenHelper {

    public static final String TAG = "TestSQLite";
    public static final String DATABASE_NAME= "student.db";
    public static final int VERSION = 1;
    public static final int SECOND_VERSION = 2;
    public static final String TABLE_NAME = "stu_table";

    // constructor is a must
    public StuDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSql = "create table " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, STU_NAME varchar(20), STU_AGE int, STU_GENDER varchar(10), STU_HEIGHT real)";
        Log.d(TAG, "==>create table");
        sqLiteDatabase.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "==>update database");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
