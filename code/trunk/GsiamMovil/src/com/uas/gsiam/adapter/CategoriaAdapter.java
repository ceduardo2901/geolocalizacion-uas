package com.uas.gsiam.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.ui.R;
import com.uas.gsiam.utils.Util;

public class CategoriaAdapter extends SimpleExpandableListAdapter {

	private LayoutInflater layoutInflater;
	private Context contexto;


	public CategoriaAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int groupLayout,
					String[] groupFrom, int[] groupTo,
					List<? extends List<? extends Map<String, ?>>> childData,
							int childLayout, String[] childFrom, int[] childTo) {
		super(context, groupData, groupLayout, groupFrom, groupTo, childData,
				childLayout, childFrom, childTo);

		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contexto = context;

	}

	
	@SuppressWarnings("unchecked")
	@Override
	public View getGroupView(int groupPosition,boolean isExpanded,View convertView,ViewGroup parent)
	{

		final View v = super.getGroupView(groupPosition, isExpanded, convertView, parent);
		
		 CategoriaDTO categoria = (CategoriaDTO) ((Map<String,Object>)this.getGroup(groupPosition)).get("CategoriaDTO");
		
		((TextView)v.findViewById(R.id.nombre)).setText(categoria.getDescripcionGrupo());
		
		int idImagen = Util.getDrawableIdFromString(contexto, categoria.getImagenGrupo());
		
		// imagen por defecto
		if (idImagen == 0){
			idImagen = Util.getDrawableIdFromString(contexto, "save");
		}
		
		((ImageView)v.findViewById(R.id.imagen)).setImageResource(idImagen);
		
	//	((TextView)v.findViewById(R.id.nombre)).setText( (String) ((Map<String,Object>)this.getGroup(groupPosition)).get(Constantes.CATEGORIAS_NOMBRE) );
//		((ImageView)v.findViewById(R.id.imagen)).setImageResource( (Integer) ((Map<String,Object>)this.getGroup(groupPosition)).get(Constantes.CATEGORIAS_IMAGEN) );

		return v;

	}


	@Override
	public View newGroupView(boolean isLastChild, ViewGroup parent) {
		return layoutInflater.inflate(R.layout.categoria_item_group, null, false);
	}


	@SuppressWarnings("unchecked")
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);

		 CategoriaDTO categoria = (CategoriaDTO) ((Map<String,Object>)this.getChild(groupPosition, childPosition)).get("CategoriaDTO");
			
			((TextView)v.findViewById(R.id.nombre)).setText(categoria.getDescripcion());
			
			int idImagen = Util.getDrawableIdFromString(contexto, categoria.getImagen());
			
			// imagen por defecto
			if (idImagen == 0){
				idImagen = Util.getDrawableIdFromString(contexto, "calendar");
			}
			
			((ImageView)v.findViewById(R.id.imagen)).setImageResource(idImagen);
			
		
		
	//	((TextView)v.findViewById(R.id.nombre)).setText( (String) ((Map<String,Object>)getChild(groupPosition, childPosition)).get(Constantes.CATEGORIAS_NOMBRE) );
	//	((ImageView)v.findViewById(R.id.imagen)).setImageResource( (Integer) ((Map<String,Object>)getChild(groupPosition, childPosition)).get(Constantes.CATEGORIAS_IMAGEN) );

		return v;
	}

	@Override
	public View newChildView(boolean isLastChild, ViewGroup parent) {
		return layoutInflater.inflate(R.layout.categoria_item, null, false);
	}



	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	
}
