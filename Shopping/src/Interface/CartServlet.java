package Interface;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ShoppingDatabase.*;
@WebServlet("/CartServlet")
public class CartServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public CartServlet() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out =response.getWriter();
		int pid = Integer.parseInt(request.getParameter("PID"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		DBOps db = new DBOps();
		int result;
		try {
			result = db.PurchaseItem(pid,quantity);
			if (result==-1){
				out.print("purchase sucessful");}
			else
				out.print("enter quantity less than "+ result);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	
		
		