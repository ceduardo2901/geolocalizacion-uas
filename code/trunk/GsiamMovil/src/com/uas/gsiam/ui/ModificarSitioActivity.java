package com.uas.gsiam.ui;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.ModificarSitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ModificarSitioActivity extends Activity {

	protected static String TAG = "ModificarSitioActivity";
	protected EditText nombre;
	protected EditText direccion;
	protected SitioDTO sitio;
	protected IntentFilter filtroModificarSitio;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modificar_sitio);
		nombre = (EditText) findViewById(R.id.txtNombreId);
		direccion = (EditText) findViewById(R.id.txtDireccionId);

		filtroModificarSitio = new IntentFilter(
				Constantes.MODIFICAR_SITIO_FILTRO_ACTION);
	}

	public void onResume() {
		super.onResume();

		sitio = (SitioDTO) getIntent().getSerializableExtra("sitio");

		nombre.setText(sitio.getNombre());
		direccion.setText(sitio.getDireccion());

		registerReceiver(modificarSitioReceiver, filtroModificarSitio);
	}

	public void onPause() {
		super.onPause();
		unregisterReceiver(modificarSitioReceiver);
	}

	public void btnModificar(View v) {

		Intent intentModificarSitio = new Intent(this,
				ModificarSitioServicio.class);
		sitio.setNombre(nombre.getText().toString());
		sitio.setDireccion(direccion.getText().toString());
		intentModificarSitio.putExtra("sitio", sitio);
		startService(intentModificarSitio);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

	}

	private void mostarSitios() {
		Intent intentMostarSitios = new Intent(this, SitiosActivity.class);
		startActivity(intentMostarSitios);
	}

	protected BroadcastReceiver modificarSitioReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "mensaje de prueba estoy aca !!!!");
			Util.dismissProgressDialog();
			String error = intent.getStringExtra("error");

			if (error != null) {
				Util.showToast(context, error);
			} else {

				mostarSitios();

			}

		}
	};

}
