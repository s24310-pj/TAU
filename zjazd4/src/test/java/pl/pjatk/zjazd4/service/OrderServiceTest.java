package pl.pjatk.zjazd4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private PaymentService paymentService;
    private InventoryService inventoryService;
    private NotificationService notificationService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        paymentService = mock(PaymentService.class);
        inventoryService = mock(InventoryService.class);
        notificationService = mock(NotificationService.class);
        orderService = new OrderService(paymentService, inventoryService, notificationService);
    }

    @Test
    void testOrderPlacedSuccessfully() {
        // Mocking
        when(inventoryService.isProductAvailable("product1")).thenReturn(true);
        when(paymentService.processPayment("user1", 100.0)).thenReturn(true);

        // Execution
        String result = orderService.placeOrder("user1", "product1", 100.0);

        // Verification
        assertEquals("Order successful", result);
        verify(notificationService, times(1)).sendNotification("user1", "Order placed successfully");
    }

    @Test
    void testProductNotAvailable() {
        // Mocking
        when(inventoryService.isProductAvailable("product1")).thenReturn(false);

        // Execution
        String result = orderService.placeOrder("user1", "product1", 100.0);

        // Verification
        assertEquals("Product not available", result);
        verifyNoInteractions(paymentService);
        verifyNoInteractions(notificationService);
    }

    @Test
    void testPaymentFailed() {
        // Mocking
        when(inventoryService.isProductAvailable("product1")).thenReturn(true);
        when(paymentService.processPayment("user1", 100.0)).thenReturn(false);

        // Execution
        String result = orderService.placeOrder("user1", "product1", 100.0);

        // Verification
        assertEquals("Payment failed", result);
        verify(notificationService, never()).sendNotification(anyString(), anyString());
    }

    @Test
    void testPaymentServiceThrowsException() {
        // Mocking
        when(inventoryService.isProductAvailable("product1")).thenReturn(true);
        when(paymentService.processPayment("user1", 100.0)).thenThrow(new RuntimeException("Payment system error"));

        // Execution
        String result = orderService.placeOrder("user1", "product1", 100.0);

        // Verification
        assertEquals("Payment error: Payment system error", result);
        verify(notificationService, never()).sendNotification(anyString(), anyString());
    }
}
