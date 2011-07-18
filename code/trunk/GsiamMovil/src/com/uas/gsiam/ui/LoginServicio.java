package com.uas.gsiam.ui;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.uas.gsiam.negocio.Usuario;

public class LoginServicio implements ILoginServicio{

	private static final String url = "http://10.0.2.2:8080/GsiamWeb/rest/usuario/login?email={email}&pass={pass}";
	private static final RestTemplate restTemp;
	
	static{
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		
	}
	
	@Override
	public Usuario login(String email, String pass) {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("email", email);
		parms.put("pass", pass);
		Usuario user = restTemp.getForObject(url, Usuario.class,parms);
		return user;
	}

	
	
	
}
