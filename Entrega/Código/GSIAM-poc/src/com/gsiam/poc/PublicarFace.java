package com.gsiam.poc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class PublicarFace extends Activity {
	protected static final String TAG = "PublicarActivity";

	private AsyncFacebookRunner mAsyncRunner;
	private Facebook facebook;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		facebook = new Facebook("264847263529970");

		this.mAsyncRunner = new AsyncFacebookRunner(facebook);

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					getResources().getStringArray(R.array.permissions),
					new FaceBookDialog());
		} else {
			postOnWall("Prueba");
		}
	}

	public void postOnWall(String msg) {

		try {
			String response;

			Bundle parameters = new Bundle();

			parameters.putString("message", msg);
			parameters.putString("description", "test test test");

			response = facebook.request("me/feed", parameters, "POST");

			if (response == null) {
				Log.i(TAG, "error al comentar en facebook");
			}

		} catch (Exception e) {
			Log.v("Error", "Blank response");
			e.printStackTrace();
		}
	}
	
	public class FaceBookDialog implements DialogListener {

		@Override
		public void onComplete(Bundle values) {
			
			postOnWall("Prueba");

		}

		@Override
		public void onFacebookError(FacebookError e) {
			Log.e(TAG, "Error al autenticase en facebook");

		}

		@Override
		public void onError(DialogError e) {
			Log.e(TAG, "Error al autenticase en facebook");

		}

		@Override
		public void onCancel() {

		}

	}
}
