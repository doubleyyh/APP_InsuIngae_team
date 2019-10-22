package com.example.insuingae.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.example.insuingae.adapter.CompleteAdapter;
import com.example.insuingae.adapter.TodoAdapter;

import java.util.ArrayList;
import java.util.Date;

public class CompleteFragment extends Fragment {
    private RecyclerView recyclerView;

    public CompleteFragment() {}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test", "frag2 실행");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_to_do, container, false);
        Log.d("test", "frag2 실행");
        recyclerView = viewGroup.findViewById(R.id.recyclerView);
        //FloatingActionButton floatingActionButton = viewGroup.findViewById(R.id.floatingActionButton).setOnClickListener();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CompleteAdapter adapter = new CompleteAdapter(getActivity());
        ArrayList<String> test = new ArrayList<String>();
        test.add("aa");ArrayList<String> tags = new ArrayList<String>();
        tags.add( "#dadfasdf");
        adapter.addItem(new Insus("test", "ddd", test, tags, new Date()));



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
}
