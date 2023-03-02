package com.example.Cachingpracticeusingcaffeinecache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CachingPracticeUsingCaffeineCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingPracticeUsingCaffeineCacheApplication.class, args);
	}

}
