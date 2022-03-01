package com.alcheringa.alcheringa2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alcheringa.alcheringa2022.Model.OwnTime
import com.alcheringa.alcheringa2022.Model.eventdetail
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

//        val newEvents= listOf(
//                eventdetail(
//                        "Self Defense Workshop",
//                        "SafHer",
//                        type = "Campaigns",
//                        starttime = OwnTime(0,0,0) ,
//                        durationInMin =60,
//                        venue = "Campaigns",
//                        imgurl = "",
//                        descriptionEvent="Women feel a sense of fear in their day to day lives and it is unfair for them to have to feel this way. Hence, nowadays, self defence has become crucial for any woman to learn.\n" +
//                                "\n" +
//                                "Alcheringa, as part of its women empowerment campiagn SafHer - Her Freedom, Not Feardom, conducted a Self-Defense workshop for young girls today, at KV School in IIT Guwahati.",
//                        genre = listOf("music"),
//                ),
//                eventdetail(
//                        "Run for Prithvi Marathon",
//                        "Prithvi ",
//                        type = "Campaigns",
//                        starttime = OwnTime(11,7,30) ,
//                        durationInMin =60,
//                        venue = "Campaigns",
//                        imgurl = "",
//                        descriptionEvent="Run to Live. Live to Run.\n" +
//                                "\n" +
//                                "Project Prithvi, the social awareness campaign of Alcheringa proudly presents to you 'Run for Prithvi'. The campaign focuses on the principle of living life simply and thus making people realise that their role in the large movement against climate change can make this world a better place to live in.",
//                        genre = listOf("music"),
//                ),
////                eventdetail(
////                        "Policy Maker Presentation Day1",
////                        "North East Unveil",
////                        type = "Campaigns",
////                        starttime = OwnTime(12,12,0) ,
////                        durationInMin =120,
////                        venue = "Campaigns",
////                        imgurl = "",
////                        descriptionEvent="",
////                        genre = listOf("music"),
////                ),
////                eventdetail(
////                        "Panel Discussion Day1",
////                        "North East Unveil",
////                        type = "Campaigns",
////                        starttime = OwnTime(12,14,0) ,
////                        durationInMin =60,
////                        venue = "Campaigns",
////                        imgurl = "",
////                        descriptionEvent="",
////                        genre = listOf("music"),
////                ),
////                eventdetail(
////                        "Performance Day1",
////                        "North East Unveil",
////                        type = "Campaigns",
////                        starttime = OwnTime(12,15,0) ,
////                        durationInMin =120,
////                        venue = "Campaigns",
////                        imgurl = "",
////                        descriptionEvent="",
////                        genre = listOf("music"),
////                ),
////                eventdetail(
////                        "Short Movie Screening Day2",
////                        "North East Unveil",
////                        type = "Campaigns",
////                        starttime = OwnTime(13,10,0) ,
////                        durationInMin =60,
////                        venue = "Campaigns",
////                        imgurl = "",
////                        descriptionEvent="",
////                        genre = listOf("music"),
////                ),
////                eventdetail(
////                        "Policy Maker Presentation  Day2",
////                        "North East Unveil",
////                        type = "Campaigns",
////                        starttime = OwnTime(13,12,0) ,
////                        durationInMin =120,
////                        venue = "Campaigns",
////                        imgurl = "",
////                        descriptionEvent="",
////                        genre = listOf("music"),
////                ),
////                eventdetail(
////                        "Panel Discussion  Day2",
////                        "North East Unveil",
////                        type = "Campaigns",
////                        starttime = OwnTime(13,14,0) ,
////                        durationInMin =60,
////                        venue = "Campaigns",
////                        imgurl = "",
////                        descriptionEvent="",
////                        genre = listOf("music"),
////                ),
////                eventdetail(
////                        "Performance  Day2",
////                        "North East Unveil",
////                        type = "Campaigns",
////                        starttime = OwnTime(13,15,0) ,
////                        durationInMin =120,
////                        venue = "Campaigns",
////                        imgurl = "",
////                        descriptionEvent="",
////                        genre = listOf("music"),
////                ),
////                eventdetail(
////                        "Saaz",
////                        "Pronites",
////                        type = "Pronites",
////                        starttime = OwnTime(11,20,0) ,
////                        durationInMin =90,
////                        venue = "Auditorium 1",
////                        imgurl = "",
////                        descriptionEvent="",
////                        genre = listOf("music"),
////                ),
////                eventdetail(
////                "Pronite 2",
////                "Pronites",
////                type = "Pronites",
////                starttime = OwnTime(12,19,0) ,
////                durationInMin =120,
////                venue = "" ,
////                imgurl = "",
////                descriptionEvent="",
////                    genre = listOf("music"),
////            ),
////                eventdetail(
////                        "Pronite 3",
////                        "Pronites",
////                        type = "Pronites",
////                        starttime = OwnTime(13,19,0) ,
////                        durationInMin =120,
////                        venue = "" ,
////                        imgurl = "",
////                        descriptionEvent="",
////                        genre = listOf("music"),
////                ),
//
////            eventdetail(
////                "Glamour Nova",
////                "Competitions",
////                type = "Vogue Nation",
////                starttime = OwnTime(12,13,0) ,
////                durationInMin =60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                descriptionEvent="Alcheringa, Northeast’s largest cultural extravaganza, once again brings to you all Glamour Nova. A chance for the most glamorous people to show off their skills. It is the fashion benchmark of North-East ,the flagship of Vogue Nation. Part of Haute Cotoure, a fashion designing and Photoshoot competition in which participants from around the country show off their pizzazz and exoticism.  Top 10 teams consisting of 2 models, 2 designers and 1 photographer will get a chance to showcase their skills in real time. They will get an opportunity to photoshoot across the stellar campus of IITG during a 2 day long event.",
////                genre = listOf("Fashion"),
////            ),
////
////            eventdetail(
////                "States of Dress",
////                "Competitions",
////                type = "Vogue Nation",
////                starttime = OwnTime(12,13,0),
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                descriptionEvent="Fashion is something that expresses the inner you. It is a form of art which becomes exciting with lesser resources. Throughout history, fashion has transformed from a stricter sense to an innovative art which uses a wide range of materials. In states of dress, we design the most spectacular and innovative outfit for your friend. Create the coolest and most sustainable dress using your designing and origami skills. ",
////                genre = listOf("Fashion"),
////            ),
////
////            eventdetail(
////                "Poetry Slam",
////                "Competitions",
////                starttime = OwnTime(12,13,0) ,
////                durationInMin =60,
////                venue = "Auditorium 2" ,
////                imgurl = "",
////                type="Literary",
////                descriptionEvent="Get the mic and put your voice in front of a live audience of North- East India as Alcheringa gives you a platform to perform spoken word poetry. So, let your soul speak for you, impress the panel of judges and win the hearts of the audience cheering for you.",
////                genre = listOf("Literary")
////            ),
////
////            eventdetail(
////                "Mehfil-a-Alcheringa",
////                "Competitions",
////                starttime = OwnTime(12, 17, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                descriptionEvent="",
////                genre = listOf("Musicp"),
////                type = "Literary"
////            ),
////
////            eventdetail(
////                "Zephyr",
////                "Competitions",
////                starttime = OwnTime(13, 14, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                type="Literary",
////                descriptionEvent="Imagine creating a fictional world or humming the lyrics of a poem that you created yourselves, but could never put it on paper. Alcheringa, Northeast’s largest cultural extravaganza, brings you a golden opportunity to put your thoughts or emotions on paper and show the world what you are capable of. ",
////                genre = listOf("Literary")
////            ),
////
////            eventdetail(
////                "Snap Thrillz",
////                "Competitions",
////                starttime = OwnTime(0, 0, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                type="Lights Camera Action",
////                descriptionEvent="Let us see the world through your eyes, let your photographs tell a story - compile the best of your pictures and make a movie, let others escape in your imagination!",
////                genre = listOf("Drama")
////            ),
////
////            eventdetail(
////                "Who is it?",
////                "Competitions",
////                starttime = OwnTime(0, 0, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                type="Art Talkies",
////                descriptionEvent="Whose face do you have in mind, when you take a pencil and your hand starts drawing those random strokes? Let your magic make those faces that come on TV screens come live on paper.",
////                genre = listOf("Art")
////            ),
////
////            eventdetail(
////                "Rangoli",
////                "Competitions",
////                starttime = OwnTime(13, 14, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                type="Art Talkies",
////                descriptionEvent="Make the floor your canvas and fill it with your imagination. Create a festive mood for everyone, with your rango wali rangoli!",
////                genre = listOf("Art")
////            ),
////
////            eventdetail(
////                "Best out of waste",
////                "Competitions",
////                starttime = OwnTime(13, 14, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                type="Art Talkies",
////                descriptionEvent="Pick up the things that are just about to go to the dustbin, and start with your magic. Create something that amazes everyone and makes the best out of the waste.",
////                genre = listOf("Art")
////            ),
////
////            eventdetail(
////                "Custom Brush",
////                "Competitions",
////                starttime = OwnTime(13, 14, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                type="Digital Dexterity",
////                descriptionEvent="Make your imaginations come live digitally! Pick up the drawing tablet and create your own little masterpiece, coming out of the pixels and the layers.",
////                genre = listOf("Art","Tech")
////            ),
////
////            eventdetail(
////                "Xpressions",
////                "Competitions",
////                starttime = OwnTime(13, 14, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                type="Digital Dexterity",
////                descriptionEvent="",
////                genre = listOf("Drama","Tech")
////            ),
////
////            eventdetail(
////                "Alcher Diva or Hunk",
////                "Competitions",
////                starttime = OwnTime(13, 14, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                type="Digital Dexterity",
////                descriptionEvent="In this world of social media, where millions of pictures get uploaded every second, do you think that your picture can stand out? Send us a picture of yours and let the social media decide if it is impressive or not!",
////                genre = listOf("Art","Tech")
////            ),
////
////            eventdetail(
////                "Doodle Pad",
////                "Competitions",
////                starttime = OwnTime(13, 14, 0) ,
////                durationInMin = 60,
////                venue = "Auditorium 1" ,
////                imgurl = "",
////                type="Digital Dexterity",
////                descriptionEvent="Do you remember all those drawings you made in the book or the back of your notebook in those boring math classes, in the school years? It is time to visit those doodles again and show the world your hidden talent.\n",
////                genre = listOf("Art","Tech")
////            ),
//         )
//
//        pushEvents(newEvents);
//    }
    }
}