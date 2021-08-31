package cyto.iridium.mselamat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedbackViewActivity extends AppCompatActivity {

    private Button btnBack;

    private ProgressBar PB;

    ListView listView;
    ArrayList<FeedbackModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_view);

        btnBack = findViewById(R.id.fbBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        arrayList = new ArrayList<>();
        listView = findViewById(R.id.feedbackList);
        PB = findViewById(R.id.PBLoadingFB);

        PB.setVisibility(View.VISIBLE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference Ref = db.collection("feedbacks");
        Ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> doc = task.getResult().getDocuments();
                for (DocumentSnapshot row : doc) {
                    //System.out.println(row.getId() + " " + row.get("user_name") + " " + row.get("feedback_text"));

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");

                    Date ts = row.getDate("submit_date");
                    String dateStr = sdf.format(ts);

                    FeedbackModel model = new FeedbackModel();
                    model.setDisplayname(String.valueOf(row.get("user_name")));
                    model.setFeedbackText(String.valueOf(row.get("feedback_text")));
                    model.setId(String.valueOf(row.getId()));
                    model.setSubmitDate(dateStr);
                    arrayList.add(model);
                }
                PB.setVisibility(View.GONE);
                FeedbackAdapter adapter = new FeedbackAdapter(FeedbackViewActivity.this, arrayList);
                listView.setAdapter(adapter);
            }
        });


    }

    public void fbDelete(View view){

        String attribute = (String) view.getTag();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("feedbacks").document(attribute)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Feedback deleted.",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"ERROR!",Toast.LENGTH_SHORT).show();
                    }
                });
        refresh();
    }

    private void refresh(){
        finish();
        startActivity(getIntent());
    }
}