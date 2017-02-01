import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.lang.StringBuffer;

public class autocomplete extends HttpServlet {
	 ServletContext context;
	 ComposerData compData = new ComposerData();
	 HashMap composers = compData.getComposers();



	/*@override*/
	public void init(ServletConfig config) throws ServletException {
		this.context = config.getServletContext();
	}


	/*@override*/
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String targetId = request.getParameter("searchId");
		String action = request.getParameter("action");


		StringBuffer sb = new StringBuffer();
		if (targetId != null) {
			targetId = targetId.trim().toLowerCase();

		} else {
			boolean namesAdded = false;
			if (action.equals("complete")) {
				if (!targetId.equals("")) {
					Iterator it = composers.keySet().iterator();
					while (it.hasNext()) {
						String id = (String)it.next();
						Composer composer = (Composer)composers.get(id);
						if (composer.getproductName().toLowerCase().startsWith(targetId)) {
							sb.append("<composer>");
							sb.append("<id>" + composer.getId() + "<id>");
							sb.append("<productName>" + composer.getproductName() + "<productName>");
							sb.append("</composer>");
							namesAdded = true;


						}
					}

				}
				if (namesAdded = true) {
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("<composers>" + sb.toString() + "</composers>");
				} else {
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				}
			}
			if(action.equals("lookup")){
				if((targetId!=null)&&composers.containsKey(targetId.trim())){
					request.setAttribute("composer",composers.get(targetId));
					context.getRequestDispatcher("/comdisplay").forward(request,response);
				}
			}
		}
	}
}
