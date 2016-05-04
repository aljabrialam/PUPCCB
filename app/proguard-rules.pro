# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Program Files (x86)\Android\android-studio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-keepattributes *Annotation*
-dontwarn

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.app.** { *; }
-keep interface android.support.v7.app.** { *; }

-keepclassmembers class pupccb.solutionsresource.com.** { *; }

-keep class com.android.support.** { *; }

-keep class com.mobsandgeeks.** { *; }
-keep class com.octo.android.** { *; }

-keep class com.squareup.picasso.** { *; }
-keep class com.android.support.** { *; }
-keep class net.steamcrafted.** { *; }
-keep class com.getbase.** { *; }
-keep class me.zhanghai.android.materialprogressbar.** { *; }
-keep class com.wdullaer.** { *; }
-keep class com.afollestad.** { *; }
-keep class de.hdodenhof.** { *; }
-keep class com.jpardogo.** { *; }
-keep class me.grantland.** { *; }
-keep class com.orhanobut.** { *; }

-keep class com.squareup.** { *; }

-keep interface com.mobsandgeeks.** { *; }
-keep interface com.octo.android.** { *; }

-keep interface com.squareup.picasso.** { *; }
-keep interface com.android.support.** { *; }
-keep interface net.steamcrafted.** { *; }
-keep interface com.getbase.** { *; }
-keep interface me.zhanghai.android.materialprogressbar.** { *; }
-keep interface com.wdullaer.** { *; }
-keep interface com.afollestad.** { *; }
-keep interface de.hdodenhof.** { *; }
-keep interface com.jpardogo.** { *; }
-keep interface me.grantland.** { *; }
-keep interface com.orhanobut.** { *; }

-keep interface com.squareup.** { *; }

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}
#
-keepclassmembers class **.R$* {
    public static <fields>;
}
#
# Keep fragments
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v7.app.Fragment
-keep public class * extends android.app.Fragment

# Serializables

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

## Native Methods
#
-keepclasseswithmembernames class * {
    native <methods>;
}
#
#
# Button methods

-keepclassmembers class * {

public void *ButtonClicked(android.view.View);

}
#
## Reflection
#

# Remove Logging

-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}

