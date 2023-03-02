package com.example.Cachingpracticeusingcaffeinecache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"example"})
public class MockService {

    private static final Logger LOG= LoggerFactory.getLogger(MockService.class);

    @Cacheable
    public String getString(){
        LOG.info("SIMulating Expensive DB Fetch");
        return "Expensive Response";
    }
}
