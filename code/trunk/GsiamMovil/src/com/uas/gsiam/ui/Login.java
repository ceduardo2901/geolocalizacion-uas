package com.uas.gsiam.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private String email;
	private String pass;
	//private Button login;
	private static final String regEx ="^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		EditText emailTxt = (EditText) findViewById(R.id.emailTxt);
		
		EditText passTxt = (EditText) findViewById(R.id.passTxt);
		Button login = (Button) findViewById(R.id.entrarBtn);
		
		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();
		
	}
	
	
    public void btnValidarEmail(View v){
    	
    	Matcher match = Pattern.compile(regEx).matcher(email);
    	if(!match.matches()){
    		Toast.makeText(v.getContext(), "El email es invalido", Toast.LENGTH_LONG).show();
    	}else{
    		Bundle bundle = new Bundle();
    		bundle.putString("email", email);
            bundle.putString("pass", pass);
    		Intent intent = new Intent(); 
    	//	intent.setClass(this, GeolocationPoc.class);
    		startActivity(intent);
    	}
    }


    


}
