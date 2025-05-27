package com.deep.order.repo;

import com.deep.order.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Repo extends MongoRepository<Order, String> {
}
