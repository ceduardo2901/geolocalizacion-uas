package com.uas.gsiam.utils;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.ui.R;

public class ComentarioAdapter extends ArrayAdapter<PublicacionDTO> {

	Activity context;
	private List<PublicacionDTO> publicaciones;

	public ComentarioAdapter(Activity context, int resource,
			List<PublicacionDTO> objects) {
		super(context, resource, objects);
		this.context = context;
		publicaciones = objects;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		PublicacionDTO comentario = publicaciones.get(position);
		View item = convertView;
		ViewHolder holder;

		if (item == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.comentario, null);

			holder = new ViewHolder();
			holder.fecha = (TextView) item.findViewById(R.id.fechaId);
			holder.comentario = (TextView) item.findViewById(R.id.comentarioTxt);
			holder.usuario = (TextView) item.findViewById(R.id.userId);
			holder.rating = (RatingBar) item.findViewById(R.id.puntajeComentarioId);
			item.setTag(holder);

		} else {
			holder = (ViewHolder) item.getTag();

		}
		holder.usuario.setText(comentario.getNombreUsuario());
		holder.fecha.setText(dateFormate(comentario.getFecha()));
		holder.comentario.setText(comentario.getComentario());
		holder.rating.setRating(comentario.getPuntaje());
		return item;
	}
	
	private String dateFormate(Date fecha){
		DateFormat format = new DateFormat();
		return format.format("dd/MM/yyyy", fecha).toString();
	}
	
	static class ViewHolder {
		TextView usuario;
		TextView fecha;
		TextView comentario;
		RatingBar rating;
	}

}
