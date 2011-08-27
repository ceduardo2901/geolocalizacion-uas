package com.uas.gsiam.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.LoginServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SessionStore;
import com.uas.gsiam.utils.Util;

public class LoginActivity extends Activity {

	protected static String TAG = "LoginActivity";
	protected String email;
	protected String pass;

	protected EditText emailTxt;
	protected EditText passTxt;
	protected Button login;
	protected IntentFilter loginFiltro;

	protected UsuarioDTO user;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		user = new UsuarioDTO();
		emailTxt = (EditText) findViewById(R.id.emailTxt);
		
		email = SessionStore.restore(this);
		if (email != null) {
			actividadPrincipal();
		}
		passTxt = (EditText) findViewById(R.id.passTxt);
		login = (Button) findViewById(R.id.entrarBtn);
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
			Toast.makeText(v.getContext(), Constantes.MSG_ERROR_MAIL,
					Toast.LENGTH_LONG).show();
		} else {
			Bundle bundle = new Bundle();
			bundle.putString("email", email);
			bundle.putString("pass", pass);

			Intent intent = new Intent(this, LoginServicio.class);
			intent.putExtras(bundle);
			startService(intent);

			Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
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
			String error = intent.getStringExtra("error");
			Bundle bundle = intent.getBundleExtra("usuario");
			user = (UsuarioDTO) bundle.getSerializable("usuario");
			if (error != null) {
				Util.showToast(context, error);
			} else {
				if (user != null) {
					SessionStore.save(email, getApplicationContext());
					actividadPrincipal();
				}
			}

		}
	};
}
