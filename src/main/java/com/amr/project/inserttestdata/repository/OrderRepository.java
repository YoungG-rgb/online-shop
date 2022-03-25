package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.Order;
import com.amr.project.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_Id(Long id);

    @Query("select o from Order o left join o.items items " +
            "where o.user.id = :userId and o.status = :status and items.shop.id = :shopId")
    List<Order> findAllByUserAndStatusAndShopId(@Param("userId") Long userId, @Param("status") Status status,
                                                @Param("shopId") Long shopId);

    @Query("select o from Order o left join o.items items " +
            "where o.user.id = :userId and o.status = :status and items.id = :itemId")
    List<Order> findAllByUserAndStatusAndItemId(@Param("userId") Long userId, @Param("status") Status status,
                                                @Param("itemId") Long itemId);

}
