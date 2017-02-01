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
import java.util.ArrayList;
import java.util.Iterator;

public class FindReviews extends HttpServlet {
	
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
		//boolean check = false;
				
		try {
			
			// Get the form data
			String productName = request.getParameter("productName");
			int productPrice = Integer.parseInt(request.getParameter("productPrice"));
			String retailerZip = request.getParameter("retailerZip");
			String retailerCity = request.getParameter("retailerCity");
			String retailerName = request.getParameter("retailerName");
			String retailerState = request.getParameter("retailerState");
			String userName = request.getParameter("userName");
			String userAge = request.getParameter("userAge");
			String userGender = request.getParameter("userGender");
			String userOccupation = request.getParameter("userOccupation");
			String productCategory = request.getParameter("productCategory");
			//String retailerState = request.getParameter("retailerState");
			String reviewDate = request.getParameter("reviewDate");
			String patternSearch = request.getParameter("patternSearch");
			String reviewText = request.getParameter("reviewText");
			String productOnSale = request.getParameter("productOnSale");
			//String manufacturerName = request.getParameter("manufacturerName");
			String manufacturerRebate = request.getParameter("manufacturerRebate");
			
			int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
			String compareRating = request.getParameter("compareRating");
			String comparePrice = request.getParameter("comparePrice");
			String returnValueDropdown = request.getParameter("returnValue");
			String groupByextrabuttons = request.getParameter("groupByextrabuttons");
			String groupByDropdown = request.getParameter("groupByDropdown");
			//String extraButton = request.getParameter("extraButton");
			String advancedByDropdown = request.getParameter("advancedByDropdown");
			
			//Boolean flags to check the filter settings
			boolean noFilter = false;
			boolean filterByProduct = false;
			boolean filterByPrice = false;
			boolean filterByZip = false;
			boolean filterByCity = false;
			boolean filterByRating = false;
			boolean filterByRetailerCity = false;
			boolean filterByRetailerName = false;
			boolean filterByProductOnSale = false;
			boolean filterByManufacturerRebate = false;


			boolean filterByProductCategory = false;
			boolean searchByText = false;
			
			boolean groupBy = false;
			//boolean groupbuttonBy = false;
			boolean groupByCity = false;
			boolean groupByProduct = false;
			boolean groupByRetailerName = false;
			boolean groupByZipCode = false;
			boolean groupByUserId = false;
			boolean groupByUserAge = false;
			boolean groupByUserGender = false;
			boolean groupByUserOccupation = false;
			boolean groupByReviewRating = false;
			boolean groupByReviewDate = false;
			boolean groupByProductCategory = false;
			boolean groupByRetailerState = false;
			boolean groupByProductPrice = false;
			boolean groupByHighestPriceCity = false;
			boolean groupByTop5LikedCity = false;
			boolean groupByProductOnSale = false;
			boolean groupByManufacturerName = false;
			boolean groupByManufacturerRebate = false;
			boolean top5DislikedByCityRetailerZip = false;
			boolean check = false;
			boolean check1 = false;
			boolean check2 = false;
			int n=0;
			
			boolean MedianPriceCity = false;
			boolean top5LikedExpensive = false;
			boolean top5Disliked = false;
			boolean advancedBy = false;
			//boolean advancedButton = false;
			boolean ageMoreThan50CityWise = false;
			
			boolean groupByMedianCity = false;
			
			boolean trendBy = false;
			boolean trendByCity = false;
			
			boolean countOnly = false;
						
			//Get the filters selected
			//Filter - Simple Search
			String[] filters = request.getParameterValues("queryCheckBox");
			//Filters - Group By
			String[] extraSettings = request.getParameterValues("extraSettings");
			String[] trendSettings = request.getParameterValues("trendSettings");
			String[] advancedSettings = request.getParameterValues("advancedSettings");
			String[] extraButton = request.getParameterValues("extraButton");
			String[] searchFilter = request.getParameterValues("searchCheckBox");
			
			DBCursor dbCursor = null;
			AggregationOutput aggregateData = null;
			
			//Check for extra settings(Grouping Settings)
			if(extraSettings != null){				
				//User has selected extra settings
				groupBy = true;
				
				for(int x = 0; x <extraSettings.length; x++){
					switch (extraSettings[x]){						
						case "COUNT_ONLY":
							//Not implemented functionality to return count only
							countOnly = true;				
							break;
						case "GROUP_BY":	
							//Can add more grouping conditions here
							if(groupByDropdown.equals("GROUP_BY_CITY")){
								groupByCity = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCT")){
								groupByProduct = true;
							}else if(groupByDropdown.equals("GROUP_BY_RETAILERSTATE")){
								groupByRetailerState = true;	
							}else if(groupByDropdown.equals("GROUP_BY_RETAILER_NAME")){
								groupByRetailerName = true;
							}else if(groupByDropdown.equals("GROUP_BY_RETAILERZIP")){
								groupByZipCode = true;		
							}else if(groupByDropdown.equals("GROUP_BY_TOP5_LIKED_CITY")){
								groupByTop5LikedCity = true;	
							}else if(groupByDropdown.equals("GROUP_BY_USERAGE")){
								groupByUserAge = true;	
							}else if(groupByDropdown.equals("GROUP_BY_USERID")){
								groupByUserId = true;
							}else if(groupByDropdown.equals("GROUP_BY_USERGENDER")){
								groupByUserGender = true;
							}else if(groupByDropdown.equals("GROUP_BY_USEROCCUPATION")){
								groupByUserOccupation = true;

							}else if(groupByDropdown.equals("GROUP_BY_RATING")){
								groupByReviewRating = true;
							}else if(groupByDropdown.equals("GROUP_BY_REVIEWDATE")){
								groupByReviewDate = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCTCATAGORY")){
								groupByProductCategory = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCTPRICE")){
								groupByProductPrice = true;
							}else if(groupByDropdown.equals("GROUP_BY_RETAILER_STATE")){
								groupByRetailerState = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCTONSALE")){
								groupByProductOnSale = true;
							}else if(groupByDropdown.equals("GROUP_BY_MANUFACTURERNAME")){
								groupByManufacturerName = true;
							}else if(groupByDropdown.equals("GROUP_BY_MANUFACTURERREBATE")){
								groupByManufacturerRebate = true;
							}
							break;
					}		
				}				
			}
			if (returnValueDropdown.equals("TOP_5")){
					check1 = true;
					n = 5;
				}else if (returnValueDropdown.equals("TOP_10")){
					
					check1 = true;
					n = 10;
			}
				
			/*if(extraButton != null){				
				//User has selected extra settings
				groupbuttonBy = true;
				
				for(int x = 0; x <extraButton.length; x++){
					switch (extraButton[x]){						
						case "COUNT_ONLY":
							//Not implemented functionality to return count only
							countOnly = true;				
							break;
						case "GROUP_BY":	
							//Can add more grouping conditions here
							if(groupByextrabuttons.equals("GROUP_BY_HIGHEST")){
								groupByCity = true;
							}/*else if(groupByextrabuttons.equals("GROUP_BY_PRODUCT")){
								groupByProduct = true;
							}//else if(groupByDropdown.equals("GROUP_BY_SHOPPERS")){
								//groupByShopperName = true;	
							else if(groupByextrabuttons.equals("GROUP_BY_RETAILER_NAME")){
								groupByRetailerName = true;
							}else if(groupByextrabuttons.equals("GROUP_BY_ZIPCODE")){
								groupByZipCode = true;	
							}else if(groupByextrabuttons.equals("GROUP_BY_HIGHESTPRICE_CITY")){
								groupByHighestPriceCity = true;	
							}else if(groupByextrabuttons.equals("GROUP_BY_TOP5_LIKED_CITY")){
								groupByTop5LikedCity = true;	
							}else if(groupByextrabuttons.equals("GROUP_BY_USER_AGE")){
								groupByUserAge = true;	
							}else if(groupByextrabuttons.equals("GROUP_BY_USER_ID")){
								groupByUserId = true;
							}else if(groupByextrabuttons.equals("GROUP_BY_USER_GENDER")){
								groupByUserGender = true;
							}else if(groupByextrabuttons.equals("GROUP_BY_USER_OCCUPATION")){
								groupByUserOccupation = true;

							}else if(groupByextrabuttons.equals("GROUP_BY_REVIEWRATING")){
								groupByReviewRating = true;
							}else if(groupByDropdown.equals("GROUP_BY_REVIEWDATE")){
								groupByReviewDate = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCTCATEGORY")){
								groupByProductCategory = true;
							}else if(groupByDropdown.equals("GROUP_BY_Price")){
								groupByProductPrice = true;
							}else if(groupByDropdown.equals("GROUP_BY_RETAILER_STATE")){
								groupByRetailerState = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCT_ON_SALE")){
								groupByProductOnSale = true;
							}else if(groupByDropdown.equals("GROUP_BY_MANUFACTURER_NAME")){
								groupByManufacturerName = true;
							}else if(groupByDropdown.equals("GROUP_BY_MANUFACTURER_REBATE")){
								groupByManufacturerRebate = true;
							}
							break;
					}		
				}				
			}*/
			

			if(advancedSettings != null){                
                //User has selected extra settings
                advancedBy = true;
                
                for(int x = 0; x <advancedSettings.length; x++){
                    switch (advancedSettings[x]){                        
                        case "COUNT_ONLY":
                            //Not implemented functionality to return count only
                            countOnly = true;                
                            break;
                        case "Advance_Search":    
                            //Can add more grouping conditions here
                            if(advancedByDropdown.equals("Median_BY_CITY")){
                                MedianPriceCity = true;
							}else if(advancedByDropdown.equals ("TOP5_LIKED_EXPENSIVE_CITY_RETAILER")){
                                top5LikedExpensive = true;
                            }else if(advancedByDropdown.equals ("TOP5_DISLIKED_CITY_RETAILER")){
                                top5Disliked = true;
                            }else if(advancedByDropdown.equals("REVIEWERAGE_GRATER_THAN_50_CITY")){
								ageMoreThan50CityWise = true;
							}else if(advancedByDropdown.equals("TOP5_DISLIKED_CITY_RETAILERZIP")){
								top5DislikedByCityRetailerZip = true;
							}                                    
                            break;
                    }        
                }                
            }
 			if(searchFilter != null){
				for (int i = 0; i < searchFilter.length; i++) {
					//Check what all filters are ON
					//Build the query accordingly
					switch (searchFilter[i]){
						case "patternSearch":							
							searchByText = true;
							//output.println("patternSearch");
							query.put("reviewText",  java.util.regex.Pattern.compile(patternSearch));
							myReviews.find(query);						
							break;
						default:
							//Show all the reviews if nothing is selected
							//output.println("patternSearch");
							noFilter = true;
							break;		
					}
				}
			}
			
			if(trendSettings != null){				
				//User has selected extra settings
				groupBy = true;
				
				for(int x = 0; x <trendSettings.length; x++){
					switch (trendSettings[x]){						
						case "COUNT_ONLY":
							//Not implemented functionality to return count only
							countOnly = true;				
							break;
						case "TREND_BY":	
							//Can add more grouping conditions here
							if(groupByDropdown.equals("GROUP_BY_CITY")){
								groupByCity = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCT")){
								groupByProduct = true;
							}//else if(groupByDropdown.equals("GROUP_BY_SHOPPERS")){
								//groupByShopperName = true;	
							else if(groupByDropdown.equals("GROUP_BY_RETAILER_NAME")){
								groupByRetailerName = true;
							}else if(groupByDropdown.equals("GROUP_BY_ZIPCODE")){
								groupByZipCode = true;	
							}else if(groupByDropdown.equals("GROUP_BY_HIGHESTPRICE_CITY")){
								groupByHighestPriceCity = true;	
							}
							break;
					}		
				}				
			}		
			/*if(filters != null && groupBy == true ){
				for (int i = 0; i < filters.length; i++) {
					//Check what all filters are ON
					//Build the query accordingly
					switch (filters[i]){										
						case "productName":							
							filterByProduct = true;
							if(!productName.equals("ALL_PRODUCTS")){
								query.put("productName", productName);
							}						
							break;
												
						case "productPrice":
							filterByPrice = true;
							if (comparePrice.equals("EQUALS_TO")) {
								query.put("productPrice", productPrice);
							}else if(comparePrice.equals("GREATER_THAN")){
								query.put("productPrice", new BasicDBObject("$gt", productPrice));
							}else if(comparePrice.equals("LESS_THAN")){
								query.put("productPrice", new BasicDBObject("$lt", productPrice));
							}
							break;
												
						case "retailerZip":
							filterByZip = true;
							query.put("retailerZip", retailerZip);
							break;
												
						case "retailerCity": 
							filterByCity = true;
							if(!retailerCity.equals("All") && !groupByCity){
								query.put("retailerCity", retailerCity);
							}							
							break;
												
						case "reviewRating":	
							filterByRating = true;
							if (compareRating.equals("EQUALS_TO")) {
								query.put("reviewRating", reviewRating);
							}else{
								query.put("reviewRating", new BasicDBObject("$gt", reviewRating));
							}
							break;
													
						default:
							//Show all the reviews if nothing is selected
							noFilter = true;
							break;						
					}				
				}
			}else{
				//Show all the reviews if nothing is selected
				noFilter = true;
			}*/
			
			//Check the main filters only if the 'groupBy' option is not selected
			if(filters != null){
				for (int i = 0; i < filters.length; i++) {
					//Check what all filters are ON
					//Build the query accordingly
					switch (filters[i]){										
						case "productName":							
							filterByProduct = true;
							if(!productName.equals("ALL_PRODUCTS")){
								query.put("productName", productName);
							}						
							break;
												
						case "productPrice":
							filterByPrice = true;
							if (comparePrice.equals("EQUALS_TO")) {
								query.put("productPrice", productPrice);
							}else if(comparePrice.equals("GREATER_THAN")){
								query.put("productPrice", new BasicDBObject("$gt", productPrice));
							}else if(comparePrice.equals("LESS_THAN")){
								query.put("productPrice", new BasicDBObject("$lt", productPrice));
							}else if(comparePrice.equals("HIGHEST_Price")){
								//query.put("productPrice", new BasicDBObject("$lt", productPrice));
								check = true;
							}
							break;
												
						case "retailerZip":
							filterByZip = true;
							query.put("retailerZip", retailerZip);
							break;
						
						case "productCategory":
							filterByProductCategory = true;
							//if(!productCategory.equals("All") && !groupByCity){
								query.put("productCategory", productCategory);
							//}							
							break;
							
						case "manufacturerRebate":
							filterByManufacturerRebate = true;
							//if(!productCategory.equals("All") && !groupByCity){
								query.put("manufacturerRebate", manufacturerRebate);
							//}							
							break;
						
						
						case "productOnSale":
							filterByProductOnSale = true;
							//if(!productCategory.equals("All") && !groupByCity){
								query.put("productOnSale", productOnSale);
							//}							
							break;
						
						case "retailerName":
							filterByRetailerName = true;
							//if(!productCategory.equals("All") && !groupByCity){
								query.put("retailerName", retailerName);
							//}							
							break;

						
						case "retailerCity": 
							filterByCity = true;
							if(!retailerCity.equals("All") && !groupByCity){
								query.put("retailerCity", retailerCity);
							}							
							break;
												
						case "reviewRating":	
							filterByRating = true;
							check2 = true;
							if (compareRating.equals("EQUALS_TO")) {
								query.put("reviewRating", reviewRating);
							}else{
								query.put("reviewRating", new BasicDBObject("$gt", reviewRating));
							}
							break;
													
						default:
							//Show all the reviews if nothing is selected
							noFilter = true;
							break;						
					}				
				}
			}else{
				//Show all the reviews if nothing is selected
				noFilter = true;
				
			}
			constructPageTop(output);
					
			 			
			//Run the query 
			if(groupBy == true){		
				//Run the query using aggregate function
				DBObject match = null;
				DBObject groupFields = null;
				DBObject group = null;
				DBObject projectFields = null;
				DBObject project = null;
				DBObject sort = null;
				
				AggregationOutput aggregate = null;
				
				if(groupByCity){
					 if(check){
						 groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("productPrice",-1));

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$productPrice");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort, group, project);
					
					
					//aggregate = myReviews.aggregate(sort, group, project);				
							
					//Construct the page content
					constructGroupByHighestPriceCityWise(aggregate, output, countOnly);
					
					 } else if(check1){
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("reviewRating",-1));

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$productPrice");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					//if()
					aggregate = myReviews.aggregate(sort, group, project);
					
					
					//aggregate = myReviews.aggregate(sort, group, project);				
							
					//Construct the page content
					constructGroupByLikedCityWise(aggregate, output, countOnly, n);
					}else if(check2){
						match = new BasicDBObject("$match", new BasicDBObject("reviewRating",reviewRating));
					
					groupFields = new BasicDBObject("_id", 0);
					//groupFields.put("_id", "$retailerCity");
					groupFields.put("_id","$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);
					

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$productPrice");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					//sort = new BasicDBObject("$sort", new BasicDBObject("count",-1));
					//limit = new BasicDBObject("$limit", 1);
					aggregate = myReviews.aggregate(match, group, project);
					
					
					//aggregate = myReviews.aggregate(sort, group, project);				
							
					//Construct the page content
					constructGroupByRating5CityWise(aggregate, output, countOnly);
					}
					else{
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("_id","$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					//if()
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByCityContent(aggregate, output, countOnly);
					}
					
				}else if(groupByRetailerName){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("RName", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByRetailerNameContent(aggregate, output, countOnly);
				
				}else if(groupByUserAge){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$userAge");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("UAge", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByUserAgeContent(aggregate, output, countOnly);
				
				}else if(groupByProductOnSale){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$productOnSale");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("PSale", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByProductSaleContent(aggregate, output, countOnly);
				
				}
			
				else if(groupByManufacturerName){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$manufacturerName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("MName", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByManufacturerNameContent(aggregate, output, countOnly);
				
				}else if(groupByManufacturerRebate){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$manufacturerRebate");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("MRebate", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByManufactuterRebateContent(aggregate, output, countOnly);
				
				}else if(groupByRetailerState){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerState");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("RState", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByRetailerStateContent(aggregate, output, countOnly);
				
				}else if(groupByUserGender){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$userGender");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("UGender", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByUserGenderContent(aggregate, output, countOnly);
				
				}else if(groupByProductCategory){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$productCategory");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("PCategory", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByProductCategoryContent(aggregate, output, countOnly);
				
				}else if(groupByProductPrice){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$productPrice");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("PPrice", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByProductPriceContent(aggregate, output, countOnly);
				
				}else if(groupByReviewRating){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$reviewRating");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("RRating", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByReviewRatingContent(aggregate, output, countOnly);
				
				}else if(groupByReviewDate){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$reviewDate");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("RDate", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByReviewDateContent(aggregate, output, countOnly);
				
				
				}else if(groupByUserOccupation){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$userOccupation");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("UOccupation", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByUserOccupationContent(aggregate, output, countOnly);
				
				
				}else if(groupByUserId){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$userName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("UName", "$_id");
					//projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByUserIdContent(aggregate, output, countOnly);
				
				}/*else if(groupByTop5LikedCity){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("reviewRating",-1));

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$productPrice");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort,group, project);
												
					//Construct the page content
					constructGroupByLikedCityWise(aggregate, output, countOnly);
				
				}*/
				else if(groupByZipCode){	
				if(check){
						 groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerZip");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("highestPrice", new BasicDBObject("$max", "$productPrice"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort",new BasicDBObject("productPrice", -1));

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Zip", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("HighestPrice", "$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					projectFields.put("Price", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort,group, project);
												
					//Construct the page content
					constructGroupByHighestPriceZipWise(aggregate, output, countOnly);
					 } else{

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerZip");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("highestPrice", new BasicDBObject("$max", "$productPrice"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort",new BasicDBObject("productPrice", -1));

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Zip", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("HighestPrice", "$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort,group, project);
												
					//Construct the page content
					constructGroupByZipContent(aggregate, output, countOnly);
				
				
				}/*else if(groupByHighestPriceCity){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("highestPrice", new BasicDBObject("$max", "$productPrice"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort",new BasicDBObject("productPrice", -1));

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("HighestPrice", "$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort,group, project);
												
					//Construct the page content
					constructGroupByHighestPriceContent(aggregate, output, countOnly);
				
				*/
				}else if(groupByProduct){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$productName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Product", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);				
							
					//Construct the page content
					constructGroupByProductContent(aggregate, output, countOnly);
					
				}			 
			}
			else if(advancedBy== true){
					DBObject match = null;
					DBObject groupFields = null;
					DBObject group = null;
					DBObject projectFields = null;
					DBObject project = null;
					//DBObject sortFields = null;
					DBObject sort;
					DBObject limit = null;
					DBObject max;
					AggregationOutput aggregate = null;
					
                if(MedianPriceCity){
                   
				    groupFields = new BasicDBObject("_id", 0);
                    groupFields.put("_id", "$retailerCity");
                    groupFields.put("count", new BasicDBObject("$sum", 1));
                    groupFields.put("productName", new BasicDBObject("$push", "$productName"));
                    groupFields.put("price", new BasicDBObject("$push", "$productPrice"));
                    groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
                    groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
                    group = new BasicDBObject("$group", groupFields);

                    sort = new BasicDBObject("$sort", new BasicDBObject("productPrice", -1));
                    
                    projectFields = new BasicDBObject("_id", 0);
                    projectFields.put("City", "$_id");
                    projectFields.put("Review Count", "$count");
                    projectFields.put("Product", "$productName");
                    projectFields.put("Price", "$price");
                    projectFields.put("User", "$userName");
                    projectFields.put("Reviews", "$review");
                    projectFields.put("Rating", "$rating");
                    project = new BasicDBObject("$project", projectFields);
                    
                    aggregate = myReviews.aggregate(sort, group, project);
                                                
                    //Construct the page content
                    constructmedianPriceByCityContent(aggregate, output, countOnly);
                    
                    
                }else if(ageMoreThan50CityWise){
				
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", new BasicDBObject("city","$retailerCity"));
					
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("price", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					groupFields.put("userAge", new BasicDBObject("$push", "$userAge"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject ("$sort", new BasicDBObject("userAge",1));

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$price");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					projectFields.put("Age", "$userAge");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort, group, project);
												
					//Construct the page content
					constructAgeGreaterThan50CityWise(aggregate, output, countOnly);
					
				}else if(top5DislikedByCityRetailerZip){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", new BasicDBObject("city","$retailerCity").append("RetailerZip","$retailerZipcode"));
					//groupFields.put("_id","$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("price", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject ("$sort", new BasicDBObject("reviewRating",1));

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$price");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort, group, project);
												
					//Construct the page content
					constructTop5DislikedByCityRetailerZip(aggregate, output, countOnly);
					
				}
				
			
				else if(top5LikedExpensive){
                    
                    //match = new BasicDBObject("$match", new BasicDBObject("reviewRating", 5));
                    
                    groupFields = new BasicDBObject("_id", 0);
                    groupFields.put("_id", new BasicDBObject("City","$city").append("RetailerName", "$retailerName"));
                    //groupFields.put("CITY", new BasicDBObject("$push", "$city"));
                    //groupFields.put("Retailername", new BasicDBObject("$push", "$retailerName"));
                    groupFields.put("count", new BasicDBObject("$sum", 1));
                    groupFields.put("productname", new BasicDBObject("$push", "$productName"));
                    groupFields.put("productprice", new BasicDBObject("$push", "$productPrice"));
                    groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
                    groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
                    group = new BasicDBObject("$group", groupFields);

                    sort = new BasicDBObject("$sort", new BasicDBObject ("city", -1).append("reviewRating",-1).append("productprice", -1)); //For Price and rating
                    
                    projectFields = new BasicDBObject("_id", 0).append("City","$City").append("retailerName", "$RetailerName");
                    projectFields.put("City", "$_id");
                    projectFields.put("Review Count", "$count");
                    projectFields.put("Retailername", "$Retailername");
                    projectFields.put("reviewRating", "$reviewRating");
                    projectFields.put("Product", "$productname");
                    projectFields.put("Price", "$productprice");
                    projectFields.put("User", "$userName");
                    projectFields.put("Reviews", "$review");
                    projectFields.put("Rating", "$rating");
                    project = new BasicDBObject("$project", projectFields);
                    
                    aggregate = myReviews.aggregate(sort, group, project);
                                                
                    //Construct the page content
                    constructtoplikedexpensivedislikedContent(aggregate, output, countOnly);
                    
                }else if(top5Disliked){
                    
                    //match = new BasicDBObject("$match", new BasicDBObject("reviewRating", 5));
                    
                    groupFields = new BasicDBObject("_id", 0);
                    groupFields.put("_id", new BasicDBObject("City","$city").append("RetailerName", "$retailerName"));
                    //groupFields.put("CITY", new BasicDBObject("$push", "$city"));
                    //groupFields.put("Retailername", new BasicDBObject("$push", "$retailerName"));
                    groupFields.put("count", new BasicDBObject("$sum", 1));
                    groupFields.put("productname", new BasicDBObject("$push", "$productname"));
                    groupFields.put("productprice", new BasicDBObject("$push", "$productprice"));
                    groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
                    groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
                    group = new BasicDBObject("$group", groupFields);

                    sort = new BasicDBObject("$sort", new BasicDBObject ("city", -1).append("reviewRating",1)); //For Price and rating
                    
                    projectFields = new BasicDBObject("_id", 0).append("City","$City").append("retailerName", "$RetailerName");
                    projectFields.put("City", "$_id");
                    projectFields.put("Review Count", "$count");
                    projectFields.put("Retailername", "$Retailername");
                    projectFields.put("reviewRating", "$reviewRating");
                    projectFields.put("Product", "$productname");
                    projectFields.put("Price", "$productprice");
                    projectFields.put("User", "$userName");
                    projectFields.put("Reviews", "$review");
                    projectFields.put("Rating", "$rating");
                    project = new BasicDBObject("$project", projectFields);
                    
                    aggregate = myReviews.aggregate(sort, group, project);
                                                
                    //Construct the page content
                    constructTop5DislikedByCityRetailer(aggregate, output, countOnly);
                    
                }
                
            }

	
			
			else{
				//Check the return value selected
				int returnLimit = 0;
				
				//Create sort variable
				DBObject sort = new BasicDBObject();
												
				if (returnValueDropdown.equals("TOP_5")){
					//Top 5 - Sorted by review rating
					returnLimit = 5;
					sort.put("reviewRating",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("TOP_10")){
					//Top 10 - Sorted by review rating
					returnLimit = 10;
					sort.put("reviewRating",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("LATEST_5")){
					//Latest 5 - Sort by date
					returnLimit = 5;
					sort.put("reviewDate",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("LATEST_10")){
					//Latest 10 - Sort by date
					returnLimit = 10;
					sort.put("reviewDate",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else{
					//Run the simple search query(default result)
					dbCursor = myReviews.find(query);
				}		
				
				//Construct the page content
				constructDefaultContent(dbCursor, output, countOnly);
			}			
			
			//Construct the bottom of the page
			constructPageBottom(output);
			
			
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
	
	public void constructPageTop(PrintWriter output){
		String pageHeading = "Query Result";
		String myPageTop = "<!DOCTYPE html>" + "<html lang=\"en\">"
					+ "<head>	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
					+ "<title>Best Deals</title>"
					+ "<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />"
					+ "</head>"
					+ "<body>"
					+ "<div id=\"container\">"
					+ "<header>"
					+ "<h1><a href=\"/\">BestDeals<span></span></a></h1><h2>Tutorial 3</h2>"
					+ "</header>"
					+ "<nav>"
					+ "<ul>"
					+ "<li class=\"\"><a href=\"index.html\">Home</a></li>"
					+ "<li class = \"start selected\"><a href=\"DataAnalytics.html\">Data Analytics</a></li>"
					+ "</ul>"
					+ "</nav>"
					+ "<div id=\"body\">"
					+ "<section id=\"review-content\">"
					+ "<article>"
					+ "<h2 style=\"color:#DE2D3A;font-weight:700;\">" +pageHeading + "</h2>";
		
		output.println(myPageTop);		
	}
	
	public void constructPageBottom(PrintWriter output){
		String myPageBottom = "</article>"
					+ "</section>"
                    + "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<footer>"
					+ "<div class=\"footer-content\">"
					+ "<ul>"
                    + "<li>"
                    + "<h4>Dummy Link Section 1</h4>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 1</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 2</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link  3</a>"
                    + "</li>"
					+ "</ul>"
					+ "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>CSP 595 - Enterprise Web Application - Assignment#3</p>"
					+ "</div>"
					+ "</footer>"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		
		output.println(myPageBottom);
	}
	
	public void constructDefaultContent(DBCursor dbCursor, PrintWriter output, boolean countOnly){
		int count = 0;
		String tableData = " ";
		String pageContent = " ";

		while (dbCursor.hasNext()) {		
			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
			tableData =  "<tr><td>Name: <b>     " + bobj.getString("productName") + " </b></td></tr>"
						+ "<tr><td>Price:       " + bobj.getInt("productPrice") + "</br>"
						+ "Retailer:            " + bobj.getString("retailerName") + "</br>"
						+ "Retailer Zipcode:    " + bobj.getString("retailerZip") + "</br>"
						+ "Retailer City:       " + bobj.getString("retailerCity") + "</br>"
						+ "Retailer State:      " + bobj.getString("retailerState") + "</br>"
						+ "Sale:                " + bobj.getString("productOnSale") + "</br>"
						+ "User ID:             " + bobj.getString("userName") + "</br>"
						+ "User Age:            " + bobj.getString("userAge") + "</br>"
						+ "User Gender:         " + bobj.getString("userGender") + "</br>"
						+ "User Occupation:     " + bobj.getString("userOccupation") + "</br>"
						+ "Manufacturer:        " + bobj.getString("manufacturerName") + "</br>"
						+ "Manufacturer Rebate: " + bobj.getString("manufacturerRebate") + "</br>"
						+ "Rating:              " + bobj.getString("reviewRating") + "</br>"
						+ "Date:                " + bobj.getString("reviewDate") + "</br>"
						+ "Review Text:         " + bobj.getString("reviewText") + "</td></tr>";
				
			count++;
				
				output.println("<h3>"+count+"</h3>");
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
		}

		//No data found
		if(count == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
	public void constructGroupByCityContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
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
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructGroupByUserAgeContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - User Age </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>User Age: "+bobj.getString("UAge")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	public void constructTop5DislikedByCityRetailer(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City & Retailer-Disliked Products </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				//BasicDBList price = (BasicDBList) bobj.get("Price");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				if(productList.size()<5){
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
					}	
				}else{
					while (productCount < 5) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
					}
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
	
	public void constructGroupByHighestPriceZipWise(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Highest Price with Zip Wise!!!!! </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productPrice = (BasicDBList) bobj.get("Price");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Zip: "+bobj.getString("Zip")+"</td>&nbsp"
						//+	"<td>Highest Price: "+productPrice.get(0)+"</td>"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							+   "Price: "+productPrice.get(0)+"</br>"
							+   "Rating: "+rating.get(0)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
									
					
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
	public void constructAgeGreaterThan50CityWise(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City & Sorted By Reviewers Age </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				List<Integer> ageList = (ArrayList<Integer>)bobj.get("Age");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td></tr>";
						
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					if(ageList.get(productCount)>50){
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Reviewers age: "+ageList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					}
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
	
	public void constructGroupByProductCategoryContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Product Category </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Product Category: "+bobj.getString("PCategory")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	public void constructGroupByRetailerStateContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Retailer State </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Retailer State: "+bobj.getString("RState")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructTop5DislikedByCityRetailerZip(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City & Retailer-Disliked Products </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				//BasicDBList price = (BasicDBList) bobj.get("Price");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				if(productList.size()<5){
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
					}	
				}else{
					while (productCount < 5) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
					}
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
	
	public void constructGroupByProductSaleContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Product On Sale </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Product On Sale: "+bobj.getString("PSale")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructGroupByManufacturerNameContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Manufacturer Name </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Manufacturer Name: "+bobj.getString("MName")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructGroupByManufactuterRebateContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Manufacturer Rebate </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Manufacturer Rebate: "+bobj.getString("MRebate")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructGroupByProductPriceContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Product Price </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Prodcut Price: "+bobj.getString("PPrice")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	
	public void constructGroupByReviewRatingContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Review Rating </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Review Rating: "+bobj.getString("RRating")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructGroupByReviewDateContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Review Date </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Review Date: "+bobj.getString("RDate")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructGroupByRating5CityWise(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
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
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	
	public void constructGroupByUserGenderContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - User Gender </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>User Gender: "+bobj.getString("UGender")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructGroupByUserIdContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - User Id </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>User Id: "+bobj.getString("UName")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructGroupByUserOccupationContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - User Occupation </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>User Occupation: "+bobj.getString("UOccupation")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructmedianPriceByCityContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
    int rowCount = 0;
    int productCount = 0;
    String tableData = " ";
    String pageContent = " ";
        
    output.println("<h1> Median Price By Retailer City </h1>");        
    for (DBObject result : aggregate.results()) {
            BasicDBObject bobj = (BasicDBObject) result;
            BasicDBList productList = (BasicDBList) bobj.get("Product");
            List<Integer> priceList = (ArrayList<Integer>) bobj.get("Price");
            BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
            BasicDBList rating = (BasicDBList) bobj.get("Rating");
            int middle = priceList.size()/2;
                
            rowCount++;
            tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
                    +    "<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
                
            pageContent = "<table class = \"query-table\">"+tableData+"</table>";        
            output.println(pageContent);
                
            //while (productCount < productList.size()) {
                    
                int med;
                if(productList.size() % 2 == 0){
                    
                med = priceList.get(middle)+priceList.get(middle-1)/2;
                tableData = "<tr rowspan = \"3\"><td> Median Price: "+med+"</br></td></tr>";
                    
                }else{
                        
                med = priceList.get(middle);
                tableData = "<tr rowspan = \"3\"><td> Median Price: "+med+"</br></td></tr>";
                        
                }
                    
                
                pageContent = "<table class = \"query-table\">"+tableData+"</table>";        
                output.println(pageContent);

                //Reset product count
                productCount =0;
    }        
        
        //No data found
        if(rowCount == 0){
            pageContent = "<h1>No Data Found</h1>";
            output.println(pageContent);
        }
        
}
	
	public void constructGroupByTrendingContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
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
						+	"<td>Reviews Found: "+bobj.getString("HighestReview")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	
	public void constructGroupByZipContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Zip </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList productPrice = (BasicDBList) bobj.get("ProductPrice");
				int High = Integer.parseInt(bobj.getString("HighestPrice"));
				
				rowCount++;
				tableData = "<tr><td>Zip: "+bobj.getString("Zip")+"</td>&nbsp"
						+   "<td>Highest Price: "+bobj.getString("HighestPrice")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				//while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							+   "ProductPrice: "+productPrice.get(0)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					//productCount++;					
				//}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
	/*public void constructGroupByHighestPriceContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Highest Price </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productPrice = (BasicDBList) bobj.get("Price");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						//+	"<td>Highest Price: "+productPrice.get(0)+"</td>"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							+   "Price: "+productPrice.get(0)+"</br>"
							+   "Rating: "+rating.get(0)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
									
					
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}*/
	
	public void constructGroupByHighestPriceCityWise(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Highest Price City Wise </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productPrice = (BasicDBList) bobj.get("Price");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						//+	"<td>Highest Price: "+productPrice.get(0)+"</td>"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							+   "Price: "+productPrice.get(0)+"</br>"
							+   "Rating: "+rating.get(0)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
									
					
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
	public void constructGroupByLikedCityWise(AggregationOutput aggregate, PrintWriter output, boolean countOnly, int n){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productPrice = (BasicDBList) bobj.get("Price");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						//+	"<td>Highest Price: "+productPrice.get(0)+"</td>"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				if(productList.size()>n){
				for(productCount = 0; productCount<n ; ++productCount){
					
						tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Price: "+productPrice.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					}
				}else{
					for(productCount = 0; productCount<productList.size() ; ++productCount){
					
						tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Price: "+productPrice.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
							
						pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					}
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
	public void constructGroupByRetailerNameContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Retailer Name </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Retailer Name: "+bobj.getString("RName")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
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
	public void constructtoplikedexpensivedislikedContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
            int rowCount = 0;
            int productCount = 0;
            String tableData = " ";
            String pageContent = " ";
            
            output.println("<h1> Top Liked and Expensive By - Retailer City </h1>");        
            for (DBObject result : aggregate.results()) {
                    BasicDBObject bobj = (BasicDBObject) result;
                    BasicDBList productList = (BasicDBList) bobj.get("Product");
                    //String Retailername = bobj.getString("Retailername");
                    BasicDBList productPrice = (BasicDBList) bobj.get("Price");
                    BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
                    BasicDBList rating = (BasicDBList) bobj.get("Rating");
                    
                    rowCount++;
                    tableData = "<tr><td> "+bobj.getString("City")+"</td>&nbsp"
                            //+    "<td>Retailer Name: "+Retailername+"</td>"
                            +    "<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
                    
                    pageContent = "<table class = \"query-table\">"+tableData+"</table>";        
                    output.println(pageContent);
                    
                    //Now print the products with the given review rating
                    while (productCount < productList.size()) {
                        tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
                                +   "Price:  "+productPrice.get(productCount)+"</br>"
                                +   "Rating: "+rating.get(productCount)+"</br>"
                                +    "Review: "+productReview.get(productCount)+"</td></tr>";
                                                    
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

	
	
	
	public void constructGroupByProductContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		output.println("<h1> Grouped By - Products </h1>");
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Product: "+bobj.getString("Product")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (reviewCount < productReview.size()) {
					tableData = "<tr rowspan = \"3\"><td>Rating: "+rating.get(reviewCount)+"</br>"
								+ "Review: "+productReview.get(reviewCount)+"</td></tr>";
							
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					reviewCount++;					
				}
					
				//Reset review count
				reviewCount = 0;
					
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
}