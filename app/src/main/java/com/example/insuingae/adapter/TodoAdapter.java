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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuingae.Insus;
import com.example.insuingae.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MainViewHolder> {
    ArrayList<Insus> items = new ArrayList<Insus>();
    Activity activity;
    LinearLayout container;
    LayoutInflater inflater;

    TextView tagtextView;
    TextView datetextView;

    public TodoAdapter(Activity activity, ArrayList<Insus> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public TodoAdapter.MainViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
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
        TextView titleTextView;
        TextView createdAtTextView;
        TextView publisherTextView;



        MainViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.titleEditText);
            createdAtTextView = v.findViewById(R.id.dateTextView);
            container = v.findViewById(R.id.container);
            tagtextView = v.findViewById(R.id.tagTextView);
            datetextView = v.findViewById(R.id.dateTextView);
            publisherTextView = v.findViewById(R.id.publisherTextView);
        }

        public void setItem(Insus item) {
            titleTextView.setText(item.getTitle());
            createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(item.getCreatedAt()));
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ArrayList<String> contentsList = item.getContents();
            ArrayList<String> tagsList = item.getTags();
            for (int i = 0; i < contentsList.size(); i++) {
                if (i == 3) {
                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(layoutParams);
                    textView.setText("자세히 보기");
                    container.addView(textView);
                }

                String contents = contentsList.get(i);
                TextView textView = (TextView) inflater.inflate(R.layout.view_contents_text, container, false);
                textView.setText(contents);
                container.addView(textView);
            }
            for (int i = 0; i < tagsList.size(); i++) {
                String tags = tagsList.get(i);
            if(i == 0) {
                    tagtextView.setText("#" + tags);
                }
                tagtextView.append(" #" + tags);
            }
            Date date = item.getCreatedAt();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            datetextView.setText(simpleDateFormat.format(date));

            publisherTextView.setText(item.getPublisher());
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


