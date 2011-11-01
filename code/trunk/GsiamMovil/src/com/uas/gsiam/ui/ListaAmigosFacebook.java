package com.uas.gsiam.ui;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.R;
import com.uas.gsiam.utils.FacebookUtil;
import com.uas.gsiam.utils.FriendsGetProfilePics;
import com.uas.gsiam.utils.SessionStore;

public class ListaAmigosFacebook extends Activity {

	protected static final String TAG = "ListaAmigosFacebook";
	private Handler mHandler;

	protected ListView listaAmigos;
	protected static JSONArray jsonArray;
	private String APP_ID;
	private Facebook facebook;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHandler = new Handler();
		APP_ID = getString(R.string.facebook_app_id);
		facebook = new Facebook(APP_ID);
		setContentView(R.layout.lista_amigos_facebook);

		SessionStore.restore(facebook, getApplicationContext(), APP_ID);
		// SessionStore.clear(this, APP_ID);
		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					getResources().getStringArray(R.array.permissions),
					new FaceBookDialog());
		} else {
			obtenerAmigos();
		}

		listaAmigos = (ListView) findViewById(R.id.friends_list);
		// listaAmigos.setOnItemClickListener(this);
		listaAmigos.setAdapter(new FriendListAdapter(this));
		
	}

	private void obtenerAmigos() {
		Bundle params = new Bundle();
		params.putString("fields", "id, name, picture");

		try {
			String respuesta = facebook.request("me/friends", params);
			jsonArray = new JSONObject(respuesta).getJSONArray("data");
			
		} catch (MalformedURLException e) {
			Log.e(TAG, e.getCause().getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getCause().getMessage());
		} catch (JSONException e) {
			Log.e(TAG, e.getCause().getMessage());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) {

		case -1:
			facebook.authorizeCallback(requestCode, resultCode, data);
			break;

		}

	}

	public class FriendListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		ListaAmigosFacebook friendsList;

		public FriendListAdapter(ListaAmigosFacebook friendsList) {
			this.friendsList = friendsList;
			if (FacebookUtil.model == null) {
				FacebookUtil.model = new FriendsGetProfilePics();
			}
			FacebookUtil.model.setListener(this);
			mInflater = LayoutInflater.from(friendsList.getBaseContext());
		}

		@Override
		public int getCount() {
			return jsonArray.length();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			JSONObject jsonObject = null;
			try {
				jsonObject = jsonArray.getJSONObject(position);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			View hView = convertView;
			if (convertView == null) {
				hView = mInflater.inflate(R.layout.amigo_facebook, null);
				ViewHolder holder = new ViewHolder();
				holder.profile_pic = (ImageView) hView
						.findViewById(R.id.profile_pic);
				holder.name = (TextView) hView.findViewById(R.id.name);
				//holder.info = (TextView) hView.findViewById(R.id.info);
				holder.check = (CheckBox) hView.findViewById(R.id.checkInv);
				hView.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) hView.getTag();
			
			try {

				holder.profile_pic.setImageBitmap(FacebookUtil.model.getImage(
						jsonObject.getString("id"),
						jsonObject.getString("picture")));

			} catch (JSONException e) {
				holder.name.setText("");
			}
			try {
				String id = jsonObject.getString("id");
				String respuesta = facebook.request("/"+id);
				holder.name.setText(jsonObject.getString("name"));
			} catch (JSONException e) {
				holder.name.setText("");
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
//			try {
//				
//				holder.info.setText(jsonObject.getJSONObject("location")
//						.getString("name"));
//
//				JSONObject location = jsonObject
//						.getJSONObject("current_location");
//				holder.info.setText(location.getString("city") + ", "
//						+ location.getString("state"));
//
//			} catch (JSONException e) {
//				holder.info.setText("");
//			}
			return hView;
		}

	}

	class ViewHolder {
		ImageView profile_pic;
		TextView name;
		//TextView info;
		CheckBox check;
	}

	public class FaceBookDialog implements DialogListener {

		@Override
		public void onComplete(Bundle values) {
			SessionStore.save(facebook, getApplicationContext(), APP_ID);
			obtenerAmigos();

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
			// TODO Auto-generated method stub

		}

	}

}
