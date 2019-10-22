package com.example.insuingae.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuingae.Insus;
import com.example.insuingae.R;

import java.util.ArrayList;


public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.MainViewHolder>{
    ArrayList<Insus> items = new ArrayList<Insus>();
    Activity activity;

    public CompleteAdapter(Activity activity) {
        this.activity = activity;
    }
    @NonNull
    @Override
    public CompleteAdapter.MainViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.complete_view, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(itemView);

        return new MainViewHolder(itemView);
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        TextView contentsTextView;
        TextView titleTextView;
        MainViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.titleEditText);
            contentsTextView = v.findViewById(R.id.contentsTextView);
        }

        public void setItem(Insus item) {

            titleTextView.setText(item.getTitle());
            //contentsTextView.setText(item.getContents());
        }

    }

    @Override
    public void onBindViewHolder(@NonNull CompleteAdapter.MainViewHolder holder, int position) {
        Insus item = items.get(position);
        holder.setItem(item);
    }

    public void addItem(Insus item) {
        items.add(item);
    }



    @Override
    public int getItemCount() {
        return items.size();
    }







}
