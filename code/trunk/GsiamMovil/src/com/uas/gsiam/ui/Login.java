package com.uas.gsiam.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.uas.gsiam.negocio.Usuario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	protected String email;
	protected String pass;
	
	protected EditText emailTxt;
	protected EditText passTxt;
	protected Button login;
	
	private static final String regEx ="^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
	

	private ILoginServicio iLoginServicio;
	private Usuario user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		emailTxt = (EditText) findViewById(R.id.emailTxt);
		
		passTxt = (EditText) findViewById(R.id.passTxt);
		login = (Button) findViewById(R.id.entrarBtn);
					
		iLoginServicio = new LoginServicio();
		
	}
	
	
    public void btnValidarEmail(View v){
    	email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();
    	Matcher match = Pattern.compile(regEx).matcher(email);
    	if(!match.matches()){
    		Toast.makeText(v.getContext(), "El email es invalido", Toast.LENGTH_LONG).show();
    	}else{
    		user = iLoginServicio.login(email, pass);
//    		Bundle bundle = new Bundle();
//    		bundle.putString("email", email);
//            bundle.putString("pass", pass);
//    		Intent intent = new Intent(); 
//    	//	intent.setClass(this, GeolocationPoc.class);
//    		startActivity(intent);
    	}
    }


    


}
