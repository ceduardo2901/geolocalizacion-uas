package com.uas.gsiam.ui;


import greendroid.app.GDTabActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.uas.gsiam.servicios.GetAmigosServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class AmigosTabActivity extends GDTabActivity {


    protected static String TAG = "AmigosTabActivity";
    protected static boolean registroMisAmigosService = false;
    private static final String TAG_MIS_AMIGOS = "Amigos";
    private static final String TAG_AGREGAR_AMIGOS = "Agregar";
    private static final String TAG_INVITAR_AMIGOS = "Invitar";
    private static final String TAG_SOLICITUDES = "Solicitudes";
    private static final String PREF_STICKY_TAB = "stickyTab";
    
    private static TabHost mTabHost;
    
    private final int ACTUALIZAR = 0;
    private final int BUSCAR = 1;
    private final int FILTRAR = 2;
    private final int FRIENDS = 3;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        inicializarActionBar();
        mTabHost = getTabHost();       
      
        
        
        añadirTab1();
        añadirTab2();
        añadirTab3();
        añadirTab4(); 
        
        // Al abrir la aplicacion restauramos la última pestaña activada
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int currentTab = prefs.getInt(PREF_STICKY_TAB, 0);
        mTabHost.setCurrentTab(currentTab);
        
        if (mTabHost.getCurrentTabTag().equalsIgnoreCase(TAG_MIS_AMIGOS)){
        	Log.i(TAG, "**** click");
        	Util.showProgressDialog(mTabHost.getContext(), Constantes.MSG_ESPERA_BUSCANDO);
			iniciarServicioMisAmigos();
			
		}
        
        mTabHost.setOnTabChangedListener(new OnTabChangeListener(){
        	@Override
        	public void onTabChanged(String tabId) {
        		Log.i(TAG, "**** click "+tabId);
        		if (tabId.equalsIgnoreCase(TAG_MIS_AMIGOS)){
        			Util.showProgressDialog(mTabHost.getContext(), Constantes.MSG_ESPERA_ACTUALIZANDO);
        			iniciarServicioMisAmigos();
        		}
        		
        		
        	}
        	});
    }
    
    public void iniciarServicioMisAmigos(){
    	Log.i(TAG, "**** elegi la uno!!! llamo al serv");
		Intent intent = new Intent(mTabHost.getContext() ,GetAmigosServicio.class);
		startService(intent);
		  
		
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        // Cuando se cierra la aplicación guardamos la pestaña activa
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        int currentTab = mTabHost.getCurrentTab();
        editor.putInt(PREF_STICKY_TAB, currentTab);
        editor.commit();
    } 
    
    
    
	/*
	 * Pestaña 1
	 */
     
    private void añadirTab1() {
    	
    	Intent intent = new Intent(this, MisAmigosActivity.class);
    	addTab(TAG_MIS_AMIGOS, "Amigos", intent);
    	
  
    }
    
	/*
	 * Pestaña 2
	 */
    
    private void añadirTab2() {
    	
    	Intent intent = new Intent(this, AgregarAmigosActivity.class);
    	addTab(TAG_AGREGAR_AMIGOS, "Agregar", intent);
  
    }
    
	/*
	 * Pestaña 3
	 */
    
    private void añadirTab3() {
    	
    	Intent intent = new Intent(this, SolicitudesActivity.class);
    	addTab(TAG_SOLICITUDES, "Solicitudes", intent);
  
    }
   
    /*
	 * Pestaña 4
	 */
    
    private void añadirTab4() {
    	
    	Intent intent = new Intent(this, InvitarAmigosActivity.class);
    	addTab(TAG_INVITAR_AMIGOS, "Invitar", intent);
          
    }
	
	private void inicializarActionBar() {
	
		addActionBarItem(Type.Refresh, ACTUALIZAR);
		addActionBarItem(Type.Refresh, ACTUALIZAR);
		
		setTitle("GSIAM - Amigos");
	}
	
	
	
	

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case BUSCAR:
			Util.showToast(this, "BUSCAR");
			break;
		case ACTUALIZAR:
			Util.showToast(this, "ACTUALIZAR");
			
			break;
		case FILTRAR:
			Util.showToast(this, "FILTRAR");
			break;
		case FRIENDS:
			Util.showToast(this, "FRIENDS");
			break;	
		case 4:
			Util.showToast(this, "FRIENDS");
			break;	
		case 5:
			Util.showToast(this, "FRIENDS");
			break;	
		case 6:
			Util.showToast(this, "FRIENDS");
			break;	

		default:
			return super.onHandleActionBarItemClick(item, position);
		}

		return true;

	}
    
}