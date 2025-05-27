package com.deep.payment.api;

import com.deep.payment.domain.Payment;
import com.deep.payment.repo.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class Api {

    private static final Logger LOG = LoggerFactory.getLogger(Api.class);

    @Autowired
    Repo repo;

    //get list of all payments
    @GetMapping
    public List<Payment> getAllPayments() {
        LOG.info("Getting all Payments");
        return repo.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable String id) {
        repo.deleteById(id);
    }

}
