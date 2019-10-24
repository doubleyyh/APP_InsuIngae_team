package com.example.insuingae.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.MainViewHolder>{
    ArrayList<Insus> items;
    Activity activity;
    LayoutInflater inflater;

    public CompleteAdapter(Activity activity, ArrayList<Insus> items) {
        this.activity = activity;
        this.items = items;
    }
    @NonNull
    @Override
    public CompleteAdapter.MainViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.complete_view, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(itemView);
        itemView.findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, mainViewHolder.getAdapterPosition()+1);
            }
        });
        return mainViewHolder;
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView createdAtTextView;
        TextView publisherTextView;
        LinearLayout container;
        TextView tagtextView;
        TextView datetextView;

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
            container.removeAllViews();
            titleTextView.setText(item.getTitle());


            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ArrayList<String> contentsList = item.getContents();
            ArrayList<String> tagsList = item.getTags();

            Log.d("test100", item.getTitle() + " : " + contentsList.get(0));



            for (int i = 0; i < contentsList.size(); i++) {
                /*if (i == 3) {
                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(layoutParams);
                    textView.setText("자세히 보기");
                    container.addView(textView);
                }*/
                String contents = contentsList.get(i);
                TextView textView = (TextView)inflater.inflate(R.layout.view_contents_text, container, false);
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH시 mm분");
            datetextView.setText(simpleDateFormat.format(date));
            publisherTextView.setText(item.getPublisher());
        }

    }

    @Override
    public void onBindViewHolder(@NonNull CompleteAdapter.MainViewHolder holder, int position) {
        Insus item = items.get(position);
        holder.setItem(item);
    }
    private void showPopup(final View v, final int position) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.restore:
                        restoreItem(v,position);
                        return true;
                    case R.id.modify2:
                        return true;
                    case R.id.delete2:
                        deleteItem(v,position);
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_complete, popup.getMenu());
        popup.show();
    }



    @Override
    public int getItemCount() {
        return items.size();
    }
    public void restoreItem (View v, int position){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Insus item = items.get(position-1);
        Date temp_date = item.getCreatedAt();
        SimpleDateFormat colTitle = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat docTitle = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        item.setIscompleted(false);
        db.collection("Insus").document(colTitle.format(temp_date)).collection("time").document(docTitle.format(temp_date)).set(item);

        items.remove(item);
        notifyDataSetChanged();
        Snackbar.make(v, "오늘 할일로 이동되었습니다.",Snackbar.LENGTH_SHORT).show();
    }
    public void deleteItem (View v, int position){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Insus item = items.get(position-1);
        Date temp_date = item.getCreatedAt();
        SimpleDateFormat colTitle = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat docTitle = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        db.collection("Insus").document(colTitle.format(temp_date)).collection("time").document(docTitle.format(temp_date)).delete();

        items.remove(item);
        notifyDataSetChanged();
        Snackbar.make(v, "삭제되었습니다.",Snackbar.LENGTH_SHORT).show();
    }

    public String timeConverter(Date date){
        Date nowTime = new Date();
        Date updatedTime = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY년 MM월 dd일");

        long diff;

        //현재시간과 넘어온 시간을 정수로 변환해 차이를 구한다
        // 밀리세컨드 단위로 변환되기때문에 1000으로 나눠서 계산
        diff = (nowTime.getTime() - updatedTime.getTime())/1000;
        //
        if (diff < 60)
            return diff + "초 전";
        else if (diff < 60*60)
            return diff/60 + "분 전";
        else if (diff < 60*60*24)
            return diff/(60*60) +"시간 전";
        else
            return dateFormat.format(date);
    }

}
