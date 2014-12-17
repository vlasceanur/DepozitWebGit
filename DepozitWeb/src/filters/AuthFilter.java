package filters;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pages.PAGE_URL;
import users.User;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/AuthFilter")
public class AuthFilter implements Filter {
	List<String> ignoredURL = new LinkedList<String>();

	/**
	 * Default constructor.
	 */
	public AuthFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rsp = (HttpServletResponse) response;

		String url = req.getRequestURL().toString();
		// if the url is not ignored check the login status
		if (!isIgnored(url)) {
			// if user is not logged in, redirect to login page
			if (!isLogedIn(req)) {
				rsp.sendRedirect(PAGE_URL.LOG_IN_HTML);
			}
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	private boolean isLogedIn(HttpServletRequest req) {
		HttpSession ses = req.getSession();
		if (ses == null)
			return false;
		User u = (User) ses.getAttribute("user");
		if (u == null)
			return false;
		return u.isVerified();
	}

	private boolean isIgnored(String url) {
		
		
		System.out.print("is ignored? - " + url + "    ");
		for (String s : ignoredURL) {
			if (url.endsWith(s)) {
				System.out.println("true");
				return true;
			}
		}
		System.out.println(false);
		return false;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		ignoredURL.add(PAGE_URL.LOG_IN_HTML);
		ignoredURL.add(PAGE_URL.LOG_IN_SERVLET);
		ignoredURL.add(PAGE_URL.LOG_IN_SERVLET);
	}

}
