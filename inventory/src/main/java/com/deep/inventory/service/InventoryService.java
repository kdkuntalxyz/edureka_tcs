package com.deep.inventory.service;

import com.deep.inventory.domain.Inventory;
import com.deep.inventory.repo.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    Repo inventoryRepository;

    private static final Logger LOG = LoggerFactory.getLogger(InventoryService.class);

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Optional<Inventory> getByProductId(String productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public boolean isInStock(String productId, int quantityRequested) {
        return inventoryRepository.findByProductId(productId)
                .map(inv -> inv.getQuantity() >= quantityRequested)
                .orElse(false);
    }

    public void deleteInventory(String id) {
        inventoryRepository.deleteById(id);
    }
}
