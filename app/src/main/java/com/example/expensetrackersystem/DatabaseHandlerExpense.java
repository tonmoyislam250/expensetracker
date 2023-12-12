package com.example.expensetrackersystem;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.expensetrackersystem.model.expenseModel;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class DatabaseHandlerExpense extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "expense.db";
    public static final String TABLE_NAME = "expense_data";
//    public static final String COL1 = "ID";
    public static final String COL2 = "AMOUNT";
    public static final String COL3 = "TYPE";
    public static final String COL4 = "NOTE";
    public static final String COL5 = "DATE";

    public DatabaseHandlerExpense(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "AMOUNT TEXT," + "TYPE TEXT," + "NOTE TEXT," + "DATE TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String a = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(a);
        onCreate(db);
    }

    public void addData(String amount, String type, String note, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, amount);
        contentValues.put(COL3, type);
        contentValues.put(COL4, note);
        contentValues.put(COL5, date);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void update(String id, String amount, String type, String note, String date) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, amount);
        contentValues.put(COL3, type);
        contentValues.put(COL4, note);
        contentValues.put(COL5, date);
        database.update(TABLE_NAME, contentValues, "id=?", new String[]{id});
    }

    public List<expenseModel> getAllIncome() {
        List<expenseModel> incomeModelList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (data.getCount() == 0) {

        } else {
            while (data.moveToNext()) {
                incomeModelList.add(new expenseModel(data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4)));
            }
        }

        return incomeModelList;
    }

}
