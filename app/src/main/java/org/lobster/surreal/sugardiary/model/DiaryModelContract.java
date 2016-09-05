package org.lobster.surreal.sugardiary.model;

import android.provider.BaseColumns;

/**
 * Created by kerry on 9/4/2016.
 */
public final class DiaryModelContract {

    private DiaryModelContract() {}

    public static class DiaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "diary";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_SYNC_ID = "_SYNCH_ID";
    }
}
