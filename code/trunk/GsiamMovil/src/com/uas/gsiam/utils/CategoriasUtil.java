package com.uas.gsiam.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.uas.gsiam.negocio.dto.CategoriaDTO;

/**
 * Clase encargada de cargar las categorias de los sitios de interes
 * 
 * @author Martín
 * 
 */
public class CategoriasUtil {

	/**
	 * Carga todas las categorias de los sitios de interes cuando el usuario se
	 * loguea al sistema
	 * 
	 * @param contexto
	 *            Contexto de la aplicacion
	 * @param categorias
	 *            Categorias del sistema guardadas en la base de datos
	 */
	public static void cargarCategorias(Context contexto,
			ArrayList<CategoriaDTO> categorias) {

		ArrayList<HashMap<String, Object>> grpCategorias = new ArrayList<HashMap<String, Object>>();
		ArrayList<ArrayList<HashMap<String, Object>>> subCategorias = new ArrayList<ArrayList<HashMap<String, Object>>>();
		ArrayList<HashMap<String, Object>> groupData = null;

		int idGrupoAnterior = -1;

		for (CategoriaDTO categoria : categorias) {

			if (categoria.getIdGrupo() == idGrupoAnterior) {
				// inserto categoria

				HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("CategoriaDTO", categoria);

				groupData.add(map);

			} else {
				// inserto grupo

				HashMap<String, Object> grupo = new HashMap<String, Object>();

				grupo.put("CategoriaDTO", categoria);

				grpCategorias.add(grupo);

				// inserto primer categoria

				groupData = new ArrayList<HashMap<String, Object>>();
				subCategorias.add(groupData);

				HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("CategoriaDTO", categoria);

				groupData.add(map);

			}

			idGrupoAnterior = categoria.getIdGrupo();

		}

		ApplicationController app = ((ApplicationController) contexto
				.getApplicationContext());

		app.setGruposCategorias(grpCategorias);
		app.setSubCategorias(subCategorias);

	}

}
