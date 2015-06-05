package com.chuangmeng.fashiondiy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SimpleSharedPreferences {

	public static long getInt(String key, Context ctx) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		return prefs.getLong(key, 0);
	}

	public static void putInt(String key, long intVal, Context ctx) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor mEditor = prefs.edit();
		mEditor.putLong(key, intVal);
		mEditor.commit();
	}

	public static void putString(String key, String val, Context ctx) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor mEditor = prefs.edit();
		mEditor.putString(key, val);

		mEditor.commit();
	}

	public static String getString(String key, Context ctx) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		return prefs.getString(key, "");
	}

	public static void setBoolean(String key, boolean bool, Context ctx) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor mEditor = prefs.edit();
		mEditor.putBoolean(key, bool);

		mEditor.commit();
	}

	public static boolean getBoolean(String key, Context ctx) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		return prefs.getBoolean(key, false);
	}

}
