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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuingae.Insus;
import com.example.insuingae.R;
import com.example.insuingae.activity.InsuActivity;
import com.example.insuingae.activity.MainActivity;
import com.example.insuingae.fragment.ToDoFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class    TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MainViewHolder> {
    private ArrayList<Insus> items = new ArrayList<Insus>();
    private Activity activity;
    private LayoutInflater inflater;
    private Button completeButton;




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
        Log.d("test", ""+mainViewHolder.getAdapterPosition());
        itemView.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, mainViewHolder.getAdapterPosition()+1);
                Log.d("test", ""+mainViewHolder.getAdapterPosition());
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
        RelativeLayout loaderlayout;
        CardView cardView;

        MainViewHolder(View v) {
            super(v);

            titleTextView = v.findViewById(R.id.titleEditText);
            createdAtTextView = v.findViewById(R.id.dateTextView);
            container = v.findViewById(R.id.container);
            tagtextView = v.findViewById(R.id.tagTextView);
            datetextView = v.findViewById(R.id.dateTextView);
            publisherTextView = v.findViewById(R.id.publisherTextView);
            completeButton = v.findViewById(R.id.completeButton);
            loaderlayout = v.findViewById(R.id.loaderLayout);
            cardView = v.findViewById(R.id.cardView);
        }

        public void setItem(Insus item) {
            titleTextView.setText(item.getTitle());
            createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(item.getCreatedAt()));
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ArrayList<String> contentsList = item.getContents();
            ArrayList<String> tagsList = item.getTags();

            Log.d("test100", item.getTitle() + " : " + contentsList.get(0));

            container.removeAllViews();

            for (int i = 0; i < contentsList.size(); i++) {
                /*if (i == 3) {
                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(layoutParams);
                    textView.setText("자세히 보기");
                    container.addView(textView);
                }*/
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
            createdAtTextView.setText(timeConverter(date));
            publisherTextView.setText(item.getPublisher());
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final TodoAdapter.MainViewHolder holder, final int position) {
        Insus item = items.get(position);
        holder.setItem(item);
        Log.d("test", position + "");
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                activity.findViewById(R.id.loaderLayout).setVisibility(View.VISIBLE);
                //버튼을 누르면 파이어베이스 상 해당 문서 iscompleted 가 true로 바뀜
                final Insus temp_insu = items.get(position);
                Date temp_date = temp_insu.getCreatedAt();
                Date now = new Date();
                temp_insu.setIscompleted(true);
                temp_insu.setCompletedAt(now);
                SimpleDateFormat colTitle =  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat docTitle = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("Insus").document(colTitle.format(temp_date)).collection("time").document(docTitle.format(temp_date)).set(temp_insu)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("test", "complete_success");


                                items.remove(temp_insu);
                                notifyDataSetChanged();
                                Snackbar.make(v, "오늘 한일로 이동되었습니다.",Snackbar.LENGTH_SHORT).show();

                                activity.findViewById(R.id.loaderLayout).setVisibility(View.INVISIBLE);
                            }
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Insus item) {
        items.add(item);
    }

    private void showPopup(final View v, final int position) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.modify:

                        return true;
                    case R.id.delete:
                        deleteItem(v,position);
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
    public void deleteItem (View v,int position){
        Insus item = items.get(position-1);
        Date temp_date = item.getCreatedAt();
        SimpleDateFormat colTitle = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat docTitle = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Insus").document(colTitle.format(temp_date)).collection("time").document(docTitle.format(temp_date)).delete();

        items.remove(item);
        notifyDataSetChanged();

        Snackbar.make(v, "삭제되었습니다.",Snackbar.LENGTH_SHORT).show();

        activity.findViewById(R.id.loaderLayout).setVisibility(View.INVISIBLE);
    }
    //시각을 전달받아 현재 시간과 차이를 구하는 메서드
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


