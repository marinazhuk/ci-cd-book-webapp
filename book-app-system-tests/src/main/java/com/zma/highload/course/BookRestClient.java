package com.zma.highload.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookRestClient {
    private final RestTemplate restTemplate;

    public BookRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createBook(String url, Book request) {
        restTemplate.postForObject(url, request, Book.class);
    }
}
