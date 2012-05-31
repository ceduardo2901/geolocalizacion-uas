package com.uas.gsiam.sitio.test;

import junit.framework.Assert;
import net.sf.json.JSONArray;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;

public class ObtenerSitiosTest {

	RestAssured restAssured;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();

	}

	@Test
	public void servicioNoEcontrado() {

		Response response = restAssured.expect().get(
				"/Gsiameb2/sitios/{lat}/{lon}", -34.909141, -56.167225);

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void noExistenSitios() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/sitios/{lat}/{lon}", 0, 0);
		ResponseBody b = response.getBody();
		String str = b.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertEquals(0, jsonArray.size());
	}

	@Test
	public void obtenerSitios() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/sitios/{lat}/{lon}", -34.909141, -56.167225);
		ResponseBody b = response.getBody();
		String str = b.asString();

		JSONArray jsonArray = JSONArray.fromObject(str);

		Assert.assertNotNull(jsonArray.getJSONObject(1));
	}

}
