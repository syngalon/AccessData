package com.tpv.mantis.accessdata.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tpv.mantis.accessdata.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DBUtils {
    private static final String TAG = "DBUtils";

    public static void save(Context context, EditText editText) {
        Log.d(TAG, "save E");
        try {
            FileOutputStream fos = context.openFileOutput("demoFile.txt", Context.MODE_PRIVATE);
            fos.write(editText.getText().toString().getBytes());
            fos.close();
//            Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToSdcard(Context context, EditText editText) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            File saveFile = new File(sdCardDir, "sdcardFile.txt");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(saveFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.write(editText.getText().toString().getBytes());
                    fos.close();
                    Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void load(Context context, String fileName, TextView contentView) {
        Log.d(TAG, "load E");
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;

            while ((length=fis.read(buffer))!= -1) {
                baos.write(buffer, 0, length);
            }

            baos.close();
            fis.close();

            contentView.setText(baos.toString());
//            Toast.makeText(context, R.string.load_success, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
