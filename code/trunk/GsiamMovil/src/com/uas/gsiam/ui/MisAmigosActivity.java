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
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class MisAmigosActivity extends ListActivity implements OnItemClickListener{

	
	protected IntentFilter misAmigosFiltro;
	protected ProgressDialog progressDialog;
	protected ListView lv;
	protected ArrayList<UsuarioDTO> misAmigos;
	
	protected static String TAG = "MisAmigosActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.mis_amigos_tab);
		  lv = getListView();
		  lv.setOnItemClickListener(this);
		  
		  misAmigosFiltro = new IntentFilter(Constantes.GET_AMIGOS_FILTRO_ACTION);
		  this.registerReceiver(receiverGetAmigos, misAmigosFiltro);
		  AmigosTabActivity.registroMisAmigosService = true; 
		  Log.i(TAG, "***** REGSITRO oncreate");
		  
	/*	  Intent intent = new Intent(this,GetAmigosServicio.class);
		  startService(intent);
		  
		  Util.showProgressDialog(this, Constantes.MSG_ESPERA_BUSCANDO);
		*/  
		}
	
	
	protected void onResume() {
		 super.onResume();
		 
		 if (!AmigosTabActivity.registroMisAmigosService){
			  this.registerReceiver(receiverGetAmigos, misAmigosFiltro);
			  AmigosTabActivity.registroMisAmigosService = true;
			  Log.i(TAG, "***** REGSITRO onResume");
		 }
			 
	 }
	
	protected void onPause(){
		super.onPause();
		this.unregisterReceiver(receiverGetAmigos);
		AmigosTabActivity.registroMisAmigosService = false;
		Log.i(TAG, "***** SACO onPause");
		Util.dismissProgressDialog();
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
			lv.setAdapter(adaptador);
	
			
		}
	
	  @Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		  
		  
			UsuarioDTO usuario = misAmigos.get(position);
			
			Log.i(TAG, "Seleccione: "+ usuario.getNombre());
			
			
		}
	  
}