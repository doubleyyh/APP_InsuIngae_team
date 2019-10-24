package com.example.insuingae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {
    LinearLayout container;
    Insus insus;
    String addInsuTextView;
    String addTagTextView;
    HashMap<String, Object> tempMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        insus = getIntent().getParcelableExtra("insus");
        container = findViewById(R.id.containter);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container.removeAllViews();
        try{
            Log.d("test", insus.getTitle());
            actionBar.setTitle(insus.getTitle());
            String title = insus.getTitle();
            String publisher = insus.getPublisher();
            ArrayList<String> contents = insus.getContents();
            ArrayList<Date> contentsAt= insus.getContentsAt();
            Date createdAt = insus.getCreatedAt();
            Date completedAt = insus.getCompletedAt();
            boolean iscompleted = insus.isIscompleted();
            ArrayList<String> tags = insus.getTags();

        }catch(Exception e){
          e.printStackTrace();
        }
        final LinearLayout view = (LinearLayout)getLayoutInflater().inflate(R.layout.view_update, container ,false);
        ((TextView)view.findViewById(R.id.contentsTextView2)).setText(insus.getContents().get(0));
        for(String s : insus.getTags()){
            ((TextView)view.findViewById(R.id.tagTextView2)).append(s);
        }
        ((TextView)view.findViewById(R.id.dateTextView2)).setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(insus.getCreatedAt()));
        ((TextView)view.findViewById(R.id.sosockTextView2)).setText(insus.getPublisher());

        container.addView(view);
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInsuTextView = ((EditText)findViewById(R.id.addContentsTextview)).getText().toString();
                addTagTextView = ((EditText)findViewById(R.id.addTagsTextView)).getText().toString();
                Log.d("test", "clickbutton");

                final LinearLayout view = (LinearLayout)getLayoutInflater().inflate(R.layout.view_update, container ,false);
                ((TextView)view.findViewById(R.id.contentsTextView2)).setText(addInsuTextView);
                String[] tags = addTagTextView.split("#");
                for(String s : tags){
                    if (s.equals("")){
                        continue;
                    }
                    ((TextView)view.findViewById(R.id.tagTextView2)).append( " #" + s);
                }
                ((TextView)view.findViewById(R.id.dateTextView2)).setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("users").document(auth.getUid());
                Task<DocumentSnapshot> documentSnapshotTask = docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                ((TextView)view.findViewById(R.id.sosockTextView2)).setText(""+document.get("sosok") + " " + ""+document.get("jikcheck") + " "+""+document.get("name"));


                            } else {
                                Log.d("test", "No such document");
                            }
                        } else {
                            Log.d("test", "get failed with ", task.getException());
                        }
                    }
                });



                container.addView(view);
                Snackbar.make(v, "인수인계가 추가되었습니다", Snackbar.LENGTH_SHORT).show();
            }
        });
        }
        /*
        contentsTextView = findViewById(R.id.contentsTextView);
        tagTextView = findViewById(R.id.tagTextView);
        dateTextView = findViewById(R.id.dateTextView);
        sosockTextView = findViewById(R.id.sosockTextView);
        contentsTextView.setText("");
        tagTextView.setText("");

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
*/




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

