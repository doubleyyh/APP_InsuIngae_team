package com.example.insuingae;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

public class Insus {
    private String title;
    private String publisher;
    private ArrayList<String> contents;
    ArrayList<Date> contentsAt;
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
}
