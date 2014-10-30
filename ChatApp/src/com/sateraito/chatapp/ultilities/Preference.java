package com.sateraito.chatapp.ultilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.sateraito.chatapp.R;

public class Preference {
	public static final String mPreferenceName = R.class.toString();
	
	// Set String preference
	public static void savePreferences(Context context, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	// Get String preference
	public static String loadPreferences(Context context, String key, String backup) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
		String result = sharedPreferences.getString(key, backup);
		return result;
	}

	// Set integer preference
	public static void savePreferences(Context context, String key, int value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	// Get integer preference
	public static int loadPreferences(Context context, String key, int backup) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
		int result = sharedPreferences.getInt(key, backup);
		return result;
	}

	// Set boolean preference
	public static void savePreferences(Context context, String key, boolean value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	// Get boolean preference
	public static boolean loadPreferences(Context context, String key, boolean backup) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(mPreferenceName, Context.MODE_PRIVATE);
		boolean result = sharedPreferences.getBoolean(key, backup);
		return result;
	}
	
	public static boolean isDisplayLogin(Context context){
		return loadPreferences(context, Constant.IS_DISPLAY_LOGIN, true);
	}
}
