import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AutoCompleteServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String prefix = request.getParameter("productName");
		if (prefix != null) {
			String products = AjaxUtility.readData(prefix);
			if (!products.isEmpty()) {
				response.setContentType("text/xml");
				response.getWriter().append("<products>").append(products).append("</products>");
				System.out.println(products);
			}
		}
	}
}
