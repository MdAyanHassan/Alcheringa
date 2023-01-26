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
                    reglink = "https://registrations.alcheringa.in/"
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
                reglink = "https://registrations.alcheringa.in/"
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
                reglink = "https://registrations.alcheringa.in/"
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
                artist = "Rock-O-Phonix",
                category = "Competition",
                starttime = OwnTime(3, 8, 0),
                imgurl = "",
                durationInMin = 570,
                genre = listOf(),
                descriptionEvent = "As the shredding of the guitars and the beating of the drums" +
                        "picks up steam, heads bang along to the music. A highlight" +
                        "of the festival and an emblem of the talent suffused in the" +
                        "many faces in the crowd, this is one event to hold your breath" +
                        "for." +
                        "\n" +
                        "Rock-O-Phonix, the holy grail of rock! The winner takes all...!",
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
                descriptionEvent = "One of the most anticipated events of the year, brings you one" +
                        "\n" +
                        "of its own kind personality competition. Are you up for the" +
                        "challenge? Alcheringa awaits to accolade its next Mr" +
                        "\n" +
                        "Alcheringa and Ms Alcheringa...!!",
                venue = "Lecture Hall 1",
                type = "Class Apart",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Crossfade",
                category = "Competition",
                starttime = OwnTime(3, 9, 30),
                imgurl = "",
                durationInMin = 420,
                genre = listOf(),
                descriptionEvent = "“Music is How we speak without ever moving our Lips.” The" +
                        "masters of the mood, the creators of the music meet up on a" +
                        "stage to put on a show like no one else! Crossfade! The Battle" +
                        "of DJ’s... Let the Music Speak!!",
                venue = "Old Sac Wall",
                type = "Class Apart",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Alcher Con",
                category = "Competition",
                starttime = OwnTime(5,2,0),
                imgurl = "",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "Cosplay Competition",
                venue = "Library Shed",
                type = "",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Theatrix",
                category = "Competition",
                starttime = OwnTime(3,8,30),
                imgurl = "",
                durationInMin = 240,
                genre = listOf(),
                descriptionEvent = "The plethora of expressions, diversity of unimaginable" +
                        "\n" +
                        "characters, red of the blood all to impress you! Actors from the" +
                        "region will enliven the stage and perform their hearts out to" +
                        "stand victorious in this tough clash of theatre-acting." +
                        "Welcome to the Stage play competition!",
                venue = "Auditorium",
                type = "Stagecraft",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Halla Boll",
                category = "Competition",
                starttime = OwnTime(5,10,30),
                imgurl = "",
                durationInMin = 270,
                genre = listOf(),
                descriptionEvent = "The eloping beats of nagadas and dholkis filling the" +
                        "atmosphere with hysterical enthusiasm are sure to give one" +
                        "\n" +
                        "dose of live action and stunts amidst a live crowd. The" +
                        "thunderous performances have questions that need to be" +
                        "\n" +
                        "answered, and answers that will leave you baffled. Halla Bol" +
                        "(Nukkad Natak), a form of theatre which aims at spreading" +
                        "social awareness with style!",
                venue = "Audi Park",
                type = "Stagecraft",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Mono Drama",
                category = "Competition",
                starttime = OwnTime(5,9,0),
                imgurl = "",
                durationInMin = 120,
                genre = listOf(),
                descriptionEvent = "It is a one-man/woman show where only one person has to" +
                        "act. Mono acting refers to a single actor playing multiple roles." +
                        "There is only one actor on stage during mono acting.",
                venue = "Mini Auditorium",
                type = "Stagecraft",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Electic Heels",
                category = "Competition",
                starttime = OwnTime(3,4,30),
                imgurl = "",
                durationInMin = 120,
                genre = listOf(),
                descriptionEvent = "“Dance is a way to find yourself and lose yourself at the same" +
                        "time.” A signature event of Alcheringa, Electric heels is a" +
                        "group dance competition, featuring the most talented crews" +
                        "across the nation. Your team, your grooves, your beats, our" +
                        "arena. Are you up for the challenge?",
                venue = "Auditorium",
                type = "Anybody Can Dance",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Step Up",
                category = "Competition",
                starttime = OwnTime(4,12,0),
                imgurl = "",
                durationInMin = 270,
                genre = listOf(),
                descriptionEvent = "Why fight when you can dance? Introducing the most" +
                        "exhilarating, vigorous and effervescent face-off you can" +
                        "imagine! The mean and tough streets are unforgiving. Bring" +
                        "out your game face and show us what you got! Presenting" +
                        "Step Up, Dance face-off!",
                venue = "Old Sac Wall",
                type = "Anybody Can Dance",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Dancing Duo",
                category = "Competition",
                starttime = OwnTime(4,12,0),
                imgurl = "",
                durationInMin = 210,
                genre = listOf(),
                descriptionEvent = "We dance for laughter, we dance for tears, we dance for" +
                        "madness, we dance for fears, we dance for hopes, we dance" +
                        "\n" +
                        "for screams, we are the dancers, we create the dreams." +
                        "Alcheringa gives you the chance to create these dreams with" +
                        "your partner.",
                venue = "Mini Auditorium",
                type = "Anybody Can Dance",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "SUTUCD",
                category = "Competition",
                starttime = OwnTime(3,8,30),
                imgurl = "",
                durationInMin = 330,
                genre = listOf(),
                descriptionEvent = "“Every day brings a chance for you to draw in a breath, kick off" +
                        "your shoes and dance”. Who will be the one? The champion?" +
                        "The one who thinks he can dance!",
                venue = "Mini Auditorium",
                type = "Anybody Can Dance",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Navra",
                category = "Competition",
                starttime = OwnTime(3,2,30),
                imgurl = "",
                durationInMin = 210,
                genre = listOf(),
                descriptionEvent = "This is what takes us to our roots. Alcheringa strives to give" +
                        "Indian classical dancers a bigger and a better stage with each" +
                        "\n" +
                        "upcoming edition. Prepare yourselves to witness some of the" +
                        "most intricate rhythms, footwork and spins to have graced this" +
                        "planet!",
                venue = "Mini Auditorium",
                type = "Anybody Can Dance",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Unplugged",
                category = "Competition",
                starttime = OwnTime(3,8,30),
                imgurl = "",
                durationInMin = 330,
                genre = listOf(),
                descriptionEvent = "Witness the notes being charged up without power, when" +
                        "Alcheringa brings an event without electrical amplification.",
                venue = "Expo Stage",
                type = "Music",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "VOA",
                category = "Competition",
                starttime = OwnTime(5,8,0),
                imgurl = "",
                durationInMin = 480,
                genre = listOf(),
                descriptionEvent = "This event is for the lone wolves unafraid to showcase their" +
                        "unique voices.We call out to all the nightingales to soar high" +
                        "this Alcheringa.May the most melodious master prevail!",
                venue = "Lecture Hall 4",
                type = "Music",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "MUN",
                category = "Competition",
                starttime = OwnTime(3,8,0),
                imgurl = "",
                durationInMin = 480,
                genre = listOf(),
                descriptionEvent = "Have you ever seen politicians squabble on TV, and thought" +
                        "to yourself, \"I could do a much better job, if only I had this kind" +
                        "of power”? IITG MUN gives you the chance to see for yourself.",
                venue = "Conference Hall 1",
                type = "Model United Nation",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Parliamentary Debate",
                category = "Competition",
                starttime = OwnTime(3,8,0),
                imgurl = "",
                durationInMin = 570,
                genre = listOf(),
                descriptionEvent = "Do you feel the urge to speak up at every issue, do a logic" +
                        "check at every fact, present it with your own reasoning and" +
                        "\n" +
                        "stand for it till the end...? Wait no more, Alcheringa gives you" +
                        "an opportunity to let speak your words.",
                venue = "Core 5",
                type = "Literary",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Poetry Slam",
                category = "Competition",
                starttime = OwnTime(4, 11,0),
                imgurl = "",
                durationInMin = 240,
                genre = listOf(),
                descriptionEvent = "Poetry Competition",
                venue = "Lecture Hall 2",
                type = "Literary",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Mehfil-e-Alcheringa",
                category = "Competition",
                starttime = OwnTime(4,8,0),
                imgurl = "",
                durationInMin = 480,
                genre = listOf(),
                descriptionEvent = "Shayari Competition",
                venue = "Lecture Hall 3",
                type = "Literary",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Clay Modeling",
                category = "Competition",
                starttime = OwnTime(3,12,0),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "“Love to play with clay? This is your chance to make models" +
                        "with the help of clay.”",
                venue = "Library Basement",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Ink The Tree",
                category = "Competition",
                starttime = OwnTime(3,9,30),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "T-shirt Painting Competition",
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Face Art",
                category = "Competition",
                starttime = OwnTime(4,9,30),
                imgurl = "",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "Face Painting Competition",
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Graffiti",
                category = "Competition",
                starttime = OwnTime(4,12,0),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "Graffiti are writing or drawings that have been scribbled," +
                        "scratched or painted typically illicitly on a wall or other" +
                        "surface.",
                venue = "Library Basement",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Rangoli",
                category = "Competition",
                starttime = OwnTime(2,2,30),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "Rangoli Making Competition",
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Who Is It?",
                category = "Competition",
                starttime = OwnTime(4,3,30),
                imgurl = "",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "“The artist is a receptacle for emotions that come from all over" +
                        "the place:from the sky, from the earth, from a scrap of paper," +
                        "from a spider’s web.” -Pablo Picasso",
                venue = "Library Shed",
                type = "Art Talkies",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "5 on 5 Football",
                category = "Competition",
                starttime = OwnTime(3,9,0),
                imgurl = "",
                durationInMin = 180,
                genre = listOf(),
                descriptionEvent = "This shorter version of Football is your shot at glory and a" +
                        "chance for you to vindicate your love for the game. Held in" +
                        "\n" +
                        "one of the football hotspots of the country, this 5-on-5" +
                        "competition will put your mettle and skills to test unlike any" +
                        "other. Come, play your best shot!",
                venue = "Football Ground",
                type = "Sports",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Arm Wrestling",
                category = "Competition",
                starttime = OwnTime(4,9,0),
                imgurl = "",
                durationInMin = 270,
                genre = listOf(),
                descriptionEvent = "On that Sunday evening before supper at your friends place," +
                        "or on that holiday you had with your cousins, or in between" +
                        "classes when you had nothing better to do and sought a way" +
                        "to become the cynosure of the class, and before you lost your" +
                        "way in the winding alleys of studies, college and life, what did" +
                        "\n" +
                        "you do? You Arm-Wrestled! Alcheringa gives you a chance to" +
                        "redeem yourself and win your muscles’ worth." +
                        "\n" +
                        "There will be 6 categories:" +
                        "\n" +
                        "(<55, 55-60, 60-65, 65-70, 70-75, >75)",
                venue = "Front of Graffiti Wall",
                type = "Sports",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "3 on 3 Basketball",
                category = "Competition",
                starttime = OwnTime(3,9,0),
                imgurl = "",
                durationInMin = 510,
                genre = listOf(),
                descriptionEvent = "This shorter version of Basketball is your shot at glory and a" +
                        "chance for you to vindicate your love for the game. Come," +
                        "play your best shot",
                venue = "Basketball Courts",
                type = "Sports",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Gully Cricket",
                category = "Competition",
                starttime = OwnTime(3,9,0),
                imgurl = "",
                durationInMin = 480,
                genre = listOf(),
                descriptionEvent = "This takes you to your childhood when you have played" +
                        "\n" +
                        "cricket in streets with your friends. Alcheringa gives you a" +
                        "chance to remember and cherish these child memories and" +
                        "\n" +
                        "again.",
                venue = "Behind Graffiti Wall",
                type = "Sports",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "3 on 3 Volleyball",
                category = "Competition",
                starttime = OwnTime(3,10,0),
                imgurl = "",
                durationInMin = 450,
                genre = listOf(),
                descriptionEvent = "This shorter version of Volleyball is your shot at glory and a" +
                        "chance for you to vindicate your love for the game. Come," +
                        "play your best shot!",
                venue = "Volleyball Court",
                type = "Sports",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Business Quiz",
                category = "Competition",
                starttime = OwnTime(4,10,0),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Lecture Hall 3",
                type = "Quiz",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "General Quiz",
                category = "Competition",
                starttime = OwnTime(4,13,30),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Lecture Hall 3",
                type = "Quiz",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "Sports Quiz",
                category = "Competition",
                starttime = OwnTime(5,9,0),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Lecture Hall 2",
                type = "Quiz",
                reglink = "https://registrations.alcheringa.in/"
            ),
            eventdetail(
                artist = "India Quiz",
                category = "Competition",
                starttime = OwnTime(5,12,30),
                imgurl = "",
                durationInMin = 150,
                genre = listOf(),
                descriptionEvent = "",
                venue = "Lecture Hall 2",
                type = "Quiz",
                reglink = "https://registrations.alcheringa.in/"
            ),


            )

        //pushEvents(newEvents)
    }
}
