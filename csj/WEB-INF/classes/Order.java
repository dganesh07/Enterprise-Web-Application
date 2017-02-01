import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;


public class Order extends HttpServlet {
    long diffDays = 0;

    private static final long serialVersionUID = 1L;
    public HashMap<String, ArrayList<String>> users = new HashMap<String, ArrayList<String>>();


    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String searchParameter = request.getParameter("orderNo");
            PrintWriter out = response.getWriter();

            out.println("<HTML><HEAD><TITLE>Phonebook</TITLE></HEAD>");
            out.println("<BODY>");


            Class.forName("com.mysql.jdbc.Driver");

            //creating connection with the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase", "root", "damini94");
            PreparedStatement ps = con.prepareStatement("select * from CustomerOrders where randstring=?");
            ps.setString(1, searchParameter);

            ResultSet rs = ps.executeQuery();
            /*if(rs.has)*/
            /*MySQLDataStoreUtilities obg = new MySQLDataStoreUtilities();
            ResultSet rs = obg.selectorder(searchParameter);*/

            //ResultSetMetaData rsmd = rs.getMetaData();
            out.println("<TABLE style='border:  1px; padding: 10px'>\n");

            ResultSetMetaData rsmd = rs.getMetaData();

            int numcols = rsmd.getColumnCount();

            // Title the table with the result set's column labels
            out.println("<TR style='padding: 20px'>");
            for (int i = 1; i <= numcols; i++)
                out.println("<TH>" + rsmd.getColumnLabel(i));
            out.println("</TR>\n");
            while (rs.next()) {
                out.println("<TR style='padding: 20px'>");  // start a new row
                for (int i = 1; i <= numcols; i++) {
                    out.println("<TD style='padding: 20px'>");  // start a new data element
                    Object obj = rs.getObject(i);
                    if (obj != null) {
                        out.println(obj.toString());

                    } else
                        out.println("&nbsp;");
                }
                out.println("</TR>\n");

            }

            // End the table
            out.println("</TABLE>\n");

            String htmlString = "<a href='welcome.html'>Home</a>";
            htmlString +="<a href='cancel.html' style='padding-left: 20px;'>Cancel Order</a>";
            out.println(htmlString);
            out.println("</BODY></HTML>");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

