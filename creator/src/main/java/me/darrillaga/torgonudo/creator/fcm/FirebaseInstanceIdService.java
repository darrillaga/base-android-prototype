package me.darrillaga.torgonudo.creator.fcm;

import com.google.firebase.iid.FirebaseInstanceId;

import me.darrillaga.torgonudo.creator.FirebaseTokenUserIdPersistor;

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        FirebaseTokenUserIdPersistor.createWithToken(token).attemptSend();
    }
}
