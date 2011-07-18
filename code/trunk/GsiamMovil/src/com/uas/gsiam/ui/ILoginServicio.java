package com.uas.gsiam.ui;

import com.uas.gsiam.negocio.Usuario;

public interface ILoginServicio {

	Usuario login(String email, String pass);
}
