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
                fb.collection("AllEvents2024").document(evnt.artist + " 2024").set(evnt).addOnSuccessListener {
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
//            eventdetail(
//                artist = "Rap Battle",
//                category = "Competitions",
//                starttime = OwnTime(5, 9, 0),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/Competition%2FRap%20Battle_new.jpg?alt=media&token=0ab0e0a4-6206-4dd3-8cfb-1dbb82c93e61",
//                durationInMin = 360,
//                genre = listOf(),
//                descriptionEvent = "Get ready to ignite the stage with fiery rhymes, as we bring you the ultimate Alcheringa Rap Battle! Grab the mic and show the crowd your lyrical prowess, as you face off against other talented MCs in a battle for supremacy. With beats that'll make your heart race and rhymes that'll leave the audience in awe, this is an event you don't want to miss. So, grab your friends, bring your A-game, and get ready to show the world what you're made of. It's time to put your skills to the test, in the hottest rap battle of Alcheringa!",
//                venue = "Expo Stage",
//                type = "Music",
//                reglink = "https://registrations.alcheringa.in/"
//            ),
//            eventdetail(
//                artist = "Aerreo",
//                category = "pronites",
//                starttime = OwnTime(5, 19, 30),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/events2023%2FAerreo.png?alt=media&token=74c8ed98-aeca-4ad2-8253-0609600579f9",
//                durationInMin = 150,
//                genre = listOf(),
//                descriptionEvent = "Get ready for an explosive night of high-energy beats and electrifying performances, as Aerreo takes the stage for the Blitzkrieg Event! This talented DJ and producer is known for his innovative sound, blending elements of trance, techno, and house to create an unforgettable experience. With his electrifying sets, he'll get the crowd moving and leave everyone on the edge of their seats. So come and join us for a night of non-stop beats and bass, as Aerreo brings the house down in this epic event. Get ready to experience the ultimate blitzkrieg of sound and fury",
//                venue = "Pronite Stage",
//                type = "Pronites",
//                reglink = ""
//            ),
//            eventdetail(
//                artist = "KTM Bike Stunt",
//                category = "Other",
//                starttime = OwnTime(5, 16, 0),
//                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/events2023%2FKTM_Bike.png?alt=media&token=1128de51-b085-40c8-8d95-4b0d989badb5",
//                durationInMin = 120,
//                genre = listOf(),
//                descriptionEvent = "Alcheringa, in association with KTM, invites you to experience the mind blowing stunt riding and tricks from the best stunt riders. See them perform live and experience the action with friends.",
//                venue = "Old Sac Wall",
//                type = "OTHER EVENTS",
//                reglink = "https://registrations.alcheringa.in/"
//            ),
            eventdetail(
                artist = "Theatrix Prelims",
                category = "Competitions",
                starttime = OwnTime(8, 8, 30),
                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Ftheatrix.jpg?alt=media&token=28140bdb-88b6-4c09-aa66-630854e16581",
                durationInMin = 270,
                genre = listOf(),
                descriptionEvent = "The plethora of expressions, diversity of unimaginable characters, red of " +
                        "the blood all to impress you! Actors from the region will enliven the stage " +
                        "and perform their hearts out to stand victorious in this tough clash of " +
                        "theatre-acting. Welcome to the Stage play competition!",
                venue = "Auditorium",
                type = "Stagecraft",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Electric Heels Prelims",
                category = "Competitions",
                starttime = OwnTime(8,16 , 30),
                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Felectric_heels.jpg?alt=media&token=d10396ce-39fb-45e5-a39e-cea829843b96",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "'Dance is a way to find yourself and lose yourself at the same time.' A " +
                "signature event of Alcheringa, Electric heels is a group dance " +
                        "competition, featuring the most talented crews across the nation. Your " +
                        "team, your grooves, your beats, our arena. Are you up for the " +
                        "challenge?",
                venue = "Auditorium",
                type = "Any body can dance",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Live Sketching",
                category = "Competitions",
                starttime = OwnTime(8,9 , 0),
                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Flive_sketching.jpg?alt=media&token=d0d883cd-f4ae-4e3d-bac5-7289bd99aed5",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "Witness the excitement of live sketching as artists compete to bring their creativity to life. Experience a fusion of emotions, diverse characters, and vibrant colors on canvas. Welcome to a captivating showcase where artistry meets competition.",
                venue = "Entire Campus",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "NAVRAS Prelims",
                category = "Competitions",
                starttime = OwnTime(8,9 , 30),
                imgurl = "https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fnavras.jpg?alt=media&token=9eb5f57f-92da-4ed0-9316-be3aa871d8db",
                durationInMin = 240,
                genre = listOf(),
                descriptionEvent = "This is what takes us to our roots. Alcheringa strives to give Indian " +
                        "classical dancers a bigger and a better stage with each upcoming " +
                        "edition. Prepare yourselves to witness some of the most intricate rhythms, " +
                        "footwork and spins to have graced this planet!",
                venue = "Mini Auditorium",
                type = "Any body can dance",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "SUTUCD Prelims",
                category = "Competitions",
                starttime = OwnTime(8,13 , 30),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fsutucd.jpg?alt=media&token=f06d6944-d34e-4898-9e92-cb10b9d5f0dd",
                durationInMin = 300,
                genre = listOf(),
                descriptionEvent = "Get ready to unleash your passion for dance at the SUTUCD Non-Classical Solo Dance Competition! Channeling the spirit of freedom and expression, dancers will vie for the spotlight and the title of champion. Embrace the rhythm, let go of inhibitions, and dance like never before.",
                venue = "Mini Auditorium",
                type = "Any body can dance",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Unplugged Prelims",
                category = "Competitions",
                starttime = OwnTime(8,12 , 30),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Funplugged.jpg?alt=media&token=e86d5da4-988d-4452-b17a-82cb835bd46a",
                durationInMin = 360,
                genre = listOf(),
                descriptionEvent = "Experience the magic of unplugged music at the Acoustic Band Competition during Alcheringa! Feel the raw energy as talented musicians take the stage without the need for electrical amplification. With a diverse array of instruments and compulsory vocals, each performance promises a unique and soul-stirring experience.",
                venue = "Expo Stage",
                type = "Music",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Rangoli",
                category = "Competitions",
                starttime = OwnTime(8,9 , 0),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Frangoli.jpg?alt=media&token=0575415a-571c-46c1-9acc-02369ed6c987",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "Immerse yourself in the vibrant world of colors at the Rangoli Making Competition during Alcheringa! From intricate patterns to mesmerizing designs, witness the beauty unfold as participants bring their creativity to life using colorful powders and grains.",
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Ink the tee",
                category = "Competitions",
                starttime = OwnTime(8,14 , 30),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fink_the_tee.jpg?alt=media&token=ad3a16c7-012c-4d76-859d-dd6c65d18f62",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "Unleash your creativity and make a statement with Ink The Tee, a dynamic T-Shirt Painting Competition at Alcheringa! Dive into a world of colors, patterns, and designs as participants transform blank canvases into wearable works of art."
                        ,
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Clay Modelling",
                category = "Competitions",
                starttime = OwnTime(8,12 , 0),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fclay_modelling.jpg?alt=media&token=d58c773f-644c-4168-96f2-242804b15e42",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "Love to play with clay? This is your chance to make models with the help " +
                        "of clay."
                ,
                venue = "Library Basement",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Poetry Slam (English)",
                category = "Competitions",
                starttime = OwnTime(8,9 , 0),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fpoetry_slam.jpg?alt=media&token=eb13737b-11c8-4064-a975-8673afcc9e2a",
                durationInMin = 360,
                genre = listOf(),
                descriptionEvent = "Prepare to be moved and inspired as poets take the stage to share their soul-stirring verses at the Poetry Slam! With each word, they paint pictures, evoke emotions, and ignite imaginations, all in the pursuit of poetic excellence."
                ,
                venue = "Lecture Hall 1",
                type = "Literary",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Sports Quiz",
                category = "Competitions",
                starttime = OwnTime(8,9 , 30),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fquiz.jpg?alt=media&token=15fa4562-9c45-41db-9e7e-6d53d3528120",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "Attention all sports enthusiasts and trivia buffs! It's time to put your knowledge to the test at the Sports Quiz Competition during Alcheringa! Join us for an exhilarating journey through the world of sports, where participants compete in a battle of wits and expertise."
                ,
                venue = "Lecture Hall 2",
                type = "Quiz",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Business Quiz",
                category = "Competitions",
                starttime = OwnTime(8,13 , 30),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fquiz.jpg?alt=media&token=15fa4562-9c45-41db-9e7e-6d53d3528120",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "Attention all sports enthusiasts and trivia buffs! It's time to put your knowledge to the test at the Sports Quiz Competition during Alcheringa! Join us for an exhilarating journey through the world of sports, where participants compete in a battle of wits and expertise."
                ,
                venue = "Lecture Hall 2",
                type = "Quiz",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Mr & Ms Alcheringa Prelims",
                category = "Competitions",
                starttime = OwnTime(8,10 , 0),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fmr_ms_alcher.jpg?alt=media&token=e360f8d1-2279-4195-9a43-f35778cd1a56",

                durationInMin = 420,
                genre = listOf(),
                descriptionEvent = "One of the most anticipated events of the year, brings you one of its own " +
                        "kind personality competition. Are you up for the challenge? Alcheringa " +
                        "awaits to accolade its next Mr Alcheringa and Ms Alcheringa...!!"
                ,
                venue = "Lecture Hall 3",
                type = "Class Apart",
                reglink = "https://registrations.alcheringa.in/"
            ),

            eventdetail(
                artist = "Rock-O-Phonix Day 1",
                category = "Competitions",
                starttime = OwnTime(8,8 , 0),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Frock_o_phonix.jpg?alt=media&token=6ab0761e-f61f-40b8-bcce-c3f1a143f9d6",

                durationInMin = 600,
                genre = listOf(),
                descriptionEvent = "As the Shredding of the guitars and the beating of the drums picks up " +
                        "steam, heads bang along to the music. A highlight of the festival and an " +
                        "emblem of the talent suffused in the many faces in the crowd, this is one " +
                        "event to hold your breath for Rock-O-Phonix, the holy grail of rock! The " +
                        "winner takes all...!"
                ,
                venue = "Rocko Stage",
                type = "Class Apart",
                reglink = "https://registrations.alcheringa.in/"
            ),

            eventdetail(
                artist = "MUN Day 1",
                category = "Competitions",
                starttime = OwnTime(8,9 , 0),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fmun.jpg?alt=media&token=11e281d1-5df7-4bfe-b3df-cb6e714698c7",

                durationInMin = 510,
                genre = listOf(),
                descriptionEvent = "Have you ever seen politicians squabble on TV, and thought to yourself, \\\"I could do a much better job, if only I had this kind of power”? IITG MUN gives you the chance to see for yourself."
                ,
                venue = "Conference Hall 1",
                type = "Model United Nations",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "MUN Day 2",
                category = "Competitions",
                starttime = OwnTime(9,9 , 0),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fmun.jpg?alt=media&token=11e281d1-5df7-4bfe-b3df-cb6e714698c7",

                durationInMin = 510,
                genre = listOf(),
                descriptionEvent = "Have you ever seen politicians squabble on TV, and thought to yourself, \\\"I could do a much better job, if only I had this kind of power”? IITG MUN gives you the chance to see for yourself."
                ,
                venue = "Conference Hall 1",
                type = "Model United Nations",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "MUN Day 3",
                category = "Competitions",
                starttime = OwnTime(10,9 , 0),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fmun.jpg?alt=media&token=11e281d1-5df7-4bfe-b3df-cb6e714698c7",
                durationInMin = 480,
                genre = listOf(),
                descriptionEvent = "Have you ever seen politicians squabble on TV, and thought to yourself, \\\"I could do a much better job, if only I had this kind of power”? IITG MUN gives you the chance to see for yourself."
                ,
                venue = "Conference Hall 1",
                type = "Model United Nations",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Parliamentary Debate Day 1",
                category = "Competitions",
                starttime = OwnTime(8,8 , 0),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fparliamentary_debate.jpg?alt=media&token=e9b7854f-4966-4282-8b60-1aa297aa0f86",
                durationInMin = 600,
                genre = listOf(),
                descriptionEvent = "Do you feel the urge to speak up at every issue, do a logic check at " +
                        "every fact, present it with your own reasoning and stand for it till the " +
                        "end…? Wait no more, Alcheringa gives you an opportunity to let you " +
                        "speak your words."
                ,
                venue = "Core 5",
                type = "Literary",
                reglink = "https://registrations.alcheringa.in/"
            ),

            eventdetail(
                artist = "Crossfade Prelims",
                category = "Competitions",
                starttime = OwnTime(8,8 , 30),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fcrossfade.jpg?alt=media&token=a2beaf45-9ea0-470d-bf11-7d27068ffbf7",

                durationInMin = 300,
                genre = listOf(),
                descriptionEvent = "'Music is How we speak without ever moving our Lips.' The masters of " +
                        "the mood, the creators of the music meet up on a stage to put on a show" +
                        "like no one else! Crossfade! The Battle of DJ’s... Let the Music Speak!!"
                ,
                venue = "Old Sac Stage",
                type = "Class Apart",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Crossfade Finals",
                category = "Competitions",
                starttime = OwnTime(8,14 , 30),
                imgurl="https://firebasestorage.googleapis.com/v0/b/alcheringa2022.appspot.com/o/competitions2024%2Fcrossfade.jpg?alt=media&token=a2beaf45-9ea0-470d-bf11-7d27068ffbf7",

                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "'Music is How we speak without ever moving our Lips.' The masters of " +
                        "the mood, the creators of the music meet up on a stage to put on a show" +
                        "like no one else! Crossfade! The Battle of DJ’s... Let the Music Speak!!"
                ,
                venue = "Old Sac Stage",
                type = "Class Apart",
                reglink = "https://registrations.alcheringa.in/"
            ),








            )

        pushEvents(newEvents)
    }
}
