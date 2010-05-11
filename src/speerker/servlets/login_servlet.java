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
		    
		    HttpSession session = req.getSession();
		    session.setAttribute("logon.isDone", user);
		    
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


