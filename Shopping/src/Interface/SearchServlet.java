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

import ShoppingDatabase.DBSession;
@WebServlet("/SearchServlet")
public class SearchServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public SearchServlet() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out =response.getWriter();
		String search = request.getParameter("search");
		/* request.getRequestDispatcher("/Search.html").include(request,response);*/
			 
			try {
				DBSession dbConnection = new DBSession();
				String query = "SELECT PID,PRODUCT_NAME,PRICE FROM PRODUCTS WHERE PRODUCT_NAME LIKE '%"+search+"%'";
				ResultSet rs = dbConnection.runQuery(query);
				out.print("<html><head><meta charset=ISO-8859-1><title>Insert title here</title></head><body>");
				out.print("<table><tr><th>PID</th><th>Product Name</th><th>Price</th></tr>");
				
				while(rs.next()) {
					out.print("<tr><td>" + rs.getInt("PID")+ "</td><td>" +rs.getString("PRODUCT_NAME")+"</td><td> "+rs.getInt("PRICE")+"</td></tr>");
				}
				dbConnection.close();
				out.print("</table>");
				/*
				String query = "SELECT DISTINCT CATEGORY FROM CATEGORY";
				ResultSet rs = dbConnection.runQuery(query);
				while(rs.next()) {
					out.print(rs.getString("CATEGORY")+" ");
				}
				dbConnection.close();*/
			} catch (ClassNotFoundException e) {
				out.print("HI");
				e.printStackTrace();
			} catch (SQLException e) {
				out.print("I");
				e.printStackTrace();
			}
			out.print("<br>");
			out.print("<form action=CartServlet method=POST>");
			out.print("<h3>");
			out.print("Enter PID: <input type=text name = PID />");
			out.print("<br>");
			out.print("Quantity: <input type=text name = quantity />");
			out.print("<br>");
			out.print("<input type=submit value = Purchase>");
			out.print("<br>");
			out.print("</form>");
			
			out.print("<br>");
			out.print("<br>");
			out.print("<br>");
			out.print("<form action=SpecificationServlet method=POST>");
			out.print("<h3>");
			out.print("Enter PID to know specification: <input type=text name = PID />");
			out.print("<br>");
			out.print("<input type=submit value = specification>");
			out.print("</body></html>");
			
			}
}
