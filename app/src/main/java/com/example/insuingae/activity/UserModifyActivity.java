package com.example.insuingae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insuingae.R;
import com.example.insuingae.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserModifyActivity extends AppCompatActivity {
    private FirebaseUser user;
    TextView userNameEditext;
    TextView sosockEditText;
    TextView gunbunEditText;
    TextView jikcheckEditText;
    TextView phoneEditText;
    Button checkbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_init);
        Toolbar myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        setToolbar("회원 정보 수정");
        userNameEditext = findViewById(R.id.userName_editText);
        sosockEditText = findViewById(R.id.sosock_EditText);
        gunbunEditText = findViewById(R.id.gunbun_editText);
        jikcheckEditText = findViewById(R.id.jikcheck_EditText);
        phoneEditText = findViewById(R.id.phone_EditText);
        checkbutton = findViewById(R.id.checkButton);
        checkbutton.setOnClickListener(onClickListener);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();


        DocumentReference docRef = db.collection("users").document(user.getUid());
        Task<DocumentSnapshot> documentSnapshotTask = docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        gunbunEditText.setText(""+document.get("gunbun"));
                        userNameEditext.setText(""+document.get("name"));
                        jikcheckEditText.setText(""+document.get("jikcheck"));
                        phoneEditText.setText(""+document.get("phoneNumber"));
                        sosockEditText.setText(""+document.get("sosok"));

                    } else {
                        Log.d("test", "No such document");
                    }
                } else {
                    Log.d("test", "get failed with ", task.getException());
                }
            }
        });



    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.checkButton:
                    storeUploader();

            }
        }
    };
    private void storeUploader() {
        final RelativeLayout loaderlayout = findViewById(R.id.loaderLayout);


        final String name = ((EditText) findViewById(R.id.userName_editText)).getText().toString();
        final String phoneNumber = ((EditText) findViewById(R.id.phone_EditText)).getText().toString();
        final String gunbun = ((EditText) findViewById(R.id.gunbun_editText)).getText().toString();
        final String jikcheck = ((EditText) findViewById(R.id.jikcheck_EditText)).getText().toString();
        final String sosock = ((EditText) findViewById(R.id.sosock_EditText)).getText().toString();
        UserInfo userInfo = new UserInfo(name, sosock, phoneNumber, gunbun, jikcheck);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).set(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UserModifyActivity.this, "회원정보 등록에 성공하였습니다.", Toast.LENGTH_SHORT);
                        loaderlayout.setVisibility(View.GONE);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserModifyActivity.this, "회원정보 등록에 실패하였습니다.", Toast.LENGTH_SHORT);
                        loaderlayout.setVisibility(View.GONE);
                    }
                });
    }
    public void setToolbar(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }
}
