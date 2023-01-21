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
                fb.collection("AllEvents").document(evnt.artist + " 2023").set(evnt).addOnSuccessListener {
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
        val newEvents= listOf<eventdetail>(
                eventdetail(
                    artist = "Haute Couture",
                    category = "Competition",
                    starttime = OwnTime(4, 12, 0),
                    imgurl = "",
                    durationInMin = 90,
                    genre = listOf("Fashion", "Ethnic", "Traditional"),
                    descriptionEvent = "Ever felt so thunder seeing someone so immaculately dressed that you run out of cheers and words. Haute Couture, where the most glamorous people show off their skills as they walk the ramp, a fashion show evoking all those creative peeps to put on their thinking caps and come out with the most innovative theme-based fashion show.",
                    venue = "Auditorium",
                    type = "Vogue Nation",
                    reglink = ""
                ),
            eventdetail(
                artist = "States of Dress",
                category = "Competition",
                starttime = OwnTime(5, 9, 0),
                imgurl = "",
                durationInMin = 180,
                genre = listOf("Fashion", "Ethnic", "Traditional"),
                descriptionEvent = "Design the best outfit for your friend using trash. Use your origami and fashion skills to make the perfect dress!",
                venue = "Library Shed",
                type = "Vogue Nation",
                reglink = ""
            ),
            eventdetail(
                artist = "Glamour Nova",
                category = "Competition",
                starttime = OwnTime(4, 10, 0),
                imgurl = "",
                durationInMin = 420,
                genre = listOf("Fashion", "Ethnic", "Traditional"),
                descriptionEvent = "Online Fashion Design and Photoshoot Competition",
                venue = "Entire Campus",
                type = "Vogue Nation",
                reglink = ""
            ),
            eventdetail(
                artist = "Chirag Panjwani",
                category = "Stand Up",
                starttime = OwnTime(5, 14, 0),
                imgurl = "",
                durationInMin = 30,
                genre = listOf(""),
                descriptionEvent = "Coming soon...",
                venue = "Auditorium",
                type = "Proshow",
                reglink = ""
            ),
            eventdetail(
                artist = "Rahul Kharbanda",
                category = "Magician",
                starttime = OwnTime(4, 14, 0),
                imgurl = "",
                durationInMin = 30,
                genre = listOf(""),
                descriptionEvent = "Coming soon...",
                venue = "Auditorium",
                type = "Proshow",
                reglink = ""
            ),
            eventdetail(
                artist = "Bikram Sarkar",
                category = "Stunt",
                starttime = OwnTime(5, 13, 0),
                imgurl = "",
                durationInMin = 30,
                genre = listOf(""),
                descriptionEvent = "Coming soon...",
                venue = "Old Sac Wall",
                type = "Proshow",
                reglink = ""
            ),
            eventdetail(
                artist = "Sen Jansen",
                category = "Dancer",
                starttime = OwnTime(4, 17, 0),
                imgurl = "",
                durationInMin = 30,
                genre = listOf("Dance"),
                descriptionEvent = "Coming soon...",
                venue = "Expo Stage",
                type = "Proshow",
                reglink = ""
            ),
            eventdetail(
                artist = "Yashmita Hattangdi",
                category = "Band Performance",
                starttime = OwnTime(5, 17, 0),
                imgurl = "",
                durationInMin = 30,
                genre = listOf("Bands", "Music"),
                descriptionEvent = "Coming soon...",
                venue = "Auditorium",
                type = "Proshow",
                reglink = ""
            ),
            eventdetail(
                artist = "Rock o' Phonix",
                category = "Competition",
                starttime = OwnTime(3, 8, 0),
                imgurl = "",
                durationInMin = 570,
                genre = listOf(),
                descriptionEvent = "Coming soon...",
                venue = "Rocko Stage",
                type = "Class Apart",
                reglink = "https://rocko.alcheringa.in"
            ),
            eventdetail(
                artist = "Mr and Ms Alcheringa ",
                category = "Competition",
                starttime = OwnTime(4, 10, 0),
                imgurl = "",
                durationInMin = 390,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Lecture Hall 1",
                type = "Class Apart",
                reglink = ""
            ),
            eventdetail(
                artist = "Crossfade",
                category = "Competition",
                starttime = OwnTime(3, 9, 30),
                imgurl = "",
                durationInMin = 420,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Old Sac Wall",
                type = "Class Apart",
                reglink = ""
            ),
            eventdetail(
                artist = "Alcher Con",
                category = "Competition",
                starttime = OwnTime(5,2,0),
                imgurl = "",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Library Shed",
                type = "",
                reglink = ""
            ),
            eventdetail(
                artist = "Theatrix",
                category = "Competition",
                starttime = OwnTime(3,8,30),
                imgurl = "",
                durationInMin = 240,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Auditorium",
                type = "Stagecraft",
                reglink = ""
            ),
            eventdetail(
                artist = "Halla Boll",
                category = "Competition",
                starttime = OwnTime(5,10,30),
                imgurl = "",
                durationInMin = 270,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Audi Park",
                type = "Stagecraft",
                reglink = ""
            ),
            eventdetail(
                artist = "Mono Drama",
                category = "Competition",
                starttime = OwnTime(5,9,0),
                imgurl = "",
                durationInMin = 120,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Mini Audi",
                type = "Stagecraft",
                reglink = ""
            ),
            eventdetail(
                artist = "Electic Heels",
                category = "Competition",
                starttime = OwnTime(3,4,30),
                imgurl = "",
                durationInMin = 120,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Audi",
                type = "Anybody Can Dance",
                reglink = ""
            ),
            eventdetail(
                artist = "Step Up",
                category = "Competition",
                starttime = OwnTime(4,12,0),
                imgurl = "",
                durationInMin = 270,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Old Sac Wall",
                type = "Anybody Can Dance",
                reglink = ""
            ),
            eventdetail(
                artist = "Dancing Duo",
                category = "Competition",
                starttime = OwnTime(4,12,0),
                imgurl = "",
                durationInMin = 210,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Mini Audi",
                type = "Anybody Can Dance",
                reglink = ""
            ),
            eventdetail(
                artist = "SUTUCD",
                category = "Competition",
                starttime = OwnTime(3,8,30),
                imgurl = "",
                durationInMin = 330,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Mini Audi",
                type = "Anybody Can Dance",
                reglink = ""
            ),
            eventdetail(
                artist = "Navra",
                category = "Competition",
                starttime = OwnTime(3,2,30),
                imgurl = "",
                durationInMin = 210,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Mini Audi",
                type = "Anybody Can Dance",
                reglink = ""
            ),
            eventdetail(
                artist = "Unplugged",
                category = "Competition",
                starttime = OwnTime(3,8,30),
                imgurl = "",
                durationInMin = 330,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Expo Stage",
                type = "Music",
                reglink = ""
            ),
            eventdetail(
                artist = "VOA",
                category = "Competition",
                starttime = OwnTime(5,8,0),
                imgurl = "",
                durationInMin = 480,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Lecture Hall 4",
                type = "Music",
                reglink = ""
            ),
            eventdetail(
                artist = "MUN",
                category = "Competition",
                starttime = OwnTime(3,8,0),
                imgurl = "",
                durationInMin = 480,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Conference Hall 1",
                type = "Model United Nation",
                reglink = ""
            ),
            eventdetail(
                artist = "Parliamentary Debate",
                category = "Competition",
                starttime = OwnTime(3,8,0),
                imgurl = "",
                durationInMin = 570,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Core 5",
                type = "Literary",
                reglink = ""
            ),
            eventdetail(
                artist = "Poetry Slam",
                category = "Competition",
                starttime = OwnTime(4, 11,0),
                imgurl = "",
                durationInMin = 240,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Lecture Hall 2",
                type = "Literary",
                reglink = ""
            ),
            eventdetail(
                artist = "Mehfil e Alcheringa",
                category = "Competition",
                starttime = OwnTime(4,8,0),
                imgurl = "",
                durationInMin = 480,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Lecture Hall 3",
                type = "Literary",
                reglink = ""
            ),
            eventdetail(
                artist = "Clay Modeling",
                category = "Competition",
                starttime = OwnTime(3,12,0),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Library Basement",
                type = "Art Talkies",
                reglink = ""
            ),
            eventdetail(
                artist = "Ink The Tree",
                category = "Competition",
                starttime = OwnTime(3,9,30),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = ""
            ),
            eventdetail(
                artist = "Face Art",
                category = "Competition",
                starttime = OwnTime(4,9,30),
                imgurl = "",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = ""
            ),
            eventdetail(
                artist = "Graffiti",
                category = "Competition",
                starttime = OwnTime(4,12,0),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Library Basement",
                type = "Art Talkies",
                reglink = ""
            ),
            eventdetail(
                artist = "Rangoli",
                category = "Competition",
                starttime = OwnTime(2,2,30),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = ""
            ),
            eventdetail(
                artist = "Who Is It",
                category = "Competition",
                starttime = OwnTime(4,3,30),
                imgurl = "",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = ""
            ),
            eventdetail(
                artist = "5 on 5 Football",
                category = "Competition",
                starttime = OwnTime(3,9,0),
                imgurl = "",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Football Ground",
                type = "Sports",
                reglink = ""
            ),
            eventdetail(
                artist = "Arm Wrestling",
                category = "Competition",
                starttime = OwnTime(4,9,0),
                imgurl = "",
                durationInMin = 270,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Front of Graffiti Wall",
                type = "Sports",
                reglink = ""
            ),
            eventdetail(
                artist = "3 on 3 basketball",
                category = "Competition",
                starttime = OwnTime(3,9,0),
                imgurl = "",
                durationInMin = 510,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Basketball Court",
                type = "Sports",
                reglink = ""
            ),


            )

        //pushEvents(newEvents)
    }
}
