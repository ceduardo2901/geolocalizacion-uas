package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem.Type;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.maps.MyLocationOverlay;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.ActualizarPosicionServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.PosicionGPS;

public class MainActivity extends GDActivity {

	private ImageButton sitiosButton;
	private Button perfilButton;
	private Button amigosButton;
	private Button confButton;
	protected UsuarioDTO user;
	protected TextView nombreTxt;
	protected TextView textAplicacion;
	private SharedPreferences preferencias;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setActionBarContentView(R.layout.main);

		nombreTxt = (TextView) findViewById(R.id.textNombre);
		sitiosButton = (ImageButton) findViewById(R.id.sitios_button);
		sitiosButton.setOnClickListener(botonListener);

		perfilButton = (Button) findViewById(R.id.perfil_button);
		perfilButton.setOnClickListener(botonListener);

		amigosButton = (Button) findViewById(R.id.amigos_button);
		amigosButton.setOnClickListener(botonListener);

		confButton = (Button) findViewById(R.id.preferencias_button);
		confButton.setOnClickListener(botonListener);

		ApplicationController app = ((ApplicationController) getApplicationContext());
		user = app.getUserLogin();

		nombreTxt.setText("Bienvenido: " + user.getNombre());
		inicializarBar();

		actualizarPosicion();
	}

	private void actualizarPosicion() {
		preferencias = PreferenceManager.getDefaultSharedPreferences(this);
		boolean compartirUbicacion = preferencias.getBoolean("compUbicacionId",
				false);
		Intent intent = new Intent(this, ActualizarPosicionServicio.class);
		Location loc = PosicionGPS.getPosicion(this);
		//MyLocationOverlay d = new MyLocationOverlay(this, null);
		//d.getMyLocation();
		if (compartirUbicacion) {
			if (!isMyServiceRunning()) {
				startService(intent);
			}

		} else {
			if (isMyServiceRunning()) {
				stopService(intent);
			}
		}
	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if ("com.uas.gsiam.servicios.ActualizarPosicionServicio"
					.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public void onResume() {
		super.onResume();
		actualizarPosicion();
	}

	private void inicializarBar() {

		addActionBarItem(Type.Help, 0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.cerrarSesionId:
			// SessionStore.clear(this);
			if(isMyServiceRunning()){
				Intent intent = new Intent(this, ActualizarPosicionServicio.class);
				stopService(intent);
			}
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivity(loginIntent);
			
			break;

		default:
			return super.onOptionsItemSelected(item);

		}
		return true;
	}

	private OnClickListener botonListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sitios_button:
				sitiosActivity();
				break;
			case R.id.perfil_button:
				perfilActivity();
				break;
			case R.id.amigos_button:
				amigosActivity();
				break;
			case R.id.preferencias_button:
				preferenciasActivity();
				break;
			}

		}
	};

	private void sitiosActivity() {
		Intent sitioIntent = new Intent(this, SitiosActivity.class);
		startActivity(sitioIntent);

	}

	private void preferenciasActivity() {
		Intent preferenciasIntent = new Intent(this, Preferencias.class);
		startActivity(preferenciasIntent);
	}

	private void perfilActivity() {
		Intent perfilIntent = new Intent(this, PerfilActivity.class);
		startActivity(perfilIntent);
	}

	private void amigosActivity() {
		Intent amigosIntent = new Intent(this, AmigosTabActivity.class);
		startActivity(amigosIntent);
	}
}
