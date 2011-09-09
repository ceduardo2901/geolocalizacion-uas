package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;


public class PerfilActivity extends Activity {

	protected TextView nombreTxt;
	protected TextView emailTxt;
	protected IntentFilter crearUsuarioFiltro;
		
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfilusuario);
		
		ApplicationController app = ((ApplicationController)getApplicationContext());
	    UsuarioDTO user = app.getUserLogin();
	    
	    nombreTxt = (TextView) findViewById(R.id.nombreTxt);
		emailTxt = (TextView) findViewById(R.id.emailTxt);
	    
	    nombreTxt.setText(user.getNombre());
	    emailTxt.setText(user.getPassword());
		

	}

	protected void onResume() {
		 super.onResume();
		
	 }
	
	protected void onPause(){
		super.onPause();
		
	}
	 

	
	
	
  
	
}
