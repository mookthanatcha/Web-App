/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import sit.int303.demo.model.Cart;
import sit.int303.demo.model.Customer;
import sit.int303.demo.model.OrderDetail;
import sit.int303.demo.model.Orders;
import sit.int303.demo.model.controller.OrderDetailJpaController;
import sit.int303.demo.model.controller.OrdersJpaController;
import sit.int303.demo.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author INT303
 */
public class ProcessOrderServlet extends HttpServlet {

    @PersistenceUnit(unitName = "DemoWebAppG1PU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

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
        if (session == null || session.getAttribute("shoppingCart") == null) {
            response.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        Cart cart = (Cart) session.getAttribute("shoppingCart");
        List<OrderDetail> orderDetailList = cart.getOrders();
        Customer c = (Customer) session.getAttribute("user");
        Date date = new Date();
        int orderNumber = AutoOrderNumberServlet.getNextOrderNumber();
        Orders order = new Orders(orderNumber, date, date, "In Process");
        OrdersJpaController ordersJpaController = new OrdersJpaController(utx, emf);
        OrderDetailJpaController orderDetailJpaController = new OrderDetailJpaController(utx, emf);
        String message = null ;
        try {
            order.setCustomernumber(c);
            ordersJpaController.create(order);   // Insert order to table orders
            short lineNumber = 1;
            for (OrderDetail orderDetail : orderDetailList) {   // Insert each order detail to table orderdetail
                orderDetail.setOrders(order);
                orderDetail.setOrderlinenumber(lineNumber++);
                orderDetailJpaController.create(orderDetail);
            }
            //order.setOrderDetailList(orderDetailList);
            //request.setAttribute("order", order);
            
            session.removeAttribute("shoppingCart");
            getServletContext().getRequestDispatcher("/GetOrderHistory").forward(request, response);
            return ;
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ProcessOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(ProcessOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        response.sendRedirect("https://www.google.co.th/search?q=java JPA"+ message);
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
