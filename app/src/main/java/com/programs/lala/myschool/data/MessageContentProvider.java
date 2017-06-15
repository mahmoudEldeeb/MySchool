package com.programs.lala.myschool.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by melde on 6/14/2017.
 */

public class MessageContentProvider extends ContentProvider {
    MessageDbHelper messageDbHelper;

    public static final int MESSAGE = 100;
    public static final int CODE_MESSAGE_WITH_DATE = 101;


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MessageDbHelper mOpenHelper;
    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MessageContract.CONTENT_AUTHORITY;


        matcher.addURI(authority, MessageContract.PATH_MESSAGE, MESSAGE);


        matcher.addURI(authority, MessageContract.PATH_MESSAGE + "/#", CODE_MESSAGE_WITH_DATE);

        return matcher;
    }




    @Override
    public boolean onCreate() {
        Context context=getContext();
        messageDbHelper=new MessageDbHelper(context);

        return false;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db=messageDbHelper.getReadableDatabase();
        int match=sUriMatcher.match(uri);

        Cursor recursor = null;

            switch (match) {
                case MESSAGE:
                    recursor = db.query(MessageContract.MessageEntry.FIRST_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            }


        recursor.setNotificationUri(getContext().getContentResolver(), uri);
        return recursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db=messageDbHelper.getWritableDatabase();
        int match=sUriMatcher.match(uri);

        Uri returnUri = null;
        switch (match){
            case MESSAGE:

                Long id;


                    values.remove("TABLE_NAME");
                    id=db.insert(MessageContract.MessageEntry.FIRST_TABLE_NAME,null,values);

                if(id>0){

                    returnUri= ContentUris.withAppendedId(MessageContract.MessageEntry.CONTENT_URI, id);
                }

                break;
            default:break;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
