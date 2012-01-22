package com.uas.gsiam.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * Clase que maneja los errores web que se pueden producir al llamar a los
 * servicios web REST
 * 
 * @author Antonio
 * 
 * @param <T>
 */
public class RestResponseErrorHandler<T> implements ResponseErrorHandler {

	private static final boolean jaxb2Present = ClassUtils.isPresent(
			"javax.xml.bind.Binder",
			RestResponseErrorHandler.class.getClassLoader());

	private static final boolean jacksonPresent = ClassUtils.isPresent(
			"org.codehaus.jackson.map.ObjectMapper",
			RestResponseErrorHandler.class.getClassLoader())
			&& ClassUtils.isPresent("org.codehaus.jackson.JsonGenerator",
					RestResponseErrorHandler.class.getClassLoader());

	private List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

	private final HttpMessageConverterExtractor<T> delegate;

	/**
	 * Metodo que agrega los message converter
	 * 
	 * @param responseType
	 *            Tipo de respuesta
	 */
	public RestResponseErrorHandler(Class<T> responseType) {
		// seteo los message Converters
		this.messageConverters.add(new ByteArrayHttpMessageConverter());
		this.messageConverters.add(new StringHttpMessageConverter());
		this.messageConverters.add(new ResourceHttpMessageConverter());
		this.messageConverters.add(new FormHttpMessageConverter());
		this.messageConverters.add(new SourceHttpMessageConverter());

		if (jacksonPresent) {
			this.messageConverters
					.add(new MappingJacksonHttpMessageConverter());
		}

		this.delegate = new HttpMessageConverterExtractor<T>(responseType,
				this.messageConverters);
		;
	}

	/**
	 * Si el servicio tira un codigo de mensaje 400 o 500 se manejan los errores
	 * en otro caso no
	 */

	public boolean hasError(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode = response.getStatusCode();
		if (statusCode.series() == Series.CLIENT_ERROR
				|| statusCode.series() == Series.SERVER_ERROR)
			return true;
		return false;
	}

	/**
	 * Metodo que maneja los errores que se lanzaron por la applicacion web
	 */
	public void handleError(ClientHttpResponse response) throws IOException {
		// Crea un nuevo objeto response y le seteo la respuesta, el codigo http
		// y mensaje
		ResponseEntity<T> responseEntity = new ResponseEntity<T>(
				this.delegate.extractData(response), response.getHeaders(),
				response.getStatusCode());
		String msg = (String) responseEntity.getBody();

		RestResponseException responseException = new RestResponseException(msg);
		if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
			responseException.setMensaje(response.getStatusCode() + " "
					+ response.getStatusText());
		}
		responseException.setResponseEntity(responseEntity);

		throw responseException;
	}

}
