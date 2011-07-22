package com.uas.gsiam.ui;

import com.uas.gsiam.negocio.Usuario;
import com.uas.gsiam.negocio.dto.UsuarioDTO;

public interface ILoginServicio {

	UsuarioDTO login(String email, String pass);
}
