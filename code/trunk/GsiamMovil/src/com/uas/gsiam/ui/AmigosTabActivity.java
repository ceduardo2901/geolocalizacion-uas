package com.uas.gsiam.ui;


import greendroid.app.GDTabActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.LoaderActionBarItem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.uas.gsiam.servicios.GetAmigosServicio;
import com.uas.gsiam.servicios.GetSolicitudesPendientesServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.LocationHelper;
import com.uas.gsiam.utils.Util;
import com.uas.gsiam.utils.LocationHelper.LocationResult;

public class AmigosTabActivity extends GDTabActivity {


    protected static String TAG = "AmigosTabActivity";
 //   protected static boolean registroMisAmigosService = false;
    private static final String TAG_MIS_AMIGOS = "Amigos";
    private static final String TAG_AGREGAR_AMIGOS = "Buscar";
    private static final String TAG_INVITAR_AMIGOS = "Invitar";
    private static final String TAG_SOLICITUDES = "Solicitudes";
    private static final String PREF_STICKY_TAB = "stickyTab";
    
    private static TabHost mTabHost;
    
    protected static final int ACTUALIZAR = 0;
    protected static final int RADAR = 1;
    
    private String tabClick;
	
    
    private Location loc;
	private LocationHelper locHelper;
	
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        inicializarActionBar();
        mTabHost = getTabHost();       
      
        
        anadirTab2();
        anadirTab1();
        anadirTab3();
        anadirTab4(); 
        
        // Al abrir la aplicacion restauramos la �ltima pesta�a activada
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int currentTab = prefs.getInt(PREF_STICKY_TAB, 0);
        mTabHost.setCurrentTab(currentTab);
        Log.i(TAG, "**** currentTab =  "+ currentTab);
        tabClick = mTabHost.getCurrentTabTag();
        
   /*     if (mTabHost.getCurrentTabTag().equalsIgnoreCase(TAG_MIS_AMIGOS)){
        	Log.i(TAG, "**** click");
        	Util.showProgressDialog(mTabHost.getContext(), Constantes.MSG_ESPERA_BUSCANDO);
			iniciarServicioMisAmigos();
			
		}
     */   
        mTabHost.setOnTabChangedListener(new OnTabChangeListener(){
        	@Override
        	public void onTabChanged(String tabId) {
        		Log.i(TAG, "**** click "+tabId);
        		
        		tabClick = tabId;
                
        		
        	/*	if (tabId.equalsIgnoreCase(TAG_MIS_AMIGOS)){
        			Util.showProgressDialog(mTabHost.getContext(), Constantes.MSG_ESPERA_ACTUALIZANDO);
        			iniciarServicioMisAmigos();
        		}
        		*/
        		
        	}
        	});
    }
    
  /*  public void iniciarServicioMisAmigos(){
    	Log.i(TAG, "**** elegi la uno!!! llamo al serv");
		Intent intent = new Intent(mTabHost.getContext() ,GetAmigosServicio.class);
		startService(intent);
    } 
    */
    @Override
    protected void onPause() {
        super.onPause();

        // Cuando se cierra la aplicaci�n guardamos la pesta�a activa
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        int currentTab = mTabHost.getCurrentTab();
        editor.putInt(PREF_STICKY_TAB, currentTab);
        editor.commit();
    } 
    
    
    
	/*
	 * Pesta�a 1
	 */
     
    private void anadirTab1() {
    	
    	Intent intent = new Intent(this, MisAmigosActivity.class);
    	addTab(TAG_MIS_AMIGOS, "Amigos", intent);
    	
  
    }
    
	/*
	 * Pesta�a 2
	 */
    
    private void anadirTab2() {
    	
    	Intent intent = new Intent(this, AgregarAmigosActivity.class);
    	addTab(TAG_AGREGAR_AMIGOS, "Agregar", intent);
  
    }
    
	/*
	 * Pesta�a 3
	 */
    
    private void anadirTab3() {
    	
    	Intent intent = new Intent(this, SolicitudesActivity.class);
    	addTab(TAG_SOLICITUDES, "Solicitudes", intent);
  
    }
   
    /*
	 * Pesta�a 4
	 */
    
    private void anadirTab4() {
    	
    	Intent intent = new Intent(this, InvitarAmigosActivity.class);
    	addTab(TAG_INVITAR_AMIGOS, "Invitar", intent);
          
    }
	
	private void inicializarActionBar() {
	
		getActionBar().addItem(Type.Refresh, ACTUALIZAR);
		getActionBar().addItem(Type.Locate, RADAR);
		getActionBar().setTitle("GSIAM - Amigos");
	}
	
	

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		
		case ACTUALIZAR:
			
			if (tabClick.equalsIgnoreCase(TAG_MIS_AMIGOS)){
				//Util.showToast(this, "TAG_MIS_AMIGOS");
				actualizarAmigos();
				
			}
			if (tabClick.equalsIgnoreCase(TAG_AGREGAR_AMIGOS)){
				//Util.showToast(this, "TAG_AGREGAR_AMIGOS");
				LoaderActionBarItem loaderActionBarItem = (LoaderActionBarItem) getActionBar().getItem(AmigosTabActivity.ACTUALIZAR);
				loaderActionBarItem.setLoading(false);
			}
			if (tabClick.equalsIgnoreCase(TAG_SOLICITUDES)){
				//Util.showToast(this, "TAG_SOLICITUDES");
				actualizarSolicitudes();
		    	
			}
			if (tabClick.equalsIgnoreCase(TAG_INVITAR_AMIGOS)){
				//Util.showToast(this, "TAG_INVITAR_AMIGOS");
				LoaderActionBarItem loaderActionBarItem = (LoaderActionBarItem) getActionBar().getItem(AmigosTabActivity.ACTUALIZAR);
				loaderActionBarItem.setLoading(false);
			}
			
			break;
		

		case RADAR:

			if (tabClick.equalsIgnoreCase(TAG_MIS_AMIGOS)){

				locHelper = new LocationHelper();
				locHelper.getLocation(this, locationResult);

				if (loc == null){
					Util.showToast(this, Constantes.MSG_GPS_DISABLE);

				} else {
					radarAmigos();
				}

			}
			if (tabClick.equalsIgnoreCase(TAG_AGREGAR_AMIGOS)){
				Util.showToast(this, Constantes.MSG_RADAR_TAB_AMIGOS);
			}
			if (tabClick.equalsIgnoreCase(TAG_SOLICITUDES)){
				Util.showToast(this, Constantes.MSG_RADAR_TAB_AMIGOS);
			}
			if (tabClick.equalsIgnoreCase(TAG_INVITAR_AMIGOS)){
				Util.showToast(this, Constantes.MSG_RADAR_TAB_AMIGOS);
			}

			break;
			
		default:
			return super.onHandleActionBarItemClick(item, position);
		}

		return true;

	}
	
	public LocationResult locationResult = new LocationResult() {

		@Override
		public void obtenerUbicacion(final Location location) {
			loc = location;
		}
	};
	
	private void actualizarAmigos(){
		Intent intent = new Intent(this,GetAmigosServicio.class);
		startService(intent);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_ACTUALIZANDO);
	}
	
	private void actualizarSolicitudes(){
		Intent intentCargarSolicitudes = new Intent(this,GetSolicitudesPendientesServicio.class);
    	startService(intentCargarSolicitudes);
    	Util.showProgressDialog(this, Constantes.MSG_ESPERA_ACTUALIZANDO);
	}
	
	private void radarAmigos(){
		Intent radarIntent = new Intent(this, RadarActivity.class);
		startActivity(radarIntent);
	}
    
}