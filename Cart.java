/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author INT303
 */
public class Cart {
    Map<OrderDetailPK, OrderDetail> cart ;
    
    public boolean isEmpty() {
        return cart==null || cart.size()==0 ;
    }

    public List<OrderDetail> getOrders() {
        return new ArrayList(cart.values()) ;
    }
    
    public void addItem(OrderDetail orderdetail) {
        if(cart == null) {
            cart = new HashMap(32) ;
        }
        if (orderdetail.getQuantityordered() == 0) {
            cart.remove(orderdetail.getOrderDetailPK()) ;
            return ;
        }
        OrderDetail temp = cart.get(orderdetail.getOrderDetailPK()) ;
        if (temp == null) {
            cart.put(orderdetail.getOrderDetailPK(), orderdetail) ;
        } else {
            temp.setQuantityordered(temp.getQuantityordered() + orderdetail.getQuantityordered());
        }
    }

    public OrderDetail getItem(OrderDetailPK orderdetailPK) {
        return cart.get(orderdetailPK) ;
    }

    public int getSize() {
        if (cart == null) {
            return 0 ;
        }
        return cart.size() ;
    }

    public double getTotalPrice() {
        double sum = 0 ;
        Collection<OrderDetail> orderDetails = cart.values();
        for (OrderDetail orderDetail : orderDetails) {
            sum = orderDetail.getQuantityordered() * orderDetail.getPriceeach().doubleValue() + sum ;
        }
        return sum ;
    }

    public void remove(OrderDetailPK orderdetailPK) {
        cart.remove(orderdetailPK) ;
    }
    
}
