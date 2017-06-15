package com.programs.lala.myschool.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.widget.RemoteViewsService;


import com.programs.lala.myschool.R;
import com.programs.lala.myschool.data.MessageContract;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by melde on 4/2/2017.
 */

public class RemoteViewsControl extends RemoteViewsService {
     private final DecimalFormat dollarFormat= (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    private final DecimalFormat dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    private final DecimalFormat percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());



    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        RemoteViewsFactory remoteViewsFactory= new RemoteViewsFactory() {
            private Cursor dataCursor = null;


            @Override
            public void onCreate() {
                Log.v("tttt","gg1");
                dollarFormatWithPlus.setPositivePrefix("+$");
                percentageFormat.setMaximumFractionDigits(2);
                percentageFormat.setMinimumFractionDigits(2);
                percentageFormat.setPositivePrefix("+");

            }

            @Override
            public void onDataSetChanged() {
                if (dataCursor != null) {
                    dataCursor.close();
                }


                final long identityToken = Binder.clearCallingIdentity();

                dataCursor = getContentResolver().query(MessageContract.MessageEntry.CONTENT_URI
                        , new String[]{MessageContract.MessageEntry.COLUMN_name,
                                MessageContract.MessageEntry.COLUMN_message

                        }
                        , null, null, MessageContract.MessageEntry.COLUMN_id+" DESC");

                Binder.restoreCallingIdentity(identityToken);

            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return dataCursor.getCount();
            }

            @Override
            public android.widget.RemoteViews getViewAt(int position) {
                dataCursor.moveToPosition(position);

                android.widget.RemoteViews views = new android.widget.RemoteViews(getPackageName(), R.layout.item);
              views.setImageViewResource(R.id.messageImge,R.drawable.ic_account_circle_white_24dp);
               views.setTextViewText(R.id.name,dataCursor.getString(0));
                views.setTextViewText(R.id.message,dataCursor.getString(1));

                return views;
            }

            @Override
            public android.widget.RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }
        };
        return remoteViewsFactory;
    }

}