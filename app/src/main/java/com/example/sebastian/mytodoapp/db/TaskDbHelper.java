package com.example.sebastian.mytodoapp.db;

/**
 * Created by sebastian on 16.10.16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTasks = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_TASKS + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_STAR + " INTEGER, " +
                TaskContract.TaskEntry.COL_TASK_IS_STRIKETROUGH + " INTEGER, " +
                TaskContract.TaskEntry.COL_TASK_PRIORITY + " INTEGER, " +
                TaskContract.TaskEntry.COL_TASK_DATE + " TEXT NOT NULL " +");";

        String createTableStatistic = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_STATISTIC + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_STAT_ALL_TASK + " INTEGER, " +
                TaskContract.TaskEntry.COL_STAT_DELETED_TASK + " INTEGER, " +
                TaskContract.TaskEntry.COL_STAT_FINISHED_TASK + " INTEGER, " +
                TaskContract.TaskEntry.COL_STAT_ADDED_NOTE + " INTEGER, " +
                TaskContract.TaskEntry.COL_STAT_DELETED_NOTE + " INTEGER " +");";

        String createTableNotes = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NOTES + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_NOTE_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_NOTE_CONTENT + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_NOTE_IMG + " BLOB " +");";

        db.execSQL(createTableTasks);
        db.execSQL(createTableStatistic);
        db.execSQL(createTableNotes);

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry._ID , 1);
        values.put(TaskContract.TaskEntry.COL_STAT_ALL_TASK, 0);
        values.put(TaskContract.TaskEntry.COL_STAT_DELETED_TASK, 0);
        values.put(TaskContract.TaskEntry.COL_STAT_FINISHED_TASK, 0);
        values.put(TaskContract.TaskEntry.COL_STAT_DELETED_NOTE, 0);
        values.put(TaskContract.TaskEntry.COL_STAT_ADDED_NOTE, 0);
        db.insert(TaskContract.TaskEntry.TABLE_STATISTIC, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_STATISTIC);
        onCreate(db);
    }


    public void addTaskWithStar(String task, int star, int prioriti, String date, int strikeTrough ) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TaskContract.TaskEntry.COL_TASK_STAR, star);
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
        values.put(TaskContract.TaskEntry.COL_TASK_PRIORITY, prioriti);
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, date);
        values.put(TaskContract.TaskEntry.COL_TASK_IS_STRIKETROUGH, strikeTrough);

//        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
//                null,
//                values,
//                SQLiteDatabase.CONFLICT_REPLACE);
        db.insert(TaskContract.TaskEntry.TABLE_TASKS, null, values);

        String incrementSQlQuery = "UPDATE " + TaskContract.TaskEntry.TABLE_STATISTIC
                + " SET " + TaskContract.TaskEntry.COL_STAT_ALL_TASK + "="
                + TaskContract.TaskEntry.COL_STAT_ALL_TASK + " + 1 WHERE "
                + TaskContract.TaskEntry._ID +" = 1";

        db.execSQL(incrementSQlQuery);




        db.close();
    }

    public void addNote(String titlenote, String content){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TaskContract.TaskEntry.COL_NOTE_TITLE, titlenote);
        values.put(TaskContract.TaskEntry.COL_NOTE_CONTENT, content);

        db.insert(TaskContract.TaskEntry.TABLE_NOTES, null, values);

        String incremenSQlQuery = "UPDATE " + TaskContract.TaskEntry.TABLE_STATISTIC
                + " SET " + TaskContract.TaskEntry.COL_STAT_ADDED_NOTE + "="
                + TaskContract.TaskEntry.COL_STAT_ADDED_NOTE + " + 1 WHERE "
                + TaskContract.TaskEntry._ID +" = 1";

        db.execSQL(incremenSQlQuery);
        db.close();

    }

    public void updateTask(String task, int isStrikeTrough) {

        String strikeSQlQuery = null;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TaskContract.TaskEntry.COL_TASK_IS_STRIKETROUGH, isStrikeTrough);
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
        db.update(TaskContract.TaskEntry.TABLE_TASKS, values,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});

        if(isStrikeTrough == 1){

            strikeSQlQuery = "UPDATE " + TaskContract.TaskEntry.TABLE_STATISTIC
                    + " SET " + TaskContract.TaskEntry.COL_STAT_FINISHED_TASK + "="
                    + TaskContract.TaskEntry.COL_STAT_FINISHED_TASK + " + 1 WHERE "
                    + TaskContract.TaskEntry._ID +" = 1";
        }else{

            strikeSQlQuery = "UPDATE " + TaskContract.TaskEntry.TABLE_STATISTIC
                    + " SET " + TaskContract.TaskEntry.COL_STAT_FINISHED_TASK + "="
                    + TaskContract.TaskEntry.COL_STAT_FINISHED_TASK + " - 1 WHERE "
                    + TaskContract.TaskEntry._ID +" = 1";
        }

        db.execSQL(strikeSQlQuery);

        db.close();
    }


    public void deleteNote(String noteName) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE_NOTES,
                TaskContract.TaskEntry.COL_NOTE_TITLE + " = ?",
                new String[]{noteName});

//        String incrementNoteSQlQuery = "UPDATE " + TaskContract.TaskEntry.TABLE_STATISTIC
//                + " SET " + TaskContract.TaskEntry.COL_STAT_DELETED_NOTE + "="
//                + TaskContract.TaskEntry.COL_STAT_DELETED_NOTE + " + 1 WHERE "
//                + TaskContract.TaskEntry._ID +" = 1";
//
//        db.execSQL(incrementNoteSQlQuery);
        db.close();
        }





    public void deleteTask(String task, int isStrikeTrough) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE_TASKS,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});



        String incrementSQlQuery = "UPDATE " + TaskContract.TaskEntry.TABLE_STATISTIC
                + " SET " + TaskContract.TaskEntry.COL_STAT_DELETED_TASK + "="
                + TaskContract.TaskEntry.COL_STAT_DELETED_TASK + " + 1 WHERE "
                + TaskContract.TaskEntry._ID +" = 1";

        if(isStrikeTrough == 1){

            String strikeSQlQuery = "UPDATE " + TaskContract.TaskEntry.TABLE_STATISTIC
                    + " SET " + TaskContract.TaskEntry.COL_STAT_FINISHED_TASK + "="
                    + TaskContract.TaskEntry.COL_STAT_FINISHED_TASK + " - 1 WHERE "
                    + TaskContract.TaskEntry._ID +" = 1";

            db.execSQL(strikeSQlQuery);

        }

        db.execSQL(incrementSQlQuery);
        db.close();
    }

//    public Cursor getStrikeTrouh(){
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery
//                ("SELECT " + TaskContract.TaskEntry._ID +  " as _id, "
//                        + TaskContract.TaskEntry.COL_TASK_IS_STRIKETROUGH
//                        + " from "
//                        + TaskContract.TaskEntry.TABLE_TASKS, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }

    public Cursor getNoteValues() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT " + TaskContract.TaskEntry._ID +  " as _id, "
                        + TaskContract.TaskEntry.COL_NOTE_TITLE
                        + " , "+ TaskContract.TaskEntry.COL_NOTE_CONTENT
                        + " from "
                        + TaskContract.TaskEntry.TABLE_NOTES, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getStatisiticValues() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT " + TaskContract.TaskEntry._ID +  " as _id, "
                        + TaskContract.TaskEntry.COL_STAT_DELETED_TASK
                        + " , "+ TaskContract.TaskEntry.COL_STAT_ALL_TASK
                        + " , "+ TaskContract.TaskEntry.COL_STAT_FINISHED_TASK
                        + " , "+ TaskContract.TaskEntry.COL_STAT_ADDED_NOTE
                        + " from "
                        + TaskContract.TaskEntry.TABLE_STATISTIC, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getDataForListView() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT " + TaskContract.TaskEntry._ID +  " as _id, " + TaskContract.TaskEntry.COL_TASK_STAR
                        + " , "+ TaskContract.TaskEntry.COL_TASK_TITLE
                        + " , "+ TaskContract.TaskEntry.COL_TASK_PRIORITY
                        + " , "+ TaskContract.TaskEntry.COL_TASK_DATE
                        + " , "+ TaskContract.TaskEntry.COL_TASK_IS_STRIKETROUGH
                        + " from "
                        + TaskContract.TaskEntry.TABLE_TASKS, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }



}