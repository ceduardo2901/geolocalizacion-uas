package com.uas.gsiam.ui;

import greendroid.app.GDTabActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.LoaderActionBarItem;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TabHost;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.SitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class SitioTabActivity extends GDTabActivity {

	protected static String TAG = "SitioTabActivity";
	private static final String TAG_SITIO_DETALLE = "Sitio";
	private static final String TAG_SITIO_COMENTARIO = "Comentario";
	private static final String PREF_STICKY_TAB = "stickyTab";
	protected IntentFilter sitioAccion;

	private static TabHost mTabHost;

	private static final int ACTUALIZAR = 0;
	private static final int MAPA = 1;
	private static final int COMPARTIR = 2;
	private static final int RESULT = 1;
	private Intent intent;
	protected static SitioDTO sitio;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		inicializarActionBar();
		mTabHost = getTabHost();
		intent = getIntent();
		sitio = (SitioDTO) intent.getSerializableExtra("sitio");

		addTab1();
		addTab2();

		// Al abrir la aplicacion restauramos la �ltima pesta�a activada
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int currentTab = prefs.getInt(PREF_STICKY_TAB, 0);
		mTabHost.setCurrentTab(currentTab);
		
		Log.i(TAG, "**** currentTab =  " + currentTab);
		mTabHost.getCurrentTabTag();
		sitioAccion = new IntentFilter(Constantes.SITIO_FILTRO_ACTION);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Cuando se cierra la aplicaci�n guardamos la pesta�a activa
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		int currentTab = mTabHost.getCurrentTab();
		editor.putInt(PREF_STICKY_TAB, currentTab);
		editor.commit();
		
	}
	
	protected void OnResume() {
		super.onResume();
	}
	
	
	protected BroadcastReceiver sitiosReceiver = new BroadcastReceiver() {
		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "mensaje de prueba estoy aca !!!!");

			Bundle b = intent.getExtras();
			ArrayList<SitioDTO> sitios = (ArrayList<SitioDTO>) b
					.getSerializable("sitios");

			if (sitios != null) {
				sitio = sitios.get(0);
				
			}
			Util.dismissProgressDialog();

			LoaderActionBarItem loaderActionBarItem = (LoaderActionBarItem) getActionBar()
					.getItem(ACTUALIZAR);
			loaderActionBarItem.setLoading(false);

			if (sitios.size() == 0) {

				Util.showToast(getApplicationContext(), Constantes.MSG_NO_EXISTEN_SITIOS);

			}

		}
	};

	private void inicializarActionBar() {

		getActionBar().addItem(Type.Refresh, ACTUALIZAR);
		getActionBar().addItem(Type.Locate, MAPA);
		addActionBarItem(Type.Share, COMPARTIR);
		getActionBar().setTitle("GSIAM - Sitio");
	}

	public void agregar() {
		getActionBar().addItem(Type.Share, 2);
	}

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case MAPA:
			mostarMapa();
			break;
		case COMPARTIR:
			compartir();
			break;
		case ACTUALIZAR:
			actualizar();
			break;
		default:
			return super.onHandleActionBarItemClick(item, position);
		}

		return true;

	}
	
	private void actualizar() {
		Intent intent = new Intent(this, SitioServicio.class);
		SitioDTO sitioUpdate = new SitioDTO();
		sitioUpdate.setIdSitio(sitio.getIdSitio());
		intent.putExtra("sitio", sitioUpdate);
		
		startService(intent);

	}

	private void compartir() {
		Intent compartirIntent = new Intent(Intent.ACTION_SEND);
		compartirIntent.setType("plain/text");
		compartirIntent.putExtra(Intent.EXTRA_TEXT,
				"Mira el sitio " + sitio.getNombre() + " en Gsiam");

		startActivityForResult(Intent.createChooser(compartirIntent, "Title:"),
				RESULT);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT:
			Util.showToast(this, Constantes.MSG_MENSAJE_ENVIADO);
			break;

		default:
			break;

		}

	}

	/*
	 * Tab 1
	 */

	private void addTab1() {
		Intent intent = new Intent(this, SitioDetalleActivity.class);
		addTab(TAG_SITIO_DETALLE, "Sitio", intent);

	}

	/*
	 * Tab 2
	 */
	private void addTab2() {
		Intent intent = new Intent(this, ComentariosActivity.class);
		addTab(TAG_SITIO_COMENTARIO, "Comentarios", intent);

	}
	

	private void mostarMapa() {
		intent.setClass(this, MostrarMapaActivity.class);
		startActivity(intent);

	}


}
