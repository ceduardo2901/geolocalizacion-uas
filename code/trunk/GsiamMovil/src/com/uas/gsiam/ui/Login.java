package com.uas.gsiam.ui;


import org.springframework.util.StringUtils;

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
import android.widget.Toast;

import com.uas.gsiam.servicios.LoginServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class Login extends Activity {

	protected static String TAG="Login";
	protected String email;
	protected String pass;

	protected EditText emailTxt;
	protected EditText passTxt;
	protected Button login;
	protected IntentFilter loginFiltro;
	private ProgressDialog progressDialog;

	//private UsuarioDTO user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		emailTxt = (EditText) findViewById(R.id.emailTxt);

		passTxt = (EditText) findViewById(R.id.passTxt);
		login = (Button) findViewById(R.id.entrarBtn);
		loginFiltro = new IntentFilter(Constantes.LOGIN_FILTRO_ACTION);

	}
	
	 protected void onResume() {
		 super.onResume();
		 
		registerReceiver(prueba, loginFiltro);
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

			Intent intent = new Intent(this,LoginServicio.class);
			intent.putExtras(bundle);
			startService(intent);
			
			showLoadingProgressDialog();
		}
	}

	
	
	public void btnRegistrarse(View v) {
	
		Intent intent = new Intent();
		intent.setClass(this, CrearUsuarioActivity.class);
		startActivity(intent);
		
	}
	
	
	protected BroadcastReceiver prueba = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	Log.i(TAG, "mensaje de prueba estoy aca !!!!");
	    	dismissProgressDialog();
	    	String error = intent.getStringExtra("error");
	    	if(error != null){
	    		Util.showToast(context, error);
	    	}else{
	    		Intent actividadPrincipal = new Intent(getApplicationContext(), MainActivity.class);
				
				startActivity(actividadPrincipal);
	    	}
	    	
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
