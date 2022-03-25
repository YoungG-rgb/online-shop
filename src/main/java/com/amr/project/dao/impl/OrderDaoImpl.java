package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.OrderDao;
import com.amr.project.model.entity.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderDaoImpl extends ReadWriteDaoImpl<Order, Long> implements OrderDao {


    @Override
    public List<Order> getShopOrders(Long shopId) {
        return entityManager.createNativeQuery("SELECT o.id, o.date, o.item_cost, o.shipping_cost, o.status, " +
                        "o.subtotal, o.tax, o.total FROM orders o " +
                        "JOIN shop_orders ON(o.id=shop_orders.orders_id) " +
                        "JOIN shop s ON(s.id=:shopId)")
                .setParameter("shopId", shopId)
                .getResultList();
    }
}
