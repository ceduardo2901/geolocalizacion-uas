package com.uas.gsiam.ui;

import greendroid.app.GDTabActivity;
import greendroid.widget.LoaderActionBarItem;
import greendroid.widget.SegmentedAdapter;
import greendroid.widget.SegmentedBar.OnSegmentChangeListener;
import greendroid.widget.SegmentedHost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.uas.gsiam.adapter.UsuarioAdapter;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.GetSolicitudesPendientesServicio;
import com.uas.gsiam.servicios.ResponderSolicitudServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Esta activity muestra las solicitudes pendientes y las solicitudes enviadas
 * del usuario. Se podra aceptar las solicitudes pendientes para asi vincularse
 * a un usuario de la aplicacion
 * 
 * @author Martin
 * 
 */
public class SolicitudesActivity extends ListActivity implements
		OnItemClickListener {

	private final Handler mHandler = new Handler();
	private SolicitudesSegmentedAdapter mAdapter;
	protected ListView lv;
	private int vista;
	protected IntentFilter solicitudesFiltro;
	protected IntentFilter responderSolicitudFiltro;
	public static ArrayList<UsuarioDTO> usuariosSolicitudesEnviadas;
	public static ArrayList<UsuarioDTO> usuariosSolicitudesRecibidas;
	protected ApplicationController app;
	private SegmentedHost segmentedHost;

	protected UsuarioDTO usuarioSeleccionadoRecibidas;
	protected int pos;
	protected boolean aceptarAmistad;

	protected static String TAG = "SolicitudesActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = ((ApplicationController) getApplicationContext());
		if (usuariosSolicitudesEnviadas != null)
			usuariosSolicitudesEnviadas.clear();
		if (usuariosSolicitudesRecibidas != null)
			usuariosSolicitudesRecibidas.clear();
		lv = getListView();
		setContentView(R.layout.solicitudes_amigos_tab);

		solicitudesFiltro = new IntentFilter(
				Constantes.GET_SOLICITUDES_FILTRO_ACTION);
		responderSolicitudFiltro = new IntentFilter(
				Constantes.RESPONDER_SOLICITUD_AMISTAD_FILTRO_ACTION);

		Intent intentCargarSolicitudes = new Intent(getApplicationContext(),
				GetSolicitudesPendientesServicio.class);
		startService(intentCargarSolicitudes);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_BUSCANDO);

		lv.setOnItemClickListener(this);
		lv.setCacheColorHint(Color.TRANSPARENT);

		segmentedHost = (SegmentedHost) findViewById(R.id.segmented_host);

		segmentedHost.getSegmentedBar().setOnSegmentChangeListener(
				new OnSegmentChangeListener() {

					@Override
					public void onSegmentChange(int index, boolean clicked) {
						Log.i(TAG, "************************************");
						Log.i(TAG, "***** cambio el segemento");
						Log.i(TAG, "***** index:" + index);
						Log.i(TAG, "***** clicked:" + clicked);
						Log.i(TAG, "************************************");

						if (clicked) {

							vista = index;

							if (index == 0) {

								mostrarSolicitudesRecibidas();

							} else {

								mostrarSolicitudesEnviadas();
							}

						}

					}
				});

		mAdapter = new SolicitudesSegmentedAdapter();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mAdapter.notifyDataSetChanged();
			}
		}, 1000);

		segmentedHost.setAdapter(mAdapter);
	}

	protected void onResume() {
		super.onResume();
		this.registerReceiver(receiverSolicitudes, solicitudesFiltro);
		this.registerReceiver(receiverResponderSolicitud,
				responderSolicitudFiltro);
		// esto es por si tengo que actualizar la lista 
		if (usuariosSolicitudesRecibidas != null)
			mostrarSolicitudesRecibidas();
	}

	protected void onPause() {
		super.onPause();
		this.unregisterReceiver(receiverSolicitudes);
		this.unregisterReceiver(receiverResponderSolicitud);
		Log.i(TAG, "saco el servicio");
	}

	/**
	 * Recibe la respuesta del servicio con las solicitudes del usuario, las
	 * pendientes y las enviadas
	 */
	protected BroadcastReceiver receiverSolicitudes = new BroadcastReceiver() {
		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {

			String error = intent.getStringExtra("error");
			usuariosSolicitudesEnviadas = (ArrayList<UsuarioDTO>) intent
					.getSerializableExtra("listaEnviadas");
			usuariosSolicitudesRecibidas = (ArrayList<UsuarioDTO>) intent
					.getSerializableExtra("listaRecibidas");

			if (error != null && !error.isEmpty()) {

				Util.showToast(context, error);

			} else {

				mostrarSolicitudesEnviadas();
				mostrarSolicitudesRecibidas();

				segmentedHost.getSegmentedBar().setCurrentSegment(0);

				Util.dismissProgressDialog();
				GDTabActivity padre = (GDTabActivity) getParent();
				LoaderActionBarItem loaderActionBarItem = (LoaderActionBarItem) padre
						.getActionBar().getItem(AmigosTabActivity.ACTUALIZAR);
				loaderActionBarItem.setLoading(false);

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
				usuariosSolicitudesRecibidas.remove(pos);
				
				if (respuesta.equalsIgnoreCase(Constantes.MSG_SOLICITUD_APROBADA)){
					// tengo que actualizar la lista de amigos.. en caso de q este en memoria
					
					if (MisAmigosActivity.misAmigos != null){
						MisAmigosActivity.misAmigos.add(usuarioSeleccionadoRecibidas);
						Collections.sort(MisAmigosActivity.misAmigos);
					}
					 
					// tengo que eliminarlo de la lista de busqueda en caso de que este en memoria
					
					if (AgregarAmigosActivity.usuarios != null){
						
						if (!AgregarAmigosActivity.usuarios.isEmpty()){
							
							ListIterator<UsuarioDTO> usuarios = AgregarAmigosActivity.usuarios.listIterator();
						    while (usuarios.hasNext()) {
						    	UsuarioDTO user = usuarios.next();
						    	if(user.getEmail().equalsIgnoreCase(usuarioSeleccionadoRecibidas.getEmail())){
									
						    		usuarios.remove();
									
								}
						    }
						}
					}
					
				}
				else{
					// tengo que modificarlo de la lista de busqueda en caso de que este en memoria
					if (AgregarAmigosActivity.usuarios != null){

						if (!AgregarAmigosActivity.usuarios.isEmpty()){

							
							ListIterator<UsuarioDTO> usuarios = AgregarAmigosActivity.usuarios.listIterator();
						    while (usuarios.hasNext()) {
						    	UsuarioDTO user = usuarios.next();
						    	if(user.getEmail().equalsIgnoreCase(usuarioSeleccionadoRecibidas.getEmail())){
									
						    		user.setSolicitudRecibida(false);
									usuarios.set(user);
									
								}
						    }
							
							
						}
					}
				}
				
				mostrarSolicitudesRecibidas();

			}
		}
	};

	public void mostrarSolicitudesEnviadas() {

		UsuarioAdapter adaptador = new UsuarioAdapter(
				(Activity) lv.getContext(), R.layout.usuario_item,
				usuariosSolicitudesEnviadas);
		lv.setAdapter(adaptador);

	}

	public void mostrarSolicitudesRecibidas() {

		UsuarioAdapter adaptador = new UsuarioAdapter(
				(Activity) lv.getContext(), R.layout.usuario_item,
				usuariosSolicitudesRecibidas);
		lv.setAdapter(adaptador);

	}

	private class SolicitudesSegmentedAdapter extends SegmentedAdapter {

		@Override
		public View getView(int position, ViewGroup parent) {

			return lv;

		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public String getSegmentTitle(int position) {

			switch (position) {
			case 0:
				return "Recibidas";
			case 1:
				return "Enviadas";

			}

			return null;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {

		final Context appContext = this;
		pos = position;

		if (vista == 0) {

			usuarioSeleccionadoRecibidas = usuariosSolicitudesRecibidas
					.get(pos);

			Log.i(TAG,
					"Seleccione: " + usuarioSeleccionadoRecibidas.getNombre());

			// El usuario seleccionado ya envio una solicitud y esta pendiente

			AlertDialog.Builder dialog = new AlertDialog.Builder(appContext);

			dialog.setTitle(Html
					.fromHtml("<b><font color=#ff0000> Solicitud de Amistad Pendiente"));
			dialog.setMessage(usuarioSeleccionadoRecibidas.getNombre()
					+ " quiere ser tu amigo.\n\n¿Deseas responder la solicitud?");

			dialog.setCancelable(false);
			dialog.setIcon(android.R.drawable.ic_dialog_alert);

			dialog.setPositiveButton("Si",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int id) {

							AlertDialog.Builder dialogResponder = new AlertDialog.Builder(
									appContext);
							dialogResponder
									.setTitle("Solicitud de Amistad Pendiente");
							dialogResponder
									.setMessage("Responder Solicitud de "
											+ usuarioSeleccionadoRecibidas
													.getNombre());
							dialogResponder.setCancelable(true);
							dialogResponder
									.setIcon(android.R.drawable.ic_dialog_alert);

							dialogResponder.setPositiveButton(
									"Confirmar \nAmistad",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog, int id) {
											// Confirmar Amistad

											responderSolicitud(
													usuarioSeleccionadoRecibidas
															.getId(),
													Constantes.ACEPTAR_SOLICITUD);
											dialog.cancel();
										}
									});

							dialogResponder.setNegativeButton(
									"Rechazar \nAmistad",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog, int id) {
											// Rechazar Amistad

											responderSolicitud(
													usuarioSeleccionadoRecibidas
															.getId(),
													Constantes.RECHAZAR_SOLICITUD);
											dialog.cancel();
										}
									});

							dialogResponder.show();

							dialog.cancel();
						}
					});
			dialog.setNegativeButton("No",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int id) {

							dialog.cancel();
						}
					});
			dialog.show();

		}

	}

	/**
	 * Responde a una solicitud de amistad
	 * 
	 * @param idUsuarioSeleccionado
	 *            Identificador de usuario seleccionado
	 * 
	 * @param accion
	 */
	public void responderSolicitud(int idUsuarioSeleccionado, String accion) {

		SolicitudContactoDTO solicitud = new SolicitudContactoDTO();
		solicitud.setIdUsuarioSolicitante(idUsuarioSeleccionado);
		solicitud.setIdUsuarioAprobador(app.getUserLogin().getId());

		Intent intentAceptarSolicitud = new Intent(getApplicationContext(),
				ResponderSolicitudServicio.class);
		intentAceptarSolicitud.putExtra("solicitud", solicitud);
		intentAceptarSolicitud.putExtra("accion", accion);

		startService(intentAceptarSolicitud);

		if (accion.equalsIgnoreCase(Constantes.ACEPTAR_SOLICITUD)) {
			Util.showProgressDialog(this,
					Constantes.MSG_ESPERA_ACEPTANDO_SOLICITUD);
		} else {
			Util.showProgressDialog(this,
					Constantes.MSG_ESPERA_RECHAZANDO_SOLICITUD);
		}

	}

}