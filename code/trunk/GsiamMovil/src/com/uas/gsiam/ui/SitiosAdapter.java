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
		View item = convertView;
		ViewHolder holder;
		
		if (item == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.sitios, null);
			
			holder = new ViewHolder();
			holder.Nombre = (TextView) item.findViewById(R.id.LblNombre);
			holder.Direccion = (TextView) item.findViewById(R.id.LblDireccion);
			
			item.setTag(holder);
			
		}else{
			holder = (ViewHolder) item.getTag();
				
		}
		
		
		holder.Nombre.setText(sitioMovil.getNombre());
		holder.Direccion.setText(sitioMovil.getDireccion());

		return item;
	}
	
	static class ViewHolder {
		TextView Nombre;
		TextView Direccion;
		}
}
