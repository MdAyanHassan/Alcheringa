package com.example.alcheringa2022.Model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.alcheringa2022.eventdetail
import com.google.firebase.abt.FirebaseABTesting
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow

class viewModelHome: ViewModel() {
    val fb = FirebaseFirestore.getInstance()
    val topEvents= mutableStateListOf<eventdetail>()
    val allEvents= mutableStateListOf<eventdetail>()
    val ownEvents= mutableStateListOf<eventdetail>()

    fun pushEvents(evnts:List<eventdetail>){
        for(evnt in evnts){
        fb.collection("AllEvents").document(evnt.artist).set(evnt).addOnSuccessListener {
            Log.d("pushevents","process succeed")
        }.addOnFailureListener{
            Log.d("pushevents","process failed")
        }
        }
    }
    fun getAllEvents(){
        fb.collection("AllEvents").get()

    }





}