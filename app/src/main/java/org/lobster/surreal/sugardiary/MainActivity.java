package org.lobster.surreal.sugardiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_DATE = "org.lobster.surreal.sugardiary.EXTRA_MESSAGE_DATE";
    public final static String EXTRA_MESSAGE_TIME = "org.lobster.surreal.sugardiary.EXTRA_MESSAGE_TIME";
    public final static String EXTRA_MESSAGE_WHAT = "org.lobster.surreal.sugardiary.EXTRA_MESSAGE_WHAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Save Value button */
    public void saveValue(View view) {
        Intent intent = new Intent(this, SaveValueActivity.class);

        DatePicker datePicker = (DatePicker) findViewById(R.id.date_value);
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        Calendar when = Calendar.getInstance();
        when.set(year, month, day, 0, 0, 0);
        intent.putExtra(EXTRA_MESSAGE_DATE, when.getTimeInMillis());

        TimePicker timePicker = (TimePicker) findViewById(R.id.time_value);
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();
        intent.putExtra(EXTRA_MESSAGE_TIME, String.valueOf(hour) + String.valueOf(min));

        EditText editText = (EditText) findViewById(R.id.edit_value);
        int value = Integer.valueOf(editText.getText().toString());
        intent.putExtra(EXTRA_MESSAGE_WHAT, value);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_today:
            case R.id.show_week:
            case R.id.show_month:
            case R.id.show_all:
                showValues(item.getItemId());
                return true;
            case R.id.edit_value:
                editValue();
                return true;
            case R.id.help:
                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editValue() {

    }

    private void showHelp() {

    }

    private void showValues(int selectionId) {
        Intent intent = new Intent(this, ShowValuesActivity.class);
        intent.putExtra("SELECTION_ID", selectionId);
        startActivity(intent);
    }
}
