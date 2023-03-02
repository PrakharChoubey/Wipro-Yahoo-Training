package com.demo.CustomerAuth.Controller;

import com.demo.CustomerAuth.Entity.Customer;
import com.demo.CustomerAuth.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@EnableCaching
public class CustomerController {


    @Autowired
    private CustomerService service;


    @GetMapping("/getAll")
    public Flux<Customer> getAll() {
        return service.getAll();
    }


    @GetMapping("getById/{id}")
    @Cacheable(value = "customerCache", key = "#id", condition = "#id<3")
    public Mono<Customer> getByID(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping("/register")
    public Mono<Customer> save(@RequestBody Mono<Customer> customer) {
        return service.save(customer);
    }

    @GetMapping("/loginViaPath/{id}/{password}")
    public Mono<String> loginViaPath(@PathVariable int id, @PathVariable String password) throws InterruptedException, ExecutionException {
        return service.loginAuth(id, password);
    }

    @DeleteMapping("/deleteById/{id}")
    @CacheEvict(value = "customerCache", key = "#id")
    public Mono<Void> deleteById(@PathVariable int id) {
        return service.delete(id);
    }

    @PutMapping("/update/{id}")
    @CachePut(value = "customerCache", key = "#id", condition = "#id<3")
    public Mono<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) throws InterruptedException, ExecutionException {
        return service.update(id, customer);

    }
}
