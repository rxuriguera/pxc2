package speerker.servlets;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class formulario extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException
{
	    String name = req.getParameter("Nombre");
	    System.out.println(name);
	    System.out.println("<a href=\"\">Tornar a la plana principal</a>");
}
	
	
}