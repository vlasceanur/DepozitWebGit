package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pages.PAGE_URL;
import database.DataBaseManager;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
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
		
		response.getWriter().write("<html><head><title>Deleting user</title></head><body>");
		if(DataBaseManager.removeUser(request.getParameter("usr_name")))
		{
			response.getWriter().append("User was deleted. Click <a href=\"" + PAGE_URL.ADMIN_EDIT_USERS + "\">here</a> to go back to user list");
		}
		else
		{
			response.getWriter().append("User was not deleted. Click <a href=\"" + PAGE_URL.ADMIN_EDIT_USERS + "\">here</a> to go back to user list");
		}
		response.getWriter().write("</body></html>");
		
	}

}
