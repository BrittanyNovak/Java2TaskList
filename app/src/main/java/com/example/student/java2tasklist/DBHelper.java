package com.example.student.java2tasklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by student on 6/29/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tasklists.db";
    public static final String TASK_LIST_NAME = "TASKS";
    public static final String TASKS_COLUMN_TASK = "TASK";
    public static final String TASKS_COLUMN_IS_COMPLETED = "IS_COMPLETED";
    private static String DB_PATH = "/data/data/com.example.student.java2tasklist/databases/";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE TASKS (" +
               // "ID integer Primary Key AUTOINCREMENT," +
               // "TASK text," +
               // "IS_COMPLETED," +
               // "ENTERED DATETIME DEFAULT CURRENT_TIMESTAMP)" );

        try {

            InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
            String outFileName = DB_PATH + DATABASE_NAME;
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e){
            // Failed to write to database
            Toast.makeText(myContext, "Unable to write db", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDatabase() throws SQLException {

        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close(){
        if(myDataBase != null)
            myDataBase.close();
        super.close();
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
            res.moveToNext();
        }
        return array_list;
    }
}
