package com.uas.gsiam.ui;


import com.uas.gsiam.servicios.GetAmigosServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class AmigosTabActivity extends TabActivity {

    private TabHost mTabHost;
    protected static String TAG = "AmigosTabActivity";
    protected static boolean registroMisAmigosService = false;
    private static final String TAG_MIS_AMIGOS = "Amigos";
    private static final String TAG_AGREGAR_AMIGOS = "Agregar";
    private static final String TAG_INVITAR_AMIGOS = "Invitar";
    private static final String TAG_SOLICITUDES = "Solicitudes";
    private static final String PREF_STICKY_TAB = "stickyTab";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amigos);
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
        	
			iniciarServicioMisAmigos();
		}
        
        mTabHost.setOnTabChangedListener(new OnTabChangeListener(){
        	@Override
        	public void onTabChanged(String tabId) {
        		
        		if (tabId.equalsIgnoreCase(TAG_MIS_AMIGOS)){
        			
        			iniciarServicioMisAmigos();
        		}
        		
        		
        	}
        	});
    }
    
    public void iniciarServicioMisAmigos(){
    	Util.showProgressDialog(mTabHost.getContext(), Constantes.MSG_ESPERA_BUSCANDO);
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
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_MIS_AMIGOS);
   /*     TextView tab1 = null;
        tab1 = new TextView(this);
        tab1.setText("Mis Amigos");
        tab1.setGravity(android.view.Gravity.CENTER);
        tab1.setTextColor(R.color.fondo);
        spec.setIndicator(tab1);
        */
        spec.setIndicator("Mis Amigos");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
	/*
	 * Pestaña 2
	 */
    
    private void añadirTab2() {
    	
    	Intent intent = new Intent(this, AgregarAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_AGREGAR_AMIGOS);
        spec.setIndicator("Agregar Amigos");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
	/*
	 * Pestaña 3
	 */
    
    private void añadirTab3() {
    	
    	Intent intent = new Intent(this, InvitarAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_INVITAR_AMIGOS);
        spec.setIndicator("Invitar Amigos");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
   
    /*
	 * Pestaña 4
	 */
    
    private void añadirTab4() {
    	
    	Intent intent = new Intent(this, InvitarAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_SOLICITUDES);
        spec.setIndicator("Solicitudes");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
	
    
}