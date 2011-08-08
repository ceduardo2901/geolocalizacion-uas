package com.uas.gsiam.sitios.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.login.ui.R;

public class SitioActivity extends Activity{

	private static final String TAG = "SitioActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitios);
	}
	
	public void onStart(){
		super.onStart();
		Log.d(TAG, "prueba");
	}
}
