package com.uas.gsiam.login.ui;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.principal.ui.MainActivity;
import com.uas.gsiam.utils.SessionEvents;
import com.uas.gsiam.utils.SessionEvents.AuthListener;
import com.uas.gsiam.utils.SessionStore;

public class FacebookLoginActivity extends Activity {

	protected Facebook facebook;
	private static String[] PERMISSIONS;
	private static final String url = "http://10.0.2.2:8080/GsiamWeb/usuario/login?token=123";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.login);
		
		PERMISSIONS = getResources().getStringArray(R.array.permissions);
		facebook = new Facebook(getString(R.string.facebook_app_id));
		boolean session = SessionStore.restore(facebook, this);
		SessionEvents.addAuthListener(new LoginListener());
		SessionStore.clear(this);
		//if(session){
			facebook.authorize(this, PERMISSIONS, new LoginDialogListener());
		//}

	}
	
	public void succeed(){
		Bundle bundle = new Bundle();
		bundle.putString("token", facebook.getAccessToken());
		
		Intent intent = new Intent();
		intent.setClass(this, LoginServicio.class);
		intent.putExtras(bundle);
		startActivity(intent);
		
//		RestTemplate restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
//		Map<String, String> parms = new HashMap<String, String>();
//		//parms.put("token", token);
//		
//		UsuarioDTO user = restTemp.getForObject(url, UsuarioDTO.class);
//		
//		user.getId();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
		
	}
	
	private class LoginDialogListener implements DialogListener{

		@Override
		public void onComplete(Bundle values) {
			SessionEvents.onLoginSuccess();
			
		}

		@Override
		public void onFacebookError(FacebookError e) {
			SessionEvents.onLoginError(e.getMessage());
        }
        
        public void onError(DialogError error) {
            SessionEvents.onLoginError(error.getMessage());
        }

        public void onCancel() {
            SessionEvents.onLoginError("Action Canceled");
        }
		
	}
	
	private class LoginListener implements AuthListener{

		@Override
		public void onAuthSucceed() {
			//SessionStore.save(facebook, getApplicationContext());
			succeed();
						
		}

		@Override
		public void onAuthFail(String error) {
			
			
		}
	}
}
