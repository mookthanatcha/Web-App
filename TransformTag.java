/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.tag;

import java.io.StringWriter;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author INT303
 */
public class TransformTag extends SimpleTagSupport {

    private String mode;
    private String color = "gray";

    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {
            JspFragment f = getJspBody();
            StringWriter body = new StringWriter() ;
            f.invoke(body);
            String newBody ;
            if (mode.equalsIgnoreCase("upper")) {
                newBody = body.toString().toUpperCase() ;
            } else if (mode.equalsIgnoreCase("hide")) {
                newBody = "" ;
            } else {
                newBody = "!!! ERROR: invalid mode !!!" ;
            }
            out.print("<p style='color:" + color + "'>");
            out.print(newBody);
            out.print("</p>");
        } catch (java.io.IOException ex) {
            throw new JspException("Error in TransformTag tag", ex);
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
