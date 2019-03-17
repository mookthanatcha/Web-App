/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import sit.int303.demo.model.Customer;
import sit.int303.demo.model.Orders;
import sit.int303.demo.model.controller.CustomerJpaController;

/**
 *
 * @author Khaitong
 */
public class GetOrderHistoryServlet extends HttpServlet {
    @PersistenceUnit(unitName = "DemoWebAppG1PU")
    EntityManagerFactory emf ;
    
    @Resource
    UserTransaction utx ;
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
        Customer c = (Customer) request.getSession(false).getAttribute("user");
        CustomerJpaController customerJpaController = new CustomerJpaController(utx, emf);
        c = customerJpaController.findCustomer(c.getCustomernumber());  // refresh customer 
        List<Orders> historyOrders = c.getOrdersList();
        Collections.sort(historyOrders, new Comparator<Orders>() {
            public int compare(Orders a, Orders b) {
                return b.getOrdernumber() - a.getOrdernumber();
            }
        });
        if (historyOrders != null) {
            request.setAttribute("historyOrders", historyOrders);
            request.setAttribute("flag", "founded");
        } else {
            request.setAttribute("flag", "notfoun");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/local-jsp/OrdersHistory.jsp").forward(request, response);
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
