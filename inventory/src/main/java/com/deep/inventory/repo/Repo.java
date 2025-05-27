package com.deep.inventory.repo;

import com.deep.inventory.domain.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Repo extends MongoRepository<Inventory, String> {
    Optional<Inventory> findByProductId(String productId);
}
