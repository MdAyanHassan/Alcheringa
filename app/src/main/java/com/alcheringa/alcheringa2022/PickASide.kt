package com.alcheringa.alcheringa2022

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.security.AccessController.getContext


class PickASide : AppCompatActivity() {

    private val TAG = PickASide::class.java.simpleName

    lateinit var blackSideText: LinearLayout
    lateinit var whiteSideText: LinearLayout
    lateinit var pickASide1: TextView
    lateinit var pickASide2: TextView
    lateinit var furiousMorgok: ImageView
    lateinit var alcherLady: ImageView
    lateinit var bg: ImageView
    lateinit var parentLayout: ConstraintLayout
    lateinit var continueButton: Button
    lateinit var pureBlackBG: ImageView
    var status = "none"
    var animationDuration:Long = 300
    var m = 1
    var clickable = true

    var sharedPreferences: SharedPreferences? = null

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_a_side)

//        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE)
//
//        firebaseAuth = FirebaseAuth.getInstance()
//        firebaseFirestore = FirebaseFirestore.getInstance()

        blackSideText = findViewById(R.id.black_side_text)
        whiteSideText = findViewById(R.id.white_side_text)
        pickASide1 = findViewById(R.id.text_pick_a)
        pickASide2 = findViewById(R.id.text_side)
        furiousMorgok = findViewById(R.id.furious_morgok)
        alcherLady = findViewById(R.id.alcher_lady)
        parentLayout= findViewById(R.id.parent_layout)
        bg = findViewById(R.id.pick_side_bg)
        continueButton = findViewById(R.id.continue_button)
        pureBlackBG = findViewById(R.id.pure_black_bg)

        furiousMorgok.setOnClickListener {click("black")}
        blackSideText.setOnClickListener {click("black")}

        alcherLady.setOnClickListener{click("white")}
        whiteSideText.setOnClickListener{click("white")}

        continueButton.setOnClickListener{ submit() }
    }

    private fun submit() {
        if(!clickable) return
//        uploadToFirebase()
//        val editor = sharedPreferences!!.edit()
//        editor.putString("mode", status)
//        editor.apply()
        when (status) {
            "white" -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
            "black" -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        }
        Toast.makeText(applicationContext, if (status=="black")"Welcome to the dark side!" else "The Alcher Lady welcomes you!", Toast.LENGTH_SHORT).show()

        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

//    private fun uploadToFirebase() {
//        //val data = hashMapOf("mode" to status)
//
//        val user = firebaseAuth.currentUser!!
//        val email = user.email!!
//        val doc = firebaseFirestore.collection("USERS").document(email)
//        doc
//            .update("mode",status)
//            //.set(data, SetOptions.merge())
//            .addOnSuccessListener {
//                Toast.makeText(this, if (status=="black")"Welcome to the dark side!" else "The Alcher Lady welcomes you!", Toast.LENGTH_SHORT).show()
//                Log.d(TAG, "Added side successfully to Firebase")
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Failed to add your choice to database", Toast.LENGTH_SHORT).show()
//                Log.d(TAG, "Failed to add side to Firebase")
//            }
//
//
//    }

    private fun click(color: String){
        if(!clickable || color==status) return
        clickable = false
        if(status=="none") continueButton.visibility = View.VISIBLE
        m = if(color=="black") 1 else -1

        val moveBG = ObjectAnimator.ofFloat(bg, "translationX", -150f*m).apply {
            duration = animationDuration
        }
        val moveMorgok = ObjectAnimator.ofFloat(furiousMorgok, "translationX", -250f*m).apply {
            duration = animationDuration
        }
        val moveLadyX = ObjectAnimator.ofFloat(alcherLady, "translationX", -290f*m).apply {
            duration = animationDuration
        }
        val moveLadyY = ObjectAnimator.ofFloat(alcherLady, "translationY", 150f*m).apply {
            duration = animationDuration
        }
        val scaleLadyX = ObjectAnimator.ofFloat(alcherLady, "scaleX", if(color=="white") 1.15f else 1f).apply {
            duration = animationDuration
        }
        val scaleLadyY = ObjectAnimator.ofFloat(alcherLady, "scaleY", if(color=="white") 1.15f else 1f).apply {
            duration = animationDuration
        }
        val scaleMorgokX = ObjectAnimator.ofFloat(furiousMorgok, "scaleX", if(color=="black") 1.3f else 1f).apply {
            duration = animationDuration
        }
        val scaleMorgokY = ObjectAnimator.ofFloat(furiousMorgok, "scaleY", if(color=="black") 1.3f else 1f).apply {
            duration = animationDuration
        }
        val moveTextUp1 = ObjectAnimator.ofFloat(blackSideText, "translationY", -150f*m).apply {
            duration = animationDuration
        }
        val moveTextUp2 = ObjectAnimator.ofFloat(whiteSideText, "translationY", 150f*m).apply {
            duration = animationDuration
        }
        val rotateBG = ObjectAnimator.ofFloat(bg, "rotation", -2f*m).apply {
            duration = animationDuration
        }
        val scaleBG = ObjectAnimator.ofFloat(bg, "scaleY", 1.2f).apply{
            duration = animationDuration
        }

        if(color=="black"){
            whiteSideText.visibility = View.INVISIBLE
            blackSideText.visibility = View.VISIBLE
        }else{
            whiteSideText.visibility = View.VISIBLE
            blackSideText.visibility = View.INVISIBLE
        }

        AnimatorSet().apply{
            play(moveBG).with(moveMorgok)
            play(moveBG).with(moveLadyX)
            play(moveBG).with(moveLadyY)
            play(moveBG).with(moveTextUp1)
            play(moveBG).with(moveTextUp2)
            play(moveBG).with(scaleLadyX)
            play(moveBG).with(scaleLadyY)
            play(moveBG).with(rotateBG)
            play(moveBG).with(scaleBG)
            play(moveBG).with(scaleMorgokX)
            play(moveBG).with(scaleMorgokY)
            start()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {

                    if(color=="black"){
                        pickASide1.text = "Pick"
                        pickASide2.text = " a side"
                    }else{
                        pickASide1.text = "Pick a side"
                        pickASide2.text = ""
                    }

                    status = color
                    clickable = true

                }
            })
        }


    }

}