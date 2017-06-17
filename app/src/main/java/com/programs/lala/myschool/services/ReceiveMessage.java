package com.programs.lala.myschool.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.programs.lala.myschool.MainActivity;
import com.programs.lala.myschool.R;
import com.programs.lala.myschool.data.MessageContract;


public class ReceiveMessage extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.v("ff", "kk");
        Log.v("ff", remoteMessage.getData().get("name"));
        //////
        ContentValues contentValues = new ContentValues();
        contentValues.put("TABLE_NAME", MessageContract.MessageEntry.FIRST_TABLE_NAME);
        contentValues.put(MessageContract.MessageEntry.COLUMN_sender_id, remoteMessage.getData().get("sendId"));
        contentValues.put(MessageContract.MessageEntry.COLUMN_receive_id, "1");
        contentValues.put(MessageContract.MessageEntry.COLUMN_name, remoteMessage.getData().get("name"));
        contentValues.put(MessageContract.MessageEntry.COLUMN_message, remoteMessage.getData().get("message"));
        contentValues.put(MessageContract.MessageEntry.COLUMN_type, "1");
        contentValues.put(MessageContract.MessageEntry.COLUMN_sender_image, remoteMessage.getData().get("image"));

        Uri uri = getContentResolver().insert(MessageContract.MessageEntry.CONTENT_URI, contentValues);
        if (uri != null) {
            Log.v("ff", "good");
        }


        /////////

        sendNotification(remoteMessage.getData().get("name"));

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}