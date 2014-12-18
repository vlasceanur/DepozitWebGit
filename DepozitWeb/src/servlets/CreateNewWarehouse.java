package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DataBaseManager;

/**
 * Servlet implementation class CreateNewWarehouse
 */
@WebServlet("/CreateNewWarehouse")
public class CreateNewWarehouse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewWarehouse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("manager_id") == null)
		{
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
			return;
		}
		
		
		PrintWriter pw = response.getWriter();
		Enumeration<String> nms = request.getParameterNames();
		while(nms.hasMoreElements())
		{
			String s = nms.nextElement(); 
			pw.write(s + "  " + request.getParameter(s));
			pw.write("<br/>");
		}
		
		if(DataBaseManager.addWarehouse(request.getParameter("name"), 
				request.getParameter("location"),
				Double.parseDouble(request.getParameter("capital")), 
				Float.parseFloat(request.getParameter("volume")), 
				Integer.parseInt(request.getParameter("manager_id"))))
		{
			pw.write("<br/>Warehouse created!");
		}
		else
		{
			pw.write("<br>Warehouse not created!");
		}
		
	}

}
