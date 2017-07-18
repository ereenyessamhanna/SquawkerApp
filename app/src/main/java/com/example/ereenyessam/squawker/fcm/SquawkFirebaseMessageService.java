package com.example.ereenyessam.squawker.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.ereenyessam.squawker.MainActivity;
import com.example.ereenyessam.squawker.R;
import com.example.ereenyessam.squawker.provider.SquawkContract;
import com.example.ereenyessam.squawker.provider.SquawkProvider;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Ereeny Essam on 7/17/2017.
 */

public class SquawkFirebaseMessageService extends FirebaseMessagingService {
    private static final String JSON_KEY_AUTHOR = SquawkContract.COLUMN_AUTHOR;
    private static final String JSON_KEY_AUTHOR_KEY = SquawkContract.COLUMN_AUTHOR_KEY;
    private static final String JSON_KEY_MESSAGE = SquawkContract.COLUMN_MESSAGE;
    private static final String JSON_KEY_DATE = SquawkContract.COLUMN_DATE;


    private static final int NOTIFICATION_MAX_CHARACTERS = 30;
    private static String LOG_TAG = SquawkFirebaseMessageService.class.getSimpleName();



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(LOG_TAG,"From :" + remoteMessage.getFrom());


        Map<String,String> data = remoteMessage.getData();

        if (data.size()>0){


            Log.d(LOG_TAG, "Message Data :" + data);
            sendNotification(data);
            insertSquawk(data);
    }
    }
    private void insertSquawk(final Map<String, String> data) {

        AsyncTask<Void,Void,Void> mInsertSquawkTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                ContentValues mValues = new ContentValues();
                mValues.put(SquawkContract.COLUMN_AUTHOR_KEY,data.get(JSON_KEY_AUTHOR_KEY));
                mValues.put(SquawkContract.COLUMN_AUTHOR,data.get(JSON_KEY_AUTHOR));
                mValues.put(SquawkContract.COLUMN_MESSAGE,data.get(JSON_KEY_MESSAGE));
                mValues.put(SquawkContract.COLUMN_DATE,data.get(JSON_KEY_DATE));
                getContentResolver().insert(SquawkProvider.SquawkMessages.CONTENT_URI,mValues);
                return null;
            }
        };
        mInsertSquawkTask.execute();

    }
    private void sendNotification(Map<String, String> data) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String author = data.get(JSON_KEY_AUTHOR);
        String message = data.get(JSON_KEY_MESSAGE);

        if (message.length() > NOTIFICATION_MAX_CHARACTERS) {
            message = message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026";

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_web)
                    .setContentTitle(String.format(getString(R.string.notification_message), author))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }





    }

    }


