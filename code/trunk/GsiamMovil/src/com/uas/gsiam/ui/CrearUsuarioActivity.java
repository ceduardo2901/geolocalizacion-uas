package com.uas.gsiam.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.CrearUsuarioServicio;
import com.uas.gsiam.utils.Constantes;

import com.uas.gsiam.utils.Util;

public class CrearUsuarioActivity extends Activity {

	protected String email;
	protected String pass;
	protected String nombre;

	protected EditText nombreTxt;
	protected EditText emailTxt;
	protected EditText passTxt;
	protected Button registrarseBtn;
	protected IntentFilter crearUsuarioFiltro;
	protected ProgressDialog progressDialog;
	
	protected static String TAG = "CrearUsuarioActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crearusuario);
		
		nombreTxt = (EditText) findViewById(R.id.nombreTxt);
		emailTxt = (EditText) findViewById(R.id.emailTxt);
		passTxt = (EditText) findViewById(R.id.passTxt);
		registrarseBtn = (Button) findViewById(R.id.crearUsuarioBtn);
		
		crearUsuarioFiltro = new IntentFilter(Constantes.CREAR_USUARIO_FILTRO_ACTION);

	}

	protected void onResume() {
		 super.onResume();
		 Log.i(TAG, "onResume");
		registerReceiver(receiverCrearUsuario, crearUsuarioFiltro);
	 }
	
	protected void onPause(){
		super.onPause();
		unregisterReceiver(receiverCrearUsuario);
	}
	 
	public void crearUsuario(View v) {
		Log.i(TAG, "crearUsuario");
		nombre = nombreTxt.getText().toString().trim();
		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();
	
		if (!Util.validaMail(email)) {
			Util.showToast(v.getContext(), Constantes.MSG_ERROR_MAIL);
					
		} else {
				
			
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setNombre(nombre);
			usuario.setEmail(email);
			usuario.setPassword(pass);
			
			
			Bundle bundle = new Bundle();
			bundle.putSerializable("usuario", usuario);
			

			Intent intent = new Intent(this,CrearUsuarioServicio.class);
			intent.putExtras(bundle);
			startService(intent);
			
			Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

		}
	}

	
	
	protected BroadcastReceiver receiverCrearUsuario = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	Log.i(TAG, "onReceive");
	    	Bundle bundle = intent.getExtras();
			String respuesta = bundle.getString("respuesta");
		
			Util.dismissProgressDialog();
			
	    	if (respuesta.equals(Constantes.RETURN_OK)){
	    		Util.showToast(context, "El usuario se ha creado exitosamente");
				Intent actividadPrincipal = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(actividadPrincipal);
				
			}
			else{
				Util.showAlertDialog(context, "Aviso", respuesta);
			}
	    	
			
	    }
	  };
  
	
}
