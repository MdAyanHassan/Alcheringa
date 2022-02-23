package com.alcheringa.alcheringa2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SizeChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_size_chart_hoodies)

        val type: String = intent.extras!!.getString("type","")
        if(type=="Hoodie"){
            setContentView(R.layout.activity_size_chart_hoodies)
        }else{
            setContentView(R.layout.activity_size_chart_tshirts)
        }

        findViewById<ImageButton>(R.id.backbtn).setOnClickListener{finish()}
    }
}