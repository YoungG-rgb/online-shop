package com.amr.project.webapp.rest_controller;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
@DataSet(cleanBefore = true, value = "datasets/Orders.xml")
public class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin1_firstname", authorities = {"ADMIN"})
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("http://localhost:8888/api/order/{1}", 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getOrder() throws Exception {
        mockMvc.perform(get("/api/order/{id}", 1L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.buyerName").value("user4_username"))
                .andExpect(jsonPath("$.buyerPhone").value("user4_phone"))
                .andExpect(jsonPath("$.status").value("START"))
                .andExpect(jsonPath("$.total").value(333333.00))
                .andExpect(jsonPath("$.address.id").value(4))
                .andExpect(jsonPath("$.address.cityIndex").value("123456"))
                .andExpect(jsonPath("$.address.street").value("user4_street"))
                .andExpect(jsonPath("$.address.house").value("user4_house"))
                .andExpect(jsonPath("$.address.country.id").value(2))
                .andExpect(jsonPath("$.address.country.name").value("Ukraine"))
                .andExpect(jsonPath("$.shippingCost").value(0.0))
                .andExpect(jsonPath("$.subtotal").value(0.0))
                .andExpect(jsonPath("$.user.cartItems.[0].id").value(1))
                .andExpect(jsonPath("$.user.cartItems.[1].id").value(2))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "user1_firstname", authorities = {"USER"})
    void createOrder() throws Exception {

        this.mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"buyerName\": \"user1_firstname\"," +
                                "\"buyerPhone\": \"678856744567\"," +
                                "\"status\": \"PAID\"" +
                                "\"address\": {" +
                                "\"id\": 3}}"+
                                "\"total\": 333333.00" +
                                "\"user\": {" +
                                "\"id\": 2}}"+
                                "\"shippingCost\": 0.0" +
                                "\"subtotal\": 0.0" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyerName").value("user1_firstname"))
                .andExpect(jsonPath("$.buyerPhone").value("678856744567"))
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.address.id").value(3))
                .andExpect(jsonPath("$.total").value(333333.00))
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.user.cartItems.[0].id").value(1))
                .andExpect(jsonPath("$.user.cartItems.[1].id").value(2))
                .andExpect(jsonPath("$.shippingCost").value(0.0))
                .andExpect(jsonPath("$.subtotal").value(0.0))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "user1_firstname", authorities = {"USER"})
    public void testUpdateOrder() throws Exception {
        this.mockMvc.perform(put("/api/order", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"buyerName\": \"user1_firstname\"," +
                        "\"buyerPhone\": \"88888744567\"," +
                        "\"status\": \"SENT\"" +
                        "\"address\": {" +
                        "\"id\": 2}}"+
                        "\"total\": 10010081.00" +
                        "\"user\": {" +
                        "\"id\": 2}}"+
                        "\"shippingCost\": 100.0" +
                        "\"subtotal\": 0.0" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyerName").value("user1_firstname"))
                .andExpect(jsonPath("$.buyerPhone").value("88888744567"))
                .andExpect(jsonPath("$.status").value("SENT"))
                .andExpect(jsonPath("$.address.id").value(2))
                .andExpect(jsonPath("$.total").value(10010081.00))
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.user.cartItems.[0].id").value(10))
                .andExpect(jsonPath("$.user.cartItems.[1].id").value(20))
                .andExpect(jsonPath("$.shippingCost").value(100.0))
                .andExpect(jsonPath("$.subtotal").value(0.0))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
