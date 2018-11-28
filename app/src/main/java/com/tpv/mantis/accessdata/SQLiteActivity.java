package com.tpv.mantis.accessdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SQLiteActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        initComponent();

    }

    private void initComponent() {
        Button createBtn = findViewById(R.id.createBtn);
        Button updateBtn = findViewById(R.id.updateDbBtn);
        Button insertBtn = findViewById(R.id.insertBtn);
        Button modifyBtn = findViewById(R.id.modifyBtn);
        Button queryBtn = findViewById(R.id.queryBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);

        createBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        insertBtn.setOnClickListener(this);
        modifyBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createBtn:{
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                break;
            }
            case R.id.updateDbBtn:{
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                break;
            }
            case R.id.insertBtn:{
                Log.d(StuDBHelper.TAG, "==>insert data");
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues stuInfo = new ContentValues();
                stuInfo.put("_id", 1);
                stuInfo.put("STU_NAME", "Syngalon");
                stuInfo.put("STU_AGE", 30);
                stuInfo.put("STU_GENDER", "male");
                stuInfo.put("STU_HEIGHT", 172.5);
                db.insert(StuDBHelper.TABLE_NAME, null, stuInfo);
                db.close();
                break;
            }
            case R.id.modifyBtn:{
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues newStuInfo = new ContentValues();
                newStuInfo.put("STU_HEIGHT", 175.2);
                String whereClause = "_id=?";
                String [] whereArgs = {String.valueOf(1)};
                db.update(StuDBHelper.TABLE_NAME, newStuInfo, whereClause, whereArgs);
                db.close();
                break;
            }
            case R.id.queryBtn:{
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(StuDBHelper.TABLE_NAME, new String[]{"_id", "STU_NAME", "STU_AGE", "STU_GENDER", "STU_HEIGHT"}, "_id=?", new String[]{"1"}, null, null, null);
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("STU_NAME"));
                    String age = cursor.getString(cursor.getColumnIndex("STU_AGE"));
                    String gender = cursor.getString(cursor.getColumnIndex("STU_GENDER"));
                    String height = cursor.getString(cursor.getColumnIndex("STU_HEIGHT"));
                    Log.d(StuDBHelper.TAG, "==>name: " + name + ", age: " + age + ", gender: " + gender + ", height: " + height);
                }
                cursor.close();
                db.close();
                break;
            }
            case R.id.deleteBtn:{
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String whereClause = "_id=?";
                String [] whereArgs = {String.valueOf(2)};
                db.delete(StuDBHelper.TABLE_NAME, whereClause, whereArgs);
                db.close();
                break;
            }
        }

    }
}
