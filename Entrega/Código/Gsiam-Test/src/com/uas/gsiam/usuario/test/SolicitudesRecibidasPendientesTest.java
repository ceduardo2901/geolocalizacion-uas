package com.uas.gsiam.usuario.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import net.sf.json.JSONArray;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class SolicitudesRecibidasPendientesTest {

	RestAssured restAssured;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();

	}

	@Test
	public void servicioNoEcontrado() {

		Response response = restAssured.expect().get(
				"/Gsiameb2/usuarios/solicitudesrecibida/{id}", 1);

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void faltaParametro() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/solicitudesrecibidas/");
		
		
		Assert.assertEquals(405, response.getStatusCode());

	}
	
	@Test
	public void noExisteSolicitudRecibidasPendiente() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/solicitudesrecibidas/{id}", 1);

		String str = response.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertEquals(0, jsonArray.size());

	}
	
	@Test
	public void solicitudRecibidaPendiente() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/solicitudesrecibidas/{id}", 3);

		String str = response.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertNotSame(0, jsonArray.size());

	}

}
