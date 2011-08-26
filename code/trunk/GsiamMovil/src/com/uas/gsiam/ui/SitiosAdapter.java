package com.uas.gsiam.ui;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uas.gsiam.utils.SitioMovilDTO;

public class SitiosAdapter extends ArrayAdapter<SitioMovilDTO> {

	Activity context;
	private List<SitioMovilDTO> sitiosDTO;

	public SitiosAdapter(Activity context, int resource,
			List<SitioMovilDTO> objects) {
		super(context, resource, objects);
		this.context = context;
		sitiosDTO = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		SitioMovilDTO sitioMovil = sitiosDTO.get(position);
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(R.layout.sitios, null);

		TextView lblTitulo = (TextView) item.findViewById(R.id.LblNombre);
		lblTitulo.setText(sitioMovil.getNombre());

		TextView lblSubtitulo = (TextView) item.findViewById(R.id.LblDireccion);
		lblSubtitulo.setText(sitioMovil.getDireccion());

		return item;
	}
}
