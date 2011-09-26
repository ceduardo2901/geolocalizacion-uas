package com.uas.gsiam.ui;


import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AmigosTabActivity extends TabActivity{

    private TabHost mTabHost;
      
    private static final String TAG_SCHEDULED = "Scheduled";
    private static final String TAG_CREATE = "Create";
    private static final String TAG_OPTIONS = "Options";
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
    
    
    protected void onResume(){
		super.onResume();
	
	}
    
	/*
	 * Pestaña 1
	 */
     
    private void añadirTab1() {
    	
    	Intent intent = new Intent(this, MisAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_SCHEDULED);
        spec.setIndicator("martin 1");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
	/*
	 * Pestaña 2
	 */
    
    private void añadirTab2() {
    	
    	Intent intent = new Intent(this, BuscarAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_CREATE);
        spec.setIndicator("martin 2");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
	/*
	 * Pestaña 3
	 */
    
    private void añadirTab3() {
    	
    	Intent intent = new Intent(this, InvitarAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_OPTIONS);
        spec.setIndicator("martin 3");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
    
   
    
}