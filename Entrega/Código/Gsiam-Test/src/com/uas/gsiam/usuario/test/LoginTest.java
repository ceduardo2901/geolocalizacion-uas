package com.uas.gsiam.usuario.test;

import junit.framework.Assert;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class LoginTest {

	RestAssured restAssured;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();
	}

	@Test
	public void errorUrlLogin() {

		Response response = restAssured.expect().get(
				"/Gsiameb2/usuarios/logi/{email}/{pass}", "pepe@gmail.com",
				"pass1");

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void usuarioNoRegistrado() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/login/{email}/{pass}",
				"pepeppe@gmail.com", "pass1");
		String msg = response.getBody().asString();
		Assert.assertEquals("El email o password es incorrecto", msg);
	}

	@Test
	public void usuarioRegistrado() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/login/{email}/{pass}", "pepe@gmail.com",
				"pass1");

		Assert.assertEquals(200, response.getStatusCode());
	}

	@Test
	public void nombreUsuarioRegistrado2() {
		Response response = restAssured
				.given()
				.auth()
				.none()
				.expect()
				.body("nombre", equalTo("Elsa Lame"))
				.when()
				.get("/GsiamWeb2/usuarios/login/{email}/{pass}",
						"pepe@gmail.com", "pass1");
	}

	@Test
	public void idUsuarioIncorrecto() {
		Response response = restAssured
				.given()
				.auth()
				.none()
				.expect()
				.body("id", Matchers.not(equalTo(5)))
				.when()
				.get("/GsiamWeb2/usuarios/login/{email}/{pass}",
						"pepe@gmail.com", "pass1");
	}

}
