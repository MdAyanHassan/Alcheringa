# 🎪 Alcheringa  
**Official Cultural Fest App of IIT Guwahati**  

![Downloads](https://img.shields.io/badge/Downloads-5K+-brightgreen?logo=google-play)  
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple?logo=kotlin)  
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-1.6-blue?logo=jetpack-compose)  
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?logo=firebase)  

The official app for IIT Guwahati's annual cultural fest, featuring real-time updates and retro pixel-art UI.

---

## ✨ Features  
- **Real-time Event Updates**: Instant notifications for schedule changes  
- **Nostalgic Pixel UI**: Retro-themed interface with modern animations   
- **Secure Auth**: Firebase-powered login system  

---

## 🛠 Tech Stack  
| Layer          | Technology               |
|----------------|--------------------------|
| **Frontend**   | Jetpack Compose (Android) + Flutter (iOS) |
| **Architecture** | MVVM with Clean Architecture |
| **Backend**    | Firebase (Firestore, Auth, Cloud Functions) |

---

## ⚡ Setup & Build  

### Option 1: Android Studio  
1. Open project in Android Studio  
2. Click "Run" → Select device/emulator  

### Option 2: Command Line (Gradle)  
```bash
# Clone repository
git clone https://github.com/Alcheringa-Web-and-App-Operations/Alcheringa2022.git
cd app

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# For release build (requires signing config)
./gradlew assembleRelease
