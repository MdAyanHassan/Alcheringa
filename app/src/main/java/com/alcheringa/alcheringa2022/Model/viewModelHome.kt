package com.alcheringa.alcheringa2022.Model

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alcheringa.alcheringa2022.Database.ScheduleDatabase
import com.alcheringa.alcheringa2022.LoaderView
import com.alcheringa.alcheringa2022.Model.eventdetail
import com.alcheringa.alcheringa2022.Model.eventWithLive
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.AccessController.getContext
import java.util.*

fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.add(item)
    this.value = oldValue
}

fun <T> MutableLiveData<MutableList<T>>.removeAnItem(item: T) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.remove(item)
    this.value = oldValue
}

suspend fun <T> MutableLiveData<MutableList<T>>.removeAndAddItemAtPos(item: T) {
    val oldValue = this.value ?: mutableListOf()
    val pos = oldValue.indexOf(item)
    oldValue.removeAt(pos + 1)
    this.value = oldValue
//    delay(100)
    oldValue.add(pos, item)
    this.value = oldValue

}


class viewModelHome : ViewModel() {
    val fb = FirebaseFirestore.getInstance()
    val allEventsWithLivedata = MutableLiveData<MutableList<eventWithLive>>()
    val allEventsWithLive = mutableStateListOf<eventWithLive>()
    val liveEvents = mutableStateListOf<eventWithLive>()
    val featuredEventsWithLivedata = MutableLiveData<MutableList<eventWithLive>>()
    val featuredEventsWithLivestate = mutableStateListOf<eventWithLive>()
    val OwnEventsWithLive = MutableLiveData<MutableList<eventdetail>>()
    val OwnEventsLiveState = mutableStateListOf<eventdetail>()
    val OwnEventsWithLiveState = mutableStateListOf<eventWithLive>()
    val upcomingEventsLiveState = mutableStateListOf<eventWithLive>()
    val merchhome = mutableStateListOf<merchmodelforHome>()
    var crnttime = mutableStateOf(OwnTime())
    val merchMerch = SnapshotStateList<merchModel>()
    val forYouEvents = mutableStateListOf<eventWithLive>()

    fun getAllEvents() {
        viewModelScope.launch {
            fb.collection("AllEvents").get().addOnSuccessListener { evnts ->
                val list = mutableListOf<eventWithLive>()
                list.clear()
                for (evnt in evnts) {
                    list.add(eventWithLive(evnt.toObject(eventdetail::class.java)))
                }
                Log.d("eventlist", list.toString())
                allEventsWithLivedata.postValue(list)
                Log.d("getevents", "eventsfetched")
                checklive()
            }
        }

    }

    fun checklive() {
        viewModelScope.launch {

            while (true) {
                Log.d("livecheck", "started")
                val c = Calendar.getInstance()
                var dt = 0
                if (c.get(Calendar.MONTH) == Calendar.FEBRUARY) {
                    dt = c.get(Calendar.DATE) - 31
                } else {
                    dt = c.get(Calendar.DATE)
                }
                crnttime.value =
                    OwnTime(date = dt, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE))
                delay(50)
                liveEvents.clear()

                for (data in allEventsWithLive) {

                    data.isLive.value = (c.get(Calendar.YEAR) == 2023) and
                            (c.get(Calendar.MONTH) == Calendar.FEBRUARY) and
                            (c.get(Calendar.DATE) == data.eventdetail.starttime.date) and
                            (((data.eventdetail.starttime.hours * 60)..(data.eventdetail.starttime.hours * 60 + data.eventdetail.durationInMin))
                                .contains((c.get(Calendar.HOUR_OF_DAY) * 60) + c.get(Calendar.MINUTE)))
                    if(data.isLive.value){ liveEvents.add(data) }
                }

                for (data in OwnEventsWithLiveState) {

                    data.isLive.value = (c.get(Calendar.YEAR) == 2023) and
                            (c.get(Calendar.MONTH) == Calendar.FEBRUARY) and
                            (c.get(Calendar.DATE) == data.eventdetail.starttime.date) and
                            (((data.eventdetail.starttime.hours * 60)..(data.eventdetail.starttime.hours * 60 + data.eventdetail.durationInMin))
                                .contains((c.get(Calendar.HOUR_OF_DAY) * 60) + c.get(Calendar.MINUTE)))
                }


                for (data in allEventsWithLive) {

                    if ((c.get(Calendar.YEAR) > 2023) or
                        ((c.get(Calendar.YEAR) == 2023) and
                                (c.get(Calendar.MONTH) > Calendar.FEBRUARY)) or
                        ((c.get(Calendar.YEAR) == 2023) and
                                (c.get(Calendar.MONTH) == Calendar.FEBRUARY) and
                                (c.get(Calendar.DATE) > data.eventdetail.starttime.date)) or
                        ((c.get(Calendar.YEAR) == 2023) and
                                (c.get(Calendar.MONTH) == Calendar.FEBRUARY) and
                                (c.get(Calendar.DATE) == data.eventdetail.starttime.date) and
                                (((data.eventdetail.starttime.hours * 60))
                                        < ((c.get(Calendar.HOUR_OF_DAY) * 60) + c.get(Calendar.MINUTE))))

                    ) {
                        upcomingEventsLiveState.remove(data)
                    }
//                    else if(!data.eventdetail.stream){
//                        upcomingEventsLiveState.remove(data)
//                    }
                }
                upcomingEventsLiveState.sortBy { it.eventdetail.starttime.date*24*60 + it.eventdetail.starttime.hours*60 +it.eventdetail.starttime.min }



                delay(5000)
                Log.d("livecheck", "done")


            }
        }
    }

    fun getMerchHome() {

        viewModelScope.launch {
            fb.collection("Merch").get().addOnSuccessListener { merch ->

                merchhome.clear()
                for (mer in merch) {
                    merchhome.add(mer.toObject(merchmodelforHome::class.java))
                    //to be removed later


                }
                Log.d("merch", "fetched")
            }.addOnFailureListener { Log.d("merch", "failed") }

        }
    }

    fun getMerchMerch(){


        fb.collection("Merch").get().addOnCompleteListener { task: Task<QuerySnapshot> ->
            merchMerch.clear()
            for (documentSnapshot in task.result) {
                val obj = documentSnapshot["Images"] as ArrayList<String>?

                merchMerch.add(
                    merchModel(
                        documentSnapshot.getString("Name"),
                        documentSnapshot.getString("Type"),
                        documentSnapshot.getString("Price"),
                        documentSnapshot.getString("Description"),
                        documentSnapshot.getString("Image"),
                        documentSnapshot.getBoolean("Available"),
                        documentSnapshot.getBoolean("Small"),
                        documentSnapshot.getBoolean("Medium"),
                        documentSnapshot.getBoolean("Large"),
                        documentSnapshot.getBoolean("ExtraLarge"),
                        documentSnapshot.getBoolean("XXLarge"),
                        obj,
                        documentSnapshot.getString("Video"),
                        documentSnapshot.getString("Small_Description"),
                        documentSnapshot.getString("background"),
                        documentSnapshot.getString("Merch_Default")
                    )
                )
            }
        }

    }

    fun getfeaturedEvents() {
        viewModelScope.launch {
            fb.collection("Featured_Events").get().addOnSuccessListener { evnts ->
                val list = mutableListOf<eventWithLive>()
                list.clear()
                for (evnt in evnts) {
                    list.add(eventWithLive(evnt.toObject(eventdetail::class.java)))
                }
                Log.d("eventlist", list.toString())
                featuredEventsWithLivedata.postValue(list)
                Log.d("getevents", "eventsfetched")
            }
        }

    }


    fun fetchlocaldbandupdateownevent(scheduleDatabase: ScheduleDatabase) {
        viewModelScope.launch {
            val eventdlist = scheduleDatabase.getSchedule();

            OwnEventsWithLive.postValue(eventdlist)
        }

    }


    fun converttomin(OwnTime: OwnTime): Int {
        return ((OwnTime.date * 24 * 60) + (OwnTime.hours * 60) + OwnTime.min)
    }

    fun converttoOwnTime(min: Int): OwnTime {
        return OwnTime((min / 1440), ((min % 1440)) / 60, min % 60)
    }


//    val featuredevents = listOf(
//        eventdetail(
//            artist = "Andrew Lee",
//            category = "Magician",
//            starttime = OwnTime(12, 17, 0),
//            durationInMin = 60,
//            venue = "Auditorium 1",
//            imgurl = "",
//            genre = listOf("Magic"),
//            type = "Proshows"
//        ),
//        eventdetail(
//            "Davide Holzknecht",
//            "Sculpture artist",
//            starttime = OwnTime(13, 17, 0),
//            durationInMin = 60,
//            venue = "Auditorium 1",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Proshows"
//        ),
//
//
//        eventdetail(
//            "Sharmaji Technical",
//            "Tech-Talks",
//            starttime = OwnTime(13, 12, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Tech"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "Gadgets To Use",
//            "Tech-Talks",
//            starttime = OwnTime(13, 12, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "Palash Vaswani",
//            "Director's Cut",
//            starttime = OwnTime(13, 16, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Drama"),
//            type = "Creators' Camp"
//        ),
//    )
//
//    val AllEvents = listOf(
//        eventdetail(
//            "Voice of Alcheringa",
//            "Competitions",
//            starttime = OwnTime(12, 12, 0),
//            durationInMin = 90,
//            venue = "Auditorium 1",
//            imgurl = "",
//            type = "Music",
//            descriptionEvent = "This event is for the lone wolves unafraid to showcase their unique voices.We call out to all the nightingales to soar high on this Alcheringa. May the most melodious master prevail!\n",
//            genre = listOf("Music")
//        ),
//        eventdetail(
//            "Alcher Got Talent",
//            "Competitions",
//            starttime = OwnTime(12, 14, 0),
//            durationInMin = 60,
//            venue = "Auditorium 1",
//            imgurl = "",
//            type = "Class Apart",
//            descriptionEvent = "Every soul has a purpose, an inner voice and an unbeaten talent that has been fueled by passion! So look within, and bring the best version of yourself to life. Go carve your own niche with “Alcher’s got talent”- the talent search competition hosted by Alcheringa!\n",
//            genre = listOf("Music", "Dance", "Fashion", "Drama")
//        ),
//        eventdetail(
//            "Sixth String",
//            "Competitions",
//            starttime = OwnTime(12, 13, 0),
//            durationInMin = 60,
//            venue = "Auditorium 2",
//            imgurl = "",
//            type = "Class Apart",
//            descriptionEvent = "Where words fail, music speaks. Let your guitar speak for you as we showcase your skills on the biggest cultural fest in the whole of North-East India, Alcheringa. \n",
//            genre = listOf("Music")
//        ),
//        eventdetail(
//            "So You think You can Dance",
//            "Competitions",
//            starttime = OwnTime(12, 15, 0),
//            durationInMin = 90,
//            venue = "Auditorium 1",
//            type = "Any Body Can Dance",
//            imgurl = "",
//            descriptionEvent = "This dance event full of high spirit, ultimate competition and enthusiasm, organised under north-east unveiled, this enormous dance event experiences a great amount of participation of talented dancers from all over the north-east. High level of dedication towards dance can be seen In each move of their dance styles.",
//            genre = listOf("Dance")
//        ),
//        eventdetail(
//            "Just Reel it",
//            "Competitions",
//            starttime = OwnTime(12, 16, 0),
//            durationInMin = 30,
//            venue = "Auditorium 2",
//            imgurl = "",
//            type = "Lights Camera Action",
//            descriptionEvent = "Ever wondered…. I could make that reels on instagram! Well, to all the fans of reels out there, here we are to give you an opportunity to show your creativity and skills with Musicaly, an online reels making competition for all the creative peeps to put on their thinking caps and come out with the most creative short video—------------=.\n",
//            genre = listOf("Music", "Dance")
//        ),
//        eventdetail(
//            "Rap Battle",
//            "Competitions",
//            starttime = OwnTime(13, 12, 0),
//            durationInMin = 60,
//            venue = "Auditorium 1",
//            imgurl = "",
//            type = "Class Apart",
//            descriptionEvent = "Think you are a great rapper? Then its time to win hearts with your beats. Some say rapping is the best form of expression! Choice of words , rhythm or shear passion. Here's the biggest online Rapping Competition of 2023 hosted by Alcheringa\n",
//            genre = listOf("Music")
//        ),
//        eventdetail(
//            "Directors' Cut",
//            "Competitions",
//            starttime = OwnTime(13, 13, 0),
//            durationInMin = 120,
//            venue = "Auditorium 2",
//            imgurl = "",
//            type = "Lights Camera Action",
//            descriptionEvent = "Director’s cut provides the perfect curtain for your film direction skills, your way of portraying ideas through a story, the art of film and short movie making in general. This competition will force you to propel yourself towards the peak of your skills and come out  the best out of the all round competition. \n",
//            genre = listOf("Drama")
//        ),
//        eventdetail(
//            "Literary Module",
//            "Competitions",
//            starttime = OwnTime(13, 14, 0),
//            durationInMin = 60,
//            venue = "Auditorium 1",
//            imgurl = "",
//            type = "Literary",
//            genre = listOf("Literary")
//        ),
//        eventdetail(
//            "Electric Heels",
//            "Competitions",
//            starttime = OwnTime(13, 15, 0),
//            durationInMin = 90,
//            venue = "Auditorium 1",
//            imgurl = "",
//            type = "Any Body Can Dance",
//            descriptionEvent = "Presenting to you Electric heels, the most exhilarating group dance contest where the best Dance squads across the nation dance their way to win one of the flagship events of alcheringa, we believe in uniqueness. So your style, your rhythm, your beats and our arena. let's see what you got!\n",
//            genre = listOf("Dance")
//        ),
//        eventdetail(
//            "Choir",
//            "Competitions",
//            starttime = OwnTime(13, 16, 0),
//            durationInMin = 60,
//            venue = "Auditorium 2",
//            imgurl = "",
//            type = "music",
//            descriptionEvent = "We cannot listen to one voice above all others. We must listen to a choir of voices if we are to understand the individual tunes. We make many sounds from our mouth without knowing what if we combine them in a single melody….here we give you a chance to present your skills through Alcheringa.\n",
//            genre = listOf("Music")
//        ),
//
//        eventdetail(
//            "Andrew Lee",
//            "Magician",
//            starttime = OwnTime(12, 17, 0),
//            durationInMin = 60,
//            venue = "Auditorium 1",
//            imgurl = "",
//            genre = listOf("Magic"),
//            type = "Proshows"
//        ),
//        eventdetail(
//            "Davide Holzknecht",
//            "Sculpture artist",
//            starttime = OwnTime(13, 17, 0),
//            durationInMin = 60,
//            venue = "Auditorium 1",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Proshows"
//        ),
//
//
//        eventdetail(
//            "Sharmaji Technical",
//            "Tech-Talks",
//            starttime = OwnTime(13, 12, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Tech"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "Gadgets To Use",
//            "Tech-Talks",
//            starttime = OwnTime(13, 12, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "Palash Vaswani",
//            "Director's Cut",
//            starttime = OwnTime(13, 16, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Drama"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "Radio Ka Rohan",
//            "Beyond the Plate",
//            starttime = OwnTime(13, 14, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "Maya Vishwakarma",
//            "Women's Talks",
//            starttime = OwnTime(13, 13, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "Muskan Kalra",
//            "Content Creators",
//            starttime = OwnTime(12, 16, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "The Motor Mouth",
//            "Content Creators",
//            starttime = OwnTime(12, 16, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "India in Pixels",
//            "Content Creators",
//            starttime = OwnTime(12, 16, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "Youngest Headmaster",
//            "Achievers Panel",
//            starttime = OwnTime(13, 17, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Creators' Camp"
//        ),
//        eventdetail(
//            "Motivational Speaker",
//            "Achievers Panel",
//            starttime = OwnTime(13, 17, 0),
//            durationInMin = 60,
//            venue = "Creator's Camp",
//            imgurl = "",
//            genre = listOf("Art"),
//            type = "Creators' Camp"
//        ),
//
//        )


//    val competitons= listOf(
//        eventdetail("Electric Heels ","Competitions", ,imgurl = "https://drive.google.com/file/d/1CNYLSCGfHRHFi-pAYGa55aySuHAYb0Jz/view?usp=sharing", descriptionEvent = "Light our virtual stage on fire with unique group choreographies and flawless coordination in this freestyle group dance competition.", genre = listOf("Dance")),
//        eventdetail("SUTUCD ","Competitions", ,imgurl = "https://drive.google.com/file/d/194wL7dZehfY5nCPhpjfqSy_LEygToMKz/view?usp=sharing", descriptionEvent = "The perfect opportunity to flaunt your captivating solo choreography is back in another edition of this exciting competition. So You Think You Can Dance? Well, show us!", genre = listOf("Dance")),
//        eventdetail("Choir","Competitions", ,imgurl = "https://drive.google.com/file/d/194wL7dZehfY5nCPhpjfqSy_LEygToMKz/view?usp=sharing", descriptionEvent = "The perfect opportunity to flaunt your captivating solo choreography is back in another edition of this exciting competition. So You Think You Can Dance? Well, show us!", genre = listOf("Dance")),
//        eventdetail("Electric Heels ","Competitions", ,imgurl = "https://drive.google.com/file/d/1CNYLSCGfHRHFi-pAYGa55aySuHAYb0Jz/view?usp=sharing", descriptionEvent = "Light our virtual stage on fire with unique group choreographies and flawless coordination in this freestyle group dance competition.", genre = listOf("Dance")),
//        eventdetail("ajay  ","Competitions", ,imgurl = "https://drive.google.com/file/d/194wL7dZehfY5nCPhpjfqSy_LEygToMKz/view?usp=sharing", descriptionEvent = "The perfect opportunity to flaunt your captivating solo choreography is back in another edition of this exciting competition. So You Think You Can Dance? Well, show us!", genre = listOf("Dance")),
//        eventdetail("Electric Heels ","Competitions", ,imgurl = "https://drive.google.com/file/d/1CNYLSCGfHRHFi-pAYGa55aySuHAYb0Jz/view?usp=sharing", descriptionEvent = "Light our virtual stage on fire with unique group choreographies and flawless coordination in this freestyle group dance competition.", genre = listOf("Dance")),
//        eventdetail("ajay  ","Competitions", ,imgurl = "https://drive.google.com/file/d/194wL7dZehfY5nCPhpjfqSy_LEygToMKz/view?usp=sharing", descriptionEvent = "The perfect opportunity to flaunt your captivating solo choreography is back in another edition of this exciting competition. So You Think You Can Dance? Well, show us!", genre = listOf("Dance")),
//        )


//    val competitions= listOf(
//        eventdetail("Voice of Alcheringa","Competitions", starttime = OwnTime(12, 12, 0) ,durationInMin = 90, venue = "Auditorium 1" ,imgurl = "",type = "Competitions", descriptionEvent = "This event is for the lone wolves unafraid to showcase their unique voices.We call out to all the nightingales to soar high on this Alcheringa. May the most melodious master prevail!\n", genre = listOf("Music")),
//        eventdetail("Alcher Got Talent","Competitions", starttime = OwnTime(12, 14, 0) ,durationInMin = 60, venue = "Auditorium 1" ,imgurl = "",type = "Competitions", descriptionEvent = "Every soul has a purpose, an inner voice and an unbeaten talent that has been fueled by passion! So look within, and bring the best version of yourself to life. Go carve your own niche with “Alcher’s got talent”- the talent search competition hosted by Alcheringa!\n", genre = listOf("Music", "Dance", "Fashion", "Drama")),
//        eventdetail("Sixth String","Competitions", starttime = OwnTime(12, 13, 0) ,durationInMin = 60, venue = "Auditorium 2" ,imgurl = "", type = "Competitions",descriptionEvent = "Where words fail, music speaks. Let your guitar speak for you as we showcase your skills on the biggest cultural fest in the whole of North-East India, Alcheringa. \n", genre = listOf("Music")),
//        eventdetail("So You think You can Dance","Competitions", starttime = OwnTime(12, 15, 0) ,durationInMin = 90, venue = "Auditorium 1" ,type = "Competitions",imgurl = "", descriptionEvent = "This dance event full of high spirit, ultimate competition and enthusiasm, organised under north-east unveiled, this enormous dance event experiences a great amount of participation of talented dancers from all over the north-east. High level of dedication towards dance can be seen In each move of their dance styles.", genre = listOf("Dance")),
//        eventdetail("Just Reel it","Competitions", starttime = OwnTime(12, 16, 0) ,durationInMin = 30, venue = "Auditorium 2" ,imgurl = "",type = "Competitions", descriptionEvent = "Ever wondered…. I could make that reels on instagram! Well, to all the fans of reels out there, here we are to give you an opportunity to show your creativity and skills with Musicaly, an online reels making competition for all the creative peeps to put on their thinking caps and come out with the most creative short video—------------=.\n", genre = listOf("Music", "Dance")),
//        eventdetail("Rap Battle","Competitions", starttime = OwnTime(13, 12, 0) ,durationInMin = 60, venue = "Auditorium 1" ,imgurl = "",type = "Competitions", descriptionEvent = "Think you are a great rapper? Then its time to win hearts with your beats. Some say rapping is the best form of expression! Choice of words , rhythm or shear passion. Here's the biggest online Rapping Competition of 2023 hosted by Alcheringa\n", genre = listOf("Music")),
//        eventdetail("Directors' Cut", "Competitions", starttime = OwnTime(13, 13, 0) ,durationInMin = 120, venue = "Auditorium 2" ,imgurl = "",type = "Competitions", descriptionEvent = "Director’s cut provides the perfect curtain for your film direction skills, your way of portraying ideas through a story, the art of film and short movie making in general. This competition will force you to propel yourself towards the peak of your skills and come out  the best out of the all round competition. \n", genre = listOf("Drama")),
//        eventdetail("Literary Module","Competitions", starttime = OwnTime(13, 14, 0) ,durationInMin = 60, venue = "Auditorium 1" ,imgurl = "",type = "Competitions", genre = listOf("Literary")),
//        eventdetail("Electric Heels","Competitions", starttime = OwnTime(13, 15, 0) ,durationInMin = 90, venue = "Auditorium 1" ,imgurl = "",type = "Competitions", descriptionEvent = "Presenting to you Electric heels, the most exhilarating group dance contest where the best Dance squads across the nation dance their way to win one of the flagship events of alcheringa, we believe in uniqueness. So your style, your rhythm, your beats and our arena. let's see what you got!\n", genre = listOf("Dance")),
//        eventdetail("Choir","Competitions", starttime = OwnTime(13, 16, 0) ,durationInMin = 60, venue = "Auditorium 2" ,imgurl = "",type = "Competitions", descriptionEvent = "We cannot listen to one voice above all others. We must listen to a choir of voices if we are to understand the individual tunes. We make many sounds from our mouth without knowing what if we combine them in a single melody….here we give you a chance to present your skills through Alcheringa.\n", genre = listOf("Music")),
//    )
//    val proshows= listOf(
//        eventdetail("Andrew Lee","Magician", starttime = OwnTime(12, 17, 0) ,durationInMin = 60, venue = "Auditorium 1" ,imgurl = "", genre = listOf("Magic"), type = "Proshows"),
//        eventdetail("Davide Holzknecht","Sculpture artist", starttime = OwnTime(13, 17, 0) ,durationInMin = 60, venue = "Auditorium 1" ,imgurl = "", genre = listOf("Art"), type = "Proshows"),
//
//        )
//    val creatorsCamp =listOf(
//        eventdetail("Sharmaji Technical","Tech-Talks", starttime = OwnTime(13, 12, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Tech"), type = "Creators' Camp"),
//        eventdetail("Gadgets To Use","Tech-Talks", starttime = OwnTime(13, 12, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Art"), type = "Creators' Camp"),
//        eventdetail("Palash Vaswani","Director's Cut", starttime = OwnTime(13, 16, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Drama"), type = "Creators' Camp"),
//        eventdetail("Radio Ka Rohan","Beyond the Plate", starttime = OwnTime(13, 14, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Art"), type = "Creators' Camp"),
//        eventdetail("Maya Vishwakarma","Women's Talks", starttime = OwnTime(13, 13, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Art"), type = "Creators' Camp"),
//        eventdetail("Muskan Kalra","Content Creators", starttime = OwnTime(12, 16, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Art"), type = "Creators' Camp"),
//        eventdetail("The Motor Mouth","Content Creators", starttime = OwnTime(12, 16, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Art"), type = "Creators' Camp"),
//        eventdetail("India in Pixels","Content Creators", starttime = OwnTime(12, 16, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Art"), type = "Creators' Camp"),
//        eventdetail("Youngest Headmaster","Achievers Panel", starttime = OwnTime(13, 17, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Art"), type = "Creators' Camp"),
//        eventdetail("Motivational Speaker","Achievers Panel", starttime = OwnTime(13, 17, 0) ,durationInMin = 60, venue = "Creator's Camp" ,imgurl = "", genre = listOf("Art"), type = "Creators' Camp"),
//
//        )


}