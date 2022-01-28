package com.example.alcheringa2022.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.alcheringa2022.Model.cartModel;
import com.example.alcheringa2022.OwnTime;
import com.example.alcheringa2022.eventWithLive;
import com.example.alcheringa2022.eventdetail;

import java.util.ArrayList;

public class ScheduleDatabase  extends SQLiteOpenHelper {
    private static final String DB_NAME = "Alcheringa";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "Schedule";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String ARTIST_COL = "artist";

    // below variable id for our course duration column.
    private static final String CATEGORY_COL = "category";

    // below variable for our course description column.
    private static final String MODE_COL = "mode";

    // below variable is for our course tracks column.
    private static final String IMAGE_COL = "size";

    private static final String DURATION_COL = "duration";

    private static final String DATE_COL = "date";
    private static final String HOUR_COL = "hour";

    private static final String MIN_COL = "min";



    public ScheduleDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ARTIST_COL + " TEXT,"
                + CATEGORY_COL + " TEXT,"
                + MODE_COL + " TEXT,"
                + IMAGE_COL+  " TEXT,"
                + DATE_COL+  " TEXT,"
                + HOUR_COL+  " TEXT,"
                + MIN_COL+  " TEXT,"
                + DURATION_COL +  " TEXT)" ;

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }
    public void addEventsInSchedule(eventdetail eventdetail,Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ARTIST_COL, eventdetail.getArtist());
        values.put(CATEGORY_COL, eventdetail.getCategory());
        values.put(MODE_COL, eventdetail.getMode());
        values.put(IMAGE_COL, eventdetail.getImgurl());
        values.put(DURATION_COL, eventdetail.getDurationInMin());
        values.put(DATE_COL, eventdetail.getStarttime().component1());

        values.put(HOUR_COL, eventdetail.getStarttime().component2());

        values.put(MIN_COL, eventdetail.getStarttime().component3());
        //String query="SELECT * FROM "+ TABLE_NAME +" WHERE name = '"+name+"' "+"AND "+"size = '"+size+"'";
        db.insert(TABLE_NAME,null,values);
        Toast.makeText(context, "event added to database" , Toast.LENGTH_SHORT).show();
    }
    public ArrayList<eventdetail> getSchedule(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorcart = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<eventdetail> courseModalArrayList = new ArrayList<>();
        if (cursorcart.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new eventdetail(cursorcart.getString(1),
                        cursorcart.getString(2),
                        new OwnTime(Integer.parseInt(cursorcart.getString(5)),
                                Integer.parseInt(cursorcart.getString(6)),
                        Integer.parseInt(cursorcart.getString(7))),
                        cursorcart.getString(3),
                        cursorcart.getString(4),
                        Integer.parseInt(cursorcart.getString(8))));
            } while (cursorcart.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorcart.close();
        return courseModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
