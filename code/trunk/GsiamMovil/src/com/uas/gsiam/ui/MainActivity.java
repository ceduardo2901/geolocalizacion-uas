package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
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

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.ActualizarPosicionServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.LocationHelper;
import com.uas.gsiam.utils.LocationHelper.LocationResult;

/**
 * Esta activity es la interfaz principal de la aplicacion. En ella se puede
 * navegar a los sitios, donde se presentara informacion de los sitios cercanos
 * y las publicaciones hechas en estos. Tambien se puede navegar a la interfaz
 * de amigos donde podremos invitar a un amigo a la aplicacion y ver las
 * solicitudes de amistad. Luego podemos ir a editar el perfil de usuario o se
 * puede ir a configuracion donde podremos habilitar compartir nuestra ubicacion
 * 
 * @author Antonio
 * 
 */
public class MainActivity extends GDActivity {

	private ImageButton sitiosButton;
	private Button perfilButton;
	private Button amigosButton;
	private Button confButton;
	protected UsuarioDTO user;
	protected TextView nombreTxt;
	protected TextView textAplicacion;
	private SharedPreferences preferencias;
	private Location currentLocation;

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

		actualizarPosicion();
	}

	private void actualizarPosicion() {
		preferencias = PreferenceManager.getDefaultSharedPreferences(this);
		boolean compartirUbicacion = preferencias.getBoolean("compUbicacionId",
				false);
		Intent intent = new Intent(this, ActualizarPosicionServicio.class);

		LocationHelper h = new LocationHelper();
		boolean result = h.getLocation(this, locationResult);
		if (result) {
			intent.putExtra("loc", currentLocation);
		}
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

	public LocationResult locationResult = new LocationResult() {

		@Override
		public void obtenerUbicacion(final Location location) {
			currentLocation = location;
		}
	};

	/**
	 * Determina si el servicio de radar esta habilitado para tomar una accion
	 * 
	 * @return Devuelve true si el servicio esta activado, falso en caso
	 *         contrario
	 */
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

			if (isMyServiceRunning()) {
				Intent intent = new Intent(this,
						ActualizarPosicionServicio.class);
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

	/**
	 * Navegacion a la interfaz de sitios
	 */
	private void sitiosActivity() {
		Intent sitioIntent = new Intent(this, SitiosActivity.class);
		startActivity(sitioIntent);

	}

	/**
	 * Navegacion a la interfaz de preferencias
	 */
	private void preferenciasActivity() {
		Intent preferenciasIntent = new Intent(this, Preferencias.class);
		startActivity(preferenciasIntent);
	}

	/**
	 * Navegacion a la interfaz de perfil de usuario
	 */
	private void perfilActivity() {
		Intent perfilIntent = new Intent(this, PerfilActivity.class);
		startActivity(perfilIntent);
	}

	/**
	 * Navegacion a la interfaz de amigos
	 */
	private void amigosActivity() {
		Intent amigosIntent = new Intent(this, AmigosTabActivity.class);
		startActivity(amigosIntent);
	}
}
