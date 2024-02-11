package com.zma.highload.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BookAppSystemTestsApplication {
    private static final Logger log = LoggerFactory.getLogger(BookAppSystemTestsApplication.class);
    @Value("${url:http://localhost:8080/books}")
    private String url;

    public static void main(String[] args) {
        SpringApplication.run(BookAppSystemTestsApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {

            Book request = new Book(1, "Autor", "Title", 2000);

            restTemplate.postForObject(url, request, Book.class);

            Book book = restTemplate.getForObject(url + "/1", Book.class);

            log.info(book.toString());
        };
    }
}
