package com.deep.payment.repo;

import com.deep.payment.domain.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends MongoRepository<Payment, String> {
}
