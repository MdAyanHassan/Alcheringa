package com.example.alcheringa2022.Model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alcheringa2022.eventWithLive
import com.example.alcheringa2022.eventdetail
import com.google.firebase.abt.FirebaseABTesting
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class viewModelHome: ViewModel() {
    val fb = FirebaseFirestore.getInstance()
    val allEventsWithLive= mutableStateListOf<eventWithLive>()
    val OwnEventsWithLive= mutableStateListOf<eventWithLive>()
    val merchhome= mutableStateListOf<merchmodelforHome>()

//    fun pushEvents(evnts:List<eventdetail>){
//        for(evnt in evnts){
//        fb.collection("AllEvents").document(evnt.artist).set(evnt).addOnSuccessListener {
//            Log.d("pushevents","process succeed")
//        }.addOnFailureListener{
//            Log.d("pushevents","process failed")
//        }
//        }
//    }
    fun getAllEvents(){
        fb.collection("AllEvents").get().addOnSuccessListener {
            evnts->
            val list=mutableListOf<eventdetail>()
            list.clear()
            allEventsWithLive.clear()
            for (evnt in evnts){ list.add(evnt.toObject(eventdetail::class.java))}
            Log.d("eventlist", list.toString())
            list.forEach{data->allEventsWithLive.add(eventWithLive(data))}
            checklive()
        }

    }

    fun checklive(){
        viewModelScope.launch {
            while (true){
                Log.d("livecheck","started")
               val c= Calendar.getInstance()
                val dt= allEventsWithLive.iterator()
                while (dt.hasNext()){
                    val data=  dt.next()

                    if( (c.get(Calendar.YEAR)>2022) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)> Calendar.FEBRUARY)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.FEBRUARY) and
                                (c.get(Calendar.DATE)> data.eventdetail.starttime.date)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.FEBRUARY) and
                                (c.get(Calendar.DATE)== data.eventdetail.starttime.date)and
                                ( ((data.eventdetail.starttime.hours*60 + data.eventdetail.durationInMin))
                                        <((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) ))

                    ){ dt.remove()}
                }

                for( data in allEventsWithLive){

                    data.isLive.value = (c.get(Calendar.YEAR)==2022) and
                            (c.get(Calendar.MONTH)== Calendar.FEBRUARY) and
                            (c.get(Calendar.DATE)== data.eventdetail.starttime.date)and
                            ( ((data.eventdetail.starttime.hours*60)..(data.eventdetail.starttime.hours*60+ data.eventdetail.durationInMin))
                                .contains((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) )}




                delay(10000)
                Log.d("livecheck","done")


            }
        }
    }

    fun getMerchHome(){

        viewModelScope.launch {
            fb.collection("Merch").get().addOnSuccessListener {
            merch->

            allEventsWithLive.clear()
            for (mer in merch){ merchhome.add(mer.toObject(merchmodelforHome::class.java))}
                Log.d("merch","fetched")
            }.addOnFailureListener{Log.d("merch","failed")}


        }
    }









}