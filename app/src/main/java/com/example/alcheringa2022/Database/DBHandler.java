package com.example.alcheringa2022.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alcheringa2022.Model.cartModel;

import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "CART_DATABASE";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "CART";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String COUNT_COL = "count";

    // below variable for our course description column.
    private static final String PRICE_COL = "price";

    // below variable is for our course tracks column.
    private static final String SIZE_COL = "size";

    private static final String IMAGE_COL = "image";

    private static final String TYPE_COL = "type";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + COUNT_COL + " TEXT,"
                + PRICE_COL + " TEXT,"
                + SIZE_COL +  " TEXT,"
                + IMAGE_COL+  " TEXT,"
                + TYPE_COL +  " TEXT)" ;

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
        Log.d("logging SQLTable", "on create finally runned");

    }
    public ArrayList<cartModel> readCourses() {

        ArrayList<cartModel> courseModalArrayList = new ArrayList<>();

        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCheck = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + TABLE_NAME + "'", null);

        int tableCount = cursorCheck.getCount();
        cursorCheck.close();

        if(tableCount == 0){
            onCreate(db);
        }
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorcart = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // moving our cursor to first position.
        if (cursorcart.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new cartModel(cursorcart.getString(1),
                        cursorcart.getString(6),
                        cursorcart.getString(4),
                        cursorcart.getString(3),cursorcart.getString(5),cursorcart.getString(2),
                        "https://i.picsum.photos/id/355/200/300.jpg?hmac=CjmRk_yPeMJV6teNYBA4ceaviVpxIl8XM9NL7GQzLMU"));
            } while (cursorcart.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorcart.close();
        return courseModalArrayList;
    }
    public void addNewitemIncart(String name,String price,String size,String count,String Image_url,String Type,Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(SIZE_COL, size);
        values.put(PRICE_COL, price);
        values.put(TYPE_COL, Type);
        values.put(IMAGE_COL, Image_url);
        String query="SELECT * FROM "+ TABLE_NAME +" WHERE name = '"+name+"' "+"AND "+"size = '"+size+"'";
        if(getData(query).getCount()>0){
            Cursor cursor= getData(query);
            cursor.moveToFirst();
          //  Toast.makeText(context, "inside this", Toast.LENGTH_SHORT).show();
            String count1=Long.parseLong(cursor.getString(2))+1+"";
            values.put(COUNT_COL, count1);
            Log.d("vipin",count1+"");
            db.update(TABLE_NAME,values," name=? and size=? ",new String[]{name,size});
        }
        else{
            //Toast.makeText(context, "new element this "+values.toString(), Toast.LENGTH_SHORT).show();
            values.put(COUNT_COL, count);
            db.insert(TABLE_NAME,null,values);
        }
        db.close();
    }


    public void RemoveFromCart(String name,String price,String size,String count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(SIZE_COL, size);
        values.put(PRICE_COL, price);
        String query="SELECT * FROM "+ TABLE_NAME +" WHERE name = '"+name+"' "+"AND "+"size = '"+size+"'";
        if(getData(query).getCount()>0){
            Cursor cursor= getData(query);
            cursor.moveToFirst();
            if(Long.parseLong(cursor.getString(2))>0){
                String count1=Long.parseLong(cursor.getString(2))-1+"";
                values.put(COUNT_COL, count1);
                Log.d("vipin",count1+"");
                db.update(TABLE_NAME,values," name=? and size=? ",new String[]{name,size});
            }
        }
        else{
            values.put(COUNT_COL, count);

            db.insert(TABLE_NAME,null,values);
        }
        db.close();
    }
    public void DeleteItem(String name,String size) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "name=? AND size=?", new String[]{name,size});
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public  void Delete_all(){
        SQLiteDatabase database = getReadableDatabase();
        database.execSQL("delete from "+ TABLE_NAME);
    }

}
