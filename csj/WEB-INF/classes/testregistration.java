import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

import java.net.UnknownHostException;
import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
public class testregistration extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException{
      	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			//Get the values from the form
			String Username = request.getParameter("userid");
			String Password = request.getParameter("password");
			String Confirm_Password = request.getParameter("cpassword");
			
			 response.setContentType("text/html");
        // Get a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();

        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdatabase", "root", "damini94"); // <== Check!
            // database-URL(hostname, port, default database), username, password

            // Step 2: Allocate a Statement object within the Connection

           stmt = conn.createStatement();

            String sqlStr =  "INSERT INTO Registration(username,password,cpassword) " + "VALUES (?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sqlStr);
			pst.setString(1,Username);
			pst.setString(2,Password);
			pst.setString(3,Confirm_Password);
			pst.execute();
	







		System.out.println("Document inserted successfully");
				
						
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Welcome "+ Username + "</h1>");
			out.println("<br/>");
			out.println("<a href='welcomeLogin.html'> Click To Proceed </a>");
			out.println("</body>");
			out.println("</html>");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
}
}

										