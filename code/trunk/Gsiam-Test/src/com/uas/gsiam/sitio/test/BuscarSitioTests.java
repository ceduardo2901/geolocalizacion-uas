package com.uas.gsiam.sitio.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import net.sf.json.JSONArray;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.uas.gsiam.test.dto.SitioDTO;
import com.uas.gsiam.test.dto.SolicitudContactoDTO;

public class BuscarSitioTests {

	RestAssured restAssured;
	SitioDTO sitio;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();
		sitio = new SitioDTO();
	}

	@Test
	public void errorUrlBuscarSitio() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/sitio/sitio/{id}/{nombre}", 69, "Pasiva");

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void noExistenSitioPorNombre() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/sitios/sitio/{id}/{nombre}", " ", "Kaasd");
		ResponseBody b = response.getBody();
		String str = b.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertEquals(0, jsonArray.size());
	}
	
	@Ignore
	@Test
	public void noExistenSitioPorId() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/sitios/sitio/{id}/{nombre}", 69,"");
		ResponseBody b = response.getBody();
		String str = b.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertEquals(0, jsonArray.size());
	}
	
	@Test
	public void buscarSitiosPorNombre() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/sitios/sitio/{id}/{nombre}", " ", "Venesia");
		ResponseBody b = response.getBody();
		String str = b.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertTrue(jsonArray.size() > 0 || jsonArray.size() == 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parametrosVacios() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/sitios/sitio/{id}/{nombre}", 1);
		
		
	}

}
