package com.example.Cachingpracticeusingcaffeinecache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CaffeineCache implements CommandLineRunner {
    public static final Logger LOG= LoggerFactory.getLogger(CaffeineCache.class);
    @Autowired
    MockService mockService;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Starting the cache testing process");
        System.out.println(mockService.getString());
        System.out.println(mockService.getString());
        System.out.println(mockService.getString());
    }
}
