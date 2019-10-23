package com.example.insuingae.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WriteActivity extends AppCompatActivity {
    LinearLayout contentsLayout;
    FirebaseUser user;
    FirebaseFirestore db;
    String sosok;
    String jikcheck;
    ArrayList<String> tags;
    String name;
    /*
    ImageView imageView;
    VideoView videoView;
    SoundPool sound;
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Toolbar myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("인수인계 작성");
        }
        /*for (String ta : tag){
            tags.add(ta);
            Log.d("test", ta);
        }*/


        contentsLayout = findViewById(R.id.contentsLayout);
        user = FirebaseAuth.getInstance().getCurrentUser();
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loaderLayout).setVisibility(View.VISIBLE);
                storageUpload();
            }
        });
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        sosok = "" + document.get("sosok");
                        jikcheck = "" + document.get("jikcheck");
                        name = "" + document.get("name");
                    } else {
                        Log.d("test", "No such document");
                    }
                } else {
                    Log.d("test", "get failed with ", task.getException());
                }
            }
        });




    }

    private void storageUpload() {
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
        EditText tagEditText = findViewById(R.id.tagEditText);
        String[] a = tagEditText.getText().toString().split("#");
        tags = new ArrayList<>();
        for(int i =1; i< a.length ;i++){
            tags.add(a[i]);
        }
        if (title.length() > 0) {

            final ArrayList<String> contentsList = new ArrayList<>();
            final ArrayList<String> formatList = new ArrayList<>();

            final Date date = new Date();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            final DocumentReference documentReference = firebaseFirestore.collection("Insus").document("" + simpleDateFormat.format(date)).collection("time").
                                                        document(simpleDateFormat2.format(date));
            for (int i = 0; i < contentsLayout.getChildCount(); i++) {
                View view = (contentsLayout.getChildAt(i));
                if (view instanceof EditText) {
                    String text = ((EditText) view).getText().toString();
                    if (text.length() > 0) {
                        contentsList.add(text);
                        formatList.add("text");
                    }
                }
            }
            storeUpload(documentReference, new Insus(title, sosok + " " + jikcheck + " " + name, contentsList, tags, date));
        } else {
            Toast.makeText(WriteActivity.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }



    }
    private void storeUpload(DocumentReference documentReference, final Insus insus) {
        documentReference.set(insus.getInsus())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test", "DocumentSnapshot successfully written!");
                        /*Intent resultIntent = new Intent();
                        resultIntent.putExtra("postinfo", postInfo);
                        setResult(Activity.RESULT_OK, resultIntent);*/
                        finish();
                        findViewById(R.id.loaderLayout).setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test", "Error writing document", e);
                    }
                });
    }

}

