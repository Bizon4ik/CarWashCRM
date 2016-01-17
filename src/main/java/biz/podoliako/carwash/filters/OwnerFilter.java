package biz.podoliako.carwash.filters;


import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.pojo.UserExt;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class OwnerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        UserExt userExt = (UserExt) session.getAttribute("CurrentCarWashUser");

        if (userExt.getRole() != Role.owner) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login");
            requestDispatcher.forward(req, response);
        }else {
            chain.doFilter(req, response);
        }


    }

    @Override
    public void destroy() {

    }
}
