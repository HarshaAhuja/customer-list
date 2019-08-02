/*
package com.example.customerlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

import com.example.customerlist.Model.CustomerDetails;

public final class CustomerDetailDatabase  extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public CustomerDetailDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(CustomerDetails.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CustomerDetails.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public long insertCustomerDetail(String Name,String email_Id,String Phone_Number){
    // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(CustomerDetails.COLUMN_NAME, Name);
        values.put(CustomerDetails.COLUMN_EMAIL_ID,email_Id);
        values.put(CustomerDetails.COLUMN_PHONE_NUMBER,Phone_Number);

        // insert row
        long id = db.insert(CustomerDetails.TABLE_NAME, null, values);
        db.close();
        return id ;
    }
    public void deleteCustomerDetail(CustomerDetails Name,CustomerDetails email_Id,CustomerDetails Phone_Number){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CustomerDetails.TABLE_NAME, CustomerDetails.COLUMN_ID + " = ?",
                new String[]{String.valueOf(CustomerDetails.get)});
        db.close();
    }
}
*/
