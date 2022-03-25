package com.amr.project.payingsystem;

import com.amr.project.webapp.paypalsettings.PayPalOrderResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 * Для прохождения теста необходимо воспользоваться sandbox-аккаунтом PayPal, реквизиты аккаунта генерируются по сессиям
 * Для получения тестовых аккаунтов необходимо зарегистрировать реальный аккаунт:
 * https://developer.paypal.com/developer/accounts
 * По ссылке ниже инструкция для осуществления виртуального заказа:
 * https://developer.paypal.com/docs/api/orders/v2/
 */

public class PayPalApiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayPalApiTest.class);

    private static final String BASE_URL = "https://api.sandbox.paypal.com";
    private static final String GET_ORDER_API = "/v2/checkout/orders/";
    private static final String CLIENT_ID = "sb-ligdr8443334@personal.example.com"; // Email ID
    private static final String CLIENT_SECRET = ":Pm?yz5y"; // System Generated Password

    @Test
    public void testGetOrderDetails() {
        String orderId = ""; // 17-значный id заказа
        String requestURL = BASE_URL + GET_ORDER_API + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Accept-Language", "ru_RU");
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PayPalOrderResponse> response = restTemplate.exchange(
                requestURL, HttpMethod.GET, request, PayPalOrderResponse.class);
        PayPalOrderResponse orderResponse = response.getBody();

        assertNotNull(orderResponse);
        LOGGER.info("Order ID: " + orderResponse.getId());
        LOGGER.info("Validated: " + orderResponse.validate(orderId));
    }
}
