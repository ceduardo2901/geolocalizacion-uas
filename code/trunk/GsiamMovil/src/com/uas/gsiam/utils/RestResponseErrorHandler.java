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

public class RestResponseErrorHandler<T> implements ResponseErrorHandler {

	private static final boolean jaxb2Present =
			ClassUtils.isPresent("javax.xml.bind.Binder", RestResponseErrorHandler.class.getClassLoader());

	private static final boolean jacksonPresent =
			ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", RestResponseErrorHandler.class.getClassLoader()) &&
					ClassUtils.isPresent("org.codehaus.jackson.JsonGenerator", RestResponseErrorHandler.class.getClassLoader());

	private List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

	private final HttpMessageConverterExtractor<T> delegate;
	
	public RestResponseErrorHandler(Class<T> responseType) {
		//Set up the message Converters
		this.messageConverters.add(new ByteArrayHttpMessageConverter());
		this.messageConverters.add(new StringHttpMessageConverter());
		this.messageConverters.add(new ResourceHttpMessageConverter());
		this.messageConverters.add(new FormHttpMessageConverter());
		this.messageConverters.add(new SourceHttpMessageConverter());
//		if (jaxb2Present) {
//			this.messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
//		}
		if (jacksonPresent) {
			this.messageConverters.add(new MappingJacksonHttpMessageConverter());
		}
		
		this.delegate = new HttpMessageConverterExtractor<T>(responseType, this.messageConverters);;
	}

	// If a 400 or 500 series error is returned then we want to handle the error, otherwise not
	public boolean hasError(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode = response.getStatusCode();
		if (statusCode.series() == Series.CLIENT_ERROR || statusCode.series() == Series.SERVER_ERROR)
			return true;
		return false;
	}

	public void handleError(ClientHttpResponse response) throws IOException {
		// Create a new generic Response Entity adding the unmarshalled response, headers and status code
		ResponseEntity<T> responseEntity = new ResponseEntity<T>(this.delegate.extractData(response), response.getHeaders(), response.getStatusCode());
		String msg = (String) responseEntity.getBody();
		//Create a new RestResponseException and set the ResponseEntity
		RestResponseException responseException = new RestResponseException (msg);
		if(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)){
			responseException.setMensaje(response.getStatusCode() + " " + response.getStatusText());
		}
		responseException.setResponseEntity(responseEntity);
		
		// Throw the Exception
		throw responseException;
	}

}
