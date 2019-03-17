/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import sit.int303.demo.model.Cart;
import sit.int303.demo.model.OrderDetail;
import sit.int303.demo.model.Product;
import sit.int303.demo.model.controller.ProductJpaController;

/**
 *
 * @author INT303
 */
@WebServlet(name = "AddItemToCartServlet", urlPatterns = {"/AddItemToCart"})
public class AddItemToCartServlet extends HttpServlet {
    @PersistenceUnit(unitName = "DemoWebAppG1PU")
    EntityManagerFactory emf ;
    
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
        HttpSession session = request.getSession();
        if (session.getAttribute("shoppingCart") == null) {
            session.setAttribute("shoppingCart", new Cart());
        }
        
        String item = request.getParameter("item");
        Cart cart = (Cart) session.getAttribute("shoppingCart") ;
        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
        Product product = productJpaCtrl.findProduct(item) ;
        
        OrderDetail orderDetail = new OrderDetail(1, item);
        orderDetail.setProduct(product);
        orderDetail.setQuantityordered(1);
        orderDetail.setPriceeach(product.getMsrp()); // new code
        cart.addItem(orderDetail);
        
        getServletContext().getRequestDispatcher("/ProductList").forward(request, response);
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
