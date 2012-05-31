package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.LoginServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Este activity representa la interfaz para realizar el login en la aplicacion.
 * Permite a el usuario, a partir del email y la contraseña, la autenticacion en
 * la misma
 * 
 * Tambien permite la opccion de registrarse en el sistema si es que aun no esta
 * registrado
 * 
 * @author Antonio
 * 
 */
public class LoginActivity extends Activity implements TextWatcher{

	protected static String TAG = "LoginActivity";
	protected String email;
	protected String pass;

	protected EditText emailTxt;
	protected EditText passTxt;
	protected TextView textAplicacion;
	protected IntentFilter loginFiltro;
	protected Button loginBtn;

	protected UsuarioDTO user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		user = new UsuarioDTO();
		this.passTxt = (EditText) findViewById(R.id.passTxt);
		this.emailTxt = (EditText) findViewById(R.id.emailTxt);
		this.loginBtn = (Button) findViewById(R.id.entrarBtn);
		
		passTxt.addTextChangedListener(this);
		emailTxt.addTextChangedListener(this);
		loginBtn.setEnabled(false);
		loginFiltro = new IntentFilter(Constantes.LOGIN_FILTRO_ACTION);

	}

	protected void onStart() {
		super.onStart();

	}

	protected void onResume() {
		super.onResume();

		registerReceiver(loginReceiver, loginFiltro);

	}

	protected void onPause() {
		super.onPause();

		unregisterReceiver(loginReceiver);

	}

	public void login(View v) {

		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();

		if (!Util.validaMail(email)) {

			Util.showToast(v.getContext(), Constantes.MSG_ERROR_MAIL);

		} else {
			if (isOnline()) {

				Intent intent = new Intent(this, LoginServicio.class);
				intent.putExtra("email", email);
				intent.putExtra("pass", pass);
				startService(intent);

				Util.showProgressDialog(this,
						Constantes.MSG_ESPERA_INICIANDO_SESION);
			} else {
				Util.showToast(this, Constantes.MSG_CONEXION_ERROR);
			}
		}
	}

	/**
	 * Determina si el dispositivo movil tiene conexion a internet, en caso
	 * contrario avisa al usuario que habilite algun mecanismo de conexion
	 * 
	 * @return Devuelve true si esta hilitada la conexion a internet en el
	 *         dispositivo movil, false en caso contrario
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public void btnRegistrarse(View v) {

		Intent intent = new Intent();
		intent.setClass(this, CrearUsuarioActivity.class);
		startActivity(intent);

	}

	private void actividadPrincipal() {
		Intent actividadPrincipal = new Intent(this, MainActivity.class);

		startActivity(actividadPrincipal);
	}

	protected BroadcastReceiver loginReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			
			Util.dismissProgressDialog();
			String error = intent.getStringExtra("error");
			if (error != null) {
				Util.showToast(context, error);
			} else {
					user = (UsuarioDTO) intent.getSerializableExtra("usuario");
					if (user.getEmail() != null) {

						actividadPrincipal();
					} else {
						Util.showToast(context, Constantes.MSG_LOGIN_ERROR);
					}
				
			}

		}
	};

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!emailTxt.getText().toString().equals("")
				&& !passTxt.getText().toString().equals("")
				) {
			loginBtn.setEnabled(true);
		} else {
			loginBtn.setEnabled(false);
		}
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}
}
