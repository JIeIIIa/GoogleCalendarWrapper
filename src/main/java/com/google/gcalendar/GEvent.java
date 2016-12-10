package com.google.gcalendar;

import java.util.Date;

public class GEvent {
    private String Title;
    private Date start;
    private Date finish;
    private String id;

    public GEvent(String title, Date start, Date finish) {
        Title = title;
        this.start = start;
        this.finish = finish;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public String getId() {
        return id;
    }

    public GEvent setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "GEvent{" +
                ", id='" + id + '\'' +
                "Title='" + Title + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                '}';
    }
}

