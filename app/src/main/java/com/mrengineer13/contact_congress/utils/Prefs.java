package com.mrengineer13.contact_congress.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jon on 6/28/16.
 */

public class Prefs {
    private final SharedPreferences prefs;

    public Prefs(Application app) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(app);
    }

    public void saveZip(String zip) {
        putString(Keys.ZIP_CODE, zip);
    }

    public String getZip() {
        return getString(Keys.ZIP_CODE);
    }


    public boolean hasZip() {
        return prefs.contains(Keys.ZIP_CODE);
    }

    public boolean areNotificationsEnabled() {
        return getBoolean(Keys.NOTIFICATION, true);
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }

  /* Helpers */

    public void remove(String key) {
        prefs.edit().remove(key).apply();
    }

    public void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public void putLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    public void putFloat(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return getLong(key, -1);
    }

    public long getLong(String key, long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    public float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public float getFloat(String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }
}
