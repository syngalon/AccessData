package com.tpv.mantis.accessdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ConProvActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_prov);

        initComponent();
    }

    private void initComponent() {
        Button readContactBtn = findViewById(R.id.readContactBtn);
        Button updateContactBtn = findViewById(R.id.updateContactBtn);
        Button insertStuBtn = findViewById(R.id.insertStuBtn);
        Button readStuBtn = findViewById(R.id.readStuInfoBtn);

        readContactBtn.setOnClickListener(this);
        updateContactBtn.setOnClickListener(this);
        readStuBtn.setOnClickListener(this);
        insertStuBtn.setOnClickListener(this);

    }

    private void readContact() {
        String[] columns = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, columns, null, null, null);

        if (null != cursor && cursor.moveToFirst()) {
            String name ;
            String number;
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);//PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            do {
                name = cursor.getString(nameFieldColumnIndex);
                number = cursor.getString(numberFieldColumnIndex);
                Toast.makeText(this, name + ", " + number, Toast.LENGTH_SHORT).show();

            } while (cursor.moveToNext());
        }
        if (null != cursor) {
            cursor.close();
        }
    }

//    private void updateContact(String name) {
//        Uri uri = null;
//        ContentValues values = new ContentValues();
//        values.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, name);
//        String whereClause = "ContactsContract.CommonDataKinds.Phone.NUMBER = ?";
//        String []selectionArgs = new String[]{"153358465665"};
//        getContentResolver().update(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, values, whereClause, selectionArgs);
//    }

    private void insertStudentInfo(String stuName, int age, String gender, float height) {
        ContentValues cv = new ContentValues();
        cv.put(Students.Student.STU_NAME, stuName);
        cv.put(Students.Student.STU_AGE, age);
        cv.put(Students.Student.STU_GENDER, gender);
        cv.put(Students.Student.STU_HEIGHT, height);
        getContentResolver().insert(Students.Student.CONTENT_URI, cv);
    }

    private void readStudentInfo() {
        String[] columns = new String[]{Students.Student._ID, Students.Student.STU_NAME, Students.Student.STU_AGE};
        Cursor cursor = getContentResolver().query(Students.Student.CONTENT_URI, columns, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            String id;
            String name;
            String age;
            int idColIndex = cursor.getColumnIndex(Students.Student._ID);
            int nameColIndex = cursor.getColumnIndex(Students.Student.STU_NAME);
            int ageColIndex = cursor.getColumnIndex(Students.Student.STU_AGE);
            do {
                id = cursor.getString(idColIndex);
                name = cursor.getString(nameColIndex);
                age = cursor.getString(ageColIndex);
                Toast.makeText(this, "Syngalon==> id: " + id + ", name : " + name + ", age: " + age, Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }
        if (null != cursor) {
            cursor.close();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.readContactBtn:
                readContact();
                break;
            case R.id.updateContactBtn:
//                updateContact("Tom");
                break;
            case R.id.insertStuBtn:
                insertStudentInfo("Goory", 22, "male", 171.5f);
                break;
            case R.id.readStuInfoBtn:
                readStudentInfo();
                break;
        }

    }
}
