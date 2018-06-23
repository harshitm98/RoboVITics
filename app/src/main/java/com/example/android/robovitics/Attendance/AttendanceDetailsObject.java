package com.example.android.robovitics.Attendance;

public class AttendanceDetailsObject {

    private String attended;
    private String notAttended;
    private String date;
    private String time;
    private String reason;
    private String type;
    private String attendees;
    private String absentees;


    public String getAttendees() {
        return attendees;
    }

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    public String getAbsentees() {
        return absentees;
    }

    public void setAbsentees(String absentees) {
        this.absentees = absentees;
    }


    public String getAttended() {
        return attended;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public String getNotAttended() {
        return notAttended;
    }

    public void setNotAttended(String notAttended) {
        this.notAttended = notAttended;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
