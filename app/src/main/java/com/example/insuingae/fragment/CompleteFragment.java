package com.example.insuingae.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.example.insuingae.adapter.CompleteAdapter;
import com.example.insuingae.adapter.TodoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CompleteFragment extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<Insus> insulist = new ArrayList<>();
    CompleteAdapter adapter;
    RelativeLayout loaderlayout;



    public CompleteFragment() {}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test", "frag2 실행");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loaderlayout = getActivity().findViewById(R.id.loaderLayout);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_complete, container, false);
        Log.d("test", "frag2 실행");
        recyclerView = viewGroup.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CompleteAdapter(getActivity(), insulist);
        update();
        recyclerView.setAdapter(adapter);
        return viewGroup;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new CompleteAdapter(getActivity(), insulist);

        update();
        recyclerView.setAdapter(adapter);
    }

    private void update() {
        loaderlayout.setVisibility(View.VISIBLE);
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collectionGroup("time").whereEqualTo("iscompleted", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    insulist.clear();
                    Log.d("test", "sucess");

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try{Date date = new Date(document.getDate("completedAt").getTime());
                            if(sdf.format(date).equals(sdf.format(new Date()))){
                                insulist.add(new Insus(
                                        document.getData().get("title").toString(),
                                        document.getData().get("publisher").toString(),
                                        (ArrayList<String>) document.getData().get("contents"),
                                        (ArrayList<Date>) document.getData().get("contentsAt"),
                                        new Date(document.getDate("createdAt").getTime()),
                                        date,
                                        true,
                                        (ArrayList<String>) document.getData().get("tags"))
                                );
                            }}catch (Exception e){e.printStackTrace();}



                    }
                    adapter.notifyDataSetChanged();
                    loaderlayout.setVisibility(View.INVISIBLE);
                } else {
                    Log.d("test", "Error getting documents: ", task.getException());
                }
            }
        });
        Log.d("test", "update끝");
    }
}
