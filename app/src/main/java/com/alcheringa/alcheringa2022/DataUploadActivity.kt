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



            )

        //pushEvents(newEvents)
    }
}
