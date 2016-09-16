package Interface;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import UsersDatabase.UserDBOperations;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SignUpServlet() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		 UserDBOperations usr_DBOps = new UserDBOperations();	
		 PrintWriter out =response.getWriter();
		 String usr_name=request.getParameter("name");
		 String usr_pass=request.getParameter("pass");
		 boolean check;
		 try {
			check = usr_DBOps.doValidate(usr_name);
			if(check==false)
		     {
		         String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		    	 out.print(docType + "<HTML><body><h3><font color= white>User name exists, try another one</font></h3></body></HTML>");
		    	 //session.invalidate();
		         RequestDispatcher rd=request.getRequestDispatcher("/SignUp.html");
		         rd.include(request,response);
			 }
		     else
		     {
		    	 String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		    	 out.print(docType + "<HTML><body><h3><font color= white> Signed Up successfully. Try Logging in</font></h3></body></HTML>");
		         usr_DBOps.addAccountDetails(usr_name,usr_pass);
		    	 RequestDispatcher rd=request.getRequestDispatcher("/Login.html");
		         rd.include(request,response);		    	 
		     }			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}    
     
    }
}