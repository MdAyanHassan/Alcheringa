package com.alcheringa.alcheringa2022;

import java.util.Date;

public class NotificationData {

    String heading,subheading;
    Date date;

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

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date;}

    public NotificationData(String heading, String subheading, Date date) {
        this.heading = heading;
        this.subheading = subheading;
        this.date = date;

    }
}
