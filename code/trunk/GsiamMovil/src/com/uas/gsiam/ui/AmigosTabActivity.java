package com.uas.gsiam.ui;


import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class AmigosTabActivity extends TabActivity {

    private TabHost mTabHost;
    protected static String TAG = "AmigosTabActivity";
    private static final String TAG_MIS_AMIGOS = "Amigos";
    private static final String TAG_AGREGAR_AMIGOS = "Agregar";
    private static final String TAG_INVITAR_AMIGOS = "Invitar";
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
        
        // Al abrir la aplicacion restauramos la última pestaña activada
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int currentTab = prefs.getInt(PREF_STICKY_TAB, 0);
        mTabHost.setCurrentTab(currentTab);   
        
        mTabHost.setOnTabChangedListener(new OnTabChangeListener(){
        	@Override
        	public void onTabChanged(String tabId) {
        		Log.d(this.getClass().getName(), ">>>>>>>>>>>>>>>>>>>>>>>fffff> tabId: " + tabId);
        	}
        	});
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
        Log.i(TAG, "en el onpause ");
    } 
    
    
    protected void onResume(){
		super.onResume();
		Log.i(TAG, "en el onResume ");
	}
    
	/*
	 * Pestaña 1
	 */
     
    private void añadirTab1() {
    	
    	Intent intent = new Intent(this, MisAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_MIS_AMIGOS);
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
   

	
    
}