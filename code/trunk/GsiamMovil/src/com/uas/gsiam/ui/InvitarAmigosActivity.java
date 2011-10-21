package com.uas.gsiam.ui;

import com.uas.gsiam.utils.Util;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class InvitarAmigosActivity extends Activity {

	private final int ACTUALIZAR = 0;
	protected static String TAG = "InvitarAmigosActivity";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.invitar_amigos_tab);
		
		Log.i(TAG, "***** estoy en el onCreate");
		
		  setTitle("GSIAM - Amigos 1");

		
	}
	
	
	
	
	
}