package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "FrontCtl", urlPatterns = "*.do")		//*: Anything after login protect page
public class FrontCtl implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		
		session.setMaxInactiveInterval(30);			//30 = 30 second
		if(session.getAttribute("user") == null) {
			req.setAttribute("msg", "Your session is expired please login again!");
			
			RequestDispatcher rd = req.getRequestDispatcher("LoginView.jsp");
			rd.forward(request, response);
			return;
		}else {
			chain.doFilter(req, resp);		//redirect to the login page
		}
	}
	@Override
	public void destroy() {
	}

}
