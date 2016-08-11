package com.example.student.java2tasklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by student on 6/29/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TASK_LIST_NAME = "TASKS";
    public static final String TASKS_COLUMN_TASK = "TASK";
    public static final String TASKS_COLUMN_IS_COMPLETED = "IS_COMPLETED";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TASKS (" +
                "ID integer Primary Key AUTOINCREMENT," +
                "TASK text," +
                "IS_COMPLETED," +
                "ENTERED DATETIME DEFAULT CURRENT_TIMESTAMP)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TASKS");
        onCreate(db);
    }

    public boolean insertTask(String task, Boolean is_completed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TASK", task);
        contentValues.put("IS_COMPLETED",is_completed);
        db.insert("TASKS", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM TASKS WHERE ID="+id+"", null );
        return res;
    }

    public boolean updateTask (Integer id, String task, Boolean is_completed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TASK", task);
        contentValues.put("IS_COMPLETED", is_completed);
        db.update("TASKS", contentValues, "ID = ?", new String[] {Integer.toString(id)});
        return true;
    }

    public Integer deleteTask (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("TASKS", "ID = ?", new String[] {Integer.toString(id)});
    }

    public ArrayList<String> getAllTasks(){
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * From Tasks", null );
        res.moveToFirst();

        while(res.isAfterLast()== false){
            array_list.add(res.getString(res.getColumnIndex(TASKS_COLUMN_TASK)));
        }
        return array_list;
    }
}
