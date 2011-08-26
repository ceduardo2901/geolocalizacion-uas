package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button sitiosButton;
	
	protected TextView nombreTxt;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sitiosButton = (Button) findViewById(R.id.sitios_button);
		sitiosButton.setOnClickListener(sitioOnListener);
		nombreTxt =  (TextView)findViewById(R.id.textNombre);
		nombreTxt.setText("Bienvenido Josesito");
		
	}

	
	private void sitiosActivity(){
		Intent sitioIntent = new Intent(this,SitioActivity.class);
		startActivity(sitioIntent);
	}
		
	private OnClickListener sitioOnListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sitios_button:
				sitiosActivity();
				break;

			}

		}
	};

}
