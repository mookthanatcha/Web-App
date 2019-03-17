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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author INT303
 */
public class TrailerFilter implements Filter {

    private FilterConfig config;
    private String msg;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        this.msg = filterConfig.getInitParameter("msg");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StringResponse tmpResponse = new StringResponse((HttpServletResponse) response);

        chain.doFilter(request, tmpResponse);

        PrintWriter out = response.getWriter();
        String tmpHtml = tmpResponse.toString();
        int index = tmpHtml.lastIndexOf("</div>");
        if (index > 0) {
            StringBuilder finalString = new StringBuilder(tmpHtml.substring(0, index));
            finalString.append("<div class='row'>");
            finalString.append("<div class='col-sm-1 col-lg-1'></div>");
            finalString.append("<div class='col-sm-8 col-lg-10'>");
            finalString.append("<hr>\n");
            finalString.append("<p style='font-size: xx-small'>\n");
            finalString.append(msg);
            finalString.append("\n</p>\n");
            finalString.append("</div>\n");
            finalString.append("</div>\n");
            finalString.append("</body>\n");
            finalString.append("</html>");
            out.write(finalString.toString());
        } else {
            out.write(tmpHtml);
        }
        out.close();
    }

    @Override
    public void destroy() {
    }
}

class StringResponse extends HttpServletResponseWrapper {

    private StringWriter responseBuffer;

    public StringResponse(HttpServletResponse response) {
        super(response);
        responseBuffer = new StringWriter();
    }

    @Override
    public String toString() {
        return responseBuffer.toString();
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(responseBuffer);
    }
}
