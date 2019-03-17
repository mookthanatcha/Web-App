/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sit.int303.demo.model.Orders;

/**
 *
 * @author INT303
 */
public class AutoOrderNumberServlet extends HttpServlet {

    @PersistenceUnit(unitName = "DemoWebAppG1PU")
    EntityManagerFactory emf;

    private static int LAST_ORDER_NUMBER;

    @Override
    public void init() throws ServletException {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT o FROM Orders o ORDER BY o.ordernumber DESC");
        q.setMaxResults(1);
        List<Orders> orderList = q.getResultList();
        if (orderList.size() > 0) {
            LAST_ORDER_NUMBER = orderList.get(0).getOrdernumber() ;
        }
    }

    public static synchronized int getNextOrderNumber() {
        return ++LAST_ORDER_NUMBER;
    }

}
