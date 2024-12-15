package pl.pjatk.zjazd4.service;

import org.springframework.stereotype.Service;
@Service
public class OrderService {

    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;

    public OrderService(PaymentService paymentService, InventoryService inventoryService, NotificationService notificationService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.notificationService = notificationService;
    }

    public String placeOrder(String userId, String productId, double amount) {
        if (!inventoryService.isProductAvailable(productId)) {
            return "Product not available";
        }

        try {
            boolean paymentSuccess = paymentService.processPayment(userId, amount);
            if (!paymentSuccess) {
                return "Payment failed";
            }
        } catch (Exception e) {
            return "Payment error: " + e.getMessage();
        }

        notificationService.sendNotification(userId, "Order placed successfully");

        return "Order successful";
    }
}
