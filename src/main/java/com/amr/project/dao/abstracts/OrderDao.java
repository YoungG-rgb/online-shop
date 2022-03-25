package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Order;

import java.util.List;

public interface OrderDao extends ReadWriteDao<Order, Long>{
    List<Order> getShopOrders(Long shopId);

}
