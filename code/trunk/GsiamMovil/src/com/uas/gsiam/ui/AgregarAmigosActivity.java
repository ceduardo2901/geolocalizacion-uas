package com.uas.gsiam.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

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

/**
 * 
 * Esta activity representa el tab para agregar a un amigo en el sistema.                       
 * 
 * @author Martín
 *
 */
public class AgregarAmigosActivity extends ListActivity implements OnItemClickListener{

	
	protected IntentFilter buscarUsuariosFiltro;
	protected IntentFilter enviarSolicitudFiltro;
	protected IntentFilter responderSolicitudFiltro;
	protected ProgressDialog progressDialog;
	protected ListView lv;
	public static ArrayList<UsuarioDTO> usuarios;
	protected EditText nombreTxt;
	protected ApplicationController app;
	protected UsuarioDTO usuarioSeleccionado;
	protected int pos; 
	protected boolean aceptarAmistad;
	
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

		if (nombre.length() == 0) {
			Util.showToast(v.getContext(), "Debe ingresar \nal menos un caracter");
		
		} else {

			Intent intent = new Intent(this,GetUsuariosServicio.class);
			intent.putExtra("nombre", nombre);
			startService(intent);

			Util.showProgressDialog(this, Constantes.MSG_ESPERA_BUSCANDO);
		}

	}
	
	
	
	protected void onResume() {
		 super.onResume();
		 this.registerReceiver(receiverGetUsuarios, buscarUsuariosFiltro);
		 this.registerReceiver(receiverEnviarSolicitud, enviarSolicitudFiltro);
		 this.registerReceiver(receiverResponderSolicitud, responderSolicitudFiltro);
		 
		// esto es por si tengo que actualizar la lista 
			if (usuarios != null)
				mostrarUsuarios();
		 
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
	    		
			
			ArrayList<UsuarioDTO> respuesta = (ArrayList<UsuarioDTO>) intent.getSerializableExtra("respuesta");
			String error = intent.getStringExtra("error");
			Util.dismissProgressDialog();
			
			if (error != null && !error.isEmpty()) {

				Util.showToast(context, error);

			} else {
				
				usuarios = respuesta;
					
				mostrarUsuarios();
				
			}
	    }
	  };
	

	  protected BroadcastReceiver receiverEnviarSolicitud = new BroadcastReceiver() {
			@Override
		    public void onReceive(Context context, Intent intent) {

				String respuesta = intent.getStringExtra("respuesta");
				String error = intent.getStringExtra("error");
				Util.dismissProgressDialog();
				

				if (error != null && !error.isEmpty()) {

					Util.showToast(context, error);

				} else {
					Util.showToast(context, respuesta);
					usuarioSeleccionado.setSolicitudEnviada(true);
					usuarios.set(pos, usuarioSeleccionado);
					mostrarUsuarios();
					
					//Se agrega la solicitud enviada a la vista de solicitudes 
					if (SolicitudesActivity.usuariosSolicitudesEnviadas != null)
						SolicitudesActivity.usuariosSolicitudesEnviadas.add(usuarioSeleccionado);
					
				}

			}
		  };
	
		  
		  protected BroadcastReceiver receiverResponderSolicitud = new BroadcastReceiver() {
				@Override
			    public void onReceive(Context context, Intent intent) {
					
					String respuesta = intent.getStringExtra("respuesta");
					String error = intent.getStringExtra("error");
					Util.dismissProgressDialog();
					
					if (error != null && !error.isEmpty()) {

						Util.showToast(context, error);

					} else {
						Util.showToast(context, respuesta);

						if (aceptarAmistad){
							usuarios.remove(pos);
							
							// tengo que actualizar la lista de amigos.. en caso de q este en memoria
							
							if (MisAmigosActivity.misAmigos != null){
								MisAmigosActivity.misAmigos.add(usuarioSeleccionado);
								Collections.sort(MisAmigosActivity.misAmigos);
							}
							 
							// tengo que eliminarlo de la lista de solicitudes recibidas en caso de que este en memoria
							
							if (SolicitudesActivity.usuariosSolicitudesRecibidas != null){
								
								if (!SolicitudesActivity.usuariosSolicitudesRecibidas.isEmpty()){
									
									ListIterator<UsuarioDTO> usuarios = SolicitudesActivity.usuariosSolicitudesRecibidas.listIterator();
								    while (usuarios.hasNext()) {
								    	UsuarioDTO user = usuarios.next();
								    	Log.i(TAG, "Itero a = " + user.getNombre());
								    	if(user.getEmail().equalsIgnoreCase(usuarioSeleccionado.getEmail())){
								    		Log.i(TAG, "Voy a aeliminar a = "+user.getNombre());
								    		usuarios.remove();
											
										}
								    }
								}
							}
							

						}
						else{
							usuarioSeleccionado.setSolicitudRecibida(false);
							usuarios.set(pos, usuarioSeleccionado);
			
							// tengo que eliminarlo de la lista de solicitudes recibidas en caso de que este en memoria
							if (SolicitudesActivity.usuariosSolicitudesRecibidas != null){

								if (!SolicitudesActivity.usuariosSolicitudesRecibidas.isEmpty()){

									
									ListIterator<UsuarioDTO> usuarios = SolicitudesActivity.usuariosSolicitudesRecibidas.listIterator();
								    while (usuarios.hasNext()) {
								    	UsuarioDTO user = usuarios.next();
								    	if(user.getEmail().equalsIgnoreCase(usuarioSeleccionado.getEmail())){
											
								    		usuarios.remove();
											
										}
								    }
									
									
								}
							}
							
							
						}

						
						
						mostrarUsuarios();
					}

			    }
			  };
		  
		  
	  public void mostrarUsuarios() {
		  
			UsuarioAdapter adaptador = new UsuarioAdapter(this, R.layout.usuario_item, usuarios);
			setListAdapter(adaptador);
			
		}
	  
	  @Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		  
		    pos = position;
			usuarioSeleccionado = usuarios.get(pos);
			final Context appContext = this;
			
			Log.i(TAG, "Seleccione: "+ usuarioSeleccionado.getNombre());
			Log.i(TAG, "Seleccione: "+ usuarioSeleccionado.getId());
			
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
				        	   
				        	   dialogResponder.setPositiveButton("Confirmar \nAmistad", new DialogInterface.OnClickListener() {

				        		   public void onClick(DialogInterface dialog, int id) {
				        			   //  Confirmar Amistad 
				        			   responderAmistad(usuarioSeleccionado.getId(), Constantes.ACEPTAR_SOLICITUD);
				        			   aceptarAmistad = true;
				        			   dialog.cancel();
				        		   }
				        	   });

				        	   dialogResponder.setNegativeButton("Rechazar \nAmistad", new DialogInterface.OnClickListener() {

				        		   public void onClick(DialogInterface dialog, int id) {
				        			   //  Rechazar Amistad
				        			   responderAmistad(usuarioSeleccionado.getId(), Constantes.RECHAZAR_SOLICITUD);
				        			   aceptarAmistad = false;
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
		  
	
		  SolicitudContactoDTO solicitud = new SolicitudContactoDTO();
		  solicitud.setIdUsuarioSolicitante(app.getUserLogin().getId());
		  solicitud.setIdUsuarioAprobador(idUsuarioSeleccionado);
		  
		  Intent intentEnviarSolicitud = new Intent(getApplicationContext(),CrearSolicitudAmistadServicio.class);
		  intentEnviarSolicitud.putExtra("solicitud", solicitud);
		  startService(intentEnviarSolicitud);

		  Util.showProgressDialog(this, Constantes.MSG_ESPERA_ENVIANDO_SOLICITUD);

	  }
	  
	  
	  public void responderAmistad(int idUsuarioSeleccionado, String accion){
		  
		  SolicitudContactoDTO solicitud = new SolicitudContactoDTO();
		  solicitud.setIdUsuarioSolicitante(idUsuarioSeleccionado);
		  solicitud.setIdUsuarioAprobador(app.getUserLogin().getId());

		  Intent intentAceptarSolicitud = new Intent(getApplicationContext(),ResponderSolicitudServicio.class);
		  intentAceptarSolicitud.putExtra("solicitud", solicitud);
		  intentAceptarSolicitud.putExtra("accion", accion);
		  
		  
		  startService(intentAceptarSolicitud);
		  
		  if (accion.equalsIgnoreCase(Constantes.ACEPTAR_SOLICITUD)){
			  Util.showProgressDialog(this, Constantes.MSG_ESPERA_ACEPTANDO_SOLICITUD);
		  }
		  else{
			  Util.showProgressDialog(this, Constantes.MSG_ESPERA_RECHAZANDO_SOLICITUD);
		  }
		 

	  }
	  
	  
}