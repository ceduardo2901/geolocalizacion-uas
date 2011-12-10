package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.uas.gsiam.servicios.EnviarInvitacionServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class InvitarAmigosActivity extends Activity {

	protected static String TAG = "InvitarAmigosActivity";
	
	protected IntentFilter enviarInvitacionesFiltro;
	protected EditText emailTxt;
	protected String email;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.invitar_amigos_tab);		
		this.emailTxt = (EditText) findViewById(R.id.emailTxt);
		enviarInvitacionesFiltro = new IntentFilter(Constantes.ENVIAR_INVITACIONES_FILTRO_ACTION);
		
	}
	
	
	protected void onResume() {
		super.onResume();
		registerReceiver(receiverEnviarInvitaciones, enviarInvitacionesFiltro);	
	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiverEnviarInvitaciones);
	}
	
	
	public void enviarInvitacion(View v) {

		email = emailTxt.getText().toString().trim();

		if (!Util.validaMail(email)) {
			
			Util.showToast(v.getContext(), Constantes.MSG_ERROR_MAIL);
			
		} else {
			
			
			Intent intent = new Intent(this,EnviarInvitacionServicio.class);
			intent.putExtra("direccion", email);
			startService(intent);
			
			Util.showProgressDialog(this, Constantes.MSG_ESPERA_ENVIANDO_INVITACION);
		}
	}
	
	
	public void btnInvitarAmigosFacebook(View v) {

		Intent intent = new Intent(this, ListaAmigosFacebook.class);
		startActivity(intent);
		
	}
	
	
	protected BroadcastReceiver receiverEnviarInvitaciones = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	
			String error = intent.getStringExtra("error");
	    	
			Util.dismissProgressDialog();
			
			if (error != null && !error.isEmpty()) {

				Util.showToast(context, error);

			} else {
				
				Util.showToast(context, Constantes.MSG_INVITACIONES_OK);
				
			}
	    }
	  };
  
	
}