import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.sql.*;



public class OrderPage extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
								doGet(request,response);
								}
	
  public void doGet(HttpServletRequest request,HttpServletResponse response)
      throws ServletException, IOException {
		  PrintWriter out = response.getWriter();
		  String htmlString="";
    HttpSession session = request.getSession();
	//htmlString = Utilities.printHtml("Header.html");
if (session.getAttribute("username")!=null){
String username = session.getAttribute("username").toString();
username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
htmlString = htmlString
+ "<li><a>Hello, "+username+"</a></li>"
+ "<li><a href='Account'>Account</a></li>"
+ "<li><a href=LogoutServlet>Logout</a></li>";
}
else{
/*htmlString = htmlString + "<li><a href='http://localhost/csj/Login'>Login</a></li>";
htmlString = htmlString + "<li><a href='http://localhost/csj/'>SignUp</a></li>";*/
}
htmlString = htmlString
+ "</ul></div></span></div></td>";

//htmlString += Utilities.printHtml("UpperNav.html");
    ShoppingCart cart;
    //synchronized(session) {
      cart = (ShoppingCart)session.getAttribute("shoppingCart");
      if (cart == null) {
        cart = new ShoppingCart();
        session.setAttribute("shoppingCart", cart);
      }
      String itemID = request.getParameter("productid");
      if (itemID != null) {
        String numItemsString =
          request.getParameter("numItems");
        if (numItemsString == null) {
          //cart.addItem(itemID,session);
           cart.addItem(itemID);
        } else {
          int numItems;
          try {
            numItems = Integer.parseInt(numItemsString);
          } catch(NumberFormatException nfe) {
            numItems = 1;
          }
          //cart.setNumOrdered(itemID, numItems,session);
          cart.setNumOrdered(itemID, numItems);
        }
      }
   // }
    response.setContentType("text/html");
    
    String title = "Status of Your Order";
    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
        //synchronized(session) {
      List itemsOrdered = cart.getItemsOrdered();
      if (itemsOrdered.size() == 0) {
        htmlString+="<H2><I>No items in your cart...</I></H2>";
      } else {
        
        htmlString+=
          "<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
           "<TR BGCOLOR=\"#FFAD00\">\n" +
           "  <TH>Item ID<TH>Name\n" +
           "  <TH>Unit Cost<TH>Number<TH>Total Cost";
        ItemOrder order;
        NumberFormat formatter =
          NumberFormat.getCurrencyInstance();

        for(int i=0; i<itemsOrdered.size(); i++) {
          order = (ItemOrder)itemsOrdered.get(i);
          htmlString+=
            "<TR>\n" +
             "  <TD>" + order.getItemID() + "\n" +
             "  </TD><TD>" + order.getItemName() + "\n" +
             "  </TD><TD>" +
             formatter.format(order.getUnitCost()) + "\n" +
             "  </TD><TD>" +
             "<FORM>\n" +  // Submit to current URL
             "<INPUT TYPE=\"HIDDEN\" NAME=\"itemID\"\n" +
             "       VALUE=\"" + order.getItemID() + "\">\n" +
             "<INPUT TYPE=\"TEXT\" NAME=\"numItems\"\n" +
             "       SIZE=3 VALUE=\"" + 
             order.getNumItems() + "\">\n" +
             "<SMALL>\n" +
             "</SMALL>\n" +
             "</FORM>\n" +
             "  </TD><TD>" +
             formatter.format(order.getTotalCost())+"</TD></TR>";


try{


Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdatabase", "root", "damini94");
             stmt = conn.createStatement();

             String sqlStr =  "INSERT INTO orderdetails(ItemID,ItemName,UnitCost) " + "VALUES (?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sqlStr);
			pst.setString(1,order.getItemID());
			pst.setString(2,order.getItemName());
			pst.setInt(3,order.getUnitCost());

			pst.execute();

}catch(Exception e)
{
	System.out.println(e);
}





        }
        String checkoutURL =
          response.encodeURL("OrderForm.html");
        // "Proceed to Checkout" button below table
        htmlString+=
          "</TABLE>\n" +
           "<FORM ACTION=\"" + checkoutURL + "\">\n" +
           "<BIG><CENTER>\n" +
           "<INPUT TYPE=\"SUBMIT\"\n" +
           "       VALUE=\"Proceed to Checkout\">\n" +
           "</CENTER></BIG></FORM>";
      }
     // htmlString += Utilities.printHtml("LeftNav.html");
//htmlString += Utilities.printHtml("Footer.html");


out.println(htmlString);
    //}
  }
}
