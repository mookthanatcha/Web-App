/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author INT303
 */
public class AuthenticationFilter implements Filter {

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig ;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request ;
        HttpSession session = httpServletRequest.getSession() ;
        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else {
            String target = httpServletRequest.getRequestURI() ;
            target = target.substring(target.lastIndexOf("/")) ;
            config.getServletContext().getRequestDispatcher("/Login?target="+target).forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
