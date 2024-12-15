package pl.pjatk.zjazd4.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean processPayment(String userId, double amount) {
        System.out.println("Processing payment of " + amount + " for user " + userId);
        return true;
    }
}
