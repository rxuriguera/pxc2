package speerker.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class register_servlet extends HttpServlet {
	
	 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,
     IOException {
		 
		 	res.setContentType("text/html");
		    PrintWriter out = res.getWriter();
		    
		    String user = req.getParameter("Usuario");
		    String nombre = req.getParameter("Nombre");
		    String apellido = req.getParameter("Apellido");
		    String pass = req.getParameter("Contraseña");
		    String pass2 = req.getParameter("Contraseña2");
		    String email = req.getParameter("Email");
		    
		    if(!pass.contentEquals(pass2))
		    {
		    	out.println("<html>");
		    	out.println("<head>");
		        out.println("<title>" + "Error en el Registro" + "</title>");
		        out.println("</head>");
		        out.println("<body>");
		        out.println("No coinciden las contraseñas" + "<br>");
		        out.println("Vuelva a introducir la misma contraseña" + "<br>");
		        out.println("<a href=\"../Speerker/Formulario.html\">Volver a la pagina de registro</a>");
		        out.println("</body>");
		        out.println("</html>");
		    }
		    
		    String query = "SELECT username FROM users" + "WHERE username =" + nombre;
		    
		    if(nombre.isEmpty())
		    {
		    	//El nombre de usuario no puede ser vacio
		    	out.println("<html>");
		    	out.println("<head>");
		        out.println("<title>" + "Error en el Registro" + "</title>");
		        out.println("</head>");
		        out.println("<body>");
		        out.println("El nombre no puede ser vacio" + "<br>");
		        out.println("Vuelva a registrarse con otro nombre" + "<br>");
		        out.println("<a href=\"../Speerker/Formulario.html\">Volver a la pagina de registro</a>");
		        out.println("</body>");
		        out.println("</html>");
		    }
		    else if(query.isEmpty())
		    {
		    	//El usuario no existe y se puede crear
		    	
		    	String query2 = "INSERT INTO users (username,password,firstName,lastName) " + 
		    	"VALUES("+ user + "," + pass + "," + nombre + "," + apellido + ")";	    	
		    }
		    else
		    {
		    	//El usuario existe se debe introducir un nuevo usuario
		    	out.println("<html>");
		    	out.println("<head>");
		        out.println("<title>" + "Error en el Registro" + "</title>");
		        out.println("</head>");
		        out.println("<body>");
		        out.println("El nombre de usuario ya existe");
		        out.println("Vuelva a registrarse con otro nombre");
		        out.println("<a href=\"../Speerker/Formulario.html\">Volver a la pagina de registro</a>");
		        out.println("</body>");
		        out.println("</html>");
		    	 
		    }
	 
	 }

}