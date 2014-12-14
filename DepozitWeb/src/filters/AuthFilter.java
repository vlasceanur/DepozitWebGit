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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/AuthFilter")
public class AuthFilter implements Filter {
	List<String> ignoredURL =  new LinkedList<String>();
	
    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rsp = (HttpServletResponse)response;
		
		
		String url = req.getRequestURL().toString();
		//if the url is not ignored check the login status
		if(!isIgnored(url))
		{
			//if user is not login redirect to login page
			if(!isLogedIn(req)){
				rsp.sendRedirect("login.html");
			}
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	
	private boolean isLogedIn(HttpServletRequest req) {
		if(req.getCookies()!=null)
		{
			for(Cookie c : req.getCookies())
			{
				if(c.getName().equals("user"))
					return true;
			}
		}
		
		return false;
	}

	private boolean isIgnored(String url)
	{
		for(String s: ignoredURL)
		{
			if (s.endsWith(url))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ignoredURL.add("login.html");
		ignoredURL.add("index.html");
		ignoredURL.add("LoginServlet");
	}

}
