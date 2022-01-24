package com.example.alcheringa2022;

import android.content.Context;

import com.example.alcheringa2022.Model.cartModel;

import java.util.ArrayList;

public class Utility{

    public static boolean functionName(){
        //....
        return true;
    }
    public static int calculateCartQuantity(Context context){
        int count = 0;
        DBHandler dBHandler;
        ArrayList<cartModel> cartModelArrayList1;
        dBHandler=new DBHandler(context);
        cartModelArrayList1 =dBHandler.readCourses();

        if(cartModelArrayList1.size() != 0){
            for(int i = 0; i< cartModelArrayList1.size(); i++){
                count += Integer.parseInt(cartModelArrayList1.get(i).getCount());
            }
        }
        return count;
    }
}