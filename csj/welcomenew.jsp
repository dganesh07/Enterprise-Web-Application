<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*" %>
<%@page import="java.util.List" %>
<%@page import="java.net.*" %>
<%@page import="java.io.*" %>
<%@page import="road.Product" %>
<%@page import="road.XMLlist" %>

<html>

<head>
	<title>CSP 595</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="script.js"></script>
</head>

<body onload="init()">


<div id="container">
    <header>
    	<h1><a href="/">Best<span>Deals</span></a></h1>
        <h2>Online Store!!!!</h2>
    </header>
    <nav>
    	<ul>
        	<li class="start selected"><a href="index.html">Home</a></li>
            <li class=""><a href="SmartPhones?category=SmartPhone">SmartPhones</a></li>
            <li class=""><a href="SmartPhones?category=Tablets">Tablets</a></li>
            <li class=""><a href="SmartPhones?category=Laptops">Laptop</a></li>
			<li class="end"><a href="SmartPhones?category=TV">TV</a></li>
			<li class=""><a href='testtrend'>Trending</a></li>
			<li class=""><a href='DataAnalytics.html'>DataAnalytics</a></li>
            <li class="order"><a href='index.html'>LogIN</a></li>
			
		</ul>
    </nav>

	<img class="header-image" src="main.jpg" width = "100%" height = "100%" alt="Index Page Image" />
	

    <div id="body">		

	<section id="content">

	    <article>
			
			<h2>Welcome to Best Deals Store</h2>
			
            <p>Get the best of the rest </p>	
            
            <p>Main manufacturer being samsung , LG , Apple and many more!!</p>
			
		</article>


<%  
XMLlist prList= new XMLlist("ProductCatalog.xml");
List<Product> prlist123= prList.getProducts();
System.out.println(prlist123.size());
		String name;

%>
<%

try {
%>
			<div id='content'>
			<div class='post'>
			<h4 class='title'>
			<a href='#'>Welcome to BestDeals online store </a></h4>
			<div class='entry'>
			<br>
			<h4>The world trusts us to deliver the best service </h4>
			<h4>We beat our competitors in all aspects. Price-Match Guaranteed</h4>
		<%	


			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader("C:/apache-tomcat-7.0.34/webapps/csj/DealMatches.txt"));
			line = reader.readLine();
			if (line == null) {
			%>
				<h2>no offers</h2>
			<%

			} else {
				while ((line = reader.readLine()) != null) {
					line = reader.readLine();
					//pw.print(line);

					do {

						%>


					<h4><%=  line %></h4>
						<%
						break;


					} while ((line = reader.readLine()) != null);
				}
			}



		} catch (Exception e) {
			System.out.println(e);
		}


%>



<%
Scanner reader1 = new Scanner(new File("C:/apache-tomcat-7.0.34/webapps/csj/DealMatchId.txt")).useDelimiter(",//s*");

		ArrayList<String> temp = new ArrayList<String>();

		while (reader1.hasNextLine()) {
			temp.add(reader1.nextLine());
		}

		reader1.close();

		Set<String> hs = new HashSet<>();
		hs.addAll(temp);
		temp.clear();
		temp.addAll(hs);

		for (int g = 0; g < temp.size(); g++) {
			pw.println(temp.get(g));
		}

		Product p = null;

		for (Product product : prlist123) {
			for (String haha : temp) {
				if (product.getId().equals(haha)) {
					p = product;
					%>





<div><ul type='none'>
<%
System.out.println(p.getId());
					System.out.println(p.getName());

					name = p.name;
					String num = (String)p.id;


%>
		<li><form action='OrderPage.jsp' method='get'><table>
	<tr><td><img width='200px' height='200px' src='images/<%=p.image%>' /></td></tr>
		<tr><td><%=name%></td></tr>
		<!-- <tr><td><%=num%></td></tr> -->
	<tr><td><%=p.price%></td></tr>
	

		<tr><td><input type='hidden' name='productid' value='<%=num%>'/></td></tr>
		
	<tr><td><input type='submit' value='AddToCart'/></td></tr>
		</table></form></li>
		<form action='view.jsp' method='GET'><input type='submit' value='ViewReview'/></form>
		<form action='WriteReview.jsp' method='GET'><input type='submit' value='WriteReview' name='WriteReview'/></form>






<%}
}
%>
</ul></div>
</body>
</html>
