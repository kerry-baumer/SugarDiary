package org.lobster.surreal.sugardiary.provider;

import static org.lobster.surreal.sugardiary.model.DiaryModelContract.DiaryEntry.TABLE_NAME;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import org.lobster.surreal.sugardiary.model.DiaryModelContract;
import org.lobster.surreal.sugardiary.model.SugarDatabaseOpenHelper;

/**
 * Created by kerry on 9/5/2016.
 */
public class DiaryContentProvider extends ContentProvider {

    public static final String AUTHORITY = "org.lobster.surreal.sugardiary";
    public static final Uri DIARY_CONTENT_URI =
            Uri.parse("content://"+ AUTHORITY + "/diary");
    private SugarDatabaseOpenHelper mDbHelper;

    private static final int DIARY = 1;
    private static final int DIARY_ID = 2;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "diary", DIARY);
        uriMatcher.addURI(AUTHORITY, "diary/#", DIARY_ID);
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new SugarDatabaseOpenHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        boolean dbOk = (db != null);
        db.close();
        return dbOk;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        switch(uriMatcher.match(uri)) {
            case DIARY_ID:
                //---if getting a particular survey---
                sqlBuilder.appendWhere(
                        DiaryModelContract.DiaryEntry._ID + " = " + uri.getPathSegments().get(1));
            case DIARY:
                if (sortOrder==null || sortOrder=="")
                    sortOrder = DiaryModelContract.DiaryEntry.COLUMN_NAME_DATE;
                sqlBuilder.setTables(TABLE_NAME);
                break;
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = sqlBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        //---register to watch a content URI for changes---
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            // --- get all entries ---
            case DIARY:
                return "vnd.android.cursor.dir/vnd.diary.data.dir ";
            //---get a particular entry---
            case DIARY_ID:
                return "vnd.android.cursor.item/vnd.diary.row ";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    /**
     *
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = 0;
        switch(uriMatcher.match(uri)) {
            case DIARY:
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                rowID = db.insert(TABLE_NAME, "", values);
                break;
            default:
                throw new IllegalArgumentException("" + uri);
        }
        //---if added successfully---
        if (rowID > 0) {
            Uri _uri = null;
            switch(uriMatcher.match(uri)) {
                case DIARY:
                    _uri = ContentUris.withAppendedId(DIARY_CONTENT_URI, rowID);
                    break;
                default:
                    throw new IllegalArgumentException("" + uri);
            }
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new IllegalStateException("Failed to insert row into " + uri);
    }

    /**
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
