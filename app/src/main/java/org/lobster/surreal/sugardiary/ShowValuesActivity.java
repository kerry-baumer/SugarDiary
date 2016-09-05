package org.lobster.surreal.sugardiary;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.lobster.surreal.sugardiary.provider.DiaryContentProvider;
import org.lobster.surreal.sugardiary.model.DiaryModelContract.DiaryEntry;
public class ShowValuesActivity extends AppCompatActivity {

    private final String TAG = "ShowValuesActivity";

    // A "projection" defines the columns that will be returned for each row
    private static final String[] mProjection = {
            DiaryEntry._ID,                 // Contract class constant for the _ID column name
            DiaryEntry.COLUMN_NAME_DATE,    // Contract class constant for the date column name
            DiaryEntry.COLUMN_NAME_TIME,    // Contract class constant for the time column name
            DiaryEntry.COLUMN_NAME_VALUE    // Contract class constant for the value column name
    };

    // Defines a list of columns to retrieve from the Cursor and load into an output row
    String[] mWordListColumns = {
            DiaryEntry.COLUMN_NAME_DATE,    // Contract class constant for the date column name
            DiaryEntry.COLUMN_NAME_TIME,    // Contract class constant for the time column name
            DiaryEntry.COLUMN_NAME_VALUE    // Contract class constant for the value column name
    };
    // Defines a list of View IDs that will receive the Cursor columns for each row
    private static final int[] mWordListItems = { R.id.row_date, R.id.row_time, R.id.row_value};

    // Defines a string to contain the selection clause
    String mSelectionClause = null;

    // Initializes an array to contain selection arguments
    String[] mSelectionArgs = {""};

    private ListView mListView;
    private SimpleCursorAdapter mCursorAdapter;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_values);
        int selectionId = getIntent().getIntExtra("SELECTION_ID", R.id.show_all);

        String mSortOrder = DiaryEntry.COLUMN_NAME_DATE + " ASC, "+
                DiaryEntry.COLUMN_NAME_TIME + " ASC";
        ;
        mCursor = getContentResolver().query(
                DiaryContentProvider.DIARY_CONTENT_URI,   // The content URI of the words table
                null,   // mProjection,                        // The columns to return for each row
                null,   // mSelectionClause,                    // Selection criteria
                null,   // mSelectionArgs,                     // Selection criteria
                mSortOrder);

        EditText resultMessage = (EditText) findViewById(R.id.result_message);
        int count = mCursor.getCount();
        if(count > 0) {
            resultMessage.setText(String.valueOf(count) + " results found for query");
        } else {
            resultMessage.setText("No results found for query");
        }

        Log.d(TAG, "Got a cursor");
        mListView = (ListView) findViewById(R.id.listView);

// Creates a new SimpleCursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(
                this,
//              getApplicationContext(),               // The application's Context object
                R.layout.sugar_list_row,               // A layout in XML for one row in the ListView
                mCursor,                               // The result from the query
                mWordListColumns,                      // A string array of column names in the cursor
                mWordListItems,                        // An integer array of view IDs in the row layout
                0);                                    // Flags (usually none are needed)

        // Sets the adapter for the ListView
        mListView.setAdapter(mCursorAdapter);

    }
}
