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
      
        a�adirTab1();
        a�adirTab2();
        a�adirTab3();        
        
        // Al abrir la aplicacion restauramos la �ltima pesta�a activada
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int currentTab = prefs.getInt(PREF_STICKY_TAB, 0);
        mTabHost.setCurrentTab(currentTab);   
    }
    
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
    
    
    protected void onResume(){
		super.onResume();
	
	}
    
	/*
	 * Pesta�a 1
	 */
     
    private void a�adirTab1() {
    	
    	Intent intent = new Intent(this, MisAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_SCHEDULED);
        spec.setIndicator("martin 1");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
	/*
	 * Pesta�a 2
	 */
    
    private void a�adirTab2() {
    	
    	Intent intent = new Intent(this, BuscarAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_CREATE);
        spec.setIndicator("martin 2");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
	/*
	 * Pesta�a 3
	 */
    
    private void a�adirTab3() {
    	
    	Intent intent = new Intent(this, InvitarAmigosActivity.class);
    	
        TabSpec spec = mTabHost.newTabSpec(TAG_OPTIONS);
        spec.setIndicator("martin 3");
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
    
   
    
}