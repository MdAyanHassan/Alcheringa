package com.alcheringa.alcheringa2022.Model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.Model.eventdetail
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.add(item)
    this.value = oldValue
}
fun <T> MutableLiveData<MutableList<T>>.removeAnItem(item: T){
    val oldValue = this.value ?: mutableListOf()
    oldValue.remove(item)
    this.value = oldValue
}

class viewModelHome: ViewModel() {
    val fb = FirebaseFirestore.getInstance()
    val allEventsWithLivedata= MutableLiveData<MutableList<eventWithLive>>()
    val allEventsWithLive= mutableStateListOf<eventWithLive>()
    val featuredEventsWithLivedata= MutableLiveData<MutableList<eventWithLive>>()
    val featuredEventsWithLivestate= mutableStateListOf<eventWithLive>()
    val OwnEventsWithLive= MutableLiveData<MutableList<eventdetail>>()
    val OwnEventsLiveState= mutableStateListOf<eventdetail>()
    val upcomingEventsLiveState= mutableStateListOf<eventWithLive>()
    val merchhome= mutableStateListOf<merchmodelforHome>()
    var crnttime= mutableStateOf(OwnTime())









    fun pushEvents(evnts:List<eventdetail>){
        for(evnt in evnts){
        fb.collection("Featured_Events").document(evnt.artist).set(evnt).addOnSuccessListener {
            Log.d("pushevents","process succeed")
        }.addOnFailureListener{
            Log.d("pushevents","process failed")
        }
        }
    }


  fun getAllEvents(){
    viewModelScope.launch {
        fb.collection("AllEvents").get().addOnSuccessListener {
            evnts->
            val list=mutableListOf<eventWithLive>()
            list.clear()
            for (evnt in evnts){ list.add(eventWithLive(evnt.toObject(eventdetail::class.java)))}
            Log.d("eventlist", list.toString())
            allEventsWithLivedata.postValue(list)
            Log.d("getevents","eventsfetched")
            checklive()
        }
    }

    }

    fun checklive(){
        viewModelScope.launch {

            while (true){
                Log.d("livecheck","started")
               val c= Calendar.getInstance()
                var dt=0
                if (c.get(Calendar.MONTH)== Calendar.FEBRUARY){dt=c.get(Calendar.DATE)-28}else{dt=c.get(Calendar.DATE)}
                crnttime.value= OwnTime(date =dt ,c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE))
                delay(500)


                for( data in allEventsWithLive){

                    data.isLive.value = (c.get(Calendar.YEAR)==2022) and
                            (c.get(Calendar.MONTH)== Calendar.MARCH) and
                            (c.get(Calendar.DATE)== data.eventdetail.starttime.date)and
                            ( ((data.eventdetail.starttime.hours*60)..(data.eventdetail.starttime.hours*60+ data.eventdetail.durationInMin))
                                .contains((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) )}


                for(data in allEventsWithLive){

                    if( (c.get(Calendar.YEAR)>2022) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)> Calendar.MARCH)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
                                (c.get(Calendar.DATE)> data.eventdetail.starttime.date)) or
                        ((c.get(Calendar.YEAR)==2022) and
                                (c.get(Calendar.MONTH)== Calendar.MARCH) and
                                (c.get(Calendar.DATE)== data.eventdetail.starttime.date)and
                                ( ((data.eventdetail.starttime.hours*60 + data.eventdetail.durationInMin))
                                        <((c.get(Calendar.HOUR_OF_DAY)*60) + c.get(Calendar.MINUTE)) ))

                    ){ upcomingEventsLiveState.remove(data)}
                }




                delay(9000)
                Log.d("livecheck","done")


            }
        }
    }

    fun getMerchHome(){

        viewModelScope.launch {
            fb.collection("Merch").get().addOnSuccessListener {
            merch->

            merchhome.clear()
            for (mer in merch)
            { merchhome.add(mer.toObject(merchmodelforHome::class.java))
                //to be removed later



            }
                Log.d("merch","fetched")
            }.addOnFailureListener{Log.d("merch","failed")}

        }
    }

    fun getfeaturedEvents(){
        viewModelScope.launch {
            fb.collection("Featured_Events").get().addOnSuccessListener {
                    evnts->
                val list=mutableListOf<eventWithLive>()
                list.clear()
                for (evnt in evnts){ list.add(eventWithLive(evnt.toObject(eventdetail::class.java)))}
                Log.d("eventlist", list.toString())
                featuredEventsWithLivedata.postValue(list)
                Log.d("getevents","eventsfetched")
            }
        }

    }


    fun fetchlocaldbandupdateownevent(scheduleDatabase: ScheduleDatabase){
        viewModelScope.launch {
            val eventdlist = scheduleDatabase.getSchedule();

            OwnEventsWithLive.postValue(eventdlist)
        }

    }


    fun converttomin(ownTime: OwnTime): Int {
     return(( ownTime.date*24*60) + (ownTime.hours*60)+ ownTime.min)
    }

    fun converttoowntime(min:Int): OwnTime {
        return OwnTime((min/1440), ((min%1440))/60, min%60 )
    }



    val competitons= listOf(
        eventdetail("Electric Heels ","Competitions", imgurl = "https://drive.google.com/file/d/1CNYLSCGfHRHFi-pAYGa55aySuHAYb0Jz/view?usp=sharing", descriptionEvent = "Light our virtual stage on fire with unique group choreographies and flawless coordination in this freestyle group dance competition.", genre = listOf("Dance")),
        eventdetail("SUTUCD ","Competitions", imgurl = "https://drive.google.com/file/d/194wL7dZehfY5nCPhpjfqSy_LEygToMKz/view?usp=sharing", descriptionEvent = "The perfect opportunity to flaunt your captivating solo choreography is back in another edition of this exciting competition. So You Think You Can Dance? Well, show us!", genre = listOf("Dance")),
        eventdetail("Choir","Competitions", imgurl = "https://drive.google.com/file/d/194wL7dZehfY5nCPhpjfqSy_LEygToMKz/view?usp=sharing", descriptionEvent = "The perfect opportunity to flaunt your captivating solo choreography is back in another edition of this exciting competition. So You Think You Can Dance? Well, show us!", genre = listOf("Dance")),







        )












}