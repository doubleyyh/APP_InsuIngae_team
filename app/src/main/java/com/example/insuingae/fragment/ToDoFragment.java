package com.example.insuingae.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.example.insuingae.activity.WriteActivity;
import com.example.insuingae.adapter.TodoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;


public class ToDoFragment extends Fragment {
    private RecyclerView recyclerView;
    Context context;

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
        Log.d("test", "frag1 실행");
        recyclerView = viewGroup.findViewById(R.id.recyclerView);
        viewGroup.findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(WriteActivity.class);
            }
        });
        ArrayList<String> test = new ArrayList<String>();
        test.add("aa");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TodoAdapter adapter = new TodoAdapter(getActivity());
        ArrayList<String> tags = new ArrayList<String>();
        tags.add( "#dadfasdf");
        adapter.addItem(new Insus("test", "ddd", test, tags, new Date()));
        adapter.addItem(new Insus("test", "ddd", test, tags, new Date()));
        adapter.addItem(new Insus("test", "ddd", test, tags, new Date()));
        adapter.addItem(new Insus("test", "ddd", test, tags, new Date()));
        adapter.addItem(new Insus("test", "ddd", test, tags, new Date()));
        adapter.addItem(new Insus("test", "ddd", test, tags, new Date()));

        recyclerView.setAdapter(adapter);
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

}