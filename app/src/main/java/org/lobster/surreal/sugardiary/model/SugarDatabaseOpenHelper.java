package org.lobster.surreal.sugardiary.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kerry on 9/4/2016.
 */
public class SugarDatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DiaryModelContract.DiaryEntry.TABLE_NAME + " (" +
                    DiaryModelContract.DiaryEntry._ID + " integer primary key autoincrement, " +
                    DiaryModelContract.DiaryEntry.COLUMN_NAME_SYNC_ID + " INTEGER, " +
                    DiaryModelContract.DiaryEntry.COLUMN_NAME_DATE + " TEXT, " +
                    DiaryModelContract.DiaryEntry.COLUMN_NAME_TIME + " TEXT, " +
                    DiaryModelContract.DiaryEntry.COLUMN_NAME_VALUE + " integer);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DiaryModelContract.DiaryEntry.TABLE_NAME;

    public SugarDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
