package com.example.application1;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class CompanyProvider extends ContentProvider {
    public CompanyProvider() {
    }

    //public static final  String authority = "com.application.my.company.provider";
    public static final  String authority = "com.example.application1";
    public static final Uri content_uri = Uri.parse("content://"+authority+"/emp");
    static  int emp = 1;
    static  int  emp_id = 2;

    SQLiteDatabase mydb;
    private static final String database_name = "company";
    private static final String database_table = "emp";
    private static final int database_version = 1;


    //INITIALIZING URI MATCHER WITH NO MATCH FIRST AND THEN ADDING BELOW URI THAT NEED TO MATCHED
    static UriMatcher my_uri = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        my_uri.addURI(authority , "emp" , emp);
        my_uri.addURI(authority , "emp/#" , emp_id);
    }


    //THIS CLASS IS BEING USED TO CREATE DATABASE
    private class database extends SQLiteOpenHelper{

        public database(Context ct){
            super(ct , database_name , null , database_version);

        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table "+database_table+"(_id integer primary key autoincrement , emp_name text , role text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists "+database_table);
        }
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long row  = mydb.insert(database_table , null , values);
        if(row>0){
            uri = ContentUris.withAppendedId(content_uri , row);
            getContext().getContentResolver().notifyChange(uri , null);
        }
        return uri;
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        database db = new database(getContext());
        mydb = db.getWritableDatabase();
        if(mydb!=null)
        {
            return true;
        }
        else
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder my_query = new SQLiteQueryBuilder();
        my_query.setTables(database_table);

        Cursor cr = my_query.query(mydb , null ,null,null,null,null,"_id");
        cr.setNotificationUri(getContext().getContentResolver(),uri);
        return cr;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}