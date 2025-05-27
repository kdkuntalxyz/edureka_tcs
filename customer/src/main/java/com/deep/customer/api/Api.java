package com.deep.customer.api;

import com.deep.customer.domain.Customer;
import com.deep.customer.repo.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class Api {

    private static final Logger LOG = LoggerFactory.getLogger(Api.class);

    @Autowired
    Repo repo;

    //get list of all customers
    @GetMapping
    public List<Customer> getAllUsers() {
        LOG.info("Getting all students from database");
        return repo.findAll();
    }

    //get customer by id
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getSingleUserFromId(@PathVariable Integer id) {
        LOG.info("Getting single student from database with id {}", id);

        Optional<Customer> userFound = repo.findById(id);
        if (userFound.isEmpty()) {
            LOG.error("user id not found {}", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userFound.get());
    }

    //insert customer
    @PostMapping
    public ResponseEntity<Customer> addUser(@RequestBody Customer customer) {
        LOG.info("adding student to database");

        Customer savedCustomer = repo.save(customer);
        LOG.info("added user to database with id {}", savedCustomer.getId());
        return ResponseEntity.created(URI.create(customer.getName())).body(savedCustomer);
    }

    //update customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateUser(@PathVariable Integer id, @RequestBody Customer customer) {
        LOG.info("updating customer with id "+id);

        Optional<Customer> userFound = repo.findById(id);
        if (userFound.isEmpty()) {
            LOG.error("user id not found {}", id);
            return ResponseEntity.notFound().build();
        }

        userFound.get().setName(customer.getName());
        Customer savedCustomer = repo.save(userFound.get());
        LOG.info("updated customer to database with id {}", savedCustomer.getId());
        return ResponseEntity.ok(savedCustomer);
    }

    //delete customer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        LOG.info("deleting customer from database");
        Optional<Customer> customer = repo.findById(id);
        if(customer.isPresent()){
            repo.delete(customer.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
