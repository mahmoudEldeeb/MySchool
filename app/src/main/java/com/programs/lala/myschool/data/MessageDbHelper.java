package com.programs.lala.myschool.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by melde on 6/14/2017.
 */

public class MessageDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myMessages.db";
    private static final int DATABASE_VERSION = 1;

    public MessageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MESSAGE_TABLE =
                "CREATE TABLE " + MessageContract.MessageEntry.FIRST_TABLE_NAME + " (" +


                        MessageContract.MessageEntry.COLUMN_id             + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        MessageContract.MessageEntry.COLUMN_sender_id              +" Text NOT NULL,"+
                        MessageContract.MessageEntry.COLUMN_receive_id              +" Text NOT NULL,"+
                        MessageContract.MessageEntry.COLUMN_name              +" Text NOT NULL,"+
                        MessageContract.MessageEntry.COLUMN_message             +" Text NOT NULL,"+
                        MessageContract.MessageEntry.COLUMN_sender_image              +" Text NOT NULL,"+
                        MessageContract.MessageEntry.COLUMN_type            +" INTEGER"+
                        ");";



        db.execSQL(SQL_CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  MessageContract.MessageEntry.FIRST_TABLE_NAME);
        onCreate(db);

    }
}
