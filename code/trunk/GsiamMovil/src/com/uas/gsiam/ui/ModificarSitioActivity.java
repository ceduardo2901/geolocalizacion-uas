package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem.Type;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.ModificarSitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Esta activity despliega la interfaz grafica para modificar un sitio de
 * interes.
 * 
 * @author Antonio
 * 
 */
public class ModificarSitioActivity extends GDActivity {

	protected static String TAG = "ModificarSitioActivity";
	protected EditText nombre;
	protected EditText direccion;
	protected EditText telefono;
	protected EditText web;
	protected SitioDTO sitio;
	protected IntentFilter filtroModificarSitio;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.modificar_sitio);

		nombre = (EditText) findViewById(R.id.txtNombreId);
		direccion = (EditText) findViewById(R.id.txtDireccionId);
		telefono = (EditText) findViewById(R.id.txtTelefonoId);
		web = (EditText) findViewById(R.id.txtWebId);
		inicializarActionBar();
		filtroModificarSitio = new IntentFilter(
				Constantes.MODIFICAR_SITIO_FILTRO_ACTION);
	}

	private void inicializarActionBar() {

		getActionBar().setTitle(getString(R.string.gsiam_modificar));
	}

	public void onResume() {
		super.onResume();

		sitio = (SitioDTO) getIntent().getSerializableExtra("sitio");

		nombre.setText(sitio.getNombre());
		direccion.setText(sitio.getDireccion());
		telefono.setText(sitio.getTelefono());
		web.setText(sitio.getWeb());
		registerReceiver(modificarSitioReceiver, filtroModificarSitio);
	}

	public void onPause() {
		super.onPause();
		unregisterReceiver(modificarSitioReceiver);
	}

	/**
	 * Accion para invocar el servicio externo que modifica el sitio
	 * 
	 * @param v
	 */
	public void btnModificar(View v) {

		Intent intentModificarSitio = new Intent(this,
				ModificarSitioServicio.class);
		sitio.setNombre(nombre.getText().toString());
		sitio.setDireccion(direccion.getText().toString());
		sitio.setTelefono(telefono.getText().toString());
		sitio.setWeb(web.getText().toString());
		intentModificarSitio.putExtra("sitio", sitio);
		startService(intentModificarSitio);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

	}

	private void mostarSitios() {
		Intent intentMostarSitios = new Intent(this, SitiosActivity.class);
		startActivity(intentMostarSitios);
	}

	/**
	 * Recibe la respuesta del servicio que modifica el sitio de interes
	 */
	protected BroadcastReceiver modificarSitioReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "mensaje de prueba estoy aca !!!!");
			Util.dismissProgressDialog();
			String error = intent.getStringExtra("error");
			String respuesta = intent.getStringExtra("respuesta");
			if (error != null) {
				Util.showToast(context, error);
			} else {
				if (respuesta != null) {
					Util.showToast(context, respuesta);
					mostarSitios();
				}

			}

		}
	};

}
