package com.alcheringa.alcheringa2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


class PickASide : AppCompatActivity() {

    lateinit var blackSideText: LinearLayout
    lateinit var whiteSideText: LinearLayout
    lateinit var pickASide1: TextView
    lateinit var pickASide2: TextView
    lateinit var furiousMorgok: ImageView
    lateinit var alcherLady: ImageView
    lateinit var bg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_a_side)

        blackSideText = findViewById(R.id.black_side_text)
        whiteSideText = findViewById(R.id.white_side_text)
        pickASide1 = findViewById(R.id.text_pick_a)
        pickASide2 = findViewById(R.id.text_side)
        furiousMorgok = findViewById(R.id.furious_morgok)
        alcherLady = findViewById(R.id.alcher_lady)
        bg = findViewById(R.id.pick_side_bg)


    }

}