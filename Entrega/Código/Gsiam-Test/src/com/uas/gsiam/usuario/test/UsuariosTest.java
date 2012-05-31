package com.uas.gsiam.usuario.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import net.sf.json.JSONArray;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;

public class UsuariosTest {

	RestAssured restAssured;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();

	}

	@Test
	public void errorUrlUsuarios() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/usuario/{id}/{nombre}", 69, "pablo");

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void respuestaCorrectaObtenerUsuarios() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/usuarios/{id}/{nombre}", 69, "tito");

		Assert.assertEquals(200, response.getStatusCode());
	}

	@Test
	public void obtenerUsuarios() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/usuarios/{id}/{nombre}", 69, "tito");
		ResponseBody b = response.getBody();
		String str = b.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertEquals(2, jsonArray.size());
	}

}
