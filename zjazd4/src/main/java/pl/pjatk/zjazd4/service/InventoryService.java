package pl.pjatk.zjazd4.service;

import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    public boolean isProductAvailable(String productId) {
        System.out.println("Checking availability for product " + productId);
        return true;
    }
}
