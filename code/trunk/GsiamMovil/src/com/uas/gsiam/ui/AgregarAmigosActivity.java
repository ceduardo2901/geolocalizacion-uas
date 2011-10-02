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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.uas.gsiam.adapter.UsuarioAdapter;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.GetUsuariosServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class AgregarAmigosActivity extends ListActivity{

	
	protected IntentFilter buscarUsuariosFiltro;
	protected ProgressDialog progressDialog;
	protected ListView lv;
	protected ArrayList<UsuarioDTO> usuarios;
	protected EditText nombreTxt;
	
	protected static String TAG = "AgregarAmigosActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_amigos_tab);
		nombreTxt = (EditText) findViewById(R.id.nombreTxt);
		lv = getListView();
		buscarUsuariosFiltro = new IntentFilter(Constantes.GET_USUARIOS_FILTRO_ACTION);
	}
	
	
	public void buscarUsuarios(View v) {

		String nombre = nombreTxt.getText().toString().trim();

		if (nombre.isEmpty()) {
			Util.showToast(v.getContext(), "Debe ingresar un nombre");
		
			
			
		} else {


			Bundle bundle = new Bundle();
			bundle.putString("nombre", nombre);

			Intent intent = new Intent(this,GetUsuariosServicio.class);
			intent.putExtras(bundle);
			startService(intent);

			Util.showProgressDialog(this, Constantes.MSG_ESPERA_BUSCANDO);
		}

	}
	
	
	
	protected void onResume() {
		 super.onResume();
		 this.registerReceiver(receiverGetUsuarios, buscarUsuariosFiltro);
		 
	 }
	
	protected void onPause(){
		super.onPause();
		this.unregisterReceiver(receiverGetUsuarios);
		
	}
	 
	
	
	
	protected BroadcastReceiver receiverGetUsuarios = new BroadcastReceiver() {
		@Override
	    public void onReceive(Context context, Intent intent) {
	    		
	    	Bundle bundle = intent.getExtras();
			usuarios = (ArrayList<UsuarioDTO>) bundle.getSerializable("lista");
		
	    	Log.i(TAG, "mi lista}11111111 = "+usuarios.size());
	    	
	    	
	    	mostrarUsuarios();

	    	
			Util.dismissProgressDialog();
			
	    	
	    	
			
	    }
	  };
	
	
	
	
	  public void mostrarUsuarios() {
		  
			UsuarioAdapter adaptador = new UsuarioAdapter(this, R.layout.usuario_item, usuarios);
			this.setListAdapter(adaptador);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					UsuarioDTO usuario = usuarios.get(position);
					
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