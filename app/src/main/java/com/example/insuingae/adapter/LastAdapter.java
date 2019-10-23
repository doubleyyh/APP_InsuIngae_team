package com.example.insuingae.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class LastAdapter extends RecyclerView.Adapter<LastAdapter.MainViewHolder> {
    private ArrayList<Insus> items;
    private Activity activity;
    private LayoutInflater inflater;
    ArrayList<String> dates;
    String [] strings;
    int i =0;





    public LastAdapter(Activity activity, ArrayList<Insus> items) {
        this.activity = activity;
        this.items = items;

    }

    @NonNull
    @Override
    public LastAdapter.MainViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.last_view, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(itemView);
        return new MainViewHolder(itemView);

    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView lastTextView;
        MainViewHolder(View v) {
            super(v);
            lastTextView = v.findViewById(R.id.lastTextView);

        }
        public void setItem(Insus item) {

        }


    }



    @Override
    public void onBindViewHolder(@NonNull final LastAdapter.MainViewHolder holder, final int position) {
        Insus item = items.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.format(item.getCompletedAt());
        Log.d("test", simpleDateFormat.format(item.getCompletedAt()));
        if (simpleDateFormat.format(item.getCompletedAt()) != null){
            try{strings[i++] = simpleDateFormat.format(item.getCompletedAt());
                Log.d("test", strings[0]);
        }
            catch (Exception e){e.printStackTrace();}
        }



        holder.setItem(item);

    }


    @Override
    public int getItemCount() {


        return items.size();
    }




}


