package com.uas.gsiam.ui;

import com.uas.gsiam.servicios.SitiosServicio;
import com.uas.gsiam.ui.R;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button sitiosButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sitiosButton = (Button) findViewById(R.id.sitios_button);
		sitiosButton.setOnClickListener(sitioOnListener);
	}

	
	private void sitiosActivity(){
		Intent sitioIntent = new Intent(this,SitiosServicio.class);
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
