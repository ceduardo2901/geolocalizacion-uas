package com.uas.gsiam.ui;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uas.gsiam.negocio.dto.UsuarioDTO;

import com.uas.gsiam.utils.Util;

public class CrearUsuario extends Activity {

	protected String email;
	protected String pass;
	protected String nombre;

	protected EditText nombreTxt;
	protected EditText emailTxt;
	protected EditText passTxt;
	protected Button registrarseBtn;

	private UsuarioDTO user;
	private ProgressDialog progressDialog;
	
	private static final String url = "http://10.0.2.2:8080/GsiamWeb2/rest/usuarios/agregar?usuarioDto={usuarioDto}";
	private RestTemplate restTemp;

	

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
			Toast.makeText(v.getContext(), "El email es invalido",
					Toast.LENGTH_LONG).show();
		} else {
				
			 new DownloadStatesTask(user).execute();

		}
	}

	
	
	public void actividadPrincipal(){
		Intent actividadPrincipal = new Intent();
		actividadPrincipal.setClass(this, MainActivity.class);
		startActivity(actividadPrincipal);
	}
	
	private void showLoadingProgressDialog() {
		progressDialog = ProgressDialog.show(this, "",
				"Loading. Please wait...", true);
	}

	private void dismissProgressDialog() {
		if (progressDialog != null) {

			progressDialog.dismiss();
		}
	}


	// ***************************************
	// Private classes
	// ***************************************
	private class DownloadStatesTask extends AsyncTask<Void, Void, UsuarioDTO> {
		private UsuarioDTO usuario;
		
		public DownloadStatesTask(UsuarioDTO usuario) {
			this.usuario=usuario;
			
		}

		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected UsuarioDTO doInBackground(Void... params) {
			
            	restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
				Map<String, UsuarioDTO> parms = new HashMap<String, UsuarioDTO>();
				parms.put("usuario", usuario);
				UsuarioDTO user = restTemp.getForObject(url, UsuarioDTO.class,parms);
				
				return user;
			
		}

		@Override
		protected void onPostExecute(UsuarioDTO usuario) {
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			actividadPrincipal();
			
		}
	}


	
	
}
