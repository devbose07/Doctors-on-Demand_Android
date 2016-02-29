package com.globussoft.readydoctors.patient.model;

import java.util.Date;

/**
 * Created by GLB-276 on 12/24/2015.
 */
public class AppointmentListModel
{
    public String time;
    public String timelimit;
    public Date date;
    public boolean isHeader;
    public String mBelongsTo;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimelimit() {
        return timelimit;
    }

    public void setTimelimit(String timelimit) {
        this.timelimit = timelimit;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setIsHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    public String getmBelongsTo() {
        return mBelongsTo;
    }

    public void setmBelongsTo(String mBelongsTo) {
        this.mBelongsTo = mBelongsTo;
    }
}
