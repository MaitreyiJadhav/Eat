package com.eat.maitreyijadhav.eat;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import com.google.android.gms.actions.ItemListIntents;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivityPage extends AppCompatActivity implements View.OnClickListener{
    private ImageView backgroundOne;
    private ImageView backgroundTwo;
    private TextView onCreateAccount, textSkip;
    private Button logInUser;
    private SignInButton googleSignInButton;
    private EditText emailTextField, passwordTextField;
    private String passwordHolder, emailHolder;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(LoginActivityPage.this, "SignIn Error", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MainActivityNavigation.class));
            finish();
        }
        initiateViews();
        startAnimation();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textViewCreateAccount:
                Intent intent = new Intent(this, RegisterUser.class);
                startActivity(intent);
                finish();
                break;

            case R.id.buttonLogin:
                logInUserAfterAuthentication();
                break;
            case R.id.buttonGoogleLogin:
                signIn();
                progressDialog.setMessage("SignIn user...");
                progressDialog.show();
                break;
            case R.id.textViewSkip:
                startActivity(new Intent(this, MainActivityNavigation.class));
                break;
        }
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void startAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                float width = backgroundOne.getWidth();
                float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void logInUserAfterAuthentication(){
        passwordHolder = passwordTextField.getText().toString();
        emailHolder = emailTextField.getText().toString();

        if(TextUtils.isEmpty(passwordHolder)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(emailHolder)){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Login user...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emailHolder, passwordHolder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivityNavigation.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Email or password incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivityPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivityNavigation.class));
                            finish();
                        }
                    }
                });
    }


    public void initiateViews(){
        googleSignInButton = (SignInButton)findViewById(R.id.buttonGoogleLogin);
        googleSignInButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        backgroundOne = (ImageView) findViewById(R.id.imageViewBackground1);
        textSkip = (TextView)findViewById(R.id.textViewSkip);
        textSkip.setOnClickListener(this);
        logInUser = (Button)findViewById(R.id.buttonLogin);
        logInUser.setOnClickListener(this);
        backgroundTwo = (ImageView) findViewById(R.id.imageViewBackground2);
        onCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        onCreateAccount.setOnClickListener(this);
        emailTextField = (EditText)findViewById(R.id.editTextEmail);
        passwordTextField = (EditText)findViewById(R.id.editTextPassword);
    }
}
