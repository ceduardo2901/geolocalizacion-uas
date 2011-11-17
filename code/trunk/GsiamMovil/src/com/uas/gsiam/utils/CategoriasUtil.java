package com.uas.gsiam.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.uas.gsiam.negocio.dto.CategoriaDTO;

public class CategoriasUtil {

	
	
	public static void cargarCategorias(Context contexto, ArrayList<CategoriaDTO> categorias){
		
		ArrayList<HashMap<String, Object>> grpCategorias = new ArrayList<HashMap<String, Object>>();
		ArrayList<ArrayList<HashMap<String, Object>>> subCategorias = new ArrayList<ArrayList<HashMap<String, Object>>>();
		ArrayList<HashMap<String, Object>> groupData = null;
		
		int idGrupoAnterior = -1;
		 
		for(CategoriaDTO categoria : categorias){
			
			
			if (categoria.getIdGrupo() == idGrupoAnterior){
				// inserto categoria
				
				HashMap<String, Object> map = new HashMap<String,Object>();
			//	map.put(Constantes.CATEGORIAS_NOMBRE, categoria.getDescripcion());
			//	map.put(Constantes.CATEGORIAS_IMAGEN, getDrawableFromString(contexto, "icon"));//  contexto.getResources().getDrawable(R.drawable.icon));
				
				map.put("CategoriaDTO", categoria);
				
				groupData.add(map);
				
			}else{
				// inserto grupo
				
				HashMap<String, Object> grupo = new HashMap<String, Object>();
			
				//grupo.put(Constantes.CATEGORIAS_NOMBRE, categoria.getDescripcionGrupo());
				//grupo.put(Constantes.CATEGORIAS_IMAGEN, getDrawableFromString(contexto, "gd_action_bar_all_friends"));// contexto.getResources().getDrawable(R.drawable.gd_action_bar_all_friends));
				
				grupo.put("CategoriaDTO", categoria);
				
				grpCategorias.add( grupo );
				
				
				//inserto primer categoria
				
				groupData = new ArrayList<HashMap<String, Object>>();
				subCategorias.add(groupData);

				HashMap<String, Object> map = new HashMap<String,Object>();
			//	map.put(Constantes.CATEGORIAS_NOMBRE, categoria.getDescripcion());
			//	map.put(Constantes.CATEGORIAS_IMAGEN, getDrawableFromString(contexto, "calendar"));//  contexto.getResources().getDrawable(R.drawable.icon));
				
				map.put("CategoriaDTO", categoria);
				
				groupData.add(map);
				
			}
			
			idGrupoAnterior = categoria.getIdGrupo();
			
	    }
		
		
		
		ApplicationController app = ((ApplicationController)contexto.getApplicationContext());
		
		app.setGruposCategorias(grpCategorias);
		app.setSubCategorias(subCategorias);
			
	}
	
	/*
	public static ArrayList<HashMap<String, Object>> cargarGruposCategoriasManual(Context contexto, ArrayList<CategoriaDTO> categorias){
		
		ArrayList<HashMap<String, Object>> gruposCategorias = new ArrayList<HashMap<String, Object>>();

		
		HashMap<String, Object> grpComida = new HashMap<String, Object>();
		grpComida.put(Constantes.CATEGORIAS_NOMBRE, "Comida");
		grpComida.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.gd_action_bar_all_friends));
		gruposCategorias.add( grpComida );

		HashMap<String, Object> grpTiendas = new HashMap<String, Object>();
		grpTiendas.put(Constantes.CATEGORIAS_NOMBRE, "Tiendas");
		grpTiendas.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.gd_action_bar_compass));
		gruposCategorias.add( grpTiendas);

		HashMap<String, Object> grpEntretenimiento = new HashMap<String, Object>();
		grpEntretenimiento.put(Constantes.CATEGORIAS_NOMBRE, "Entretenimiento");
		grpEntretenimiento.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.gd_action_bar_compose));
		gruposCategorias.add( grpEntretenimiento);

		HashMap<String, Object> grpTurismo = new HashMap<String, Object>();
		grpTurismo.put(Constantes.CATEGORIAS_NOMBRE, "Turismo");
		grpTurismo.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.gd_action_bar_compose));
		gruposCategorias.add( grpTurismo);

		HashMap<String, Object> grpTransporte = new HashMap<String, Object>();
		grpTransporte.put(Constantes.CATEGORIAS_NOMBRE, "Transporte");
		grpTransporte.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.gd_action_bar_compose));
		gruposCategorias.add( grpTransporte);

		HashMap<String, Object> grpServicios = new HashMap<String, Object>();
		grpServicios.put(Constantes.CATEGORIAS_NOMBRE, "Servicios");
		grpServicios.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.gd_action_bar_compose));
		gruposCategorias.add( grpServicios);

		HashMap<String, Object> grpOtros = new HashMap<String, Object>();
		grpOtros.put(Constantes.CATEGORIAS_NOMBRE, "Otros");
		grpOtros.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.gd_action_bar_compose));
		gruposCategorias.add( grpOtros);
		
		return gruposCategorias;
	}


	public static ArrayList<ArrayList<HashMap<String, Object>>> cargarSubCategoriasManual (Context contexto, ArrayList<CategoriaDTO> categorias){

		ArrayList<ArrayList<HashMap<String, Object>>> subCategorias = new ArrayList<ArrayList<HashMap<String, Object>>>();

		ArrayList<HashMap<String, Object>> groupDataComida = new ArrayList<HashMap<String, Object>>();
		subCategorias.add(groupDataComida);

		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Fast Food");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Fast Food");
		groupDataComida.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Restaurant");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Restaurant");
		groupDataComida.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Cafe");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Cafe");
		groupDataComida.add(map);


		ArrayList<HashMap<String, Object>> groupDataTiendas = new ArrayList<HashMap<String, Object>>();
		subCategorias.add(groupDataTiendas);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Ropa");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Ropa");
		groupDataTiendas.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Electronica");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Electronica");
		groupDataTiendas.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Shopping");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Shopping");
		groupDataTiendas.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Farmacias");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Farmacias");
		groupDataTiendas.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Supermercados");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Supermercados");
		groupDataTiendas.add(map);


		ArrayList<HashMap<String, Object>> groupDataEntretenimiento = new ArrayList<HashMap<String, Object>>();
		subCategorias.add(groupDataEntretenimiento);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Cine");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Cine");
		groupDataEntretenimiento.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Pub");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Pub");
		groupDataEntretenimiento.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Disco");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Disco");
		groupDataEntretenimiento.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Club Deportivo");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Club Deportivo");
		groupDataEntretenimiento.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Estadio");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Estadio");
		groupDataEntretenimiento.add(map);


		ArrayList<HashMap<String, Object>> groupDataTurismo = new ArrayList<HashMap<String, Object>>();
		subCategorias.add(groupDataTurismo);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Museo");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Museo");
		groupDataTurismo.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Monumento");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Monumento");
		groupDataTurismo.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Hotel");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Hotel");
		groupDataTurismo.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Playa");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Playa");
		groupDataTurismo.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Montaña");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Montaña");
		groupDataTurismo.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Lago");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Lago");
		groupDataTurismo.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Parque");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Parque");
		groupDataTurismo.add(map);


		ArrayList<HashMap<String, Object>> groupDataTransporte = new ArrayList<HashMap<String, Object>>();
		subCategorias.add(groupDataTransporte);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Aeropuerto");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Aeropuerto");
		groupDataTransporte.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Terminal Buses");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Terminal Buses");
		groupDataTransporte.add(map);


		ArrayList<HashMap<String, Object>> groupDataServicios = new ArrayList<HashMap<String, Object>>();
		subCategorias.add(groupDataServicios);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Hospital");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Hospital");
		groupDataServicios.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Policia");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Policia");
		groupDataServicios.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Bomberos");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Bomberos");
		groupDataServicios.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Cambios");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Cambios");
		groupDataServicios.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Cajeros");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Cajeros");
		groupDataServicios.add(map);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Estacion de Servicio");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Estacion de Servicio");
		groupDataServicios.add(map);


		ArrayList<HashMap<String, Object>> groupDataOtros = new ArrayList<HashMap<String, Object>>();
		subCategorias.add(groupDataOtros);

		map = new HashMap<String,Object>();
		map.put(Constantes.CATEGORIAS_NOMBRE, "Otros");
		map.put(Constantes.CATEGORIAS_IMAGEN, contexto.getResources().getDrawable(R.drawable.icon));
		map.put("idMartin", "Otros");
		groupDataOtros.add(map);

		return subCategorias;
		
	}
	*/
}
