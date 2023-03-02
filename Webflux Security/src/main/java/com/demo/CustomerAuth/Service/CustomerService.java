package com.demo.CustomerAuth.Service;

import com.demo.CustomerAuth.Entity.Customer;
import com.demo.CustomerAuth.Exception.AccountNotFoundException;
import com.demo.CustomerAuth.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {


    @Autowired
    private CustomerRepository repo;

    public Flux<Customer> getAll() {
        return repo.findAll();
    }

    public Mono<Customer> getById(int id) {
        return repo.findById(id);
    }

    public Mono<Customer> save(Mono<Customer> customer) {
        return customer.flatMap(this.repo::save);
    }

    public Mono<String> loginAuth(int id, String password) throws InterruptedException, ExecutionException {
        try {
            Mono<Customer> Monocust = getById(id);
            Customer cust = Monocust.toFuture().get();
            if (cust.getPassword().equals(password))
                return Mono.just("login successful");
            return Mono.just("incorrect credentials");
        } catch (NullPointerException e) {
            throw new AccountNotFoundException("account not found please register")
                    ;
        }
    }

    public Mono<Void> delete(int id) {

        return repo.deleteById(id);
    }

    public Mono<Customer> update(int id, Customer customer) throws InterruptedException, ExecutionException {
        try {
            Mono<Customer> MonoObj = repo.findById(id);
            Customer cust = MonoObj.toFuture().get();
            cust.setId(id);
            cust.setPassword(customer.getPassword());
            cust.setUserName(customer.getUserName());
            Mono<Customer> savingObj = Mono.just(cust);
            return savingObj.flatMap(this.repo::save);

        } catch (NullPointerException e) {
            throw new AccountNotFoundException("account not found please register");
        }
    }


}