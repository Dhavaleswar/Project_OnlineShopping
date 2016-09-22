package Interface;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ShoppingDatabase.*;
@WebServlet("/transactionServlet")
public class transactionServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public transactionServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out =response.getWriter();
		try {
			DBSession dbConnection3 = new DBSession();

			request.getRequestDispatcher("/Transactions.html").include(request,response);
			String query3 = "SELECT DISTINCT BILL_NO, DATE, TIME FROM PURCHASE_HISTORY ORDER BY BILL_NO ASC";
			ResultSet rs3 = dbConnection3.runQuery(query3);			
			out.print("<center><table class='transaction-table'><tr><th>Bill No.</th><th>Amount</th><th>Date</th></tr>");
			while(rs3.next()) {
				int billNo1 = rs3.getInt("BILL_NO");
				String Date1= rs3.getString("DATE");
				DBSession dbConnection4 = new DBSession();
				String query4 = "SELECT SUM(QUANTITY*PRICE) AS Amount FROM PURCHASE_HISTORY WHERE BILL_NO="+billNo1;
				ResultSet rs4 = dbConnection4.runQuery(query4);
				rs4.next();
				int amount = rs4.getInt(1);
				out.print("<tr><td ><a href = /Shopping/BillServlet?p="+billNo1+"&a="+amount+">"+billNo1+"</a></td><td id ='price'>    "+amount+"</td><td id ='date'>     "+Date1+"</td></tr>");
				dbConnection4.close();
			}
			out.print("</table></center></body></html>");
			dbConnection3.close();
			
		}catch (ClassNotFoundException e) {
			out.print("HI");
			e.printStackTrace();
		} catch (SQLException e) {
			out.print("I");
			e.printStackTrace();
		}
	}
}
			
				
				