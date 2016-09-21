package ShoppingDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOps {

	private DBSession dbConnection ;
	
	
	// Method to get Categories
	public void getCategory() throws SQLException, ClassNotFoundException{
		dbConnection = new DBSession();
		System.out.println("Categories available: ");
		String query = "SELECT DISTINCT CATEGORY FROM CATEGORY";
		ResultSet rs = dbConnection.runQuery(query);
		while(rs.next()) {
			System.out.println(rs.getString("CATEGORY")+" ");
		}
		dbConnection.close();
	}
	
	// Method to get SubCategories
	public void getSubCategory(String category) throws ClassNotFoundException, SQLException {
		dbConnection = new DBSession();
		System.out.println("Sub Categories in " + category+ "are: ");
		String query = "SELECT SUB_CATEGORY FROM CATEGORY WHERE CATEGORY ="+category;
		ResultSet rs = dbConnection.runQuery(query);
		while(rs.next()) {
			System.out.println(rs.getString("SUB_CATEGORY")+" ");
		}
		dbConnection.close();
	}
	
	// Method to getBrands
		public void getBrands(String sub_category) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();
			String query = "SELECT DISTINCT BRAND FROM "+sub_category;
			ResultSet rs =  dbConnection.runQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString("BRAND")+" ");
			}
			dbConnection.close();
		}
	
	// Method to get brand details
		public void getBrandDetails(String sub_category,String brand) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();
			String query = "SELECT * FROM " + sub_category+ " WHERE BRAND =" + brand;
			ResultSet rs =  dbConnection.runQuery(query);
			while (rs.next()) {
				
				System.out.printf("%5d %-10s %-10s %-7d %-10s %-2b\n", rs.getInt("PID"), rs.getString("BRAND"), rs.getString("PRODUCT_NAME"), rs.getInt("PRICE"), rs.getString("COLOUR"), rs.getBoolean("EMI"));
			}			
			dbConnection.close();
		}
		
	// method to purchase item
		public int PurchaseItem(int pid, int quantity) throws ClassNotFoundException, SQLException, NumberFormatException, IOException {
			dbConnection = new DBSession();
			int tmpQuantity;
			String query = "SELECT QUANTITY FROM PRODUCTS WHERE PID =" + pid;
			ResultSet rs = dbConnection.runQuery(query);
			rs.next();
			tmpQuantity = rs.getInt(1);
			dbConnection.close();
			if (tmpQuantity >= quantity){
			//	setNcheckQuantity(pid, quantity);
				return -1;
			}
			else {
				return tmpQuantity;
			}
			
		}
		
	// to update the quantity after purchase
		private void setNcheckQuantity(int pid, int quantity) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();
			int tmpQuantity;
			String query = "SELECT QUANTITY FROM PRODUCTS WHERE PID =" + pid;
			ResultSet rs = dbConnection.runQuery(query);
			rs.next();
			tmpQuantity = rs.getInt(1);
			tmpQuantity = tmpQuantity - quantity;
			String query1 = "UPDATE PRODUCTS SET QUANTITY ="+tmpQuantity +" WHERE PID="+ pid;
			ResultSet rs1 = dbConnection.runQuery(query1);
			dbConnection.close();
		}
		public void insertCart(int pid, String user, int quantity) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();	
			String query = "INSERT INTO CART (PID, USERID, QUANTITY) VALUES ("+pid+",'"+user+"',"+quantity+")";
			dbConnection.runQuery(query);
			dbConnection.close();
		}
		public int getQuantity(int pid, String user) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();	
			String query = "SELECT COUNT(*) FROM CART WHERE PID="+pid+" AND USERID='"+user+"'";
			ResultSet rs=dbConnection.runQuery(query);
			rs.next();
			int tmpQuantity=rs.getInt(1);
			return tmpQuantity;
					
			
		}

		public void updateCart(int pid, String user, int quantity) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();	
			if (quantity==0){
				String query = "DELETE FROM CART WHERE pid = "+pid+"";
				dbConnection.runQuery(query);
			}
			else{
			String query = "UPDATE CART SET QUANTITY= "+quantity+" where pid = "+pid+" and userid= '"+user+"'";
			dbConnection.runQuery(query);
			}
			dbConnection.close();
		}
		
		public int getBillNo() throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();	
			String query = "SELECT COUNT(distinct BILL_NO) FROM PURCHASE_HISTORY;";
			ResultSet rs=dbConnection.runQuery(query);
			rs.next();
			int count=rs.getInt(1);
			return count;
					
			
		}
		public String HTML(String ID)
		{
			return("<html><head><meta charset=ISO-8859-1><title>Insert title here</title></head>"+
		            "<style>"+
	           "#s1{background: transparent;border:solid;color: white; font-size:20px;font-weight:700;}"+
	           "#s2{background: transparent; border:solid;color: white;"+"font-size:20px;font-weight:700;}"+ 
	           "#s3{background: transparent; border:solid;color: white;"+"font-size:20px;font-weight:700;}"+
	           "#btn{margin-left:700px;background: transparent;border:solid;color: white; font-size:15px;font-weight:20;}"+
	           "#btn1{margin-left:600px;}p1{text-align:center;}"+
	            "body {background-color:black; color : white;}"+ 
	            "h1 {text-align:center}"+ 
	            "table, th, td {border: 1px solid white;}"+
	            "</style>"+
	            "<body>"+
	            "<p style= font-size:20px>" + ID + "!</p> <h1>ShoppingSpree</h1>"+ 
	            "<p style= text-align:right>"+
	            "<input type=submit id = s1 name= Cart value= Cart />"+
	            "<input type=submit id = s2 name= OrderHistory value=OrderHistory />"+
	            "<input type=submit id = s3 name= LogOut value=LogOut />"+
	            "</p>");
		}
}
