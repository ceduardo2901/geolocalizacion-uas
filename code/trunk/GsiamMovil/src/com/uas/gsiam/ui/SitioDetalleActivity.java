package com.uas.gsiam.ui;

import com.uas.gsiam.servicios.CrearSitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SitioDetalleActivity extends Activity{

	protected static final String TAG = "SitioDetalleActivity";
	protected TextView txtNombre;
	protected TextView txtDireccion;
	protected Location loc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitio_detalle);
		Intent intent = getIntent();
		
		txtNombre = (TextView) findViewById(R.id.txtSitioNombreId);
		txtDireccion = (TextView) findViewById(R.id.txtSitioDireccionId);
		
		txtNombre.setText(intent.getStringExtra("nombre"));
		txtDireccion.setText(intent.getStringExtra("direccion"));
		loc = intent.getParcelableExtra("loc");
		
	}
	
	public void mostarMapa(View v) {
		
		
		Bundle bundle = new Bundle();
		bundle.putParcelable("ubicacion", loc);
		
		Intent intent = new Intent(this,MostrarMapaActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
		
	}
	
	
	
}
