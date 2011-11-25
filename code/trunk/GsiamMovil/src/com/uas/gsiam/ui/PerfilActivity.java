package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem.Type;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Util;


public class PerfilActivity extends GDActivity {

	protected TextView nombreTxt;
	protected TextView emailTxt;
	protected ImageView iv;
	protected Button editarPerfilBtn;
		
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.perfil);
		ApplicationController app = ((ApplicationController)getApplicationContext());
	    UsuarioDTO user = app.getUserLogin();
	    
	    nombreTxt = (TextView) findViewById(R.id.nombreTxt);
		emailTxt = (TextView) findViewById(R.id.emailTxt);
		iv = (ImageView)findViewById(R.id.avatar);
		editarPerfilBtn = (Button)findViewById(R.id.editarPerfilBtn);
	    
	    nombreTxt.setText(user.getNombre());
	    emailTxt.setText(user.getEmail());
	    
	    if (user.getAvatar() != null)
	    	iv.setImageBitmap(Util.ArrayToBitmap(user.getAvatar()));

	    inicializarBar();

	}

	private void inicializarBar() {

		addActionBarItem(Type.Help, 0);
		getActionBar().setTitle("GSIAM - Perfil");

	}
	
	protected void onResume() {
		 super.onResume();
		
	 }
	
	protected void onPause(){
		super.onPause();
		
	}
	 

	public void editarPerfil(View v) {
		
		Intent editarPerfilIntent = new Intent(this,EditarUsuarioActivity.class);
		startActivity(editarPerfilIntent);
		
	}
	
	
  
	
}
