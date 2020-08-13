package com.getcapacitor.community.intercom;

import android.util.Log;

import com.getcapacitor.plugin.PushNotifications;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.intercom.android.sdk.push.IntercomPushClient;

public class IntercomPushMessage extends FirebaseMessagingService {
    private final IntercomPushClient intercomPushClient = new IntercomPushClient();

    @Override
    public void onNewToken(String newToken) {
        super.onNewToken(newToken);
        PushNotifications.onNewToken(newToken);
        intercomPushClient.sendTokenToIntercom(getApplication(), newToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        PushNotifications.sendRemoteMessage(remoteMessage);
        Map message = remoteMessage.getData();
        if (intercomPushClient.isIntercomPush(message)) {
            intercomPushClient.handlePush(getApplication(), message);
        }
    }

}