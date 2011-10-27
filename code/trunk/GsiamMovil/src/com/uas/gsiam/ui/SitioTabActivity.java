package com.uas.gsiam.ui;

import greendroid.app.GDTabActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TabHost;

public class SitioTabActivity extends GDTabActivity {

	protected static String TAG = "AmigosTabActivity";
	// protected static boolean registroMisAmigosService = false;
	private static final String TAG_SITIO_DETALLE = "Sitio";
	private static final String TAG_SITIO_MAPA = "Mapa";
	private static final String TAG_SITIO_PUBLICAR = "Publicar";
	private static final String PREF_STICKY_TAB = "stickyTab";

	private static TabHost mTabHost;

	protected static final int ACTUALIZAR = 0;
	protected static final int MAPA = 1;
	protected static final int COMPARTIR = 2;
	protected Intent intent;

	private String tabClick;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		inicializarActionBar();
		mTabHost = getTabHost();
		intent = getIntent();
		
		//añadirTab2(intent);
		añadirTab1(intent);
		añadirTab3(intent);

		// Al abrir la aplicacion restauramos la última pestaña activada
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int currentTab = prefs.getInt(PREF_STICKY_TAB, 0);
		mTabHost.setCurrentTab(currentTab);
		Log.i(TAG, "**** currentTab =  " + currentTab);
		tabClick = mTabHost.getCurrentTabTag();
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Cuando se cierra la aplicación guardamos la pestaña activa
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		int currentTab = mTabHost.getCurrentTab();
		editor.putInt(PREF_STICKY_TAB, currentTab);
		editor.commit();
	}

	private void inicializarActionBar() {

		getActionBar().addItem(Type.Refresh, ACTUALIZAR);
		getActionBar().addItem(Type.Locate, MAPA);
		addActionBarItem(Type.Share, COMPARTIR);
		getActionBar().setTitle("GSIAM - Sitio");
	}
	
	public void agregar(){
		getActionBar().addItem(Type.Share, 2);
	}
	
	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case MAPA:
			mostarMapa();
			break;

		default:
			return super.onHandleActionBarItemClick(item, position);
		}

		return true;

	}

	/*
	 * Pestaña 1
	 */

	private void añadirTab1(Intent intent) {
		intent.setClass(this, SitioDetalleActivity.class);
		addTab(TAG_SITIO_DETALLE, "Sitio", intent);

	}

	/*
	 * Pestaña 2
	 */

	private void mostarMapa() {
		intent.setClass(this, MostrarMapaActivity.class);
		startActivity(intent);
		//addTab(TAG_SITIO_MAPA, "Mapa", intent);

	}

	/*
	 * Pestaña 3
	 */

	private void añadirTab3(Intent intent) {
		intent.setClass(this, PublicarActivity.class);
		addTab(TAG_SITIO_PUBLICAR, "Publicar", intent);

	}

}
