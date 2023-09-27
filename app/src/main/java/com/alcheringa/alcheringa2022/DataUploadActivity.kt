package com.alcheringa.alcheringa2022

import android.media.audiofx.DynamicsProcessing
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alcheringa.alcheringa2022.Model.OwnTime
import com.alcheringa.alcheringa2022.Model.eventdetail
import com.alcheringa.alcheringa2022.Model.sponsersnew
import com.alcheringa.alcheringa2022.Model.venue
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
//            eventdetail(
//                artist = "Gully Cricket Day 3",
//                category = "Competitions",
//                starttime = OwnTime(5, 9, 0),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2023%2Fgullycricket.png?alt=media&token=2d5d164e-4b01-46ef-9e4c-9286e7e0f1da",
//                durationInMin = 480,
//                genre = listOf(),
//                descriptionEvent = "This takes you to your childhood when you have played cricket in streets with your friends. Alcheringa gives you a chance to remember and cherish these child memories and again.",
//                venue = "Behind Graffiti Wall",
//                type = "Sports",
//                reglink = "https://registrations.alcheringa.in/"
//            ),
//            eventdetail(
//                artist = "Rock-O-Phonix Day 3",
//                category = "Competitions",
//                starttime = OwnTime(5, 8, 30),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2023%2Frockophonix.png?alt=media&token=1ba3deb5-d70b-4bae-b9da-d6096e63d9b3",
//                durationInMin = 630,
//                genre = listOf(),
//                descriptionEvent = "Note: Venue is opposite to Pronite Stage. As the shredding of the guitars and the beating of the drums picks up steam, heads bang along to the music. A highlight of the festival and an emblem of the talent suffused in the many faces in the crowd, this is one event to hold your breath for.Rock-O-Phonix, the holy grail of rock! The winner takes all...!",
//                venue = "Pronite Stage",
//                type = "Class Apart",
//                reglink = "https://registrations.alcheringa.in/"
//            ),
//            eventdetail(
//                artist = "MUN Day 3",
//                category = "Competitions",
//                starttime = OwnTime(5, 9, 0),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2023%2Fbusinessquiz.png?alt=media&token=c6ab82f7-6b59-4d60-ac72-0bc4147b685b",
//                durationInMin = 480,
//                genre = listOf(),
//                descriptionEvent = "Have you ever seen politicians squabble on TV, and thought to yourself, \"I could do a much better job, if only I had this kind of power”? IITG MUN gives you the chance to see for yourself.",
//                venue = "Conference Hall 1",
//                type = "Model United Nations",
//                reglink = "https://registrations.alcheringa.in/"
//            ),
//            eventdetail(
//                artist = "Parliamentary Debate Day 3",
//                category = "Competitions",
//                starttime = OwnTime(4, 9, 0),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2023%2Fbusinessquiz.png?alt=media&token=c6ab82f7-6b59-4d60-ac72-0bc4147b685b",
//                durationInMin = 600,
//                genre = listOf(),
//                descriptionEvent = "Do you feel the urge to speak up at every issue, do a logic check at every fact, present it with your own reasoning and stand for it till the end...? Wait no more, Alcheringa gives you an opportunity to let speak your words.",
//                venue = "Core 1",
//                type = "Literary",
//                reglink = "https://registrations.alcheringa.in/"
//            ),
////            eventdetail(
////                artist = "Nirantar",
////                category = "Kartavya",
////                starttime = OwnTime(4, 14, 0),
////                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2023%2Fbusinessquiz.png?alt=media&token=c6ab82f7-6b59-4d60-ac72-0bc4147b685b",
////                durationInMin = 60,
////                genre = listOf(),
////                descriptionEvent = "Dance performance showcasing Northeastern culture and its rich",
////                venue = "NEU Stage, Front of Audi",
////                type = "NE-Expo",
////                reglink = ""
////            ),
//            eventdetail(
//                artist = "Haute Couture Day 3",
//                category = "Competitions",
//                starttime = OwnTime(5, 11, 30),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2023%2Fhautecouture.png?alt=media&token=d2915763-d392-4243-82aa-b40ada4401d0",
//                durationInMin = 150,
//                genre = listOf(),
//                descriptionEvent = "Ever felt so thunder seeing someone so immaculately dressed that you run out of cheers and words. Haute Couture, where the most glamorous people show off their skills as they walk the ramp, a fashion show evoking all those creative peeps to put on their thinking caps and come out with the most innovative theme-based fashion show.",
//                venue = "Auditorium",
//                type = "Vogue Nation",
//                reglink = "https://registrations.alcheringa.in/"
//            ),
//            eventdetail(
//                artist = "Dancing Duo Finals",
//                category = "Competitions",
//                starttime = OwnTime(5, 12, 0),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2023%2Fdancingduo.png?alt=media&token=5a444426-d5e3-4161-9588-0cd120c2f714",
//                durationInMin = 120,
//                genre = listOf(),
//                descriptionEvent = "We dance for laughter, we dance for tears, we dance for madness, we dance for fears, we dance for hopes, we dance for screams, we are the dancers, we create the dreams.Alcheringa gives you the chance to create these dreams with your partner.",
//                venue = "Mini Auditorium",
//                type = "Anybody Can Dance",
//                reglink = "https://registrations.alcheringa.in/"
//            ),
//            eventdetail(
//                artist = "Poetry Slam (English)",
//                category = "Competitions",
//                starttime = OwnTime(5, 9, 0),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2023%2Fpoetryslam.png?alt=media&token=bdd7ee93-9b6d-4a28-8e76-b08f3ba63824",
//                durationInMin = 360,
//                genre = listOf(),
//                descriptionEvent = "Poetry Competition" ,
//                venue = "Lecture Hall 2",
//                type = "Literary",
//                reglink = "https://registrations.alcheringa.in/"
//            ),
            eventdetail(
                artist = "Rap Battle",
                category = "Competitions",
                starttime = OwnTime(5, 9, 0),
                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Competition%2FRap%20Battle_new.jpg?alt=media&token=0ab0e0a4-6206-4dd3-8cfb-1dbb82c93e61",
                durationInMin = 360,
                genre = listOf(),
                descriptionEvent = "Get ready to ignite the stage with fiery rhymes, as we bring you the ultimate Alcheringa Rap Battle! Grab the mic and show the crowd your lyrical prowess, as you face off against other talented MCs in a battle for supremacy. With beats that'll make your heart race and rhymes that'll leave the audience in awe, this is an event you don't want to miss. So, grab your friends, bring your A-game, and get ready to show the world what you're made of. It's time to put your skills to the test, in the hottest rap battle of Alcheringa!",
                venue = "Expo Stage",
                type = "Music",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Aerreo",
                category = "pronites",
                starttime = OwnTime(5, 19, 30),
                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/events2023%2FAerreo.png?alt=media&token=74c8ed98-aeca-4ad2-8253-0609600579f9",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "Get ready for an explosive night of high-energy beats and electrifying performances, as Aerreo takes the stage for the Blitzkrieg Event! This talented DJ and producer is known for his innovative sound, blending elements of trance, techno, and house to create an unforgettable experience. With his electrifying sets, he'll get the crowd moving and leave everyone on the edge of their seats. So come and join us for a night of non-stop beats and bass, as Aerreo brings the house down in this epic event. Get ready to experience the ultimate blitzkrieg of sound and fury",
                venue = "Pronite Stage",
                type = "Pronites",
                reglink = ""
            ),
            eventdetail(
                artist = "KTM Bike Stunt",
                category = "Other",
                starttime = OwnTime(5, 16, 0),
                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/events2023%2FKTM_Bike.png?alt=media&token=1128de51-b085-40c8-8d95-4b0d989badb5",
                durationInMin = 120,
                genre = listOf(),
                descriptionEvent = "Alcheringa, in association with KTM, invites you to experience the mind blowing stunt riding and tricks from the best stunt riders. See them perform live and experience the action with friends.",
                venue = "Old Sac Wall",
                type = "OTHER EVENTS",
                reglink = "https://registrations.alcheringa.in/"
            ),






            )

        pushEvents(newEvents)
    }
}
