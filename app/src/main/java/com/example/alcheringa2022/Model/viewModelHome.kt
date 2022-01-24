package com.example.alcheringa2022.Model

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

    fun getAllEvents(){

        fb.collection("")
    }





}