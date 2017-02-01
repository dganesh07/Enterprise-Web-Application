import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;

public class ViewReviews extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//String productCategory = request.getParameter("productCategory");
			//String productName = request.getParameter("productName");
			//String searchParameter = productName;
			String searchField = "productName";
			
			//Get the product selected
			String searchParameter = request.getParameter("productName");
	
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("Tutorial_3");
			
			DBCollection myReviews = db.getCollection("myReviews");
			
			// Find and display 
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put(searchField,searchParameter);

			DBCursor cursor = myReviews.find(searchQuery);
		
			PrintWriter out = response.getWriter();
			//out.println(cursor);
						
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Reviews For:"+ searchParameter+ "</h1>");
			
			out.println("<table>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='welcome.html'> Index </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='SmartPhones?category=SmartPhone'> SmartPhones </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='SmartPhones?category=Tablets'> Tablets </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='SmartPhones?category=Laptops'> Laptops </a>");
			out.println("</td>");
			out.println("</tr>");


			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='SmartPhones?category=TV'> TV </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("</table>");
			out.println("<br><br><hr>");
			
			if(cursor.count() == 0){
				out.println("There are no reviews for this product.");
				out.println(cursor.count());
			}else{
			
				out.println("<table>");
				
				/*String productName = "";
				String productCategory = "";
				String productPrice = "";
				String manufacturerName =  "";
				String userName = "";
				String userAge = "";
				String userGender = "";
				String city = "";
				String state = "";
				String zip = "";
				String reviewRating = "";
				String retailerName = "";
				String reviewDate = "";
				String reviewText = "";*/
				String userName = "";
				String userAge = "";
				String userGender = "";
				String reviewRating = "";
				String reviewDate = "";
				String reviewText = "";
				//String productName = request.getParameter("itemName");
				//String productCategory="Gaming Console";
				//String imageLocation = " ";
				/*String productonSale=request.getParameter("productonSale");
				String manufacturerName=request.getParameter("itemType");
				String manufacturerRebate=request.getParameter("manufacturerRebate");
				String productPrice =request.getParameter("itemPrice");*/
				while (cursor.hasNext()) {
					//out.println(cursor.next());
					BasicDBObject obj = (BasicDBObject) cursor.next();				
					
					out.println("<tr>");
					out.println("<td> Product Name: </td>");
					//productName = obj.getString("searchParameter");
					out.println("<td>" + searchParameter + "</td>");
					out.println("</tr>");
					
					/*out.println("<tr>");
					out.println("<td> Product Category:</td>");
					productCategory = obj.getString("productCategory");
					out.println("<td>" + productCategory + "</td>");
					out.println("</tr>");
					*/
					out.println("<tr>");
					out.println("<td> User Name: </td>");
					userName = obj.getString("userName");
					out.println("<td>" + userName + "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> User Age: </td>");
					userAge = obj.getString("userAge");
					out.println("<td>" + userAge + "</td>");
					out.println("</tr>");			
					
					out.println("<tr>");
					out.println("<td> Review Rating: </td>");
					reviewRating = obj.getString("reviewRating").toString();
					out.println("<td>" + reviewRating + "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Review Date: </td>");
					reviewDate = obj.getString("reviewDate");
					out.println("<td>" + reviewDate + "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Review Text: </td>");
					reviewText = obj.getString("reviewText");
					out.println("<td>" + reviewText + "</td>");
					out.println("</tr>");

				}
			}	
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");
			
		} catch (MongoException e) {
				e.printStackTrace();
		}
	}

	
	public void destroy(){
      // do nothing.
	}
}