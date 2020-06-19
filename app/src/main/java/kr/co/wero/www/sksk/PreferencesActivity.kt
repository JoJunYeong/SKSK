package kr.co.wero.www.sksk

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.ListPreference
import android.preference.PreferenceActivity
import android.preference.PreferenceManager


class PreferencesActivity : PreferenceActivity(),
    OnSharedPreferenceChangeListener {
    public override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        ActivityHelper().initialize(this)


    }

    override fun onSharedPreferenceChanged(
        sharedPreferences: SharedPreferences,
        key: String
    ) {
        val pref = findPreference(key)
        if (pref is ListPreference) {
            pref.setSummary(pref.entry)
            ActivityHelper().initialize(this)

        }
        if (pref is EditTextPreference) {
            pref.setSummary(pref.text)
        }
    }

    override fun onPause() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onResume() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
        val keys =
            PreferenceManager.getDefaultSharedPreferences(this).all
        for ((key, value) in keys) {
// Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            val pref = findPreference(key)
            if (pref != null) {
                pref.summary = value.toString()
            }
        }
        super.onResume()
    }
}