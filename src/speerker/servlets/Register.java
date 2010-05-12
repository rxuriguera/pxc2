package speerker.servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import speerker.db.UserGateway;
import speerker.types.User;

public class Register extends SpeerkerServlet {

	private static final long serialVersionUID = -5319538734724161500L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.initialize();

		Boolean finish = false;

		String user = req.getParameter("Usuario");
		String nombre = req.getParameter("Nombre");
		String apellido = req.getParameter("Apellido");
		String pass = req.getParameter("Contraseña");
		String pass2 = req.getParameter("Contraseña2");

		if (user == null || nombre == null || apellido == null || pass == null
				|| pass2 == null) {
			this.errors.add("No se han recibido todos los parámetros");
			finish = true;
		}

		try {
			if (user.equals("") || nombre.equals("") || apellido.equals("")
					|| pass.equals("") || pass2.equals("")) {
				this.errors.add("Debe rellenar todos los campos");
				finish = true;
			}
		} catch (NullPointerException e) {
			this.errors.add("No se han recibido todos los parámetros");
			finish = true;
		}

		if (!pass.contentEquals(pass2)) {
			this.errors.add("Las contraseñas no coinciden");
			finish = true;
		}

		User dbUser = new User();
		dbUser.setFirstName(nombre);
		dbUser.setLastName(apellido);
		dbUser.setUsername(user);
		dbUser.setPassword(pass);

		if (!finish) {
			Integer result = UserGateway.newUser(dbUser);
			if (result > 0)
				req.setAttribute("resultText", "Usuario registrado con éxito!");
			else
				this.errors.add("El usuario con nombre " + user + " ya existe");
		}

		req.setAttribute("title", "Registro de Usuarios");
		req.setAttribute("errors", this.errors);
		RequestDispatcher rd = req.getRequestDispatcher("result.jsp");
		rd.forward(req, res);
	}

}