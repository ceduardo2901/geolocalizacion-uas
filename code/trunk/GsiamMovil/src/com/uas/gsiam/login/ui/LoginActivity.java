package com.uas.gsiam.login.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		intent = new Intent(getApplicationContext(),
				FacebookLoginActivity.class);
		ImageView facebookButton = (ImageView) findViewById(R.id.facebookbut);
		facebookButton.setOnClickListener(FacebookonListener);

	}

	private OnClickListener FacebookonListener = new OnClickListener() {
		public void onClick(View v) {
			try {

				ConnectivityManager cMgr = (ConnectivityManager) v.getContext()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
				String status = netInfo.getState().toString();

				if (status.equals("CONNECTED")) {
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(),
							"No connection available", Toast.LENGTH_SHORT)
							.show();
				}

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Connection refused",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	protected void onPause() {
		super.onPause();
	}

	protected void onResume() {
		super.onResume();
	}
}
