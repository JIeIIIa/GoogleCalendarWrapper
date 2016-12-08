package com.google.gcalendar;

import java.util.Calendar;
import java.util.Date;

public class GEventChecker implements GEventCheckable {
    private Date startBeforeDate;
    private Date startAfterDate;
    private Date finishBeforeDate;
    private Date finishAfterDate;
    private String title;

    public GEventChecker setStartBeforeDate(Date date) {
        startBeforeDate = date;
        return this;
    }

    public GEventChecker setStartAfterDate(Date date) {
        startAfterDate = date;
        return this;
    }

    public GEventChecker setFinishBeforeDate(Date date) {
        finishBeforeDate = date;
        return this;
    }
    public GEventChecker setFinishAfterDate(Date date) {
        finishAfterDate = date;
        return this;
    }

    public GEventChecker setTitle(String title) {
        this.title = title;
        return this;
    }

    public Date getStartBeforeDate() {
        return startBeforeDate;
    }

    public Date getStartAfterDate() {
        return startAfterDate;
    }

    public Date getFinishBeforeDate() {
        return finishBeforeDate;
    }

    public Date getFinishAfterDate() {
        return finishAfterDate;
    }

    public String getTitle() {
        return title;
    }

    private boolean compareDate(Date first, Date second) {
        Calendar cFirst = Calendar.getInstance();
        cFirst.setTimeInMillis(first.getTime());
        cFirst.set(Calendar.MILLISECOND, 0);
        Calendar cSecond = Calendar.getInstance();
        cSecond.setTimeInMillis(first.getTime());
        cSecond.set(Calendar.MILLISECOND, 0);

        return cFirst.equals(cSecond);
    }

    public boolean check(GEvent event) {
        boolean res = true;
        if (startAfterDate != null) {
            boolean tmp = startAfterDate.before(event.getStart()) ;
            tmp |= compareDate(startAfterDate,event.getStart());
            res = tmp;
        }
        if (startBeforeDate != null) {
            res &= startBeforeDate.after(event.getStart()) || compareDate(startBeforeDate,event.getStart());
        }
        if (finishAfterDate != null) {
            res &= finishAfterDate.before(event.getFinish()) || compareDate(finishAfterDate,event.getFinish());
        }
        if (finishBeforeDate != null) {
            res &= finishBeforeDate.after(event.getFinish()) || compareDate(finishBeforeDate,event.getFinish());
        }

        if (title != null && event.getTitle() != null) {
            res &= event.getTitle().contains(title);
        }

        return res;
    }
}
