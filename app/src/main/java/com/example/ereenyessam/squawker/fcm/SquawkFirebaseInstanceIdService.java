package com.example.ereenyessam.squawker.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Ereeny Essam on 7/17/2017.
 */

public class SquawkFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static String LOG_TAG = SquawkFirebaseInstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(LOG_TAG,"Refresh Token:"+refreshToken);

        sendRegistrationToServer(refreshToken);

    }

    private void sendRegistrationToServer(String token) {

    }
}
