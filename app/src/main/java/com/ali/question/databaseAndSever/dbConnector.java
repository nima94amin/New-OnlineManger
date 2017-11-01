package com.ali.question.databaseAndSever;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ali_Najafi on 15/02/2017.
 */
public class dbConnector  extends SQLiteOpenHelper {

    public dbConnector(Context context, String name, SQLiteDatabase.CursorFactory factory,
                       int version) {
        super(context, name, factory, version);
        Log.i("dbbbbb","Connect...");


        create_Tables() ;
    }



    @Override
    public void onCreate(SQLiteDatabase arg0) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }



    private void create_Tables() {
        Log.i("ali_tag" , "creating tables");

        String query =
                "CREATE TABLE IF NOT EXISTS soalat (" +
                        "id       TEXT                                   , " +
                        "type    TEXT                                   , " +
                        "qustion    TEXT                                   , " +
                        "g1    TEXT                                 , " +
                        "g2    TEXT                                   , " +
                        "g3    TEXT                               , " +
                        "g4    TEXT                                   , " +
                        "answer    TEXT                                    " +
                        "); ";
        String query1 =
                "CREATE TABLE IF NOT EXISTS type (" +
                        "type       TEXT                                   , " +
                        "count    INTEGER                                  , " +
                        "slove    INTEGER                                   " +
                        "); ";

        this.exec(query);
        this.exec(query1);

    }



    public Boolean exec(String query) {

        try {
            this.getWritableDatabase().execSQL(query);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public Boolean insert(String table , ContentValues values) {


        try {
            this.getWritableDatabase().insert(table, null , values);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public Boolean update(String table , ContentValues values,String where,String []arg) {


        try {
            this.getWritableDatabase().update(table,values,where,arg);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Cursor select (String query) {

        Cursor c = null;
        c =  this.getWritableDatabase().rawQuery(query, null);

        return c;
    }




}
