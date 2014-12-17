package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pages.PAGE_URL;
import users.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		User u = new User(request.getParameter("usr"), request.getParameter("pwd"));
		HttpSession s = request.getSession();
		if(s.getAttribute("user") != null)
		{
			response.sendRedirect(PAGE_URL.HOME_PAGE);
			return;
		}
		try {
			if(u.verify() == true)
			{
				
				s.setAttribute("user", u);
				response.sendRedirect(PAGE_URL.HOME_PAGE);
				return;
			}
			else
			{				
				response.sendRedirect(PAGE_URL.LOG_IN_HTML);
				return;
			}
		} catch (SQLException e) {
			response.sendRedirect(PAGE_URL.LOG_IN_HTML);
			return;
		}
	}

}
