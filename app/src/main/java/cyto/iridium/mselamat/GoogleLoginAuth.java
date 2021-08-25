package cyto.iridium.mselamat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleLoginAuth extends AppCompatActivity {

    private static final String TAG = "mselamat-mobile";
    private static final int RC_SIGN_IN = 1337;
    private GoogleSignInClient GClient;

    private SignInButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login_auth);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .requestServerAuthCode("505338635145-1charv896r0ofaqf8up8768uu1erhi4c.apps.googleusercontent.com")
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GClient = GoogleSignIn.getClient(this, gso);

        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }

            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            Toast.makeText(getApplicationContext(),"Signed in as " + account.getDisplayName(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(
                    GoogleLoginAuth.this,
                MainActivity.class
            );
            startActivity(intent);
            finish();
        }
    }

    private void signIn() {
        Intent signInIntent = GClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Toast.makeText(getApplicationContext(),"Signed in as " + account.getDisplayName(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(
                    GoogleLoginAuth.this,
                    MainActivity.class
            );
            startActivity(intent);
            finish();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}