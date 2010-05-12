package speerker.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import speerker.db.UserGateway;
import speerker.types.User;

public class Login extends SpeerkerServlet {
	private static final long serialVersionUID = 1819772909748015120L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.initialize();

		Boolean correct = false;
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();

		String password = req.getParameter("password");
		if (password == null)
			correct = false;

		String user = req.getParameter("user");
		if (user == null)
			correct = false;

		User dbUser = UserGateway.findByUserName(user);
		if (dbUser == null) {
			correct = false;
		} else if (dbUser.getValid() && dbUser.getPassword().equals(password))
			correct = true;
		else
			correct = false;

		out.println(correct.toString());
		out.close();
	}

}
