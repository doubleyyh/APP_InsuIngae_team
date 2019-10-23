package com.example.insuingae.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.example.insuingae.activity.WriteActivity;
import com.example.insuingae.adapter.TodoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Date;



public class ToDoFragment extends Fragment {
    private RecyclerView recyclerView;
    Context context;
    ArrayList<Insus> insulist = new ArrayList<>();
    TodoAdapter adapter;
    FirebaseFirestore firebaseFirestore;


    public ToDoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_to_do, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        Log.d("test", "frag1 실행");
        recyclerView = viewGroup.findViewById(R.id.recyclerView);
        viewGroup.findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(WriteActivity.class);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        update();
        adapter = new TodoAdapter(getActivity(), insulist);
        recyclerView.setAdapter(adapter);

        Log.d("test", "onCreateView실행");
        return viewGroup;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        getActivity().startActivityForResult(intent, 1);
    }

    private void update() {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collectionGroup("time").whereEqualTo("iscompleted", false).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            insulist.clear();
                            Log.d("test", "sucess");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                insulist.add(new Insus(
                                        document.getData().get("title").toString(),
                                        document.getData().get("publisher").toString(),
                                        (ArrayList<String>) document.getData().get("contents"),
                                        (ArrayList<String>) document.getData().get("tags"),
                                        new Date(document.getDate("createdAt").getTime())));
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
        Log.d("test", "update끝");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("test", "onResume실행");
    }
}