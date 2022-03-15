package com.alcheringa.alcheringa2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alcheringa.alcheringa2022.Model.OwnTime
import com.alcheringa.alcheringa2022.Model.eventdetail
import com.alcheringa.alcheringa2022.Model.sponsersnew
import com.google.firebase.firestore.FirebaseFirestore

class DataUploadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_upload)

        val fb = FirebaseFirestore.getInstance()


        fun pushEvents(evnts: List<eventdetail>) {
            for (evnt in evnts) {
                fb.collection("AllEvents").document(evnt.artist).set(evnt).addOnSuccessListener {
                    Log.d("pushevents", "process succeed")
                }.addOnFailureListener {
                    Log.d("pushevents", "process failed")
                }
            }
        }


        fun pushSponsers(evnts: List<sponsersnew>) {
            for (evnt in evnts) {
                fb.collection("SponsersNew").add(evnt).addOnSuccessListener {
                    Log.d("pushevents", "process succeed")
                }.addOnFailureListener {
                    Log.d("pushevents", "process failed")
                }
            }
        }
//        val sponserlist= mutableListOf(
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404","Manikchand",true),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404","Manikchand",true),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404","Manikchand",true),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404","Manikchand",),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404","Manikchand",),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404","Manikchand",),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404","Manikchand",),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404",),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404",),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404",),
//            sponsersnew("https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Sponsors%2FLOGO%2011%20%203.png?alt=media&token=74eaff2a-e888-44ec-bcc3-abc82cf46404",),
//
//            )
//        pushSponsers(sponserlist)
        val newEvents= listOf(

            /*eventdetail("George Redd","Comedian",OwnTime(13,17,30),"Online","",30,listOf("Art"),"","Auditorium 1","Proshows","","", true),
            eventdetail("Wes Gama","Graffiti",OwnTime(13,17,0),"Online","",30,listOf("Art"),"","Auditorium 1","Proshows","","",true),
            eventdetail("Evgeny Khmara","Instrumentalist",OwnTime(12,17,30),"Online","",30,listOf("Music"),"","Auditorium 2","Proshows","","", true),
            eventdetail("Jos Repertory Theatre","Theatre",OwnTime(13,17,0),"Online","",30,listOf("Drama"),"","Auditorium 2","Proshows","","", true),*/
            /*eventdetail(
                "Ekaterina",
                "Painter",
                OwnTime(12,17,0),
                "Online",
                "",
                30,listOf("Art"),
                "",
                "Auditorium 2",
                "Proshows",
                "",
                "",
                true
            ),*/
            /*eventdetail(
                "Photography Workshop",
                "Shivang Mehta",
                OwnTime(12,15,0),
                "Online",
                "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2FPhotography%20Workshop.jpg?alt=media&token=e0722096-975d-4ba6-9aa3-5659840ff099",
                60,
                listOf("Art"),
                "An international award-winning wildlife photographer and the best-selling author of ‘A Decade with Tigers' and 'Chasing Horizons: Learnings from Africa', Shivang Mehta has donned many hats in his long career, including that of a journalist and a PR professional. Stay tuned for this fascinating session!",
                "Auditorium 2",
                "Creators' Camp",
                "",
                "",
                true
            ),
            eventdetail(
                "Chess Panel",
                "Mr RB Ramesh",
                OwnTime(12,11,0),
                "Online",
                "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2FChess%20Panel.jpg?alt=media&token=0a2012bd-d99f-4158-82e4-6c10abdf25e6",
                60,
                listOf("Fun"),
                "In our Chess’s panel, we have RB Ramesh who is a very well known indian chess grandmaster from Bangalore who won the 2002 British Championship and 2007 Commonwealth Championship. Join us to know more about his history and maybe a few tricks that he keeps under his sleeve.",
                "Creators' Camp",
                "Creators' Camp",
                "",
                "",
                true
            ),
            eventdetail(
                "Gamers' Province 2",
                "Lokesh Gamer",
                OwnTime(13,11,0),
                "Online",
                "",
                60,
                listOf("Fun"),
                "In our Gamers’ Province Panel, we have Gaming pro Lokesh Gamer, who has proved himeself multiple times in Free Fire.His love for Free Fire has him streaming it on his Youtube Channels. Join in to know about their journey from start to being one of the renowed E-sports streamer in the country.",
                "Creators' Camp",
                "Creators' Camp",
                "",
                "",
                true
            ),
            eventdetail(
                "Gamers' Province 3",
                "8Bit Mamba | S8UL Regatos | S8UL Viper",
                OwnTime(13,15,0),
                "Online",
                "",
                60,
                listOf("Fun"),
                "In our Gamers’ Province Panel, we have Gaming Pro’s 8 Bit Mamba, S8UL Regaltos and S8UL Viper. All find common love for BGMI and now even Valorant and have been streaming both on their Youtube Channels. Join in to know about their journey from start to being one of the renowed E-sports streamers in the country.",
                "Creators' Camp",
                "Creators' Camp",
                "",
                "",
                true
            ),*/
            /*eventdetail(
                "FinTalk",
                "Basavraj Tonagatti | Preeti Zinde",
                OwnTime(12,12,0),
                "Online",
                "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2FFinTalk.jpg?alt=media&token=104144ae-b764-45de-9f52-56aec902e24b",
                60,
                listOf("Finance"),
                "In our Fin Talk panel, we have Basavraj Tongatti and Preeti Zinde are very well known Finance Guru.",
                "Creators' Camp",
                "Creators' Camp",
                "",
                "",
                true
            ),*/


            eventdetail(
                "East Regional Finale",
                "ParxHunt",
                OwnTime(13,17,30),
                "OFFLINE",
                "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/eventsImage%2FGroup%20Singing%20Performance.jpg?alt=media&token=f60896f9-8611-4a29-9abe-b073fd279af9",
                60,
                listOf("Music"),
                "If you've ever wondered what combining two timeless art forms would be like, we have the answer. We are glad that East regional is hosting their finale with us, experience the culmination of music and humor at its finest.",
                "IITG Auditorium",
                "Other Events",
                "",
                "",
                true
            ),


            )

        //pushEvents(newEvents)
    }
}
