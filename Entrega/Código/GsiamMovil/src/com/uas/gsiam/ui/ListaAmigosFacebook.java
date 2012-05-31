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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
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

/**
 * Esta activity muestra la lista de amigos de facebook del usuario. Permite
 * seleccionar los amigos a los cuales se les quiere enviar una invitacion para
 * unirse a la aplicacion
 * 
 * @author Antonio
 * 
 */
public class ListaAmigosFacebook extends Activity {

	protected static final String TAG = "ListaAmigosFacebook";

	protected ListView listaAmigos;
	protected static JSONArray jsonArray;
	private ArrayAdapter<AmigoFacebook> listAdapter;
	private List<AmigoFacebook> list;
	private EditText filtrar;
	private Button btnInvitar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		list = new ArrayList<AmigoFacebook>();
		setContentView(R.layout.lista_amigos_facebook);
		filtrar = (EditText) findViewById(R.id.txtFiltrarId);
		filtrar.addTextChangedListener(filterTextWatcher);
		obtenerAmigos();

		btnInvitar = (Button) findViewById(R.id.btnInvAmigosFacebookId);
		btnInvitar.setEnabled(false);
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
		listaAmigos.setTextFilterEnabled(true);
		listAdapter = new FriendListAdapter(list, this);
		listaAmigos.setAdapter(listAdapter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		filtrar.removeTextChangedListener(filterTextWatcher);
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			listAdapter.getFilter().filter(s);
		}

	};

	/**
	 * Carga los amigos de facebook del usuario autenticado
	 */
	private void obtenerAmigos() {
		Bundle params = new Bundle();
		params.putString("fields", "id, name, picture");

		try {
			String respuesta = InvitarAmigosActivity.facebook.request(
					"me/friends", params);
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

	/**
	 * Envia la invitacion a los amigos de facebook del usuario
	 * 
	 * @param v
	 */
	public void invitarAmigos(View v) {

		boolean error = Boolean.FALSE;

		if (!list.isEmpty()) {

			if (haySeleccionados()) {

				Util.showProgressDialog(this,
						Constantes.MSG_ESPERA_ENVIANDO_INVITACION);

				Bundle params;
				try {
					for (AmigoFacebook aFace : list) {
						if (aFace.isSeleccionado()) {
							params = new Bundle();
							String response = InvitarAmigosActivity.facebook
									.request("me");
							params.putString("caption",
									getString(R.string.app_name));
							params.putString("description", "descripccion");

							ApplicationController app = ((ApplicationController) getApplicationContext());

							params.putString("message",
									Constantes.MSG_INVITACIONES_FACEBOOK);
							InvitarAmigosActivity.facebook.request(
									aFace.getId() + "/feed", params, "POST");

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

				if (!error) {
					Util.showToast(this,
							Constantes.MSG_INVITACIONES_FACEBOOK_OK);
					volver();
				} else {
					Util.showToast(this,
							Constantes.MSG_INVITACIONES_FACEBOOK_ERROR);
				}
			}
		} else {
			Util.showToast(this, Constantes.MSG_INVITACIONES_FACEBOOK_SELECCION);
		}

	}

	private void volver() {
		Intent intent = new Intent(this, AmigosTabActivity.class);
		startActivity(intent);
	}

	/**
	 * Determina se selecciono algun amigo de la lista
	 * 
	 * @return Devuelve true si se selecciono algun amigo de la lista, false en
	 *         caso contrario
	 */
	public boolean haySeleccionados() {

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
			InvitarAmigosActivity.facebook.authorizeCallback(requestCode,
					resultCode, data);
			break;

		}

	}

	/**
	 * Adaptador para la lista de amigos de facebook
	 * 
	 * @author Antonio
	 * 
	 */
	public class FriendListAdapter extends ArrayAdapter<AmigoFacebook>
			implements Filterable {
		private LayoutInflater mInflater;
		private List<AmigoFacebook> friendsList;
		private List<AmigoFacebook> filtered;
		private CustomFilter filter;

		public FriendListAdapter(List<AmigoFacebook> list, Context context) {
			super(context, R.layout.amigo_facebook, list);
			this.friendsList = new ArrayList<AmigoFacebook>(list);
			this.filtered = new ArrayList<AmigoFacebook>(list);

			if (FacebookUtil.model == null) {
				FacebookUtil.model = new FriendsGetProfilePics();
			}
			FacebookUtil.model.setListener(this);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public Filter getFilter() {
			if (filter == null)
				filter = new CustomFilter();
			return filter;
		}

		private class CustomFilter extends Filter {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				constraint = constraint.toString().toLowerCase();
				FilterResults result = new FilterResults();
				if (constraint != null && constraint.toString().length() > 0) {
					ArrayList<AmigoFacebook> filt = new ArrayList<AmigoFacebook>();
					ArrayList<AmigoFacebook> lItems = new ArrayList<AmigoFacebook>();
					synchronized (this) {
						lItems.addAll(friendsList);
					}
					for (int i = 0, l = lItems.size(); i < l; i++) {
						AmigoFacebook m = lItems.get(i);
						if (m.getNombre().toLowerCase().contains(constraint))
							filt.add(m);
					}
					result.count = filt.size();
					result.values = filt;
				} else {
					synchronized (this) {
						result.values = friendsList;
						result.count = friendsList.size();
					}
				}
				return result;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				filtered = (ArrayList<AmigoFacebook>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}

			}

		}

		@Override
		public int getCount() {
			if (filtered != null)
				return filtered.size();
			else {
				return 0;
			}
		}

		@Override
		public AmigoFacebook getItem(int position) {
			return filtered.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			AmigoFacebook amigoFace = null;

			amigoFace = filtered.get(position);

			View hView = convertView;
			ViewHolder holder;
			if (convertView == null) {
				hView = mInflater.inflate(R.layout.amigo_facebook, null);
				holder = new ViewHolder();
				holder.profile_pic = (ImageView) hView
						.findViewById(R.id.profile_pic);
				holder.name = (TextView) hView.findViewById(R.id.name);
				holder.check = (CheckBox) hView.findViewById(R.id.checkInv);
				hView.setTag(holder);

				holder.check.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						AmigoFacebook amigo = (AmigoFacebook) cb.getTag();
						amigo.setSeleccionado(cb.isChecked());
						if (haySeleccionados()) {
							btnInvitar.setEnabled(true);
						} else {
							btnInvitar.setEnabled(false);
						}
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
		CheckBox check;
	}

}
