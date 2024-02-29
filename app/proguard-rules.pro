# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**
-keep class org.osgi.framework.** { *; }
-dontwarn org.osgi.framework.**
-keep class com.google.crypto.tink.** { *; }
-dontwarn com.google.crypto.tink.**

-keep class edu.umd.cs.findbugs.annotations.SuppressFBWarnings { *; }
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings
-keep class javax.activation.DataSource { *; }
-dontwarn javax.activation.DataSource
-keep class javax.mail.internet.MimeMultipart { *; }
-dontwarn javax.mail.internet.MimeMultipart

#-keepnames class androidx.navigation.fragment.NavHostFragment
#-keep class * extends androidx.fragment.app.Fragment{}
#-keepnames class com.alcheringa.alcheringa2022.Home

-keep public class com.google.firebase.** { *; }
-keep class com.google.android.gms.internal.** { *; }
-keepclasseswithmembers class com.google.firebase.FirebaseException

-keep @androidx.annotation.Keep public class *

-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclassmembers class rx.internal.util.unsafe.** {
    long producerIndex;
    long consumerIndex;
}

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }

-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
    }

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}
-keep class com.alcheringa.alcheringa2022.Model.** {*;}


-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}