package com.uas.gsiam.ui;

import greendroid.app.GDTabActivity;
import greendroid.widget.LoaderActionBarItem;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.uas.gsiam.adapter.ComentarioAdapter;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class ComentarioTabActivity extends Activity {

	protected static final String TAG = "ComentarioTabActivity";
	private ListView listComentarios;
	private SitioDTO sitio;
	private IntentFilter comentarioFiltro;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comentarios);
		sitio = (SitioDTO) getIntent().getSerializableExtra("sitio");
		
		listComentarios = (ListView) findViewById(R.id.listComentariosId);
		
		if (!sitio.getPublicaciones().isEmpty()) {
			mostrarComentarios();
		}
		comentarioFiltro = new IntentFilter(Constantes.SITIO_FILTRO_ACTION);
	}
	
	public void onResume(){
		super.onResume();
		registerReceiver(receiverSitio, comentarioFiltro);
		
	}
	
	public void onPause(){
		super.onPause();
		unregisterReceiver(receiverSitio);
		
	}
	
	
	protected BroadcastReceiver receiverSitio = new BroadcastReceiver() {
		@Override
	    public void onReceive(Context context, Intent intent) {
	    		
	    	Bundle bundle = intent.getExtras();
			ArrayList<SitioDTO> sitios = (ArrayList<SitioDTO>) bundle.getSerializable("sitios");
			if(!sitios.isEmpty()){
				
				List<PublicacionDTO> publicaciones = sitios.get(0).getPublicaciones();
				if(!publicaciones.isEmpty()){
					setSitio(sitios.get(0));
					mostrarComentarios();
				}
				
			}
			Util.dismissProgressDialog();
			
			GDTabActivity padre = (GDTabActivity) getParent();
			LoaderActionBarItem loaderActionBarItem = (LoaderActionBarItem) padre.getActionBar().getItem(AmigosTabActivity.ACTUALIZAR);
			loaderActionBarItem.setLoading(false);
			
	    }
	  };
	
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	public SitioDTO getSitio() {
		return sitio;
	}

	public void setSitio(SitioDTO sitio) {
		this.sitio = sitio;
	}
	
}