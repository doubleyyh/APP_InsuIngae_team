package com.example.insuingae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {
    TextView contentsTextView;
    TextView tagTextView;
    TextView dateTextView;
    TextView sosockTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        contentsTextView = findViewById(R.id.contentsTextView);
        tagTextView = findViewById(R.id.tagTextView);
        dateTextView = findViewById(R.id.dateTextView);
        sosockTextView = findViewById(R.id.sosockTextView);
        contentsTextView.setText("");
        tagTextView.setText("");
        Toolbar myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        intent.setExtrasClassLoader(Insus.class.getClassLoader());
        Insus insus = intent.getParcelableExtra("insus");
        if(actionBar != null) {
            actionBar.setTitle(insus.getTitle());
        }

        for(String str : insus.getContents()){
            contentsTextView.append(str);
        }

        for(String str : insus.getTags()){
            tagTextView.append(" #" + str);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateTextView.setText(simpleDateFormat.format(insus.getCreatedAt()));
        sosockTextView.setText(insus.getPublisher());





    }
    /*private void update() {
        //loaderlayout.setVisibility(View.VISIBLE);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Insus").document(createdAtDate).collection("time").document(createdAtTime).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("bbbbbbbbbb", "DocumentSnapshot data: " + document.getData());
                        insus = new Insus(
                                document.getData().get("title").toString(),
                                document.getData().get("publisher").toString(),
                                (ArrayList<String>) document.getData().get("contents"),
                                (ArrayList<Date>) document.getData().get("contentsAt"),
                                new Date(document.getDate("createdAt").getTime()),
                                null,
                                document.getBoolean("iscompleted"),
                                (ArrayList<String>) document.getData().get("tags"));
                        Log.d("bbbb" ,insus.getTitle());
                    } else {
                        Log.d("bbbbbbbbbbbbb", "No such document");
                    }
                } else {
                    Log.d("bbbbbbbbbbbb", "get failed with ", task.getException());
                }
            }
        });*/
                /*.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {

            }

        });


        Log.d("test", "update끝");
    }*/
}
