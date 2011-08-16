package com.uas.gsiam.login.ui;

import com.uas.gsiam.negocio.dto.UsuarioDTO;

public interface ILoginServicio {

	UsuarioDTO login(String email, String pass);
}
