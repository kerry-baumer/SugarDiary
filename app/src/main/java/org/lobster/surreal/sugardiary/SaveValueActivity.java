        package org.lobster.surreal.sugardiary;

import static org.lobster.surreal.sugardiary.model.DiaryModelContract.DiaryEntry.COLUMN_NAME_DATE;
import static org.lobster.surreal.sugardiary.model.DiaryModelContract.DiaryEntry.COLUMN_NAME_TIME;
import static org.lobster.surreal.sugardiary.model.DiaryModelContract.DiaryEntry.COLUMN_NAME_VALUE;
import static org.lobster.surreal.sugardiary.model.DiaryModelContract.DiaryEntry.TABLE_NAME;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lobster.surreal.sugardiary.model.SugarDatabaseOpenHelper;
import org.lobster.surreal.sugardiary.provider.DiaryContentProvider;

import java.text.SimpleDateFormat;

public class SaveValueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_save_value);
        Intent intent = getIntent();
        long date = intent.getLongExtra(MainActivity.EXTRA_MESSAGE_DATE, 0l);
        String time = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_TIME);
        int value = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_WHAT, 0);

        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        saveValues(dateStr, time, value);

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(dateStr);
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_save_value);
        layout.addView(textView);
    }

    private long saveValues(final String date, final String time, final int value) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_DATE, date);
        values.put(COLUMN_NAME_TIME, time);
        values.put(COLUMN_NAME_VALUE, value);

        // Insert the new row, returning the primary key value of the new row
        Uri newRowUri = getContentResolver().insert(DiaryContentProvider.DIARY_CONTENT_URI, values);

        return 0l;  //  newRowId;
    }

}
