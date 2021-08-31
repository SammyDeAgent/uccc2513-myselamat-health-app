package cyto.iridium.mselamat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {


    private static final String TAG = "feedback_db";
    private Button btnSubmit;
    private Button AdminView;

    private EditText feedbackTx;

    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String displayname = null;
        try{
            displayname = account.getDisplayName();
        }catch(NullPointerException e){
            displayname = "Guest";
        }

        btnSubmit = findViewById(R.id.btnSubmit);
        AdminView = findViewById(R.id.btnAdminView);
        feedbackTx = findViewById(R.id.feedbackTX);

        String finalDisplayname = displayname;
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!feedbackTx.getText().toString().equals("")){
                    String feedbacktext = feedbackTx.getText().toString();

                    Map<String, Object> data = new HashMap<>();
                    data.put("feedback_text", feedbacktext);
                    data.put("user_name", finalDisplayname);
                    data.put("submit_date", new Date());

                    db.collection("feedbacks")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(),"Feedback has been submitted, thank you!",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });


                }else{
                    Toast.makeText(getApplicationContext(),"Feedback is empty!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        AdminView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setTitle("Input Secret Key");
                // Set up the input
                final EditText input = new EditText(FeedbackActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();

                        //Static secret key for admin to view feedbacks
                        if(m_Text.equals("vitalex")){
                            Intent intent = new Intent(
                                    FeedbackActivity.this,
                                    FeedbackViewActivity.class
                            );
                            startActivity(intent);
                        }else{
                            Toast.makeText(FeedbackActivity.this,"Invalid secret key.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    public void onClickBack(View view){
        finish();
    }
}