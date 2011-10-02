package com.uas.gsiam.ui;

import java.security.Provider;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.CrearSitioServicio;
import com.uas.gsiam.adapter.ComentarioAdapter;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.adapter.SitiosAdapter;
import com.uas.gsiam.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class SitioDetalleActivity extends Activity {

	protected static final String TAG = "SitioDetalleActivity";
	protected TextView txtNombre;
	protected TextView txtDireccion;
	protected Location loc;
	private Double lat;
	private Double lon;
	private Integer sitioId;
	private ListView listComentarios;
	private SitioDTO sitio;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitio_detalle);

		txtNombre = (TextView) findViewById(R.id.txtSitioNombreId);
		txtDireccion = (TextView) findViewById(R.id.txtSitioDireccionId);
		listComentarios = (ListView) findViewById(R.id.listComentariosId);

	}

	public void onResume() {
		super.onResume();
		Intent intent = getIntent();
		sitio = (SitioDTO) intent.getSerializableExtra("sitio");

		txtNombre.setText(sitio.getNombre());
		txtDireccion.setText(sitio.getDireccion());

		loc = intent.getParcelableExtra("ubicacion");

		if (!sitio.getPublicaciones().isEmpty()) {
			ComentarioAdapter adaptador = new ComentarioAdapter(this,
					R.layout.comentario, sitio.getPublicaciones());
			// ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);
			listComentarios.setAdapter(adaptador);
		}

	}
	
	public void onPause(){
		super.onPause();
		
	}

	public void mostarMapa(View v) {

		Bundle bundle = new Bundle();
		bundle.putDouble("lat", sitio.getLat());
		bundle.putDouble("lon", sitio.getLon());
		Intent intentMostrarMapa = new Intent(this, MostrarMapaActivity.class);
		intentMostrarMapa.putExtras(bundle);
		intentMostrarMapa.putExtra("ubicacion", loc);
		startActivity(intentMostrarMapa);

	}

	public void publicar(View v) {

		Intent intent = new Intent(this, PublicarActivity.class);
		intent.putExtra("sitioId", sitio.getIdSitio());
		intent.putExtra("nombre", sitio.getNombre());

		startActivity(intent);

	}

}
