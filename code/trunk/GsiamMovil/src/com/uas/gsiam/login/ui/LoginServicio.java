package com.uas.gsiam.login.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.principal.ui.MainActivity;

public class LoginServicio extends Activity {

	private static final String url = "http://10.0.2.2:8080/GsiamWeb/rest/usuario/login?email={email}&pass={pass}";
	private RestTemplate restTemp;
	private static final String TAG = "HttpGetActivity";
	protected String email;
	protected String pass;
	
	private ProgressDialog progressDialog;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = this.getIntent().getExtras();
            this.email = bundle.getString("postalCode");
            this.pass = bundle.getString("countryCode");
            restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    }
	
	private void showLoadingProgressDialog() {
		progressDialog = ProgressDialog.show(this, "",
				"Loading. Please wait...", true);
	}

	private void dismissProgressDialog() {
		if (progressDialog != null) {

			progressDialog.dismiss();
		}
	}

	private void logException(Exception e) {
		Log.e(TAG, e.getMessage(), e);
		Writer result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class DownloadStatesTask extends AsyncTask<Void, Void, UsuarioDTO> {
		
		
		public DownloadStatesTask() {
			
		}

		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected UsuarioDTO doInBackground(Void... params) {
			

				Map<String, String> parms = new HashMap<String, String>();
				parms.put("email", email);
				parms.put("pass", pass);
				UsuarioDTO user = restTemp.getForObject(url, UsuarioDTO.class,parms);
				
				return user;
			
		}

		@Override
		protected void onPostExecute(UsuarioDTO usuario) {
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			Intent actividadPrincipal = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(actividadPrincipal);
			// return the list of states
			// refreshStates(usuario);
		}
	}

	
	
	
}
