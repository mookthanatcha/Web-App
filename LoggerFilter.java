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
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author INT303
 */
public class LoggerFilter implements Filter {

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig ;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    long before = System.currentTimeMillis() ;
    
    chain.doFilter(request, response);
    
    long duration = System.currentTimeMillis() - before ;
    
    String message = String.format("%s : Servlet duration  %d miliSecond(s) URI: %s", 
            new Date(), duration, ((HttpServletRequest) request).getRequestURI())  ;
    
    config.getServletContext().log(message);
    
    }

    @Override
    public void destroy() {
    }
}