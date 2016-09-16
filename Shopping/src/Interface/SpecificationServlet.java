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
@WebServlet("/SpecificationServlet")
public class SpecificationServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public SpecificationServlet() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out =response.getWriter();
		int pid = Integer.parseInt(request.getParameter("PID"));
		try {
			DBSession dbConnection = new DBSession();
			String query = "SELECT ATTRIBUTE,ATT_VALUE FROM ATTRIBUTE_VALUES WHERE PID="+pid;
			ResultSet rs = dbConnection.runQuery(query);
			out.print("<html><head><meta charset=ISO-8859-1><title>Insert title here</title></head><body>");
			out.print("<table><tr><th>Attribute</th><th>Attribute_Value</th></tr>");
			
			while(rs.next()) {
				out.print("<tr><td>" + rs.getString("ATTRIBUTE")+ "</td><td>" +rs.getString("ATT_VALUE")+"</td></tr>");
			}
			dbConnection.close();
			out.print("</table>");
			
		} catch (ClassNotFoundException e) {
			out.print("HI");
			e.printStackTrace();
		} catch (SQLException e) {
			out.print("I");
			e.printStackTrace();
		}
		out.print("</body></html>");
		
	}
}