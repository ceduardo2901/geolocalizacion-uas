package com.uas.gsiam.usuario.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import net.sf.json.JSONArray;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.uas.gsiam.test.dto.UsuarioDTO;

public class AmigosTest {

	RestAssured restAssured;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();

	}

	@Test
	public void errorUrlAmigos() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/amigo/{id}", 69);

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void noExistenAmigos() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/amigos/{id}", 0);
		ResponseBody b = response.getBody();
		String str = b.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertEquals(0, jsonArray.size());
	}
	
	@Test
	public void getAmigos() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/amigos/{id}", 1);
		ResponseBody b = response.getBody();
		String str = b.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertNotSame(0, jsonArray.size());
	}

}
