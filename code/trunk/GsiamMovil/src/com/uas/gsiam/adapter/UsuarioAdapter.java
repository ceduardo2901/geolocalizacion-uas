package com.uas.gsiam.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.ui.R;
import com.uas.gsiam.utils.Util;

public class UsuarioAdapter extends ArrayAdapter<UsuarioDTO> {

	Activity context;
	private List<UsuarioDTO> usuarios;

	public UsuarioAdapter(Activity context, int resource,
			List<UsuarioDTO> objects) {
		super(context, resource, objects);
		this.context = context;
		usuarios = objects;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UsuarioDTO usuario = usuarios.get(position);
		View item = convertView;
		ViewHolder holder;

		if (item == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.usuario_item, null);

			holder = new ViewHolder();
			holder.icono = (ImageView) item.findViewById(R.id.LblIcono);
			holder.nombre = (TextView) item.findViewById(R.id.LblNombre);
			holder.email = (TextView) item.findViewById(R.id.LblMail);
			item.setTag(holder);

		} else {
			holder = (ViewHolder) item.getTag();

		}

		if (usuario.getAvatar() != null)
			holder.icono.setImageBitmap(Util.ArrayToBitmap(usuario.getAvatar()));
		
		holder.nombre.setText(usuario.getNombre());
		holder.email.setText(usuario.getEmail());
		return item;
	}
	
		
	static class ViewHolder {
		ImageView icono;
		TextView nombre;
		TextView email;
	}

}
