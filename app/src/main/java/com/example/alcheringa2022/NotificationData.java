package com.example.alcheringa2022;

public class NotificationData {

    String heading,subheading,time;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NotificationData(String heading, String subheading, String time) {
        this.heading = heading;
        this.subheading = subheading;
        this.time = time;

    }
}
