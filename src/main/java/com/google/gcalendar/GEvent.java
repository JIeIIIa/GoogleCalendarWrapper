package com.google.gcalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GEvent {
    private String id;
    private String Title;
    private Date start;
    private Date finish;
    private String description;
    private List<String> recurrenceList;
    private String colorId;

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

    public GRecurrence getReccurence() {
        GRecurrence result = null;
        try {
            if (recurrenceList != null) {
                result = GRecurrenceUtils.parseRecurrence(recurrenceList);
            }
        } catch (GCalendarException e) {
            /*NOP*/
        }
        return result;
    }

    public void setReccurence(GRecurrence recurrence) throws GCalendarException {
        this.recurrenceList = null;
        this.recurrenceList = GRecurrenceUtils.toList(recurrence);
    }

    public List<String> getRecurrenceList() {
        List<String> result = null;
        if (recurrenceList != null && recurrenceList.size() > 0) {
            result = new ArrayList<>();
            result.add(recurrenceList.get(recurrenceList.size() - 1));

        }
        return result;
    }

    @Override
    public String toString() {
        return "GEvent{" +
                "id='" + id + '\'' +
                ", Title='" + Title + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                '}';
    }
}

