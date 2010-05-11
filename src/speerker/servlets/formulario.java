package speerker.servlets;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class formulario extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException
{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<title>Informe Registro Usuario</title>");
	    out.println("<h1>Informe Registro Usuario</h1>");

	    String name = req.getParameter("Nombre");
	    out.println("html" + name + "</html>");
	    out.println("<a href=\"\">Tornar a la plana principal</a>");
}
	
	
}