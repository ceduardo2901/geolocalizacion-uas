package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.R;
import com.facebook.android.Facebook.DialogListener;
import com.uas.gsiam.servicios.EnviarInvitacionServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SessionStore;
import com.uas.gsiam.utils.Util;

public class InvitarAmigosActivity extends Activity {

	protected static String TAG = "InvitarAmigosActivity";
	
	protected IntentFilter enviarInvitacionesFiltro;
	protected EditText emailTxt;
	protected String email;
	protected String APP_ID;
	protected static Facebook facebook;

	
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
		
		APP_ID = getString(R.string.facebook_app_id);
		facebook = new Facebook(APP_ID);
		
		SessionStore.restore(facebook, getApplicationContext(), APP_ID);
		
		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					getResources().getStringArray(R.array.permissions),
					new FaceBookDialog());
		} else {
			obtenerAmigos();
		}
		
	}
	
	
	public void obtenerAmigos(){
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
  
	  public class FaceBookDialog implements DialogListener {

			@Override
			public void onComplete(Bundle values) {
				SessionStore.save(facebook, getApplicationContext(), APP_ID);
				obtenerAmigos();

			}

			@Override
			public void onFacebookError(FacebookError e) {
				Log.e(TAG, "Error al autenticase en facebook");
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(DialogError e) {
				Log.e(TAG, "Error al autenticase en facebook");
				// TODO Auto-generated method stub
			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}

		}
	
}