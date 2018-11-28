package com.tpv.mantis.accessdata;

import android.net.Uri;
import android.provider.BaseColumns;

public class Students {
    public static final String AUTHORITY = "com.tpv.StuContentProvider";

    public static final class Student implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.tpv.StuContentProvider");
        public static final String STU_NAME = "STU_NAME";
        public static final String STU_AGE = "STU_AGE";
        public static final String STU_GENDER = "STU_GENDER";
        public static final String STU_HEIGHT = "STU_HEIGHT";
    }
}
