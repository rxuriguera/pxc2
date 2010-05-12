package speerker.servlets;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class login_servlet extends HttpServlet {
	
	  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,
      IOException {
		  
		    res.setContentType("text/html");
		    PrintWriter out = res.getWriter();
		    
		    String user = req.getParameter("user");
		    String password = req.getParameter("pass");
		    
		    if(user.isEmpty() || password.isEmpty())
		    {
		    	out.println("<html>");
		    	out.println("<head>");
		        out.println("<title>" + "Error en el acceso" + "</title>");
		        out.println("</head>");
		        out.println("<body>");
		        out.println("Debe rellenar los campos de usuario  y contraseña");
		        out.println("Vuelva a loguearse");
		        out.println("<a href=\"../Speerker/Main.html\">Volver a la pagina principal</a>");
		        out.println("</body>");
		        out.println("</html>");
		    }
		    
		    else
		    {
		    	String query = "SELECT username,password FROM users " + "WHERE username =" + user +
		    	"AND password =" + password;
		    	
		    	//No existe el usuario introducido con su respectivo password
		    	if(query.isEmpty())
		    	{
			    	out.println("<html>");
			    	out.println("<head>");
			        out.println("<title>" + "Error en el acceso" + "</title>");
			        out.println("</head>");
			        out.println("<body>");
			        out.println("El nombre de usuario o conraseña no son validos");
			        out.println("Vuelva a loguearse");
			        out.println("<a href=\"../Speerker/Main.html\">Volver a la pagina principal</a>");
			        out.println("</body>");
			        out.println("</html>");
		    	}
		    	
		    	//Existe el usuario registrado con user y pass
		    	else
		    	{
			    	out.println("<html>");
			    	out.println("<head>");
			        out.println("<title>" + "Acceso del usuario" + "</title>");
			        out.println("</head>");
			        out.println("<body>");
			        out.println("Bienvenido usuario" + user);
			        out.println("<a href=\"../Speerker/Main.html\">Volver a la pagina principal</a>");
			        out.println("</body>");
			        out.println("</html>");
		    	}
		    }
		  
	  }
}


