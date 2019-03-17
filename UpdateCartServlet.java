/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sit.int303.demo.model.Cart;
import sit.int303.demo.model.OrderDetail;
import sit.int303.demo.model.OrderDetailPK;

/**
 *
 * @author INT303
 */
public class UpdateCartServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("shoppingCart") != null) {
            Cart cart = (Cart) session.getAttribute("shoppingCart");

            String[] deleteItems = request.getParameterValues("deleteItems");
            if (deleteItems != null) {
                for (String item : deleteItems) {
                    OrderDetailPK orderdetailPK = new OrderDetailPK(1, item);
                    cart.remove(orderdetailPK);
                }
            }

            Enumeration<String> params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String productCode = params.nextElement();
                if (productCode.equalsIgnoreCase("deleteItems")) {
                    continue;
                }
                int value = Integer.parseInt(request.getParameter(productCode));
                // System.out.printf("%s  %d\n", productCode, value);
                OrderDetailPK orderdetailPK = new OrderDetailPK(1, productCode);
                OrderDetail odt = cart.getItem(orderdetailPK);
                if (odt != null) {    // add
                    if (value == 0) {
                        cart.remove(orderdetailPK);
                    } else {
                        odt.setQuantityordered(value);
                    }
                }
            }
            getServletContext().getRequestDispatcher("/ViewCart").forward(request, response);
            return;
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
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
