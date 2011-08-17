package com.uas.gsiam.login.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uas.gsiam.negocio.dto.UsuarioDTO;

public class Login extends Activity {

	protected String email;
	protected String pass;

	protected EditText emailTxt;
	protected EditText passTxt;
	protected Button login;

	private static String regEx;

	private UsuarioDTO user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		regEx = this.getString(R.string.regEX);
		emailTxt = (EditText) findViewById(R.id.emailTxt);

		passTxt = (EditText) findViewById(R.id.passTxt);
		login = (Button) findViewById(R.id.entrarBtn);
		
		
		

	}

	public void btnValidarEmail(View v) {
		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();
		Matcher match = Pattern.compile(regEx).matcher(email);
		if (!match.matches()) {
			Toast.makeText(v.getContext(), "El email es invalido",
					Toast.LENGTH_LONG).show();
		} else {
			Bundle bundle = new Bundle();
			bundle.putString("email", email);
			bundle.putString("pass", pass);

			Intent intent = new Intent();
			intent.setClass(this, LoginServicio.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	
	
	public void btnRegistrarse(View v) {
	
		Intent intent = new Intent();
		intent.setClass(this, CrearUsuario.class);
		startActivity(intent);
		
	}
	
	
	
	
	
}