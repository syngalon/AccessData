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
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, StuDBHelper.SECOND_VERSION);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                break;
            }
            case R.id.updateDbBtn:{
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, StuDBHelper.SECOND_VERSION);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                break;
            }
            case R.id.insertBtn:{
                Log.d(StuDBHelper.TAG, "==>insert data");
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, StuDBHelper.SECOND_VERSION);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues stuInfo = new ContentValues();
                stuInfo.put("id", 1);
                stuInfo.put("sname", "Syngalon");
                stuInfo.put("sage", 30);
                stuInfo.put("sgender", "male");
                stuInfo.put("sheight", 172.5);
                db.insert("stu_table", null, stuInfo);
                db.close();
                break;
            }
            case R.id.modifyBtn:{
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, StuDBHelper.SECOND_VERSION);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues newStuInfo = new ContentValues();
                newStuInfo.put("sheight", 175.2);
                String whereClause = "id=?";
                String [] whereArgs = {String.valueOf(1)};
                db.update("stu_table", newStuInfo, whereClause, whereArgs);
                db.close();
                break;
            }
            case R.id.queryBtn:{
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, StuDBHelper.SECOND_VERSION);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query("stu_table", new String[]{"id", "sname", "sage", "sgender", "sheight"}, "id=?", new String[]{"1"}, null, null, null);
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("sname"));
                    String age = cursor.getString(cursor.getColumnIndex("sage"));
                    String gender = cursor.getString(cursor.getColumnIndex("sgender"));
                    String height = cursor.getString(cursor.getColumnIndex("sheight"));
                    Log.d(StuDBHelper.TAG, "==>name: " + name + ", age: " + age + ", gender: " + gender + ", height: " + height);
                }
                db.close();
                break;
            }
            case R.id.deleteBtn:{
                StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, StuDBHelper.SECOND_VERSION);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String whereClause = "id=?";
                String [] whereArgs = {String.valueOf(2)};
                db.delete("stu_table", whereClause, whereArgs);
                db.close();
                break;
            }
        }

    }
}
