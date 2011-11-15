package com.uas.gsiam.ui;

import com.uas.gsiam.adapter.ComentarioAdapter;
import com.uas.gsiam.negocio.dto.SitioDTO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ComentarioTabActivity extends Activity {

	protected static final String TAG = "ComentarioTabActivity";
	private ListView listComentarios;
	private SitioDTO sitio;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comentarios);
		sitio = (SitioDTO) getIntent().getSerializableExtra("sitio");
		listComentarios = (ListView) findViewById(R.id.listComentariosId);
		if (!sitio.getPublicaciones().isEmpty()) {
			ComentarioAdapter adaptador = new ComentarioAdapter(this,
					R.layout.comentario, sitio.getPublicaciones());
			// ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);
			listComentarios.setAdapter(adaptador);
		}
	}
	
	public void onResume(){
		super.onResume();
	}
	
	public void publicar(View v) {
		Intent intent = new Intent(this, PublicarActivity.class);
		intent.putExtra("sitio", sitio);
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		
	}
}