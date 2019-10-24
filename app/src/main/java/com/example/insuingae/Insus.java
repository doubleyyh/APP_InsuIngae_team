package com.example.insuingae;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

public class Insus implements Parcelable{
    private String title;
    private String publisher;
    private ArrayList<String> contents;
    private ArrayList<Date> contentsAt;
    private Date createdAt;
    private Date completedAt;
    private boolean iscompleted;
    private ArrayList<String> tags;

    public Insus(String title, String publisher, ArrayList<String> contents, ArrayList<Date> contentsAt, ArrayList<String> tags, Date createdAt) {
        this.title = title;
        this.publisher = publisher;
        this.contents = contents;
        this.contentsAt = contentsAt;
        this.createdAt = createdAt;
        this.tags = tags;
        this.iscompleted = false;
        this.completedAt = null;
    }
    public Insus(String title, String publisher, ArrayList<String> contents, ArrayList<Date> contentsAt, Date createdAt, ArrayList<String> tags) {
        this.title = title;
        this.publisher = publisher;

        this.contents = contents;
        this.contentsAt = contentsAt;
        this.createdAt = createdAt;
        this.completedAt = null;
        this.iscompleted = false;
        this.tags = tags;
    }

    public Insus(String title, String publisher, ArrayList<String> contents, ArrayList<Date> contentsAt, Date createdAt, Date completedAt, boolean iscompleted, ArrayList<String> tags) {
        this.title = title;
        this.publisher = publisher;

        this.contents = contents;
        this.contentsAt = contentsAt;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.iscompleted = iscompleted;
        this.tags = tags;
    }

    public Insus(Parcel source){
        try {
            this.title =source.readString();
            this.publisher = source.readString();
            this.contents = source.readArrayList(Insus.class.getClassLoader());
            this.contentsAt = source.readArrayList(Insus.class.getClassLoader());
            this.createdAt = new Date(source.readLong());
            this.completedAt = new Date(source.readLong());
            this.iscompleted = source.readByte() != 0;
            this.tags = source.readArrayList(Insus.class.getClassLoader());

        }catch (Exception e){}



    }
    public static Parcelable.Creator<Insus> CREATOR = new Parcelable.Creator<Insus>() {

        @Override
        public Insus createFromParcel(Parcel source) {
            return new Insus(source);
        }

        @Override
        public Insus[] newArray(int size) {
            return new Insus[size];
        }

    };


    public Map<String, Object> getInsus(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("title",title);
        docData.put("contents",contents);
        docData.put("publisher",publisher);
        docData.put("createdAt",createdAt);
        docData.put("contentsAt", contentsAt);
        docData.put("iscompleted", iscompleted);
        docData.put("tags", tags);
        docData.put("completedAt", completedAt);
        return  docData;
    }

    public ArrayList<Date> getContentsAt() {
        return contentsAt;
    }

    public void setContentsAt(ArrayList<Date> contentsAt) {
        this.contentsAt = contentsAt;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getContents() {
        return contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIscompleted() {
        return iscompleted;
    }

    public void setIscompleted(boolean iscompleted) {
        this.iscompleted = iscompleted;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try{dest.writeString(title);
            dest.writeString(publisher);
            dest.writeList(contents);
            dest.writeList(contentsAt);
            dest.writeLong(createdAt.getTime());
            dest.writeLong(completedAt.getTime());

            dest.writeByte((byte)(iscompleted ? 1 : 0));
            dest.writeList(tags);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
