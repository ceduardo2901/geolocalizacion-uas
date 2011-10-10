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
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.uas.gsiam.adapter.UsuarioAdapter;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.CrearSolicitudAmistadServicio;
import com.uas.gsiam.servicios.GetUsuariosServicio;
import com.uas.gsiam.servicios.ResponderSolicitudServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class AgregarAmigosActivity extends ListActivity implements OnItemClickListener{

	
	protected IntentFilter buscarUsuariosFiltro;
	protected IntentFilter enviarSolicitudFiltro;
	protected IntentFilter responderSolicitudFiltro;
	protected ProgressDialog progressDialog;
	protected ListView lv;
	protected ArrayList<UsuarioDTO> usuarios;
	protected EditText nombreTxt;
	protected ApplicationController app;
	
	protected static String TAG = "AgregarAmigosActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		app = ((ApplicationController) getApplicationContext());
		setContentView(R.layout.agregar_amigos_tab);
		nombreTxt = (EditText) findViewById(R.id.nombreTxt);
		lv = getListView();
		lv.setOnItemClickListener(this);

		buscarUsuariosFiltro = new IntentFilter(Constantes.GET_USUARIOS_FILTRO_ACTION);
		enviarSolicitudFiltro = new IntentFilter(Constantes.CREAR_SOLICITUD_AMISTAD_FILTRO_ACTION);
		responderSolicitudFiltro = new IntentFilter(Constantes.RESPONDER_SOLICITUD_AMISTAD_FILTRO_ACTION);
		
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
		 this.registerReceiver(receiverResponderSolicitud, responderSolicitudFiltro);
		 
	 }
	
	protected void onPause(){
		super.onPause();
		this.unregisterReceiver(receiverGetUsuarios);
		this.unregisterReceiver(receiverEnviarSolicitud);
		this.unregisterReceiver(receiverResponderSolicitud);
		
	}
	 

	protected BroadcastReceiver receiverGetUsuarios = new BroadcastReceiver() {
		@SuppressWarnings("unchecked")
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
	
		  
		  protected BroadcastReceiver receiverResponderSolicitud = new BroadcastReceiver() {
				@Override
			    public void onReceive(Context context, Intent intent) {

					Util.dismissProgressDialog();
					Bundle bundle = intent.getExtras();
					String respuesta = bundle.getString("respuesta");
					
					if (respuesta.equals(Constantes.RETURN_OK)){
			    		
						Util.showToast(context, Constantes.MSG_SOLICITUD_RESPONDIDA_OK);
						
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
			final Context appContext = this;
			
			Log.i(TAG, "Seleccione: "+ usuarioSeleccionado.getNombre());
			
			if (usuarioSeleccionado.isSolicitudEnviada()){
				
				// El usuario seleccionado ya tiene una solicitud enviada  
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(appContext);
				
				dialog.setTitle("Solicitud de Amistad"); 
				dialog.setMessage("La solicitud ya ha sido enviada a\n"+ usuarioSeleccionado.getNombre());
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
				
				// El usuario seleccionado ya envio una solicitud y esta pendiente 
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(appContext);
				
				dialog.setTitle(Html.fromHtml("<b><font color=#ff0000> Solicitud de Amistad Pendiente")); 
				dialog.setMessage(usuarioSeleccionado.getNombre() +" quiere ser tu amigo.\n\n¿Deseeas responder la solicitud?");
				
			//	Html.fromHtml("<b><font color=#ff0000> Html View " +"</font></b><br>Androidpeople.com")
				
				
				dialog.setCancelable(false);
				dialog.setIcon(android.R.drawable.ic_dialog_alert);  
				
				dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
				    	   
				           public void onClick(DialogInterface dialog, int id) {

				        	  
				        	   
				        	   ///////////////////////////
				        	   
				        	   AlertDialog.Builder dialogResponder = new AlertDialog.Builder(appContext);
				        	   dialogResponder.setTitle("Solicitud de Amistad Pendiente"); 
				        	   dialogResponder.setMessage("Responder Solicitud de " + usuarioSeleccionado.getNombre());
				        	   dialogResponder.setCancelable(true);
				        	   dialogResponder.setIcon(android.R.drawable.ic_dialog_alert);  
				        	   
				        	   dialogResponder.setPositiveButton("Confirmar Amistad", new DialogInterface.OnClickListener() {

				        		   public void onClick(DialogInterface dialog, int id) {
				        			   //  Confirmar Amistad 

				        			   responderAmistad(usuarioSeleccionado.getId(), Constantes.ACEPTAR_SOLICITUD);
				        			   usuarios.remove(pos);
				        			   mostrarUsuarios();

				        			   dialog.cancel();
				        		   }
				        	   });

				        	   dialogResponder.setNegativeButton("Rechazar Amistad", new DialogInterface.OnClickListener() {

				        		   public void onClick(DialogInterface dialog, int id) {
				        			   //  Rechazar Amistad

				        			   responderAmistad(usuarioSeleccionado.getId(), Constantes.RECHAZAR_SOLICITUD);
				        			   usuarioSeleccionado.setSolicitudRecibida(false);
				        			   usuarios.set(pos, usuarioSeleccionado);
				        			   mostrarUsuarios();

				        			   dialog.cancel();
				        		   }
				        	   });

				        	   dialogResponder.show();

				        	   
				        	   /////////////////////////
				        	   dialog.cancel();
				           }
				       });
				dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			    	   
			           public void onClick(DialogInterface dialog, int id) {
			        	   
			        	   dialog.cancel();
			           }
			       });
				dialog.show();
			}
			else{
				
				// Enviar la solicitud
				AlertDialog.Builder dialog = new AlertDialog.Builder(appContext);
				
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
		  
		  Bundle bundle = new Bundle();
		  
		  SolicitudContactoDTO solicitud = new SolicitudContactoDTO();
		  solicitud.setIdUsuarioSolicitante(app.getUserLogin().getId());
		  solicitud.setIdUsuarioAprobador(idUsuarioSeleccionado);

		  bundle.putSerializable("solicitud", solicitud);
		  
		  Intent intentEnviarSolicitud = new Intent(getApplicationContext(),CrearSolicitudAmistadServicio.class);
		  intentEnviarSolicitud.putExtras(bundle);
		  startService(intentEnviarSolicitud);

		  Util.showProgressDialog(this, Constantes.MSG_ESPERA_ENVIANDO_SOLICITUD);

	  }
	  
	  
	  public void responderAmistad(int idUsuarioSeleccionado, int accion){

		  Bundle bundle = new Bundle();
		  
		  SolicitudContactoDTO solicitud = new SolicitudContactoDTO();
		  solicitud.setIdUsuarioSolicitante(idUsuarioSeleccionado);
		  solicitud.setIdUsuarioAprobador(app.getUserLogin().getId());

		  bundle.putSerializable("solicitud", solicitud);
		  bundle.putInt("accion", accion);
		  
		  Intent intentAceptarSolicitud = new Intent(getApplicationContext(),ResponderSolicitudServicio.class);
		  intentAceptarSolicitud.putExtras(bundle);
		  startService(intentAceptarSolicitud);

		  Util.showProgressDialog(this, Constantes.MSG_ESPERA_ACEPTANDO_SOLICITUD);

	  }
	  
	  
}