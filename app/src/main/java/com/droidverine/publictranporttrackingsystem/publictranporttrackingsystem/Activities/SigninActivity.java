package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class SigninActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener  {
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    FirebaseDatabase firebaseDatabase;
    // [START declare_auth]
    private FirebaseAuth mAuth;


    private FirebaseAnalytics mFirebaseAnalytics;
    // [END declare_auth]

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    ImageView logoel;
    Button adminbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        DetailsManager detailsManager=new DetailsManager(this);
        // Views

        logoel=findViewById(R.id.logoel);
        Glide.with(getApplicationContext())
                .load(R.drawable.com_facebook_auth_dialog_background).centerInside().into(logoel);
        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
Log.d("signIn","sdssd");

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
Log.d("onactivityresult","sdsdsdsd");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                DetailsManager detailsManager=new DetailsManager(this);
                detailsManager.setGoogleProfileUri(account.getPhotoUrl().toString());
                detailsManager.setName(account.getDisplayName().toString());
                detailsManager.setEmail(account.getEmail().toString());


                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"12");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"userSignin");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text/plain");
                mGoogleApiClient.clearDefaultAccountAndReconnect(); //to allow account selection every time

            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                Log.d("loginfailed","sdsds");
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseMessaging.getInstance().subscribeToTopic("Techxter");
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = firebaseDatabase.getReference();
                            myRef.child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                    dataSnapshot.getRef().child("username").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                                    Log.d("firebasevalueset","set");

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.d("User", databaseError.getMessage());
                                }
                            });



                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SigninActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        signInIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.d("Signinbtn","ssss");


    }
    // [END signin]

    public void signOut(FirebaseAuth SAuth,Intent intent) {
        // Firebase sign out

        SAuth.signOut();

        startActivity(intent);
        FirebaseMessaging.getInstance().unsubscribeFromTopic("Techxter");



    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null)
        {

            startActivity(new Intent(SigninActivity.this, MainActivity.class));
            FirebaseMessaging.getInstance().subscribeToTopic("Techxter");


        }

        else{
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
    }}