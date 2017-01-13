package com.example.sebastian.mytodoapp.db;

/**
 * Created by sebastian on 16.10.16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, Contract.DB_NAME, null, Contract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTasks = "CREATE TABLE " + Contract.TaskEntry.TABLE_TASKS + " ( " +
                Contract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                Contract.TaskEntry.COL_TASK_STAR + " INTEGER, " +
                Contract.TaskEntry.COL_TASK_IS_STRIKETROUGH + " INTEGER, " +
                Contract.TaskEntry.COL_TASK_PRIORITY + " INTEGER, " +
                Contract.TaskEntry.COL_TASK_DATE + " TEXT NOT NULL " +");";

        String createTableStatistic = "CREATE TABLE " + Contract.TaskEntry.TABLE_STATISTIC + " ( " +
                Contract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TaskEntry.COL_STAT_ALL_TASK + " INTEGER, " +
                Contract.TaskEntry.COL_STAT_DELETED_TASK + " INTEGER, " +
                Contract.TaskEntry.COL_STAT_FINISHED_TASK + " INTEGER, " +
                Contract.TaskEntry.COL_STAT_ADDED_NOTE + " INTEGER, " +
                Contract.TaskEntry.COL_STAT_DELETED_NOTE + " INTEGER " +");";

        String createTableNotes = "CREATE TABLE " + Contract.TaskEntry.TABLE_NOTES + " ( " +
                Contract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TaskEntry.COL_NOTE_TITLE + " TEXT NOT NULL, " +
                Contract.TaskEntry.COL_NOTE_CONTENT + " TEXT NOT NULL, " +
                Contract.TaskEntry.COL_NOTE_IMG + " BLOB " +");";

        db.execSQL(createTableTasks);
        db.execSQL(createTableStatistic);
        db.execSQL(createTableNotes);

        ContentValues values = new ContentValues();
        values.put(Contract.TaskEntry._ID , 1);
        values.put(Contract.TaskEntry.COL_STAT_ALL_TASK, 0);
        values.put(Contract.TaskEntry.COL_STAT_DELETED_TASK, 0);
        values.put(Contract.TaskEntry.COL_STAT_FINISHED_TASK, 0);
        values.put(Contract.TaskEntry.COL_STAT_DELETED_NOTE, 0);
        values.put(Contract.TaskEntry.COL_STAT_ADDED_NOTE, 0);
        db.insert(Contract.TaskEntry.TABLE_STATISTIC, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Contract.TaskEntry.TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.TaskEntry.TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.TaskEntry.TABLE_STATISTIC);
        onCreate(db);
    }

    public void addTaskWithStar(String task, int star, int prioriti, String date, int strikeTrough ) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.TaskEntry.COL_TASK_STAR, star);
        values.put(Contract.TaskEntry.COL_TASK_TITLE, task);
        values.put(Contract.TaskEntry.COL_TASK_PRIORITY, prioriti);
        values.put(Contract.TaskEntry.COL_TASK_DATE, date);
        values.put(Contract.TaskEntry.COL_TASK_IS_STRIKETROUGH, strikeTrough);

//        db.insertWithOnConflict(Contract.TaskEntry.TABLE,
//                null,
//                values,
//                SQLiteDatabase.CONFLICT_REPLACE);
        db.insert(Contract.TaskEntry.TABLE_TASKS, null, values);

        String incrementSQlQuery = "UPDATE " + Contract.TaskEntry.TABLE_STATISTIC
                + " SET " + Contract.TaskEntry.COL_STAT_ALL_TASK + "="
                + Contract.TaskEntry.COL_STAT_ALL_TASK + " + 1 WHERE "
                + Contract.TaskEntry._ID +" = 1";

        db.execSQL(incrementSQlQuery);




        db.close();
    }

    public void addNote(String titlenote, String content){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.TaskEntry.COL_NOTE_TITLE, titlenote);
        values.put(Contract.TaskEntry.COL_NOTE_CONTENT, content);

        db.insert(Contract.TaskEntry.TABLE_NOTES, null, values);

        String incremenSQlQuery = "UPDATE " + Contract.TaskEntry.TABLE_STATISTIC
                + " SET " + Contract.TaskEntry.COL_STAT_ADDED_NOTE + "="
                + Contract.TaskEntry.COL_STAT_ADDED_NOTE + " + 1 WHERE "
                + Contract.TaskEntry._ID +" = 1";

        db.execSQL(incremenSQlQuery);
        db.close();

    }

    public void updateTask(String task, int isStrikeTrough) {

        String strikeSQlQuery = null;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.TaskEntry.COL_TASK_IS_STRIKETROUGH, isStrikeTrough);
        values.put(Contract.TaskEntry.COL_TASK_TITLE, task);
        db.update(Contract.TaskEntry.TABLE_TASKS, values,
                Contract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});

        if(isStrikeTrough == 1){

            strikeSQlQuery = "UPDATE " + Contract.TaskEntry.TABLE_STATISTIC
                    + " SET " + Contract.TaskEntry.COL_STAT_FINISHED_TASK + "="
                    + Contract.TaskEntry.COL_STAT_FINISHED_TASK + " + 1 WHERE "
                    + Contract.TaskEntry._ID +" = 1";
        }else{

            strikeSQlQuery = "UPDATE " + Contract.TaskEntry.TABLE_STATISTIC
                    + " SET " + Contract.TaskEntry.COL_STAT_FINISHED_TASK + "="
                    + Contract.TaskEntry.COL_STAT_FINISHED_TASK + " - 1 WHERE "
                    + Contract.TaskEntry._ID +" = 1";
        }

        db.execSQL(strikeSQlQuery);

        db.close();
    }

    public void deleteNote(String noteName) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete(Contract.TaskEntry.TABLE_NOTES,
                Contract.TaskEntry.COL_NOTE_TITLE + " = ?",
                new String[]{noteName});

//        String incrementNoteSQlQuery = "UPDATE " + Contract.TaskEntry.TABLE_STATISTIC
//                + " SET " + Contract.TaskEntry.COL_STAT_DELETED_NOTE + "="
//                + Contract.TaskEntry.COL_STAT_DELETED_NOTE + " + 1 WHERE "
//                + Contract.TaskEntry._ID +" = 1";
//
//        db.execSQL(incrementNoteSQlQuery);
        db.close();
        }

    public void deleteTask(String task, int isStrikeTrough) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete(Contract.TaskEntry.TABLE_TASKS,
                Contract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});



        String incrementSQlQuery = "UPDATE " + Contract.TaskEntry.TABLE_STATISTIC
                + " SET " + Contract.TaskEntry.COL_STAT_DELETED_TASK + "="
                + Contract.TaskEntry.COL_STAT_DELETED_TASK + " + 1 WHERE "
                + Contract.TaskEntry._ID +" = 1";

        if(isStrikeTrough == 1){

            String strikeSQlQuery = "UPDATE " + Contract.TaskEntry.TABLE_STATISTIC
                    + " SET " + Contract.TaskEntry.COL_STAT_FINISHED_TASK + "="
                    + Contract.TaskEntry.COL_STAT_FINISHED_TASK + " - 1 WHERE "
                    + Contract.TaskEntry._ID +" = 1";

            db.execSQL(strikeSQlQuery);

        }

        db.execSQL(incrementSQlQuery);
        db.close();
    }

    public Cursor getNoteValues() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT " + Contract.TaskEntry._ID +  " as _id, "
                        + Contract.TaskEntry.COL_NOTE_TITLE
                        + " , "+ Contract.TaskEntry.COL_NOTE_CONTENT
                        + " from "
                        + Contract.TaskEntry.TABLE_NOTES, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getStatisiticValues() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT " + Contract.TaskEntry._ID +  " as _id, "
                        + Contract.TaskEntry.COL_STAT_DELETED_TASK
                        + " , "+ Contract.TaskEntry.COL_STAT_ALL_TASK
                        + " , "+ Contract.TaskEntry.COL_STAT_FINISHED_TASK
                        + " , "+ Contract.TaskEntry.COL_STAT_ADDED_NOTE
                        + " from "
                        + Contract.TaskEntry.TABLE_STATISTIC, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getDataForListView() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT " + Contract.TaskEntry._ID +  " as _id, " + Contract.TaskEntry.COL_TASK_STAR
                        + " , "+ Contract.TaskEntry.COL_TASK_TITLE
                        + " , "+ Contract.TaskEntry.COL_TASK_PRIORITY
                        + " , "+ Contract.TaskEntry.COL_TASK_DATE
                        + " , "+ Contract.TaskEntry.COL_TASK_IS_STRIKETROUGH
                        + " from "
                        + Contract.TaskEntry.TABLE_TASKS, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}