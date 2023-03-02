package com.demo.CustomerAuth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;


@Service
public class DataService implements CommandLineRunner {

    @Value("classpath:h2/init.sql")
    private Resource initsql;

    @Autowired
    private R2dbcEntityTemplate template;

    @Override
    public void run(String... args) throws Exception {

        String query = StreamUtils
                .copyToString(initsql.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(query);
        this.template
                .getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();

    }

}
