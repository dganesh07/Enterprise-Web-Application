import java.util.*;

public class AjaxUtility {

	public static String readData(String prefix) {
		XMLlist prList = new XMLlist("ProductCatalog.xml");
		List<Product> products  = prList.getProducts();
		StringBuffer sb = new StringBuffer();

		for (Product p: products) {
			if(p.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
				sb.append("<product>");
				sb.append("<id>").append(p.getId()).append("</id>");
				sb.append("<name>").append(p.getName()).append("</name>");
				sb.append("</product>");
			}
		}
		return sb.toString();
	}
}
