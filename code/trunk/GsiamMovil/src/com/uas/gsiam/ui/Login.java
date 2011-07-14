package com.uas.gsiam.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {

	private EditText email;
	private EditText pass;
	private Button login;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		email = (EditText) findViewById(R.id.emailTxt);
		pass = (EditText) findViewById(R.id.passTxt);
		login = (Button) findViewById(R.id.entrarBtn);	
		
		setContentView(R.layout.login);
	}

	@Override
	public void onClick(View arg0) {
		
		System.out.println("Prueba de evento de boton");

	}

}
