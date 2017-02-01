import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import java.util.*;

public class SmartPhones extends HttpServlet {
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//cart c = new cart();
response.setContentType("text/html");
PrintWriter pw = response.getWriter();

String htmlString = "";
HttpSession session= request.getSession();
String ProductType=request.getParameter("category");
XMLlist prList= new XMLlist("ProductCatalog.xml");
List<Product> prlist123= prList.getProducts();//(ArrayList<Product>)session.getAttribute("productlist");
System.out.println(prlist123.size());
String name;



htmlString+="<div><ul type='none'>";
for (int i = 0; i < prlist123.size(); i++)
{
	System.out.println(prlist123.get(i).getType());
	System.out.println(prlist123.get(i).getName());
	//System.out.println(prlist123.get(i).getRetailer());
	
		if(prlist123.get(i).getType().equals(ProductType)){
		name=prlist123.get(i).name;
		String num = (String)prlist123.get(i).id;
		
		htmlString = htmlString + "<li><form action='OrderPage' method='get'><table>";
		htmlString = htmlString + "<tr><td><img width='200px' height='200px' src='images/"+prlist123.get(i).image+"' /></td></tr>";
		/*htmlString = htmlString + "<tr><td>"+prlist123.get(i).name+"</td></tr>";*/
		htmlString = htmlString + "<tr><td>"+name+"</td></tr>";
		htmlString = htmlString + "<tr><td>"+num+"</td></tr>";
		htmlString = htmlString + "<tr><td>"+prlist123.get(i).price+"</td></tr>";
		htmlString = htmlString + "<tr><td>"+prlist123.get(i).condition+"</td></tr>";
		htmlString = htmlString + "<tr><td>"+prlist123.get(i).retailer+"</td></tr>";
		//htmlString = htmlString + "<tr><td>"+num+"</td></tr>";

		htmlString = htmlString + "<tr><td><input type='hidden' name='productid' value='"+num+"'/></td></tr>";
		//htmlString = htmlString + "<tr><td><form action='OrderForm.html' method='get'><input type='submit' value='Buy'></input></td></tr>";
			//htmlString = htmlString + "<tr><td><a href='OrderForm.html' onclick='OrderForm.html' type='submit'>Buy</a></td></tr>";

		htmlString = htmlString + "<tr><td><input type='submit' value='AddToCart'/></td></tr>";
		
		htmlString = htmlString + "</table></form></li>";
		htmlString = htmlString + "<form action='view.html' method='GET'><input type='submit' value='ViewReview'/></form>";
		htmlString = htmlString + "<form action='WriteReview.html' method='GET'><input type='submit' value='WriteReview' name='WriteReview'/></form>";
	
}
}
htmlString = htmlString + "</ul></div>";







/*htmlString += Utilities.printHtml("LeftNav.html");
htmlString += Utilities.printHtml("Footer.html");*/

pw.println(htmlString);
}
}