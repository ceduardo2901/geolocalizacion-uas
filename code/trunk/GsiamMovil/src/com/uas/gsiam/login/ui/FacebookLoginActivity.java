package com.uas.gsiam.login.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.uas.gsiam.principal.ui.MainActivity;
import com.uas.gsiam.utils.SessionEvents;
import com.uas.gsiam.utils.SessionEvents.AuthListener;
import com.uas.gsiam.utils.SessionStore;

public class FacebookLoginActivity extends Activity {

	protected Facebook facebook;
	private static String[] PERMISSIONS;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.login);
		
		PERMISSIONS = getResources().getStringArray(R.array.permissions);
		facebook = new Facebook(getString(R.string.facebook_app_id));
		boolean session = SessionStore.restore(facebook, this);
		SessionEvents.addAuthListener(new LoginListener());
		
		if(session){
			facebook.authorize(this, PERMISSIONS, new LoginDialogListener());
		}

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
			SessionStore.save(facebook, getApplicationContext());
			Intent intentMainAct = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intentMainAct);
		}

		@Override
		public void onAuthFail(String error) {
			
			
		}
	}
}
