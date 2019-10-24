package com.example.insuingae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.example.insuingae.fragment.CompleteFragment;
import com.example.insuingae.fragment.LastFragment;
import com.example.insuingae.fragment.ToDoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    MenuItem mSearch;
    ToDoFragment todofragment;
    CompleteFragment completeFragment;
    LastFragment lastFragment;
    BottomNavigationView bottomNavigationView;
    ArrayList<Insus> insulist = new ArrayList<>();

    //Last2Fragment last2Fragment;
    private BackPressCloseHandler backPressCloseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backPressCloseHandler = new BackPressCloseHandler(this);

        Toolbar myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        setToolbar("해야 할 일");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            myStartActivity(LoginActivity.class);
        }else{
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(firebaseUser.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (document.exists()) {
                                Log.d("test", "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d("test", "No such document");
                                myStartActivity(UserInfoInitActivity.class);
                            }
                        }
                    } else {
                        Log.d("test", "get failed with ", task.getException());
                    }
                }
            });
        }

        todofragment= new ToDoFragment();
        completeFragment = new CompleteFragment();
        lastFragment = new LastFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, todofragment).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.todo:
                        mSearch.collapseActionView();
                        setToolbar("해야 할 일");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, todofragment).commit();
                        return true;
                    case R.id.did:
                        mSearch.collapseActionView();
                        setToolbar("오늘 한 일");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, completeFragment).commit();
                        return true;
                    case R.id.last:
                        /*mSearch.collapseActionView();*/
                        setToolbar("지난 인수인계");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, lastFragment).commit();

                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });

    }

    public void setToolbar(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }
    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        mSearch = menu.findItem(R.id.app_bar_search);
        mSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                getSupportFragmentManager().beginTransaction().replace(R.id.container, lastFragment).commit();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                getSupportFragmentManager().beginTransaction().replace(R.id.container, todofragment).commit();
                return true;
            }
        });

        SearchView v = (SearchView) mSearch.getActionView();
        v.setQueryHint("#태그로 인수인계를 검색하세요");
        v.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String[] q = query.split("#");
                lastFragment.search(q);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Log.d("test", "app");
                bottomNavigationView.setSelectedItemId(R.id.last);

                return true;
            case R.id.userInfo:
                myStartActivity(UserModifyActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override public void onBackPressed(){
        //super.onBackPressed();
        bottomNavigationView.setSelectedItemId(R.id.todo);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, todofragment).commit();

        backPressCloseHandler.onBackPressed();
    }



}

