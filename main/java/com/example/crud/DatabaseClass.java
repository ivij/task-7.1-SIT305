package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseClass extends SQLiteOpenHelper {

    Context context;
    public static final String DatabaseName = "MyNotes";
    public static final int DatabaseVersion = 1;

    public static final String TableName = "mynotes";
    public static final String ColumnId = "id";
    public static final String ColumnTitle = "title";
    public static final String ColumnDescription = "description";




    public DatabaseClass(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TableName +
                " (" + ColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ColumnTitle + " TEXT, " +
                ColumnDescription + " TEXT);";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    void addNotes(String title,String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ColumnTitle, title);
        cv.put(ColumnDescription, description);

        long resultValue = db.insert(TableName,null,cv);
        if (resultValue == -1)
        {
            Toast.makeText(context, "Data not added", Toast.LENGTH_SHORT).show();

        }

        else
        {
            Toast.makeText(context, "Data added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TableName;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database!= null)
        {
            cursor = database.rawQuery(query,null);
        }
        return cursor;
    }

    void updateNotes(String title, String description, String id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(ColumnTitle,title);
        cv.put(ColumnDescription,description);

        long result = database.update(TableName,cv,"id=?",new String[]{id});
        if(result == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteNoteByRow(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result1 = db.delete(TableName,"id=?", new String[]{id});

        if(result1 == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
