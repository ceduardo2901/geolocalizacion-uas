package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem.Type;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.SessionStore;

public class MainActivity extends GDActivity {

	private ImageButton sitiosButton;
	private Button perfilButton;
	private Button amigosButton;

	protected UsuarioDTO user;
	protected TextView nombreTxt;
	protected TextView textAplicacion;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setActionBarContentView(R.layout.main);

		nombreTxt = (TextView) findViewById(R.id.textNombre);
		textAplicacion = (TextView) findViewById(R.id.textAplicacion);
		Drawable img = getResources().getDrawable(R.drawable.logo);
		img.setBounds( 0, 0, 35, 35 );
		textAplicacion.setCompoundDrawables( img, null, null, null );
		sitiosButton = (ImageButton) findViewById(R.id.sitios_button);
		sitiosButton.setOnClickListener(botonListener);

		perfilButton = (Button) findViewById(R.id.perfil_button);
		perfilButton.setOnClickListener(botonListener);

		amigosButton = (Button) findViewById(R.id.amigos_button);
		amigosButton.setOnClickListener(botonListener);

		ApplicationController app = ((ApplicationController) getApplicationContext());
		user = app.getUserLogin();

		nombreTxt.setText("Bienvenido: " + user.getNombre());
		inicializarBar();
	}

	private void inicializarBar() {
		
		addActionBarItem(Type.Trashcan, 0);
		setTitle(R.string.app_name);
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
			SessionStore.clear(this);
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
			}

		}
	};

	private void sitiosActivity() {
		Intent sitioIntent = new Intent(this, SitiosActivity.class);
		startActivity(sitioIntent);
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
