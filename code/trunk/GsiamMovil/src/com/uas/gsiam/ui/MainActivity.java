package com.uas.gsiam.ui;

import com.uas.gsiam.utils.SessionStore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button sitiosButton;
	private Button perfilButton;
	

		
	protected TextView nombreTxt;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		nombreTxt =  (TextView)findViewById(R.id.textNombre);
		nombreTxt.setText("Bienvenido Josesito");
		
		sitiosButton = (Button) findViewById(R.id.sitios_button);
		sitiosButton.setOnClickListener(botonListener);
		
		perfilButton = (Button) findViewById(R.id.perfil_button);
		perfilButton.setOnClickListener(botonListener);
		
		
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
			Intent loginIntent = new Intent(this,LoginActivity.class);
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
	
}
