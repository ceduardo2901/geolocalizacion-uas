package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.uas.gsiam.adapter.ComentarioAdapter;
import com.uas.gsiam.negocio.dto.SitioDTO;

public class ComentariosActivity extends Activity {

	protected static final String TAG = "ComentarioTabActivity";
	private ListView listComentarios;
	private SitioDTO sitio;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comentarios);
		sitio = SitioTabActivity.sitio;
		listComentarios = (ListView) findViewById(R.id.listComentariosId);
		
		if (!sitio.getPublicaciones().isEmpty()) {
			mostrarComentarios();
		}

	}
	
	public void onResume(){
		super.onResume();
		
	}
	
	public void onPause(){
		super.onPause();
		
	}
	
	
	  public void mostrarComentarios() {
		  
		  ComentarioAdapter adaptador = new ComentarioAdapter(this,
					R.layout.comentario, sitio.getPublicaciones());
			
			listComentarios.setAdapter(adaptador);
	
			
		}
	
	public void publicar(View v) {
		Intent intent = new Intent(this, PublicarActivity.class);
		intent.putExtra("sitio", sitio);
		startActivity(intent);
	}
	
	

	public SitioDTO getSitio() {
		return sitio;
	}

	public void setSitio(SitioDTO sitio) {
		this.sitio = sitio;
	}
	
}