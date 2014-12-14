package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DataBaseManager;

/**
 * Servlet implementation class CreateRegularUserServlet
 */
@WebServlet("/CreateRegularUserServlet")
public class CreateRegularUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateRegularUserServlet() {
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
		
		String username = request.getParameter("usr");
		String password1 = request.getParameter("pwd1");
		String password2 = request.getParameter("pwd2");
		
		
		if(password1.equals(password2) == true)
		{
			if(!DataBaseManager.userExists(username))
			{
				if(DataBaseManager.createUser(username, password1))
				{
					response.sendRedirect("home.jsp");
					return;
				}
			}
		}		
		response.sendRedirect("create.html");
	}

}
