package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shoppingList1";

    public static final String TABLE_LISTS = "Lists";
    public static final String TABLE_TYPE = "Type";
    public static final String TABLE_PRODUCT = "Product";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_DESCRIPTION = "description";

    public static final String KEY_LABEL = "label";
    public static final String KEY_RULE = "rule";

    public static final String KEY_COUNT = "count";
    public static final String KEY_CHECKED = "checked";
    public static final String KEY_LIST_ID = "list_id";
    public static final String KEY_COUNT_TYPE = "count_type";

    private static final String CREATE_TABLE_LISTS = "create table "
            + TABLE_LISTS + "(" + KEY_ID + " integer primary key, " + KEY_NAME
            + " text not null, " + KEY_DATE + " integer not null, " + KEY_DESCRIPTION + " text" + ")";

    private static final String CREATE_TABLE_TYPE = "create table "
            + TABLE_TYPE + "(" + KEY_ID + " integer primary key, " + KEY_LABEL
            + " text not null, " + KEY_RULE + " text not null" + ")";

    private static final String CREATE_TABLE_PRODUCT = "create table "
            + TABLE_PRODUCT + "(" + KEY_ID + " integer primary key, " + KEY_NAME
            + " text not null, " + KEY_COUNT + " real not null, " + KEY_CHECKED
            + " integer not null, " + KEY_LIST_ID + " integer not null, "
            + KEY_COUNT_TYPE + " integer not null " + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        DB_PATH =context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LISTS);
        db.execSQL(CREATE_TABLE_TYPE);
        db.execSQL(CREATE_TABLE_PRODUCT);

        db.execSQL("INSERT INTO " + TABLE_LISTS + " (" + KEY_NAME
                + ", " + KEY_DATE + ", " + KEY_DESCRIPTION
                + ") VALUES ('List 1', 1449462120,'Lorem ipsum dolor sit amet');");

        db.execSQL("INSERT INTO " + TABLE_LISTS + " (" + KEY_NAME
                + ", " + KEY_DATE
                + ") VALUES ('List 2', 1449472233);");

        db.execSQL("INSERT INTO " + TABLE_LISTS + " (" + KEY_NAME
                + ", " + KEY_DATE
                + ") VALUES ('List 3', 1449483585);");

        db.execSQL("INSERT INTO " + TABLE_TYPE + " (" + KEY_LABEL
                + ", " + KEY_RULE
                + ") VALUES ('шт', 'int');");

        db.execSQL("INSERT INTO " + TABLE_TYPE + " (" + KEY_LABEL
                + ", " + KEY_RULE
                + ") VALUES ('кг', 'float');");

        db.execSQL("INSERT INTO " + TABLE_TYPE + " (" + KEY_LABEL
                + ", " + KEY_RULE
                + ") VALUES ('л', 'float');");

        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (" + KEY_NAME
                + ", " + KEY_COUNT + ", " + KEY_LIST_ID + ", " + KEY_CHECKED + ", " +
                KEY_COUNT_TYPE + ") VALUES ('Product 1', 0.5,1,1,2);");

        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (" + KEY_NAME
                + ", " + KEY_COUNT + ", " + KEY_LIST_ID + ", " + KEY_CHECKED + ", " +
                KEY_COUNT_TYPE + ") VALUES ('Product 2', 1,1,0,1);");

        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (" + KEY_NAME
                + ", " + KEY_COUNT + ", " + KEY_LIST_ID + ", " + KEY_CHECKED + ", " +
                KEY_COUNT_TYPE + ") VALUES ('Product 3', 2,2,0,1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_LISTS);
        db.execSQL("drop table if exists " + TABLE_TYPE);
        db.execSQL("drop table if exists " + TABLE_PRODUCT);
        onCreate(db);
    }


}
