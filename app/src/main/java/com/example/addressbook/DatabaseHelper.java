package com.example.addressbook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME = "AddressBookDatabase.db";
    public static final String ADDRESSBOOK_TABLE_NAME = "AddressBookTable";
    public static final String ADDRESSBOOK_COLUMN_ID = "id";
    public static final String ADDRESSBOOK_COLUMN_NAME = "name";
    public static final String ADDRESSBOOK_COLUMN_ADDRESS = "address";
    public static final String ADDRESSBOOK_COLUMN_EMAIL = "email";
    public static final String ADDRESSBOOK_COLUMN_CITY = "city";
    public static final String ADDRESSBOOK_COLUMN_PHONE = "phone";
    private HashMap hp;



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE "+ ADDRESSBOOK_TABLE_NAME +
                        "(id INTEGER PRIMARY KEY, name TEXT, phone TEXT, email TEXT, address TEXT, city TEXT )"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ADDRESSBOOK_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertAddress(String name, String phone, String email, String address, String city){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("address", address);
        contentValues.put("city", city);
        sqLiteDatabase.insert(ADDRESSBOOK_TABLE_NAME, null, contentValues );
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+ ADDRESSBOOK_TABLE_NAME + "WHERE id = " + id + "", null);
        return  res;
    }

    public int numberOfRows(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase,ADDRESSBOOK_TABLE_NAME );
        return numRows;
    }

    public boolean updateAddress(Integer id, String name, String phone, String email, String address, String city){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("address", address);
        contentValues.put("city", city);
        sqLiteDatabase.update(ADDRESSBOOK_TABLE_NAME, contentValues, "id = ? ", new String[] {Integer.toString(id)} );
        return true;
    }

    public Integer deleteAddress(Integer id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(ADDRESSBOOK_TABLE_NAME, "id = ? ", new String[] {Integer.toString(id)} );
    }

    @SuppressLint("Range")
    public ArrayList<String> getAllAddresses(){
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + ADDRESSBOOK_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(ADDRESSBOOK_COLUMN_NAME)));
            res.moveToNext();
        }

        return  arrayList;
    }


}
