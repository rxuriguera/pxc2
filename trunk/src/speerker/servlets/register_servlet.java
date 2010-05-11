package speerker.servlets;

import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class register_servlet extends HttpServlet {
	
	 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,
     IOException {
		 
		    res.setContentType("text/html");
		    PrintWriter out = res.getWriter();
		    
		    String nombre = req.getParameter("Nombre");
		    String apellido = req.getParameter("Apellido");
		    String pass = req.getParameter("Contraseña");
		    String pass2 = req.getParameter("Contraseña2");
		    String email = req.getParameter("Email");
		    
		    HttpSession session = req.getSession();
		    session.setAttribute("logon.isDone", nombre);
		    
		    try {
		        String target = (String) session.getAttribute("login.target");
		        if (target != null) {
		          res.sendRedirect(target);
		          return;
		        }
		      } catch (Exception ignored) {
		      }
		      
		      res.sendRedirect("/");
		    
	 }

}
