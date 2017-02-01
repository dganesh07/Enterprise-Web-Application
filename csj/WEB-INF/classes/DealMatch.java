import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import java.util.*;

public class DealMatch extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//cart c = new cart();
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		String htmlString = "";

		//HttpSession session = request.getSession();
		String ProductType = request.getParameter("category");
		XMLlist prList = new XMLlist("ProductCatalog.xml");
		List<Product> prlist123 = prList.getProducts(); //(ArrayList<Product>)session.getAttribute("productlist");
		System.out.println(prlist123.size());
		String name;



StringBuilder myvar = new StringBuilder(); 
myvar.append("<html>")
     .append("")
     .append("<head>")
     .append("	<title>CSP 595</title>")
     .append("	<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />")
     .append("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">")
     .append("<script src=\"script.js\"></script>")
     .append("</head>")
     .append("")
     .append("<body onload=\"init()\">")
     .append("")
     .append("<div id=\"container\">")
     .append("    <header>")
     .append("    	<h1><a href=\"/\">Best<span>Deals</span></a></h1>")
     .append("        <h2>Online Store!!!!</h2>")
     .append("    </header>")
     .append("    <nav>")
     .append("    	<ul>")
     .append("        	<li class=\"start selected\"><a href=\"index.html\">Home</a></li>")
     .append("            <li class=\"\"><a href=\"SmartPhones?category=SmartPhone\">SmartPhones</a></li>")
     .append("            <li class=\"\"><a href=\"SmartPhones?category=Tablets\">Tablets</a></li>")
     .append("            <li class=\"\"><a href=\"SmartPhones?category=Laptops\">Laptop</a></li>")
     .append("			<li class=\"end\"><a href=\"SmartPhones?category=TV\">TV</a></li>")
     .append("			<li class=\"\"><a href='testtrend'>Trending</a></li>")
     .append("			<li class=\"\"><a href='DataAnalytics.html'>DataAnalytics</a></li>")
     .append("            <li class=\"order\"><a href='index.html'>LogIN</a></li>")
     .append("			")
     .append("		</ul>")
     .append("    </nav>")
     .append("")
     .append("	<img class=\"header-image\" src=\"main.jpg\" width = \"100%\" height = \"100%\" alt=\"Index Page Image\" />")
     .append("	")
     .append("")
     .append("    <div id=\"body\">		")
     .append("")
     .append("	<section id=\"content\">")
     .append("")
     .append("	    <article>")
     .append("			")
     .append("			<h2>Welcome to Best Deals Store</h2>")
     .append("			")
     .append("            <p>Get the best of the rest </p>	")
     .append("            ")
     .append("            <p>Main manufacturer being samsung , LG , Apple and many more!!</p>")
     .append("			")
     .append("		</article>");
	pw.print(myvar);

try {
			pw.print("<div id='content'>");
			pw.print("<div class='post'>");
			pw.print("<h4 class='title'>");
			pw.print("<a href='#'>Welcome to BestDeals online store </a></h4>");
			pw.print("<div class='entry'>");
			//pw.print("<br>");
			//pw.print("<h4>The world trusts us to deliver the best service </h4>");
			//pw.print("<br>");
			//pw.print("<h4>We beat our competitors in all aspects. Price-Match Guaranteed</h4>");
			String line = null;
			//HashMap<String, Product> productmap = new HashMap<String, Product>();


			BufferedReader reader = new BufferedReader(new FileReader("C:/apache-tomcat-7.0.34/webapps/csj/DealMatches.txt"));
			//pw.print("here2");
			line = reader.readLine();
			if (line == null) {
				pw.print("<h2>no offers</h2>");

			} else {
                    int x = 1;
				while ((line = reader.readLine()) != null) {
					line = reader.readLine();
					//pw.print(line);

					do {

						pw.print("<h4>" + line + "</h4>");
                              x++;
						break;


					} while ((line = reader.readLine()) != null && x < 3);
				}
			}



		} catch (Exception e) {
			System.out.println(e);
		}



		StringBuilder myvar1 = new StringBuilder(); 
myvar1.append("</section>")
     .append("        ")
     .append("    <aside class=\"sidebar\">")
     .append("	")
     .append("            <ul>	")
     .append("               <li>")
     .append("                    <h4>Products</h4>")
     .append("                    <ul>")
     .append("                     <li class=\"\"><a href=\"SmartPhones?category=SmartPhone\">SmartPhones</a></li>")
     .append("            <li class=\"\"><a href=\"SmartPhones?category=Tablets\">Tablets</a></li>")
     .append("            <li class=\"\"><a href=\"SmartPhones?category=Laptops\">Laptop</a></li>")
     .append("			<li class=\"end\"><a href=\"SmartPhones?category=TV\">TV</a></li>")
     .append("                    ")
     .append("                    </ul>")
     .append("                </li>")
     .append("                ")
     .append("                <li>")
     .append("                    <h4>About us</h4>")
     .append("                    <ul>")
     .append("                        <li class=\"text\">")
     .append("                        	<p style=\"margin: 0;\">This is an Online Store.</p>")
     .append("                        </li>")
     .append("                    </ul>")
     .append("                </li>")
     .append("             ")
     .append("            <li>")
     .append("                	<h4>Search site</h4>")
     .append("                    <ul>    ")
     .append("                    <form name=\"autofillform\" action=\"AutoCompleteServlet\">")
     .append("            		<div name=\"autofillform\">")
     .append("            			<p>")
     .append("            			<input type=\"text\" name=\"searchid\" value=\"\" class=\"input\" id=\"complete-field\" onkeyup=\"doCompletion()\" placeholder=\"search...\" style =\"padding:6px;font-size: 16px;margin: 5px 0;\"/>")
     .append("            			</p>")
     .append("            			<div id =\"auto-row\">")
     .append("            				<table id=\"complete-table\" class=\"gridtable\" style=\"position: absolute;width:315px;\">")
     .append("            				</table>")
     .append("            			</div>")
     .append("            		</div>")
     .append("                    </form>")
     .append("          ")
     .append("            </ul>")
     .append("            </li>")
     .append("")
     .append("")
     .append("                <li>")
     .append("                    <h4>Helpful Links</h4>")
     .append("                    <ul>")
     .append("                        <li><a href=\"https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=good%20laptop%20configuration%202016\" title=\"premium templates\">What you buy</a></li>")
     .append("                        <li><a href=\"https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=good%20laptop%20configuration%202016\">What you buy</a></li>")
     .append("                        ")
     .append("                    </ul>")
     .append("                </li>")
     .append("                ")
     .append("            </ul>")
     .append("		")
     .append("    </aside>")
     .append("    ")
     .append("	<div class=\"clear\"></div>")
     .append("	</div>");
	

pw.print(myvar1);





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
/*
		for (int g = 0; g < temp.size(); g++) {
			pw.println(temp.get(g));
		}
*/
		Product p = null;

		for (Product product : prlist123) {
			for (String haha : temp) {
				if (product.getId().equals(haha)) {
					p = product;
					htmlString += "<div><ul type='none'>";
					System.out.println(p.getId());
					System.out.println(p.getName());

					name = p.name;
					String num = (String)p.id;

					htmlString = htmlString + "<li><form action='OrderPage' method='get'><table>";
					htmlString = htmlString + "<tr><td><img width='200px' height='200px' src='images/" + p.image + "' /></td></tr>";
					/*htmlString = htmlString + "<tr><td>"+prlist123.get(i).name+"</td></tr>";*/
					htmlString = htmlString + "<tr><td>" + name + "</td></tr>";
					htmlString = htmlString + "<tr><td>" + num + "</td></tr>";
					htmlString = htmlString + "<tr><td>" + p.price + "</td></tr>";
					htmlString = htmlString + "<tr><td>" + p.condition + "</td></tr>";
					htmlString = htmlString + "<tr><td>" + p.retailer + "</td></tr>";
					//htmlString = htmlString + "<tr><td>"+num+"</td></tr>";

					htmlString = htmlString + "<tr><td><input type='hidden' name='productid' value='" + num + "'/></td></tr>";
					//htmlString = htmlString + "<tr><td><form action='OrderForm.html' method='get'><input type='submit' value='Buy'></input></td></tr>";
					//htmlString = htmlString + "<tr><td><a href='OrderForm.html' onclick='OrderForm.html' type='submit'>Buy</a></td></tr>";

					htmlString = htmlString + "<tr><td><input type='submit' value='AddToCart'/></td></tr>";

					htmlString = htmlString + "</table></form></li>";
					htmlString = htmlString + "<form action='view.html' method='GET'><input type='submit' value='ViewReview'/></form>";
					htmlString = htmlString + "<form action='WriteReview.html' method='GET'><input type='submit' value='WriteReview' name='WriteReview'/></form>";


					htmlString = htmlString + "</ul></div>";

				}
			}
		}





pw.println(htmlString);
StringBuilder myvar2 = new StringBuilder(); 
myvar2.append("<footer>")
     .append("	")
     .append("        <div class=\"footer-content\">")
     .append("            <ul>")
     .append("            	<li><h4>Dummy Link Section 1</h4></li>")
     .append("                <li><a href=\"#\">Dummy 1</a></li>")
     .append("                <li><a href=\"#\">Dummy 2</a></li>")
     .append("                <li><a href=\"#\">Dummy 3</a></li>")
     .append("			</ul>")
     .append("           ")
     .append("        <div class=\"clear\"></div>")
     .append("        </div>")
     .append("		")
     .append("        <div class=\"footer-bottom\">")
     .append("            <p>CSP 595 - Enterprise Web Application</p>")
     .append("        </div>")
     .append("		")
     .append("    </footer>")
     .append("</div>")
     .append("")
     .append("</body>")
     .append("")
     .append("</html>");
	


pw.print(myvar2);
		
	}
}