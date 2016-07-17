package me.darrillaga.torgonudo.creator.create;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.darrillaga.torgonudo.creator.FirebaseTokenUserIdPersistor;
import me.darrillaga.torconudo.creator.databinding.FragmentCreateDeliveryOrderBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateDeliveryOrderActivityFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mUserUid;

    private FragmentCreateDeliveryOrderBinding mFragmentCreateDeliveryOrderBinding;

    public CreateDeliveryOrderActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentCreateDeliveryOrderBinding = FragmentCreateDeliveryOrderBinding.inflate(inflater, container, false);
        return mFragmentCreateDeliveryOrderBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                mUserUid = user.getUid();
                FirebaseTokenUserIdPersistor.createWithUserId(mUserUid).attemptSend();
                onLogedIn();
            } else {
                // User is signed out
                requestLogin();
            }
        };

        mFragmentCreateDeliveryOrderBinding.actionCreate.setOnClickListener(view1 -> createUserAction());
        mFragmentCreateDeliveryOrderBinding.actionLogin.setOnClickListener(view1 -> loginAction());
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void requestLogin() {
        mFragmentCreateDeliveryOrderBinding.loginForm.setVisibility(View.VISIBLE);
    }

    private void onLogedIn() {
        mFragmentCreateDeliveryOrderBinding.loginForm.setVisibility(View.GONE);
    }

    private void loginAction() {
        mAuth.signInWithEmailAndPassword(
                mFragmentCreateDeliveryOrderBinding.username.getEditText().getText().toString(),
                mFragmentCreateDeliveryOrderBinding.password.getEditText().getText().toString()
        )
                .addOnCompleteListener(getActivity(), task -> {
//                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createUserAction() {
        mAuth.createUserWithEmailAndPassword(
                mFragmentCreateDeliveryOrderBinding.username.getEditText().getText().toString(),
                mFragmentCreateDeliveryOrderBinding.password.getEditText().getText().toString()
        )
                .addOnCompleteListener(getActivity(), task -> {
//                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }
}
