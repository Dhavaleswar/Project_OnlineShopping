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

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out =response.getWriter();
		int pid = Integer.parseInt(request.getParameter("pid"));
		String category = request.getParameter("category");
		System.out.println(category);
		try {
			DBSession dbConnection = new DBSession();
			String query = "SELECT PRODUCT_NAME FROM PRODUCTS WHERE PID="+pid;
			ResultSet rs=dbConnection.runQuery(query);
			rs.next();
			String name = rs.getString("PRODUCT_NAME");
			String query1 = "INSERT INTO PRODUCT_CATEGORY (PID,CATEGORY) VALUES ("+pid+", '"+category+"')";
			dbConnection.runQuery(query1);
			out.println("<script type=\"text/javascript\">");
	        out.print("alert('Category linked to "+name+"');");
	        out.println("</script>");
		}catch (SQLException | ClassNotFoundException e) {
			out.println("<script type=\"text/javascript\">");
	        out.print("alert('Category already exists');");
	        out.println("</script>");
			e.printStackTrace();
		}
		request.getRequestDispatcher("/AddNew.html").include(request,response);
		out.print("<div id ='updating-cat-form'>");
		out.print("<form action ='CategoryServlet' method = 'POST'>");
		out.print("<input type='hidden' id='pidIn' name='pid' value = "+pid+"> "
				+ "<label for= 'category'>CATEGORY</label>" +"<br>"+"<br>"
				+ "<input type='text' id='category' name='category' value = 'category name'> "
				+ "<input type='submit' value='Submit' id ='up-form-submit'>"
				);		
		out.print("</form>");
		out.print("</div>");
	}
}