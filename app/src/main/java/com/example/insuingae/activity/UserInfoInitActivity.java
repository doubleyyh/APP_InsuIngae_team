package com.example.insuingae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.insuingae.R;
import com.example.insuingae.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoInitActivity extends AppCompatActivity {
    private RelativeLayout loaderLayout;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        setToolbar("회원 정보 입력");
        user= FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_user_info_init);
        loaderLayout = findViewById(R.id.loaderLayout);
        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void storeUploader() {

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
                        Toast.makeText(UserInfoInitActivity.this, "회원정보 등록에 성공하였습니다.", Toast.LENGTH_SHORT);
                        loaderLayout.setVisibility(View.GONE);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserInfoInitActivity.this, "회원정보 등록에 실패하였습니다.", Toast.LENGTH_SHORT);
                        loaderLayout.setVisibility(View.GONE);
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
