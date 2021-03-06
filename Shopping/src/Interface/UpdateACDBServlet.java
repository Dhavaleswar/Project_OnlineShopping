package Interface;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ShoppingDatabase.DBSession;


@WebServlet("/UpdateACDBServlet")
public class UpdateACDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateACDBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		String search = request.getParameter("search");
		request.getRequestDispatcher("/AttributenCategory.html").include(request,response);
		try {
			DBSession dbConnection = new DBSession();
			String query = "SELECT DISTINCT PRODUCTS.PID,QUANTITY, PRODUCT_NAME,PRICE FROM PRODUCTS JOIN PRODUCT_CATEGORY ON PRODUCTS.PID=PRODUCT_CATEGORY.PID WHERE PRODUCT_NAME LIKE '%"+search+"%'OR CATEGORY LIKE '%"+search+"%'";
			ResultSet rs = dbConnection.runQuery(query);
			out.print("<table class='upd-table'><tr><th>Product Name</th><th>Price</th><th>Quantity</th><th colspan = '4'>Action</th></tr>");
			
			while(rs.next()) {
				String productName = rs.getString("PRODUCT_NAME");
				int price=rs.getInt("PRICE");
				int quantity=rs.getInt("QUANTITY");
				int pid=rs.getInt("PRODUCTS.PID");				
				out.print("<tr>"
							+ "<td><a href = /Shopping/SpecificationServlet?p="+pid+">"+productName+"</a></td>"
							+ "<td id ='price'>"+price+"</td>"
							+ "<td id = 'quant'>"+ quantity+"</td>"
							+ "<td id = 'btn'><a href = /Shopping/updatingAtrrServlet?pid="+pid+"><button class='w3-btn w3-blue'>ATTRIBUTE</button></a></td>"							
							+ "<td id = 'btn'><a href = /Shopping/updatingCatServlet?pid="+pid+"><button class='w3-btn w3-red'> CATEGORY</button></a></td>"
						+"</tr>");
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
	}


}
