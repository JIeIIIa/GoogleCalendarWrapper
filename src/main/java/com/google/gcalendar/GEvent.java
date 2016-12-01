package com.google.gcalendar;

import java.util.Date;

public class GEvent {
    private String Title;
    private Date start;
    private Date finish;

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
}

