package kr.co.wero.www.sksk

import android.app.Activity
import android.content.pm.ActivityInfo
import android.preference.PreferenceManager


class ActivityHelper {
    fun initialize(activity: Activity) {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(activity)
        val orientation = prefs.getString("prefOrientation", "Null")
        if ("Landscape" == orientation) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else if ("Portrait" == orientation) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        }
    }
}