package com.geniobits.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.PublicKey;
import java.util.ArrayList;

public class SQLiteHandler extends SQLiteOpenHelper{
    private final static String database_name = "my_database";
    private final static int database_version = 1;

    //contacts table

    private static final String CONTACT_TABLE_NAME = "contact_table";
    private static final String COLUMN_ID= "contact_id";
    private static final String COLUMN_NAME= "contact_name";
    private static final String COLUMN_EMAIL= "contact_email";
    private static final String COLUMN_ADDRESS= "contact_add";
    private static final String COLUMN_PHONE= "contact_phone";
    private static final String COLUMN_A_PHONE= "contact_a_phone";
    private static final String TAG = "SQLITE";


    public static  SQLiteHandler newInstance(Context context){
        return new SQLiteHandler(context);
    }

    public SQLiteHandler(@Nullable Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACT_TABLE =
                "CREATE TABLE IF NOT EXISTS " + CONTACT_TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," //extra data can be PRIMARY KEY UNIQUE AUTOINCREMENT NOT NULL
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_EMAIL + " TEXT,"
                        + COLUMN_ADDRESS + " TEXT,"
                        + COLUMN_PHONE + " TEXT,"
                        + COLUMN_A_PHONE + " TEXT "+ ")"; //TYPE can INTEGER TEXT VARCHAR(10)
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE =
                "DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE);

        onCreate(sqLiteDatabase);

    }

    public void add_contact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.name);
        values.put(COLUMN_EMAIL, contact.email);
        values.put(COLUMN_ADDRESS, contact.address);
        values.put(COLUMN_A_PHONE, contact.alternative_phone);
        values.put(COLUMN_PHONE, contact.phone_number);

        long res=db.insert(CONTACT_TABLE_NAME, null, values);
        Log.d(TAG, res+"");

    }

    public void update_contact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.name);
        values.put(COLUMN_EMAIL, contact.email);
        values.put(COLUMN_ADDRESS, contact.address);
        values.put(COLUMN_A_PHONE, contact.alternative_phone);
        values.put(COLUMN_PHONE, contact.phone_number);

        long res=db.update(CONTACT_TABLE_NAME, values, COLUMN_ID
                + "='" + contact.id + "'", null);

        Log.d(TAG, res+"");

    }

    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + CONTACT_TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                contactArrayList.add(new Contact(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_A_PHONE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
                        ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return contactArrayList;
    }

    public ArrayList<Contact> getContactById(int id){
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + CONTACT_TABLE_NAME +" WHERE "+COLUMN_ID+" = '"+id+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                contactArrayList.add(new Contact(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_A_PHONE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return contactArrayList;
    }



    public void delete_contact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        int result=db.delete(CONTACT_TABLE_NAME,COLUMN_ID+ " = '"+contact.id+"'",null);
    }




}
