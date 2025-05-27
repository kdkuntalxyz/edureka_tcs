package com.deep.inventory.api;

import com.deep.inventory.domain.Inventory;
import com.deep.inventory.repo.Repo;
import com.deep.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventories")
public class Api {

    @Autowired
    InventoryService inventoryService;
    private static final Logger LOG = LoggerFactory.getLogger(Api.class);

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.saveInventory(inventory));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventory(@PathVariable String productId) {
        Optional<Inventory> inv = inventoryService.getByProductId(productId);
        return inv.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/check/{productId}/{qty}")
    public ResponseEntity<Boolean> isAvailable(@PathVariable String productId, @PathVariable int qty) {
        return ResponseEntity.ok(inventoryService.isInStock(productId, qty));
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
    }

}
