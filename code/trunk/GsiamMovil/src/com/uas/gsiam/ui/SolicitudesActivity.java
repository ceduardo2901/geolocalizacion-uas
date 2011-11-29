package com.uas.gsiam.ui;

import java.util.ArrayList;

import greendroid.app.GDTabActivity;
import greendroid.widget.LoaderActionBarItem;
import greendroid.widget.SegmentedAdapter;
import greendroid.widget.SegmentedBar.OnSegmentChangeListener;
import greendroid.widget.SegmentedHost;
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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.uas.gsiam.adapter.UsuarioAdapter;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.GetSolicitudesPendientesServicio;
import com.uas.gsiam.servicios.ResponderSolicitudServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class SolicitudesActivity extends ListActivity implements OnItemClickListener{

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
  
    protected static String TAG = "SolicitudesActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "***** estoy en el onCreate");
        app = ((ApplicationController) getApplicationContext());
        lv = getListView();
        setContentView(R.layout.solicitudes_amigos_tab);
 
        solicitudesFiltro = new IntentFilter(Constantes.GET_SOLICITUDES_FILTRO_ACTION);
        responderSolicitudFiltro = new IntentFilter(Constantes.RESPONDER_SOLICITUD_AMISTAD_FILTRO_ACTION);
      
        Intent intentCargarSolicitudes = new Intent(getApplicationContext(),GetSolicitudesPendientesServicio.class);
		startService(intentCargarSolicitudes);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_BUSCANDO);
        	
        
   		lv.setOnItemClickListener(this);
   		lv.setCacheColorHint(Color.TRANSPARENT);
        
        segmentedHost = (SegmentedHost) findViewById(R.id.segmented_host);
        
        segmentedHost.getSegmentedBar().setOnSegmentChangeListener(new OnSegmentChangeListener() {
            
            @Override
            public void onSegmentChange(int index, boolean clicked) {
            	    Log.i(TAG, "************************************");
            	    Log.i(TAG, "***** cambio el segemento");
                    Log.i(TAG, "***** index:"+index);
                    Log.i(TAG, "***** clicked:"+clicked);
                    Log.i(TAG, "************************************");
                    
                    if (clicked){

                    	vista = index;

                    	if (index == 0 ){

                    		mostrarSolicitudesRecibidas();

                    	}
                    	else{

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
		 this.registerReceiver(receiverResponderSolicitud, responderSolicitudFiltro);
		 Log.i(TAG, "registro el servicio");
	 }
	
	protected void onPause(){
		super.onPause();
		this.unregisterReceiver(receiverSolicitudes);
		this.unregisterReceiver(receiverResponderSolicitud);
		Log.i(TAG, "saco el servicio");
	}
    
	
	protected BroadcastReceiver receiverSolicitudes = new BroadcastReceiver() {
		@SuppressWarnings("unchecked")
		@Override
	    public void onReceive(Context context, Intent intent) {
	    		
	    	Bundle bundle = intent.getExtras();
			usuariosSolicitudesEnviadas = (ArrayList<UsuarioDTO>) bundle.getSerializable("listaEnviadas");
			usuariosSolicitudesRecibidas = (ArrayList<UsuarioDTO>) bundle.getSerializable("listaRecibidas");
	    	    	
			mostrarSolicitudesEnviadas();
			mostrarSolicitudesRecibidas();
			
			segmentedHost.getSegmentedBar().setCurrentSegment(0);
			
			
			Util.dismissProgressDialog();
			GDTabActivity padre = (GDTabActivity) getParent();
			LoaderActionBarItem loaderActionBarItem = (LoaderActionBarItem) padre.getActionBar().getItem(AmigosTabActivity.ACTUALIZAR);
			loaderActionBarItem.setLoading(false);
			
			
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
	  
	  public void mostrarSolicitudesEnviadas() {
		  
		  UsuarioAdapter adaptador = new UsuarioAdapter((Activity) lv.getContext(), R.layout.usuario_item, usuariosSolicitudesEnviadas);
		  lv.setAdapter(adaptador);
         	 	
	  }
			
	  
	  public void mostrarSolicitudesRecibidas() {
		  
		  UsuarioAdapter adaptador = new UsuarioAdapter((Activity) lv.getContext(), R.layout.usuario_item, usuariosSolicitudesRecibidas);
			lv.setAdapter(adaptador);
			
		}
    

    
    private class SolicitudesSegmentedAdapter extends SegmentedAdapter {
 
 
        @Override
        public View getView(int position, ViewGroup parent) {
 
        	Log.i(TAG, "***** estoy en el getView :"+ position);
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
                    return "Pendientes";
                case 1:
                    return "Enviadas";
                
            }
 
            return null;
        }
    
        
        
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

    	final Context appContext = this;
    	final UsuarioDTO usuario;
    	final int pos = position;
		
    	if (vista == 0){

    		usuario = usuariosSolicitudesRecibidas.get(pos);

    		Log.i(TAG, "Seleccione: "+ usuario.getNombre());


    		// El usuario seleccionado ya envio una solicitud y esta pendiente 
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(appContext);
			
			dialog.setTitle(Html.fromHtml("<b><font color=#ff0000> Solicitud de Amistad Pendiente")); 
			dialog.setMessage(usuario.getNombre() +" quiere ser tu amigo.\n\n�Deseeas responder la solicitud?");
			
		//	Html.fromHtml("<b><font color=#ff0000> Html View " +"</font></b><br>Androidpeople.com")
			
			
			dialog.setCancelable(false);
			dialog.setIcon(android.R.drawable.ic_dialog_alert);  
			
			dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			    	   
			           public void onClick(DialogInterface dialog, int id) {
			        	   
			        	   AlertDialog.Builder dialogResponder = new AlertDialog.Builder(appContext);
			        	   dialogResponder.setTitle("Solicitud de Amistad Pendiente"); 
			        	   dialogResponder.setMessage("Responder Solicitud de " + usuario.getNombre());
			        	   dialogResponder.setCancelable(true);
			        	   dialogResponder.setIcon(android.R.drawable.ic_dialog_alert);  
			        	   
			        	   dialogResponder.setPositiveButton("Confirmar Amistad", new DialogInterface.OnClickListener() {

			        		   public void onClick(DialogInterface dialog, int id) {
			        			   //  Confirmar Amistad 

			        			   responderAmistad(usuario.getId(), Constantes.ACEPTAR_SOLICITUD);
			        			   usuariosSolicitudesRecibidas.remove(pos);
			        			   mostrarSolicitudesRecibidas();

			        			   dialog.cancel();
			        		   }
			        	   });

			        	   dialogResponder.setNegativeButton("Rechazar Amistad", new DialogInterface.OnClickListener() {

			        		   public void onClick(DialogInterface dialog, int id) {
			        			   //  Rechazar Amistad
/*
			        			   responderAmistad(usuarioSeleccionado.getId(), Constantes.RECHAZAR_SOLICITUD);
			        			   usuarioSeleccionado.setSolicitudRecibida(false);
			        			   usuarios.set(pos, usuarioSeleccionado);
			        			   mostrarUsuarios();
*/
			        			   dialog.cancel();
			        		   }
			        	   });

			        	   dialogResponder.show();

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

		  if (accion == Constantes.ACEPTAR_SOLICITUD){
			  Util.showProgressDialog(this, Constantes.MSG_ESPERA_ACEPTANDO_SOLICITUD);
		  }
		  else{
			  Util.showProgressDialog(this, Constantes.MSG_ESPERA_RECHAZANDO_SOLICITUD);
		  }

	  }
    
	  
}