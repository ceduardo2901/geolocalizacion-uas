package com.uas.gsiam.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Este servicio recupera los sitios de interes a un radio determinado de la
 * ubicacion geografica del usuario. Los datos de entrada para el servicio son
 * la latitud y longitud del dispositivo movil
 * 
 * @author Antonio
 * 
 */

public class SitioServicio extends IntentService {

	protected static String TAG = "SitioServicio";
	private HttpEntity<SitioDTO> requestEntity;
	private HttpHeaders requestHeaders;
	protected RestTemplate restTemp;

	public SitioServicio() {
		super(TAG);

	}

	public void onCreate() {
		super.onCreate();
		requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory(
				HttpUtils.getNewHttpClient()));

	}

	@Override
	protected void onHandleIntent(Intent intent) {

		SitioDTO sitio = (SitioDTO) intent.getSerializableExtra("sitio");
		Boolean obtenerSitios = intent.getBooleanExtra("action", true);
		SitioDTO[] respuesta = null;

		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		requestEntity = new HttpEntity<SitioDTO>(sitio, requestHeaders);

		Intent intentSitio = new Intent(Constantes.SITIO_FILTRO_ACTION);
		try {
			if (obtenerSitios) {
				Map<String, Double> parms = new HashMap<String, Double>();
				parms.put("lat", sitio.getLat());
				parms.put("lon", sitio.getLon());

				respuesta = restTemp.getForObject(
						Constantes.SITIOS_SERVICE_URL, SitioDTO[].class, parms);
			} else {
				Map<String, String> parms = new HashMap<String, String>();
				
				if (sitio.getIdSitio() != null) {
					String id = String.valueOf(sitio.getIdSitio());
					parms.put("id", id);
				} else {
					parms.put("id", "0");
				}
				if (sitio.getNombre() != null) {
					parms.put("nombre", sitio.getNombre());
				} else {
					parms.put("nombre", " ");
				}
				parms.put("lat", sitio.getLat().toString());
				parms.put("lon", sitio.getLon().toString());
				respuesta = restTemp.getForObject(
						Constantes.BUSQUEDA_SITIOS_SERVICE_URL,
						SitioDTO[].class, parms);
				intentSitio.putExtra("buscarSitio", Constantes.MSG_NO_EXISTEN_SITIOS_POR_NOMBRE);
			}

			ArrayList<SitioDTO> lista = Util.getArrayListSitioDTO(respuesta);
			if (lista != null) {
				for (SitioDTO s : lista) {
					if (s.getPublicaciones() != null) {
						List<PublicacionDTO> publicaciones = s.getPublicaciones();
						for (PublicacionDTO p : publicaciones) {
							if (p.getFoto() != null) {
								
								String nombreArchivo =  "publicacion_" + p.getIdPublicacion() + ".jpg"; 
								Util.guardarImagenMemoria(this, p.getFoto(), nombreArchivo);
								p.setFoto(null);
								p.setFotoRuta(nombreArchivo);
								Log.i(TAG, p.getFotoRuta());
							}
						}
					}

				}
			}
			intentSitio.putExtra("sitios", lista);

		} catch (RestResponseException e) {

			Log.i(TAG, "Error: " + e.getMensaje());

			Log.e(TAG, "Error: " + e.getMensaje());
			intentSitio.putExtra("error", e.getMensaje());
		} catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentSitio.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);

		}

		sendBroadcast(intentSitio);

	}
	

}
