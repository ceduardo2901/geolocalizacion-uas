package com.uas.gsiam.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.android.R;
import com.uas.gsiam.utils.AmigoFacebook;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.FacebookUtil;
import com.uas.gsiam.utils.FriendsGetProfilePics;
import com.uas.gsiam.utils.Util;

public class ListaAmigosFacebook extends Activity {

	protected static final String TAG = "ListaAmigosFacebook";

	protected ListView listaAmigos;
	protected static JSONArray jsonArray;
	private ArrayAdapter<AmigoFacebook> listAdapter;
	private List<AmigoFacebook> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		list = new ArrayList<AmigoFacebook>();
		setContentView(R.layout.lista_amigos_facebook);

		obtenerAmigos();
		
		listaAmigos = (ListView) findViewById(R.id.friends_list);
		listaAmigos
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View item,
							int position, long id) {
						AmigoFacebook aFace = listAdapter.getItem(position);
						aFace.seleccionar();
						ViewHolder viewHolder = (ViewHolder) item.getTag();
						viewHolder.check.setChecked(aFace.isSeleccionado());
					}
				});
		listAdapter = new FriendListAdapter(list, this);
		listaAmigos.setAdapter(listAdapter);

	}

	private void obtenerAmigos() {
		Bundle params = new Bundle();
		params.putString("fields", "id, name, picture");

		try {
			String respuesta = InvitarAmigosActivity.facebook.request("me/friends", params);
			jsonArray = new JSONObject(respuesta).getJSONArray("data");
			AmigoFacebook amigo;
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.getJSONObject(i);
					amigo = new AmigoFacebook();
					amigo.setId(jo.getString("id"));
					amigo.setNombre(jo.getString("name"));
					amigo.setFotoUrl(jo.getString("picture"));
					amigo.setSeleccionado(false);
					list.add(amigo);
				}
			}

		} catch (MalformedURLException e) {
			Log.e(TAG, e.getCause().getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getCause().getMessage());
		} catch (JSONException e) {
			Log.e(TAG, e.getCause().getMessage());
		}
	}

	public void btnInvitarAmigos(View v) {
		
		boolean error = Boolean.FALSE;
		
		if (!list.isEmpty()) {

			if (haySeleccionados()){
				
				Util.showProgressDialog(this, Constantes.MSG_ESPERA_ENVIANDO_INVITACION);
				
				Bundle params;
				try {
					for (AmigoFacebook aFace : list) {
						if (aFace.isSeleccionado()) {

							params = new Bundle();
							params.putString("to", aFace.getId());
							params.putString("caption",
									getString(R.string.app_name));
							params.putString("description", "descripccion");

							ApplicationController app = ((ApplicationController) getApplicationContext());

							params.putString("message", app.getUserLogin().getNombre() + Constantes.MSG_INVITACIONES_FACEBOOK);
							//params.putString("method", "apprequests");
							InvitarAmigosActivity.facebook.request("feed", params, "POST");
							//InvitarAmigosActivity.facebook.request(params);

						}
					}
				} catch (FileNotFoundException e) {
					error = Boolean.TRUE;
					Log.e(TAG, e.getCause().getMessage());
				} catch (MalformedURLException e) {
					error = Boolean.TRUE;
					Log.e(TAG, e.getCause().getMessage());
				} catch (IOException e) {
					error = Boolean.TRUE;
					Log.e(TAG, e.getCause().getMessage());
				} finally {
					Util.dismissProgressDialog();
				}

				if(!error){
					Util.showToast(this, Constantes.MSG_INVITACIONES_FACEBOOK_OK);
				}else{
					Util.showToast(this, Constantes.MSG_INVITACIONES_FACEBOOK_ERROR);
				}
			}
		}
		else{
			Util.showToast(this, Constantes.MSG_INVITACIONES_FACEBOOK_SELECCION);
		}

	}

	
	public boolean haySeleccionados (){

		boolean haySeleccionado = Boolean.FALSE;
		
		for (AmigoFacebook aFace : list) {
			if (aFace.isSeleccionado()) {

			haySeleccionado = Boolean.TRUE;

			}
		}
		
		return haySeleccionado;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) {

		case -1:
			InvitarAmigosActivity.facebook.authorizeCallback(requestCode, resultCode, data);
			break;

		}

	}

	public class FriendListAdapter extends ArrayAdapter<AmigoFacebook> {
		private LayoutInflater mInflater;
		List<AmigoFacebook> friendsList;

		public FriendListAdapter(List<AmigoFacebook> friendsList,
				Context context) {
			super(context, R.layout.amigo_facebook, friendsList);
			this.friendsList = friendsList;
			if (FacebookUtil.model == null) {
				FacebookUtil.model = new FriendsGetProfilePics();
			}
			FacebookUtil.model.setListener(this);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if(jsonArray != null)
				return jsonArray.length();
			else{
				return 0;
			}
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			AmigoFacebook amigoFace = null;

			amigoFace = friendsList.get(position);

			View hView = convertView;
			ViewHolder holder;
			if (convertView == null) {
				hView = mInflater.inflate(R.layout.amigo_facebook, null);
				holder = new ViewHolder();
				holder.profile_pic = (ImageView) hView
						.findViewById(R.id.profile_pic);
				holder.name = (TextView) hView.findViewById(R.id.name);
				// holder.info = (TextView) hView.findViewById(R.id.info);
				holder.check = (CheckBox) hView.findViewById(R.id.checkInv);
				hView.setTag(holder);

				holder.check.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						AmigoFacebook planet = (AmigoFacebook) cb.getTag();
						planet.setSeleccionado(cb.isChecked());
					}
				});
			} else {
				holder = (ViewHolder) hView.getTag();

			}

			holder.check.setTag(amigoFace);

			holder.profile_pic.setImageBitmap(FacebookUtil.model.getImage(
					amigoFace.getId(), amigoFace.getFotoUrl()));

			holder.name.setText(amigoFace.getNombre());

		
			return hView;
		}

	}

	class ViewHolder {
		ImageView profile_pic;
		TextView name;
		// TextView info;
		CheckBox check;
	}

	

}
