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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author INT303
 */
public class FormCheckerFilter implements Filter {

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig ;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Enumeration<String> params =  config.getInitParameterNames() ;
        Map<String, String> emptyFields = new HashMap();
        boolean doChain = true ;
        while(params.hasMoreElements()) {
            String paramName = params.nextElement() ; 
            if ( ! paramName.equalsIgnoreCase("VIEW")) {
                if (request.getParameter(paramName).trim().length()==0) {
                    emptyFields.put(paramName, paramName) ;
                    doChain = false;
                }
            }
        }
        if (doChain) {
            chain.doFilter(request, response);
        } else {
            request.setAttribute("emptyFields", emptyFields);
            String form = "/"+ config.getInitParameter("VIEW") ;
            config.getServletContext().getRequestDispatcher(form).forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
