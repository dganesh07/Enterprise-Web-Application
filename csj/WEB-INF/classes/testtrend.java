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

public class testtrend extends HttpServlet {

	private static final long serialVersionUID = 1L;
	MongoClient mongo;

	public void init() throws ServletException {
		// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);

	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		DB db = mongo.getDB("Tutorial_3");
		DBCollection myReviews = db.getCollection("myReviews");
		out.println("<html><body background = blue></body></html>");


		try {
			out.print("<h4> Top 5 Zip code based on Maximum number of products reviewed!!</h4>");
			out.print("<table style='border: 1px solid black;'>");
			BasicDBObject groupFields = new BasicDBObject("_id", 0);
			groupFields.put("count", new BasicDBObject("$sum", 1));
			groupFields.put("_id", "$retailerZipcode");
			BasicDBObject group = new BasicDBObject("$group", groupFields);
			BasicDBObject sort = new BasicDBObject();
			BasicDBObject projectFields = new BasicDBObject();
			BasicDBObject project = new BasicDBObject("$project", projectFields);
			projectFields.put("value", "$_id");
			projectFields.put("ReviewValue", "$count");
			sort.put("ReviewValue", -1);
			BasicDBObject orderby = new BasicDBObject("$sort", sort);
			BasicDBObject limit = new BasicDBObject("$limit", 5);
			AggregationOutput aggregate = myReviews.aggregate(group, project, orderby, limit);
			MongoDBDataStoreUtilities obg = new MongoDBDataStoreUtilities();
			obg.constructGroupByContent(aggregate, out);
			out.print("</table>");
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			out.print("<table style='border: 1px solid black;'>");
			BasicDBObject query = new BasicDBObject();
			query.put("reviewRating", new BasicDBObject("$gt", 3));
			DBCursor dbCursor = myReviews.find(query);
			MongoDBDataStoreUtilities obg1 = new MongoDBDataStoreUtilities();
			obg1.constructTableContent(dbCursor, out);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			out.println("<h4>Top 5 Most reviewed product regardless of the rating!!");
			out.print("<table style='border: 1px solid black;'>");
			BasicDBObject groupFields = new BasicDBObject("_id", 0);
			groupFields.put("count", new BasicDBObject("$sum", 1));
			groupFields.put("_id", "$productName");
			BasicDBObject group = new BasicDBObject("$group", groupFields);
			BasicDBObject sort = new BasicDBObject();
			BasicDBObject projectFields = new BasicDBObject();
			BasicDBObject project = new BasicDBObject("$project", projectFields);
			projectFields.put("value", "$_id");
			projectFields.put("ReviewValue", "$count");
			sort.put("ReviewValue", -1);
			BasicDBObject orderby = new BasicDBObject("$sort", sort);
			BasicDBObject limit = new BasicDBObject("$limit", 5);
			AggregationOutput aggregate = myReviews.aggregate(group, project, orderby, limit);
			MongoDBDataStoreUtilities obg = new MongoDBDataStoreUtilities();
			obg.constructGroupByContent(aggregate, out);
			out.print("</table>");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
