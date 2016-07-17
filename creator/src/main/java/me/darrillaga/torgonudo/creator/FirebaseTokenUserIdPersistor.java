package me.darrillaga.torgonudo.creator;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class FirebaseTokenUserIdPersistor {

    public static FirebaseTokenUserIdPersistor createWithUserId(String userId) {
        return new FirebaseTokenUserIdPersistor(userId, null);
    }

    public static FirebaseTokenUserIdPersistor createWithToken(String token) {
        return new FirebaseTokenUserIdPersistor(null, token);
    }

    private String mUserId;
    private String mToken;

    public FirebaseTokenUserIdPersistor(String userId, String token) {
        mUserId = userId;
        mToken = token;
    }

    public boolean attemptSend() {
        if (mToken == null) {
            mToken = FirebaseInstanceId.getInstance().getToken();
            if (mToken == null) {
                return false;
            }

        } else if (mUserId == null) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            mUserId = firebaseUser != null ? firebaseUser.getUid() : null;

            if (mUserId == null) {
                return false;
            }
        }

        send();
        return true;
    }

    private void send() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put("authId", mUserId);
        keyValues.put("token", mToken);

        firebaseDatabase.getReference("/notifications/devices").child(mUserId).setValue(keyValues);
    }
}
