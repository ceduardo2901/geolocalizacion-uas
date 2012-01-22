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

/**
 * 
 * Adapter para manejar la lista de usuarios. Esta clase permite mostrar
 * la lista de usuarios con los atributos de nombre, email del usuario, estado de
 * la solicitud y la imagen del perfil del usuario
 * 
 * @author Martín
 * 
 */
public class UsuarioAdapter extends ArrayAdapter<UsuarioDTO> {

	Activity context;
	private List<UsuarioDTO> usuarios;

	/**
	 * 
	 * Contructor del adaptador
	 * 
	 * @param context
	 *            Contexto del Activity que maneja la lista
	 * @param resource
	 *            Identificador de la interfaz grafica de un usuario
	 * @param objects
	 *            Lista de usuarios a mostrar
	 */
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
			holder.solicitudFlag = (ImageView) item
					.findViewById(R.id.LblSolicitud);
			item.setTag(holder);

		} else {
			holder = (ViewHolder) item.getTag();

		}

		if (usuario.getAvatar() != null)
			holder.icono
					.setImageBitmap(Util.ArrayToBitmap(usuario.getAvatar()));

		holder.nombre.setText(usuario.getNombre());
		holder.email.setText(usuario.getEmail());

		if (usuario.isSolicitudEnviada()) {
			holder.solicitudFlag.setVisibility(View.VISIBLE);

			holder.solicitudFlag.setImageResource(R.drawable.ic_menu_forward);

		} else if (usuario.isSolicitudRecibida()) {
			holder.solicitudFlag.setVisibility(View.VISIBLE);

			holder.solicitudFlag.setImageResource(R.drawable.ic_menu_back);

		}

		else
			holder.solicitudFlag.setVisibility(View.INVISIBLE);

		return item;
	}

	static class ViewHolder {
		ImageView icono;
		TextView nombre;
		TextView email;
		ImageView solicitudFlag;
	}

}
