package com.uas.gsiam.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

	private ProgressDialog progressDialog;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crearusuario);
		
		nombreTxt = (EditText) findViewById(R.id.nombreTxt);
		emailTxt = (EditText) findViewById(R.id.emailTxt);
		passTxt = (EditText) findViewById(R.id.passTxt);
		registrarseBtn = (Button) findViewById(R.id.crearUsuarioBtn);
		

	}

	
	public void crearUsuario(View v) {
		
		nombre = nombreTxt.getText().toString().trim();
		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();
	
		if (!Util.validaMail(email)) {
			Toast.makeText(v.getContext(), Constantes.MSG_ERROR_MAIL,
					Toast.LENGTH_LONG).show();
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
			
			showLoadingProgressDialog();

		}
	}

	
	
	protected BroadcastReceiver prueba = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	dismissProgressDialog();
	    	Intent actividadPrincipal = new Intent(getApplicationContext(), MainActivity.class);
			
			startActivity(actividadPrincipal);
	    }
	  };
	
	private void showLoadingProgressDialog() {
		progressDialog = ProgressDialog.show(this, "",
				Constantes.MSG_ESPERA_GENERICO, true);
	}

	private void dismissProgressDialog() {
		if (progressDialog != null) {

			progressDialog.dismiss();
		}
	}

	
}
