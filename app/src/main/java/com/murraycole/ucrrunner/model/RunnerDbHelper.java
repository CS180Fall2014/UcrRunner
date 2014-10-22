package com.murraycole.ucrrunner.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 10/21/14.
 */

/* This class is in charge of creating the db's and updating */
public class RunnerDbHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1; //must update this if schema is changed
    public static final String DATABASE_NAME = "runner.db";

    public RunnerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
