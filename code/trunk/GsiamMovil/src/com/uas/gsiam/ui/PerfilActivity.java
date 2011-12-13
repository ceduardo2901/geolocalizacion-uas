package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem.Type;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.CerrarCuentaServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;


public class PerfilActivity extends GDActivity {

	protected TextView nombreTxt;
	protected TextView emailTxt;
	protected ImageView iv;
	protected Button editarPerfilBtn;
	protected IntentFilter cerrarCuentaFiltro;
	protected ApplicationController app;
	protected ProgressDialog progressDialog;
		
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.perfil);
		app = ((ApplicationController)getApplicationContext());
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

	    cerrarCuentaFiltro = new IntentFilter(Constantes.CERRAR_CUENTA_FILTRO_ACTION);
	}

	private void inicializarBar() {

		addActionBarItem(Type.Help, 0);
		getActionBar().setTitle("GSIAM - Perfil");

	}
	
	protected void onResume() {
		 super.onResume();
		 registerReceiver(receiverCerrarCuenta, cerrarCuentaFiltro);
	 }
	
	protected void onPause(){
		super.onPause();
		unregisterReceiver(receiverCerrarCuenta);
	}
	 

	public void editarPerfil(View v) {
		
		Intent editarPerfilIntent = new Intent(this,EditarUsuarioActivity.class);
		startActivity(editarPerfilIntent);
		
	}
	
	
	public void cerrarCuenta(View v) {
		
		
	   AlertDialog.Builder dialogResponder = new AlertDialog.Builder(this);
  	   dialogResponder.setTitle("Cierre de Cuenta"); 
  	   dialogResponder.setMessage("¿Esta se guro que desea cerrar su cuenta?");
  	   dialogResponder.setCancelable(true);
  	   dialogResponder.setIcon(android.R.drawable.ic_dialog_alert);  
  	   
  	   dialogResponder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

  		   public void onClick(DialogInterface dialog, int id) {
  			   
  			 cerrarCuenta();  
  			 dialog.cancel();
  		   }
  	   });

  	   dialogResponder.setNegativeButton("No", new DialogInterface.OnClickListener() {

  		   public void onClick(DialogInterface dialog, int id) {
  			  
  			   dialog.cancel();
  		   }
  	   });

  	   dialogResponder.show();
  	   

	}
	
	
	protected void cerrarCuenta(){
		Intent cerrarCuentaIntent = new Intent(getApplicationContext(),CerrarCuentaServicio.class);
		cerrarCuentaIntent.putExtra("usuario", app.getUserLogin());
		startService(cerrarCuentaIntent);

		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
	}
	
	protected BroadcastReceiver receiverCerrarCuenta = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	
			String error = intent.getStringExtra("error");
			String respuesta = intent.getStringExtra("respuesta");
		
			Util.dismissProgressDialog();
			
			if (error != null) {
				
				Util.showToast(context, error);
			}
			else{
	    		
				Util.showToast(context, respuesta);
	    		
				Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intentLogin);
				
			}
	    	
			
	    }
	  };
	
}
