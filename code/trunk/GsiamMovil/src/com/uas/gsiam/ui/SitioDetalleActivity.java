package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SitioDetalleActivity extends Activity{

	protected static final String TAG = "SitioDetalleActivity";
	protected TextView txtNombre;
	protected TextView txtDireccion;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitio_detalle);
		Intent intent = getIntent();
		
		txtNombre = (TextView) findViewById(R.id.txtSitioNombreId);
		txtDireccion = (TextView) findViewById(R.id.txtSitioDireccionId);
		
		txtNombre.setText(intent.getStringExtra("nombre"));
		txtDireccion.setText(intent.getStringExtra("direccion"));
		
	}
	
}
