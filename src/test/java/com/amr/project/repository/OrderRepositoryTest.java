package com.amr.project.repository;

import com.amr.project.inserttestdata.repository.OrderRepository;
import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.PaymentMethod;
import com.amr.project.model.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCreateOrder() {

        User user = testEntityManager.find(User.class, 3L);
        Address address1 = testEntityManager.find(Address.class, 1L);

        Order order = new Order();
        order.setUser(user);
        order.setBuyerName("Test");
        order.setBuyerPhone("89998887623");
        order.setShippingCost(0.0f);
        order.setAddress(address1);
        order.setDate(Calendar.getInstance());
        order.setStatus(Status.DELIVERED);
        order.setPaymentMethod(PaymentMethod.COD);
        order.setDeliverDate(new Date());

        Order savedOrder = orderRepository.save(order);
        assertThat(savedOrder.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetOrders() {
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSizeGreaterThan(0);
        orders.forEach(System.out::println);
    }

    @Test
    public void testUpdateOrder() {
        Order order = orderRepository.findById(1L).get();
        order.setBuyerPhone("89998887600");
        order.setStatus(Status.COMPLETE);
        order.setPaymentMethod(PaymentMethod.COD);
        Order updatedOrder = orderRepository.save(order);
        assertThat(updatedOrder.getStatus()).isEqualTo(Status.COMPLETE);
    }

    @Test
    public void testDeleteOrder() {
        orderRepository.deleteById(1L);
        Optional<Order> result = orderRepository.findById(1L);
        assertThat(result).isNotPresent();
    }
}

