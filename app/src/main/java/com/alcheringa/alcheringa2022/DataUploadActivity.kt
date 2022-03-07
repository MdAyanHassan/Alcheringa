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
                fb.collection("Featured_Events").document(evnt.artist).set(evnt).addOnSuccessListener {
                    Log.d("pushevents", "process succeed")
                }.addOnFailureListener {
                    Log.d("pushevents", "process failed")
                }
            }
        }

        val newEvents= listOf(
//                eventdetail(
//                        "Wanderlusts",
//                        "Shubham Gupta | Hopping Bug",
//                        type = "Creators' Camp",
//                        starttime = OwnTime(12,2,30) ,
//                        durationInMin =60,
//                        venue = "Creators' Camp",
//                        imgurl ="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Competition%2FWanderlust.png?alt=media&token=3a73923d-9d24-4293-b90b-6baf2d95af99",
//                        descriptionEvent="In our Wanderlusts Pannel, we have Youtuber and travel enthusiast Shubham Gupta, along with Youtuber and film-maker Nishit Sharma aka Hopping Bug. Both of them will be joining us to share their love for travel and their amazing stories. Stay tuned for this fascinating session!", genre = listOf("food"),
//                ),
//                eventdetail(
//                        "Beyond the Plate",
//                        "Bhooka Saand | Radio Ka Rohan",
//                        type = "Creators' Camp",
//                        starttime = OwnTime(13,2,30) ,
//                        durationInMin =60,
//                        venue = "Creators' Camp",
//                        imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Competition%2FBeyond_the_Plate.png?alt=media&token=89387d0f-f30a-4d11-8d40-1c3f46f4102e",descriptionEvent="In our Beyond the Plate Pannel, we have Puneet Singh (Bhooka Sand) & RJ Rohan. Both find common love for food and have been vlogging about it on their Youtube Channels. Join in to know about their journey from start to being one of the renowed Indian food vloggers.",
//                                genre = listOf("food"),
//                ),
                eventdetail(
                        "Underground Authority",
                        "Pronite",
                        type = "Pronite",
                        starttime = OwnTime(12,19,30) ,
                        durationInMin =60,
                        venue = "Pronites",
                        imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Competition%2FJuggernaut.png?alt=media&token=22d9c319-f500-4ddb-927d-e3ad7df81894",  descriptionEvent="We bring to you Underground Authority for Juggernaut 2022 with their stunning performance.\n" +
                                "Underground Authority is an alternative rock/rap rock band from India. Formed in early 2010, in Kolkata, their music is flavoured by a blend of protest poetry, reggae, alternative rock, rap-rock, and hard rock.",
                        genre = listOf("music"),
                ),
//                eventdetail(
//                        "BGMI",
//                        "",
//                        type = "Gaming",
//                        starttime = OwnTime(13,17,30) ,
//                        durationInMin =60,
//                        venue = "Auditorium",
//                        imgurl = "",
//                        descriptionEvent="The Battlegrounds await! This is a call for all the Naders, Campers, Flankers and Scouts! Lead your team to Chicken Dinners and get a chance to win prizes worth Rs. 130K!\n" +
//                                "\n" +
//                                "1st Place: Cash Prize worth 3K, goodies and incentives worth 44K\n" +
//                                "2nd Prize: Cash Prize worth 2K, goodies and incentives worth 40K\n" +
//                                "3rd Place: Cash Prize worth 1K, goodies adn incentives worth 40K\n" +
//                                "\n" +
//                                "Register now on D2C!\n" +
//                                "\n" +
//                                "Tournament Dates: 5th - 11th March", genre = listOf("game"),
//                ),
//                eventdetail(
//                        "Chess",
//                        "",
//                        type = "Gaming",
//                        starttime = OwnTime(13,17,30) ,
//                        durationInMin =60,
//                        venue = "Auditorium",
//                        imgurl = "",
//                        descriptionEvent="Play a Gambit, sacrifice your Rook and use your poison Pawns to win prizes worth Rs. 26K ! The Grandmasters win grand prizes!\n" +
//                                "\n" +
//                                "Tournament Dates: 5th - 11th March\n" +
//                                "\n" +
//                                "1st Place: Cash Prize worth 2.5K, goodies and incentives worth 11K\n" +
//                                "2nd Place: Cash Prize worth 1.5K, goodies and incentives worth 11K\n" +
//                                "\n" +
//                                "Register now on D2C!\n" +
//                                "\n" +
//                                "Tournament Dates: 5th - 11th March", genre = listOf("game"),
//                ),
///*                eventdetail(
//                        "UNGA",
//                        "Model United Nations ",
//                        type = "MUN",
//                        starttime = OwnTime(0,0,0) ,
//                        durationInMin =60,
//                        venue = "",
//                        imgurl = "",
//                        descriptionEvent="2nd card description: From the two World Wars to as recently as the Israel-Gaza Conflict, actions of warring factions and nations have continued to violate international laws and commit atrocities. Yet it has been found difficult to precisely define the term ‘war crime,’ and its usage has, and continues to, evolve constantly. Atrocities committed seldom fall under the jurisdiction of a governing body and often warrant no penalties, actions, or relief. \n" +
//                                "\n" +
//                                "The IITG Model United Nations Edition 14 brings to you the agenda for the UN General Assembly - Winning Against War: Discussing international conventions on the evolving situation of war crimes.", genre = listOf("music"),
//                ),
//                eventdetail(
//                        "UNHRC",
//                        "Model United Nations",
//                        type = "MUN",
//                        starttime = OwnTime(0,0,0) ,
//                        durationInMin =120,
//                        venue = "",
//                        imgurl = "",
//                        descriptionEvent="3rd card description: Race, gender, sexual orientation and nationality are some of the many things that distinguish us as humans. Although more often than not these differences have also been the cause of discrimination. Limited rights for women in the past, the current legal status of homosexual activity and a rise in police brutality are the practical manifestations of these stigmas. While the UN has exercised its authority to issue resolutions protecting the rights of these communities, the problems of discrimination remain unresolved.\n" +
//                                "\n" +
//                                "In the 14th Edition of the IIT Guwahati Model United Nations, we bring to you as part of the UNHRC the agenda - Dissecting Discrimination: Assessing racial, ethnic, and gender biases in society.",
//                        genre = listOf("music"),
//                ),*/
//////                eventdetail(
//////                        "Panel Discussion Day1",
//////                        "North East Unveil",
//////                        type = "Campaigns",
//////                        starttime = OwnTime(12,14,0) ,
//////                        durationInMin =60,
//////                        venue = "Campaigns",
//////                        imgurl = "",
//////                        descriptionEvent="",
//////                        genre = listOf("music"),
//////                ),
//////                eventdetail(
//////                        "Performance Day1",
//////                        "North East Unveil",
//////                        type = "Campaigns",
//////                        starttime = OwnTime(12,15,0) ,
//////                        durationInMin =120,
//////                        venue = "Campaigns",
//////                        imgurl = "",
//////                        descriptionEvent="",
//////                        genre = listOf("music"),
//////                ),
//////                eventdetail(
//////                        "Short Movie Screening Day2",
//////                        "North East Unveil",
//////                        type = "Campaigns",
//////                        starttime = OwnTime(13,10,0) ,
//////                        durationInMin =60,
//////                        venue = "Campaigns",
//////                        imgurl = "",
//////                        descriptionEvent="",
//////                        genre = listOf("music"),
//////                ),
//////                eventdetail(
//////                        "Policy Maker Presentation  Day2",
//////                        "North East Unveil",
//////                        type = "Campaigns",
//////                        starttime = OwnTime(13,12,0) ,
//////                        durationInMin =120,
//////                        venue = "Campaigns",
//////                        imgurl = "",
//////                        descriptionEvent="",
//////                        genre = listOf("music"),
//////                ),
//////                eventdetail(
//////                        "Panel Discussion  Day2",
//////                        "North East Unveil",
//////                        type = "Campaigns",
//////                        starttime = OwnTime(13,14,0) ,
//////                        durationInMin =60,
//////                        venue = "Campaigns",
//////                        imgurl = "",
//////                        descriptionEvent="",
//////                        genre = listOf("music"),
//////                ),
//////                eventdetail(
//////                        "Performance  Day2",
//////                        "North East Unveil",
//////                        type = "Campaigns",
//////                        starttime = OwnTime(13,15,0) ,
//////                        durationInMin =120,
//////                        venue = "Campaigns",
//////                        imgurl = "",
//////                        descriptionEvent="",
//////                        genre = listOf("music"),
//////                ),
//////                eventdetail(
//////                        "Saaz",
//////                        "Pronites",
//////                        type = "Pronites",
//////                        starttime = OwnTime(11,20,0) ,
//////                        durationInMin =90,
//////                        venue = "Auditorium 1",
//////                        imgurl = "",
//////                        descriptionEvent="",
//////                        genre = listOf("music"),
//////                ),
//////                eventdetail(
//////                "Pronite 2",
//////                "Pronites",
//////                type = "Pronites",
//////                starttime = OwnTime(12,19,0) ,
//////                durationInMin =120,
//////                venue = "" ,
//////                imgurl = "",
//////                descriptionEvent="",
//////                    genre = listOf("music"),
//////            ),
//////                eventdetail(
//////                        "Pronite 3",
//////                        "Pronites",
//////                        type = "Pronites",
//////                        starttime = OwnTime(13,19,0) ,
//////                        durationInMin =120,
//////                        venue = "" ,
//////                        imgurl = "",
//////                        descriptionEvent="",
//////                        genre = listOf("music"),
//////                ),
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
            )
//
        pushEvents(newEvents);
//    }
    }
}
