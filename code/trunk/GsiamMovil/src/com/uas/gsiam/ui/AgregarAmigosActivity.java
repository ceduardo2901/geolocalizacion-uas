package com.uas.gsiam.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.uas.gsiam.adapter.UsuarioAdapter;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.CrearSolicitudAmistadServicio;
import com.uas.gsiam.servicios.GetUsuariosServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class AgregarAmigosActivity extends ListActivity implements OnItemClickListener{

	
	protected IntentFilter buscarUsuariosFiltro;
	protected IntentFilter enviarSolicitudFiltro;
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
		lv.setOnItemClickListener(this);

		buscarUsuariosFiltro = new IntentFilter(Constantes.GET_USUARIOS_FILTRO_ACTION);
		enviarSolicitudFiltro = new IntentFilter(Constantes.CREAR_SOLICITUD_AMISTAD_FILTRO_ACTION);
		
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
		 this.registerReceiver(receiverEnviarSolicitud, enviarSolicitudFiltro);
		 
	 }
	
	protected void onPause(){
		super.onPause();
		this.unregisterReceiver(receiverGetUsuarios);
		this.unregisterReceiver(receiverEnviarSolicitud);
		
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
	

	  protected BroadcastReceiver receiverEnviarSolicitud = new BroadcastReceiver() {
			@Override
		    public void onReceive(Context context, Intent intent) {

				Util.dismissProgressDialog();
				Bundle bundle = intent.getExtras();
				String respuesta = bundle.getString("respuesta");
				
				if (respuesta.equals(Constantes.RETURN_OK)){
		    		
					Util.showToast(context, Constantes.MSG_SOLICITUD_CREADA_OK);
					
				}
				else{
					Util.showToast(context, respuesta);
				}	
		    }
		  };
	
		  
	  public void mostrarUsuarios() {
		  
			UsuarioAdapter adaptador = new UsuarioAdapter(this, R.layout.usuario_item, usuarios);
			setListAdapter(adaptador);
			
			
		}
	  
	  @Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		  
		    final int pos = position;
			final UsuarioDTO usuarioSeleccionado = usuarios.get(pos);
			
			Log.i(TAG, "Seleccione: "+ usuarioSeleccionado.getNombre());
			
			if (usuarioSeleccionado.isSolicitudEnviada()){
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				
				dialog.setTitle("Solicitud de Amistad"); 
				dialog.setMessage("La solicitud ya ha sido enviada a "+ usuarioSeleccionado.getNombre());
				dialog.setCancelable(false);
				dialog.setIcon(android.R.drawable.ic_dialog_alert);  
				dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				    	   
				           public void onClick(DialogInterface dialog, int id) {
				        	   
				        	   dialog.cancel();
				           }
				       });
				
				dialog.show();
				
			}
			else if(usuarioSeleccionado.isSolicitudRecibida()){
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				
				dialog.setTitle("lala"); 
				dialog.setMessage("La solicitud ya ha sido enviada a "+ usuarioSeleccionado.getNombre());
				dialog.setCancelable(false);
				dialog.setIcon(android.R.drawable.ic_dialog_alert);  
				dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				    	   
				           public void onClick(DialogInterface dialog, int id) {
				        	   
				        	   dialog.cancel();
				           }
				       });
				
				dialog.show();
			}
			else{
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				
				dialog.setTitle("Solicitud de Amistad"); 
				dialog.setMessage("Enviar solicitud de amitad a "+ usuarioSeleccionado.getNombre() + "?");
				dialog.setCancelable(false);
				dialog.setIcon(android.R.drawable.ic_dialog_info);  
				dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				    	   
				           public void onClick(DialogInterface dialog, int id) {
				        	   
				        	   
				        	   enviarSolicitud(usuarioSeleccionado.getId());
				        	   
				        	   usuarioSeleccionado.setSolicitudEnviada(true);
				        	   
				        	   usuarios.set(pos, usuarioSeleccionado);
				        	   
				        	   mostrarUsuarios();
				        	   
				        	   dialog.cancel();
				           }
				       });
				dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				dialog.show();
				
				
			}
			
				
			
		}
	  
	  
	  public void enviarSolicitud(int idUsuarioSeleccionado){
		  
		  ApplicationController app = ((ApplicationController)getApplicationContext());

		  Bundle bundle = new Bundle();
		  bundle.putInt("idSolicitante", app.getUserLogin().getId());
		  bundle.putInt("idAprobador", idUsuarioSeleccionado);

		  Intent intent = new Intent(getApplicationContext(),CrearSolicitudAmistadServicio.class);
		  intent.putExtras(bundle);
		  startService(intent);

		  Util.showProgressDialog(this, Constantes.MSG_ESPERA_ENVIANDO_SOLICITUD);

	  }
	  
	  
}