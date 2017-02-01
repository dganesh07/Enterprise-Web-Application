import java.util.*;
import java.sql.*;



public class ComposerData {



	public HashMap getComposers() {

		HashMap composers = new HashMap();
		try {
			

			ArrayList<Product> b = new ArrayList<Product>();

			//db connection;
			Connection conn = null;
			//Statement stmt = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase", "root", "damini94");

			//stmt = conn.createStatement();


			Statement stmt = conn.createStatement();
			String selectCustomerQuery = "select * from products";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);


			int i = 0;
			while (rs.next()) {

				Product pr = new Product();
				pr.setId(rs.getString("ProductId"));
				pr.setName(rs.getString("ProductName"));


				b.add(pr);
				composers.put(i, new Composer(pr.getId(), pr.getName()));
				i++;

			}


		} catch (Exception e) {
			System.out.println(e);
		}

		return composers;
	}


}
