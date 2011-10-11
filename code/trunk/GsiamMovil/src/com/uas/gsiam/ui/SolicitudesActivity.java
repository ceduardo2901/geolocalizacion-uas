package com.uas.gsiam.ui;

import java.util.ArrayList;

import greendroid.widget.SegmentedAdapter;
import greendroid.widget.SegmentedBar.OnSegmentChangeListener;
import greendroid.widget.SegmentedHost;
import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.uas.gsiam.adapter.UsuarioAdapter;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.GetSolicitudesPendientesServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;



public class SolicitudesActivity extends ListActivity implements OnItemClickListener{

	private final Handler mHandler = new Handler();
    private SolicitudesSegmentedAdapter mAdapter;
    protected ListView lv;
    private int vista;
    protected IntentFilter solicitudesFiltro;
    public static ArrayList<UsuarioDTO> usuariosSolicitudesEnviadas;
    public static ArrayList<UsuarioDTO> usuariosSolicitudesRecibidas;
  
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        lv = getListView();
        setContentView(R.layout.solicitudes_amigos_tab);
        Bundle bundle = new Bundle();
        
        solicitudesFiltro = new IntentFilter(Constantes.GET_SOLICITUDES_FILTRO_ACTION);
      
        Intent intentCargarSolicitudes = new Intent(getApplicationContext(),GetSolicitudesPendientesServicio.class);
        intentCargarSolicitudes.putExtras(bundle);
		startService(intentCargarSolicitudes);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_ACTUALIZANDO);
        	
        
   		lv.setOnItemClickListener(this);

        
        SegmentedHost segmentedHost = (SegmentedHost) findViewById(R.id.segmented_host);
 
        segmentedHost.getSegmentedBar().setOnSegmentChangeListener(new OnSegmentChangeListener() {
            
            @Override
            public void onSegmentChange(int index, boolean clicked) {
            	    Log.i("TAG", "************************************");
            	    Log.i("TAG", "***** cambio el segemento");
                    Log.i("TAG", "***** index:"+index);
                    Log.i("TAG", "***** clicked:"+clicked);
                    Log.i("TAG", "************************************");
                    
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
		 Log.i("TAG", "registro el servicio");
	 }
	
	protected void onPause(){
		super.onPause();
		this.unregisterReceiver(receiverSolicitudes);
		Log.i("TAG", "saco el servicio");
	}
    
	
	protected BroadcastReceiver receiverSolicitudes = new BroadcastReceiver() {
		@Override
	    public void onReceive(Context context, Intent intent) {
	    		
	    	Bundle bundle = intent.getExtras();
			usuariosSolicitudesEnviadas = (ArrayList<UsuarioDTO>) bundle.getSerializable("listaEnviadas");
			usuariosSolicitudesRecibidas = (ArrayList<UsuarioDTO>) bundle.getSerializable("listaRecibidas");
	    	    	
			mostrarSolicitudesEnviadas();
			mostrarSolicitudesRecibidas();
	
			Util.dismissProgressDialog();
			
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
    
    @Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
	  
	  
		if (vista == 0){
			
			UsuarioDTO usuario = MisAmigosActivity.misAmigos.get(position);
			
			Log.i("lala", "Seleccione: "+ usuario.getNombre());
			
		}
    	
    	
		
	}
    
    
    
    private class SolicitudesSegmentedAdapter extends SegmentedAdapter {
 
 
        @Override
        public View getView(int position, ViewGroup parent) {
 
        	Log.i("TAG", "***** estoy en el getView :"+ position);
	    	
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

	  
	  
}