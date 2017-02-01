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
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.AggregationOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;


	public class Trending extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		PrintWriter output = response.getWriter();
					
		DB db = mongo.getDB("Tutorial_3");
		
		// If the collection does not exists, MongoDB will create it for you
		DBCollection myReviews = db.getCollection("myReviews");
		
		BasicDBObject query = new BasicDBObject();
				
		try {
			
			// Get the form data
			String productName = request.getParameter("productName");
			String productCategory = request.getParameter("productCategory");
			//int productPrice = Integer.parseInt(request.getParameter("productPrice"));
            String productOnSale = request.getParameter("productOnSale");
            String consoleManufacturer = request.getParameter("consoleManufacturer");

			String retailerName = request.getParameter("retailerName");
			String retailerZipcode = request.getParameter("retailerZipcode");
			String retailerCity = request.getParameter("retailerCity");
			String retailerState = request.getParameter("retailerState");


			String manufacturerRebate = request.getParameter("manufacturerRebate");

			String userID = request.getParameter("userID");
			//int userAge = Integer.parseInt(request.getParameter("userAge"));
			String userGender = request.getParameter("userGender");
			String userOccupation = request.getParameter("userOccupation");	

			//int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
			String reviewDate = request.getParameter("reviewDate");	

			String compareRating = request.getParameter("compareRating");
			String comparePrice = request.getParameter("comparePrice");
			String compareAge = request.getParameter("compareAge");

			String returnValueDropdown = request.getParameter("returnValue");
			String groupByDropdown = request.getParameter("groupByDropdown");

			String highest_Price = request.getParameter("highestPrice");
			


			//Boolean flags to check the filter settings
			boolean noFilter = false;
			boolean filterByProductCategory = false;
			boolean filterByProduct = false;
			boolean filterByPrice = false;
			boolean filterByRetailerName = false;
			boolean filterByZip = false;
			boolean filterByCity = false;
			boolean filterByState = false;
			boolean filterBy_POnSale = false;
			boolean filterBy_PManufacturer = false;			
			boolean filterBy_Manufacturer_Rebate = false;
			boolean filterBy_User_ID = false;
			boolean filterBy_User_Age = false;
			boolean filterBy_User_Gender = false;
			boolean filterBy_User_Occupation = false;
			boolean filterByReviewDate = false;			
			boolean filterBy_Rating = false;
			
			boolean groupBy = false;
			boolean groupByProductCategory = false;
			boolean groupByProduct = false;
			boolean groupByProductPrice = false;
			boolean groupByRetailerName = false;
			boolean groupByRetailerZipCode = false;
			boolean groupByCity = false;
			boolean groupBy_R_State = false;
			boolean groupByP_On_Sale = false;
			boolean groupBy_Product_Manufacturer = false;
			boolean groupBy_Manufacturer_Rebate = false;
			boolean groupBy_User_ID = false;
			boolean groupBy_User_Age = false;
			boolean groupBy_User_Gender = false;
			boolean groupBy_User_Occupation = false;
			boolean groupBy_Review_Rating = false;
			boolean groupByReviewDate = false;
			boolean highestPrice = false;


			
			boolean countOnly = false;
			
			DBObject match = null;
			DBObject groupFields = null;
			DBObject group = null;
			DBObject projectFields = null;
			DBObject project = null;
			AggregationOutput aggregate = null;
			
			
			match= new BasicDBObject("$match",new BasicDBObject("reviewRating",5));
				
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					//groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					//groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					//projectFields.put("User", "$userName");
					//projectFields.put("Reviews", "$review");
					//projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(match, group, project);
												
					//Construct the page content
					constructGroupByCityTrending(aggregate, output, countOnly);
					
					
		}    catch (MongoException e) {
			e.printStackTrace();
		}	
	}
	
	public void constructGroupByCityTrending(AggregationOutput aggregate, PrintWriter output, boolean countOnly){	
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
						
						//+"<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount == 0) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</td></tr>";
							//+   "Rating: "+rating.get(productCount)+"</br>"
							//+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
}

			
				
					
			
			
			