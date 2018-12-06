package com.tpv.mantis.accessdata;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ConProvActivity extends AppCompatActivity implements View.OnClickListener {


    private final static String TAG = "ConProvActivity";

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
        // "content://com.android.contacts/data/phones"
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, columns, null, null, null);

        if (null != cursor && cursor.moveToFirst()) {
            String name;
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

    private void insertContact1() {
        // insert raw_contacts table to get _id
        ContentResolver resolver = this.getContentResolver();
        ContentValues contact = new ContentValues();
        long contact_id = ContentUris.parseId(resolver.insert(ContactsContract.RawContacts.CONTENT_URI, contact));
        // insert data table
        contact.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
        contact.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
//        contact.put(ContactsContract.Data.DATA3, "贺");
//        contact.put(ContactsContract.Data.DATA2, "劲");
        contact.put(ContactsContract.Data.DATA1, "康师傅");  // in fact, only need to set DATA1
        resolver.insert(ContactsContract.Data.CONTENT_URI, contact);
        contact.clear();

        contact.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
        contact.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        contact.put(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        contact.put(ContactsContract.Data.DATA1, "15693883456");
        resolver.insert(ContactsContract.Data.CONTENT_URI, contact);
        contact.clear();

        contact.put(ContactsContract.Data.RAW_CONTACT_ID, contact_id);
        contact.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        contact.put(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        contact.put(ContactsContract.Data.DATA1, "mingming@163.com");
        resolver.insert(ContactsContract.Data.CONTENT_URI, contact);
    }

    private void batchInsertContacts() throws RemoteException, OperationApplicationException {
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        ContentResolver resolver = this.getContentResolver();
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        ContentProviderOperation operation1 = ContentProviderOperation
                .newInsert(uri)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build();
        operations.add(operation1);

        uri = ContactsContract.Data.CONTENT_URI;
        ContentProviderOperation operation2 = ContentProviderOperation
                .newInsert(uri)
                //withValueBackReference的第二个参数表示引用operations[0]的操作的返回id作为此值
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Data.DATA1, "何获得")
                .build();
        operations.add(operation2);

        ContentProviderOperation operation3 = ContentProviderOperation
                .newInsert(uri)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .withValue(ContactsContract.Data.DATA1, "136456775786")
                .build();
        operations.add(operation3);

        ContentProviderOperation operation4 = ContentProviderOperation
                .newInsert(uri)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                .withValue(ContactsContract.Data.DATA1, "hanghang@qq.com")
                .build();
        operations.add(operation4);

        resolver.applyBatch(ContactsContract.AUTHORITY, operations);

    }

    //读取通讯录的全部的联系人
    //需要先在raw_contact表中遍历id，并根据id到data表中获取数据
    public void readContacts1() {
        //uri = content://com.android.contacts/contacts
        ContentResolver resolver = this.getContentResolver();
        //获得_id属性
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts._ID}, null, null, null);
        while (null != cursor && cursor.moveToNext()) {
            StringBuilder buf = new StringBuilder();
            //获得id并且在data中寻找数据
            int id = cursor.getInt(0);
            buf.append("id=" + id);
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + id + "/data");
            //data1存储各个记录的总数据，mimetype存放记录的类型，如电话、email等
            Cursor cursor2 = resolver.query(uri, new String[]{ContactsContract.Data.DATA1, ContactsContract.Data.MIMETYPE}, null, null, null);
            while (null != cursor2 && cursor2.moveToNext()) {
                String data = cursor2.getString(cursor2.getColumnIndex(ContactsContract.Data.DATA1));
                String mimeType = cursor2.getString(cursor2.getColumnIndex(ContactsContract.Data.MIMETYPE));

                if (mimeType.equals(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) {
                    // "vnd.android.cursor.item/name"
                    buf.append(",name=" + data);
                } else if (mimeType.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                    // "vnd.android.cursor.item/phone_v2"
                    buf.append(",phone=" + data);
                } else if (mimeType.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                    // "vnd.android.cursor.item/email_v2"
                    buf.append(",email=" + data);
                } else if (mimeType.equals(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)) {
                    // "vnd.android.cursor.item/postal-address_v2"
                    buf.append(",address=" + data);
                } else if (mimeType.equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
                    // "vnd.android.cursor.item/organization"
                    buf.append(",organization=" + data);
                }
            }
            if (null != cursor2) {
                cursor2.close();
            }
            String str = buf.toString();
            Log.i(TAG, "==>details: " + str);
        }
        if (null != cursor) {
            cursor.close();
        }
    }


    private void readContacts2() {
        String id, name, phoneNumber, email;
        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (null != cursor && cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            // fetch phoneNumber
            Cursor phoneCursor = contentResolver
                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
            while (null != phoneCursor && phoneCursor.moveToNext()) {
                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.d(TAG, "==>Contacts: id= " + id + ", name= " + name + ", number= " + phoneNumber);

            }
            if (null != phoneCursor) {
                phoneCursor.close();
            }

            // fetch email
            Cursor emailCursor = contentResolver
                    .query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + id, null, null);
            while (null != emailCursor && emailCursor.moveToNext()) {
                email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                Log.d(TAG, "==>Contacts: id= " + id + ", name= " + name + ", email= " + email);
            }
            if (null != emailCursor) {
                emailCursor.close();
            }
        }
        if (null != cursor) {
            cursor.close();
        }
    }
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
//                readContact();
//                readContacts1();
                readContacts2();
                break;
            case R.id.updateContactBtn:
//                insertContact1();
                try {
                    batchInsertContacts();
                } catch (RemoteException|OperationApplicationException e) {
                    e.printStackTrace();
                }
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
