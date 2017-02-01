import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import java.util.*;


public class Catalog{
  

  public static Product getItem(String itemID) {
  XMLlist prList= new XMLlist("C:/apache-tomcat-7.0.34/bin/ProductCatalog.xml");
  List<Product> prlist123 = prList.getProducts();
  //List<Product> prlist123=(ArrayList<Product>)session.getAttribute("productlist");

  Product item;
    if (itemID == null) {
      return(null);
    }
    for(int i=0; i<prlist123.size(); i++) {
      item = prlist123.get(i);
      if (itemID.equals(item.getId())) {
        return(item);
      }
    }
    return(null);
  }
}
               





