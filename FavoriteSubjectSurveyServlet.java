/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry ;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author INT303
 */
public class FavoriteSubjectSurveyServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("studentId");
        String name = request.getParameter("name");
        String email = request.getParameter("emailAddress");

        String[] subjects = request.getParameterValues("subjects");
        
        Map<String, String[]> paramMap = request.getParameterMap();

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FavoriteSubjectServeyServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table border=1><tr><td>Parameter Name</td><td>Parameter Value(s)</td></tr>");
            out.println("<tr><td>studentId</td><td>" + id + "</td></tr>");
            out.println("<tr><td>name</td><td>" + name + "</td></tr>");
            out.println("<tr><td>emailAddress</td><td>" + email + "</td></tr>");
            out.println("<tr><td>subjects</td>");
            boolean isFirstLine = true;
            for (String subject : subjects) {
                if (isFirstLine) {
                    out.println("<td>"+ subject+ "</td></tr>");
                    isFirstLine = false;
                } else {
                    out.println("<tr><td> </td><td>"+ subject+ "</td></tr>");
                }
            }
            out.println("</table>");
            Set<Map.Entry<String, String[]>> params = paramMap.entrySet() ;
            out.println("<hr>");
            out.print("Parameters using Map :: <br>");
            for (Map.Entry<String, String[]> entry : params) {
                out.println(entry.getKey() + " : ");
                String[] values = entry.getValue() ;
                for (String value : values) {
                    out.println(value + ",  ");
                }
                out.println("<br>") ;
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
