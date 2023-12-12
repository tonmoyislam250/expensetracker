package com.example.expensetrackersystem;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import androidx.annotation.Nullable;

import com.example.expensetrackersystem.model.expenseModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerExpense extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/expense_tracker/expense.db";
    private static final String TABLE_NAME = "expense_data";
    private static final String COL_ID = "ID";
    private static final String COL_AMOUNT = "AMOUNT";
    private static final String COL_TYPE = "TYPE";
    private static final String COL_NOTE = "NOTE";
    private static final String COL_DATE = "DATE";

    public DatabaseHandlerExpense(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_AMOUNT + " TEXT," +
                COL_TYPE + " TEXT," +
                COL_NOTE + " TEXT," +
                COL_DATE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(String amount, String type, String note, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AMOUNT, amount);
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_NOTE, note);
        contentValues.put(COL_DATE, date);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void update(String id, String amount, String type, String note, String date) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AMOUNT, amount);
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_NOTE, note);
        contentValues.put(COL_DATE, date);
        database.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<expenseModel> getAllIncome() {
        List<expenseModel> incomeModelList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (data.getCount() == 0) {
            // Handle case where no data is retrieved
        } else {
            while (data.moveToNext()) {
                incomeModelList.add(new expenseModel(
                        data.getString(data.getColumnIndex(COL_ID)),
                        data.getString(data.getColumnIndex(COL_AMOUNT)),
                        data.getString(data.getColumnIndex(COL_TYPE)),
                        data.getString(data.getColumnIndex(COL_NOTE)),
                        data.getString(data.getColumnIndex(COL_DATE))
                ));
            }
        }
        return incomeModelList;
    }
}
