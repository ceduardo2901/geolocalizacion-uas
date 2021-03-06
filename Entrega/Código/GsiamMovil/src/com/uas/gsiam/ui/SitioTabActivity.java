package com.uas.gsiam.ui;

import greendroid.app.GDTabActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TabHost;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.SitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

/**
 * Esta activity representa el tab que muestra el detalle del sitio de interes
 * seleccionado. En esta interfaz se muestran todos los datos del sitio, nombre,
 * direccion, telefono y web. Ademas se muestra las fotos subidas del sitio.
 * 
 * Otra de las funcionalidades de esta pantalla es la del boton como ir que
 * permite saber que camino debo tomar para ir a este sitio desde mi posicion
 * actual.
 * 
 * @author Antonio
 * 
 */
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

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		inicializarActionBar();
		mTabHost = getTabHost();
		intent = getIntent();
		sitio = (SitioDTO) intent.getSerializableExtra("sitio");

		tabDetalle();
		tabComentarios();

		// Al abrir la aplicacion restauramos la �ltima pesta�a activada
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int currentTab = prefs.getInt(PREF_STICKY_TAB, 0);
		mTabHost.setCurrentTab(0);

		mTabHost.getCurrentTabTag();
		sitioAccion = new IntentFilter(Constantes.SITIO_FILTRO_ACTION);

		Util.dismissProgressDialog();

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
		sitioUpdate.setLat(sitio.getLat());
		sitioUpdate.setLon(sitio.getLon());
		intent.putExtra("sitio", sitioUpdate);
		intent.putExtra("action", false);
		startService(intent);

	}

	private void compartir() {

		Intent compartirIntent = new Intent(Intent.ACTION_SEND);
		compartirIntent.setType("text/plain");

		compartirIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				getString(R.string.app_name));
		compartirIntent.putExtra(android.content.Intent.EXTRA_TEXT,
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

	private void tabDetalle() {
		Intent intent = new Intent(this, SitioDetalleActivity.class);
		addTab(TAG_SITIO_DETALLE, "Sitio", intent);

	}

	/*
	 * Tab 2
	 */
	private void tabComentarios() {
		Intent intent = new Intent(this, ComentariosActivity.class);
		addTab(TAG_SITIO_COMENTARIO, "Comentarios", intent);

	}

	private void mostarMapa() {
		Intent intent = new Intent(this, MostrarMapaActivity.class);
		startActivity(intent);

	}

}
