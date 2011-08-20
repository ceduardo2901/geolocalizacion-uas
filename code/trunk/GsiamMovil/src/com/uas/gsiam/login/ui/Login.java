package com.uas.gsiam.login.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.LoginServicio;
import com.uas.gsiam.utils.Util;

public class Login extends Activity {

	protected String email;
	protected String pass;

	protected EditText emailTxt;
	protected EditText passTxt;
	protected Button login;

	//private UsuarioDTO user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		emailTxt = (EditText) findViewById(R.id.emailTxt);

		passTxt = (EditText) findViewById(R.id.passTxt);
		login = (Button) findViewById(R.id.entrarBtn);


	}

	
	public void btnValidarEmail(View v) {
		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();
	
		if (!Util.validaMail(email)) {
			Toast.makeText(v.getContext(), "El email es invalido",
					Toast.LENGTH_LONG).show();
		} else {
			Bundle bundle = new Bundle();
			bundle.putString("email", email);
			bundle.putString("pass", pass);

			Intent intent = new Intent(this,LoginServicio.class);
			intent.putExtras(bundle);
			startService(intent);
		}
	}

	
	
	public void btnRegistrarse(View v) {
	
		Intent intent = new Intent();
		intent.setClass(this, CrearUsuario.class);
		startActivity(intent);
		
	}
	
	
	
	
	
}
