package com.example.insuingae.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuingae.Insus;
import com.example.insuingae.R;

import java.util.ArrayList;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MainViewHolder>{
    ArrayList<Insus> items = new ArrayList<Insus>();
    Activity activity;

    public TodoAdapter(Activity activity) {
        this.activity = activity;
    }
    @NonNull
    @Override
    public TodoAdapter.MainViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.insu_view, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(itemView);
        itemView.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, mainViewHolder.getAdapterPosition());
            }
        });
        return new MainViewHolder(itemView);
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
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
    public void onBindViewHolder(@NonNull TodoAdapter.MainViewHolder holder, int position) {
        Insus item = items.get(position);
        holder.setItem(item);
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Insus item) {
        items.add(item);
    }

    private void showPopup(View v, final int position) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.modify:

                        return true;
                    case R.id.delete:

                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(activity, c);
        activity.startActivityForResult(intent, 1);
    }
}
