package com.uas.gsiam.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;

/**
 * Esta activity guarda la configuracion del usuario. Se guarda si el usuario
 * quiere compartir su ubicacion geografica
 * 
 * @author Antonio
 * 
 */
public class Preferencias extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	private CheckBoxPreference mCheckBoxPreference;
	private Handler mHandler = new Handler();
	private AlarmManager am;
	private PendingIntent mAlarmSender;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);

		mCheckBoxPreference = (CheckBoxPreference) getPreferenceScreen()
				.findPreference("compUbicacionId");
	}

	@Override
	protected void onResume() {
		super.onResume();

		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		boolean check = sharedPreferences.getBoolean(key, false);
		if (check) {

		}

	}

}
