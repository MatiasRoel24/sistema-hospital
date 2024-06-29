package com.example.sistema_hospitalario.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseManager extends SQLiteOpenHelper {
    private static DataBaseManager instance;
    private static final String DATABASE_NAME = "DB_Hospital";
    private static final int DATABASE_VERSION = 1;

    private DataBaseManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseManager getInstance(Context context) {
        if (instance == null) instance = new DataBaseManager(context.getApplicationContext());
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (firstName TEXT, lastName TEXT, dni TEXT, userName TEXT, email TEXT, password TEXT, medical_license INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
}
