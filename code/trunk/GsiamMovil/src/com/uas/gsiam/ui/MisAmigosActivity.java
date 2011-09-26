package com.uas.gsiam.ui;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.uas.gsiam.adapter.UsuarioAdapter;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.GetAmigosServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class MisAmigosActivity extends ListActivity{

	
	protected IntentFilter misAmigosFiltro;
	protected ProgressDialog progressDialog;
	protected ListView lv;
	protected ArrayList<UsuarioDTO> misAmigos;
	
	protected static String TAG = "MisAmigosActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	//	  setContentView(R.layout.mis_amigos_tab);
		  lv = getListView();
		  misAmigosFiltro = new IntentFilter(Constantes.GET_AMIGOS_FILTRO_ACTION);
		  Intent intent = new Intent(this,GetAmigosServicio.class);
		  startService(intent);
		  
		  Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
		  
		}
	
	
	protected void onResume() {
		 super.onResume();
		 registerReceiver(receiverGetAmigos, misAmigosFiltro);
	 }
	
	protected void onPause(){
		super.onPause();
		unregisterReceiver(receiverGetAmigos);
	}
	 
	
	
	
	protected BroadcastReceiver receiverGetAmigos = new BroadcastReceiver() {
		@Override
	    public void onReceive(Context context, Intent intent) {
	    		
	    	Bundle bundle = intent.getExtras();
			misAmigos = (ArrayList<UsuarioDTO>) bundle.getSerializable("lista");
		
	    	Log.i(TAG, "mi lista}11111111 = "+misAmigos.size());
	    	
	    	
	    	mostrarAmigos();

	    	
			Util.dismissProgressDialog();
			
	    	
	    	
			
	    }
	  };
 
	
	  public void mostrarAmigos() {
		  
			UsuarioAdapter adaptador = new UsuarioAdapter(this, R.layout.usuario_item, misAmigos);
			this.setListAdapter(adaptador);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					UsuarioDTO usuario = misAmigos.get(position);
					
					Log.i(TAG, "Seleccione: "+ usuario.getNombre());
					
				/*	Intent sitioDetalleIntent = new Intent(getApplicationContext(),
							SitioDetalleActivity.class);
					sitioDetalleIntent.putExtra("sitio", sitios.get(position));
					sitioDetalleIntent.putExtra("ubicacion", loc);
					startActivity(sitioDetalleIntent);
*/
				}
			});
			
	//		lv.setOnItemLongClickListener(this);
			

		}
	
}