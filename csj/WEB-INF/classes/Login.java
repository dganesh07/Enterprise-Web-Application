import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;



import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;


public class Login extends HttpServlet {
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {*/

        String storeManagerId = "smanager";
        String storeManagerPassword = "abc"; 
        String salesmanId = "salesman";
        String salesmanPassword = "def"; 
        


        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String usertype = request.getParameter("usertype");

 if (usertype.equals("STOREMANAGER")) {
            if (userid.equals(storeManagerId) && password.equals(storeManagerPassword)) {
                response.sendRedirect("storemanager.html");
            } else {
                response.setContentType("text/html");
                //java.io.PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Login Result</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>" + "Login Failed!!" + "</h2>");
                out.println("</body>");
                out.println("</html>");
                out.close();
            }
        } else if (usertype.equals("SALESMAN")) {
            if (userid.equals(salesmanId) && password.equals(salesmanPassword)) {
                response.sendRedirect("salesman.html");
            } else {
                response.setContentType("text/html");
                //java.io.PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Login Result</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>" + "Login Failed!!" + "</h2>");
                out.println("</body>");
                out.println("</html>");
                out.close();
            }
        } 


        else if(usertype.equals("CUSTOMER")){
            try{

              
                Class.forName("com.mysql.jdbc.Driver");

     //creating connection with the database 
         Connection con=DriverManager.getConnection
                        ("jdbc:mysql://localhost:3306/testdatabase", "root", "damini94");
         PreparedStatement ps =con.prepareStatement
                             ("select * from registration where Username=? and password=?");
         ps.setString(1, userid);
         ps.setString(2, password);
         ResultSet rs =ps.executeQuery();



         


    HttpSession session = request.getSession();
      session.setAttribute("Username", userid); 



         if(true) {
                   showPage(response, "Login Success!" + "hello" + " " + userid);
               }
               else{
                showPage(response, "Login Failure! Username or password is incorrect in customer" +"*" + userid );
               }
         //st = rs.next();
            }catch(Exception e){
          e.printStackTrace();
      }
  }
}
      protected void showPage(HttpServletResponse response, String message)
    throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login Result</title>");  
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>" + message + "</h2>");
        out.println("<a href='welcomeLogin.html'> Click To Proceed </a>");
        out.println("</body>");
        out.println("</html>");
        out.close();
 
    }

    }


