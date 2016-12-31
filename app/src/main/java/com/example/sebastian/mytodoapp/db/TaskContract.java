package com.example.sebastian.mytodoapp.db;

/**
 * Created by sebastian on 16.10.16.
 */

import android.provider.BaseColumns;

public class TaskContract {

    public static final String DB_NAME = "database";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {

        public static final String TABLE_TASKS = "tasks";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_STAR = "star";
        public static final String COL_TASK_PRIORITY = "prioriti";
        public static final String COL_TASK_DATE = "date";
        public static final String COL_TASK_IS_STRIKETROUGH = "striketrough";

        public static final String TABLE_STATISTIC = "statistic";
        public static final String COL_STAT_TASK = "stask";
        public static final String COL_STAT_ALL_TASK = "addedt";
        public static final String COL_STAT_DELETED_TASK = "deleted";
        public static final String COL_STAT_FINISHED_TASK = "finished";
        public static final String COL_STAT_NOTE = "snote";
        public static final String COL_STAT_ADDED_NOTE = "addedn";

        public static final String TABLE_NOTES = "notes";
        public static final String COL_NOTE_TITLE = "titlenote";
        public static final String COL_NOTE_CONTENT = "content";
        public static final String COL_NOTE_IMG = "img";

    }
}