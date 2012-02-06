package com.uas.gsiam.sitio.test;

import java.util.List;

import junit.framework.Assert;
import net.sf.json.JSONArray;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;

public class ObtenerCategoriaTest {

	RestAssured restAssured;
	
	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();

		
	}

	@Test
	public void errorUrlObtenerCategorias() {

		Response response = restAssured.expect().get(
				"/Gsiameb2/sitios/categorias");

		Assert.assertEquals(404, response.getStatusCode());
	}
	
	@Test
	public void obtenerCategorias() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/sitios/categorias");
		ResponseBody b = response.getBody();
		String str = b.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertNotSame(0, jsonArray.size());
	}
	
	@Test
	public void obtenerCategorias2() {
		String json = restAssured.get("/GsiamWeb2/sitios/categorias").asString();
		List<String> list = JsonPath.from(json).getList("imagen");
		
		Assert.assertTrue(list.size() > 0);

	}

}
