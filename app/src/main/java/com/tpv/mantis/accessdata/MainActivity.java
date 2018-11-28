package com.tpv.mantis.accessdata;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tpv.mantis.accessdata.utils.DBUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input1;
    private EditText input2;
    private Button workBtn;
    private Button jumpSQLiteBtn;
    private Button jumpContentProviderBtn;
    private TextView contentView;

    private final static int REQUEST_PERMISSION = 1550;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestSinglePermission();
    }


    private void requestSinglePermission() {
        String writeExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readContactPermission = Manifest.permission.READ_CALENDAR;
        String writeContactPermission = Manifest.permission.WRITE_CONTACTS;

        int hasWriteExternalStoragePermission = checkSelfPermission(writeExternalStoragePermission);
        int hasReadContactPermission = checkSelfPermission(readContactPermission);
        int hasWriteContactPermission = checkSelfPermission(writeContactPermission);

        String[] permissions = new String[]{writeExternalStoragePermission, readContactPermission, writeContactPermission};
        if (hasWriteExternalStoragePermission == PackageManager.PERMISSION_DENIED
                || hasReadContactPermission == PackageManager.PERMISSION_DENIED
                || hasWriteContactPermission == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permissions, REQUEST_PERMISSION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        input1 = findViewById(R.id.edit1);
        input2 = findViewById(R.id.edit2);
        workBtn = findViewById(R.id.workBtn);
        workBtn.setOnClickListener(this);
        jumpSQLiteBtn = findViewById(R.id.jumpBtn);
        jumpSQLiteBtn.setOnClickListener(this);
        jumpContentProviderBtn = findViewById(R.id.conProvBtn);
        jumpContentProviderBtn.setOnClickListener(this);
        contentView = findViewById(R.id.contentView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.workBtn:
                DBUtils.save(this, input1);
                DBUtils.load(this, "demoFile.txt", contentView);
                DBUtils.saveToSdcard(this, input2);
                break;
            case R.id.jumpBtn:
                Intent jumpSQLiteIntent = new Intent();
                jumpSQLiteIntent.setClass(this, SQLiteActivity.class);
                this.startActivity(jumpSQLiteIntent);
                break;
            case R.id.conProvBtn:
                Intent jumpConProvIntent = new Intent();
                jumpConProvIntent.setClass(this, ConProvActivity.class);
                this.startActivity(jumpConProvIntent);
                break;
        }
    }
}
