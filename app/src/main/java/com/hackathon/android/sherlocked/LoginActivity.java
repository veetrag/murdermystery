package com.hackathon.android.sherlocked;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hackathon.android.sherlocked.util.Preferences;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        googleSignInButton = findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w("Sign In Status", "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
        }
    }


    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, StoryActivity.class);
        // intent.putExtra(ProfileActivity.GOOGLE_ACCOUNT, googleSignInAccount);

        Log.i("Photo Link", googleSignInAccount.getPhotoUrl().toString());
        Log.i("Photo Link", googleSignInAccount.getDisplayName());
        Log.i("Photo Link", googleSignInAccount.getEmail());

        editor.putString(Preferences.EMAIL, googleSignInAccount.getEmail());
        editor.commit();
        editor.putString(Preferences.NAME, googleSignInAccount.getDisplayName());
        editor.commit();
        editor.putString(Preferences.PROFILE_IMAGE, googleSignInAccount.getPhotoUrl().toString());
        editor.commit();

        startActivity(intent);
        finish();
    }


    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d("Sign In Status", "Not logged in");
        }
    }
}
