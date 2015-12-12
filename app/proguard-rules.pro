## App
-keep class com.allega.nomad.entity.** {*;}
-keep class com.allega.nomad.model.** {*;}
-keep class com.allega.nomad.service.rest.app.request.** {*;}
-keep class com.allega.nomad.service.rest.app.response.** {*;}

## Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

## Realm
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-dontwarn javax.**
-dontwarn io.realm.**

## Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

## Joda Time
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }

## Retrofit
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-dontwarn rx.**
-dontwarn retrofit.**
-dontwarn okio.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

## jp.wasabeef.glide.transformations
-dontwarn jp.co.cyberagent.android.**

## EventBus
-keepclassmembers class ** {
    public void onEvent*(**);
}

## Gson
-keepattributes Signature

-keepattributes *Annotation*

-keepattributes EnclosingMethod

-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

## Facebook
-keep class com.facebook.** { *; }
-keepattributes Signature

## Twoway-view
-keep class org.lucasr.twowayview.** { *; }


## IcePick
-dontwarn icepick.**
-keep class **$$Icepick { *; }
-keepnames class * { @icepick.State *; }
-keepclasseswithmembernames class * {
    @icepick.* <fields>;
}