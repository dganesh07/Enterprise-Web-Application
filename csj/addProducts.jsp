<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<!doctype html>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>BestDeals</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>

<body>
<div id="container">
    <header>
    	<h1><a href="http://localhost/csj/index.html"><span>BestDeals</span></a></h1>
        <h2>Best Online Retail Store</h2>
		<a href="index.jsp" style='font-weight: bold;float:right;margin-right:20px;'>Home</a>
    </header>
<%
String productName = request.getParameter("productName");
String productId = request.getParameter("productId");
String retailer = request.getParameter("retailer");
String condition = request.getParameter("condition");
String productType = request.getParameter("type");
String temp = request.getParameter("price");
int price = Integer.parseInt(temp);
String image = request.getParameter("image");

			Connection conn = null;
        	Statement stmt = null;
			String searchParameter = request.getParameter("orderNo");
			Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase", "root", "damini94"); 
			stmt = conn.createStatement();


			String sqlStr =  "INSERT INTO products(ProductId,ProductName,image,retailer,ProductCondition,ProductType,ProductPrice) " + "VALUES (?,?,?,?,?,?,?);";
			PreparedStatement ps = conn.prepareStatement(sqlStr);
			ps.setString(1, productId);
			ps.setString(2, productName);
			ps.setString(3, image);
			ps.setString(4, retailer);
			ps.setString(5, condition);
			ps.setString(6, productType);
			ps.setInt(7, price);
			ps.execute();
			%>

			<h1> The product has been added to the store!!</h1>
			</div>
			</body>
			</html>



