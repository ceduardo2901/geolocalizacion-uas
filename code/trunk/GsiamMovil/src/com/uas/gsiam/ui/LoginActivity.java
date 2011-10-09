package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.LoginServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class LoginActivity extends Activity {

	protected static String TAG = "LoginActivity";
	protected String email;
	protected String pass;

	protected EditText emailTxt;
	protected EditText passTxt;
	//protected Button login;
	protected IntentFilter loginFiltro;

	protected UsuarioDTO user;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		user = new UsuarioDTO();
		this.passTxt = (EditText) findViewById(R.id.passTxt);
		this.emailTxt = (EditText) findViewById(R.id.emailTxt);
		

		loginFiltro = new IntentFilter(Constantes.LOGIN_FILTRO_ACTION);

	}

	protected void onStart(){
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

	public void btnValidarEmail(View v) {

		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();

		if (!Util.validaMail(email)) {
			
			Util.showToast(v.getContext(), Constantes.MSG_ERROR_MAIL);
			
		} else {
			Bundle bundle = new Bundle();
			bundle.putString("email", email);
			bundle.putString("pass", pass);

			Intent intent = new Intent(this, LoginServicio.class);
			intent.putExtras(bundle);
			startService(intent);

			Util.showProgressDialog(this, Constantes.MSG_ESPERA_INICIANDO_SESION);
		}
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
			Log.i(TAG, "mensaje de prueba estoy aca !!!!");
			Util.dismissProgressDialog();
			Bundle bundleError = intent.getExtras();
			String error = bundleError.getString("error");
			
			if (error != null) {
				Util.showToast(context, error);
			} else {
				Bundle bundle = intent.getBundleExtra("usuario");
				user = (UsuarioDTO) bundle.getSerializable("usuario");
				if (user.getEmail() != null) {
					//SessionStore.save(email, getApplicationContext());
					actividadPrincipal();
				}else{
					Util.showToast(context, Constantes.MSG_LOGIN_ERROR);
				}
			}

		}
	};
}
