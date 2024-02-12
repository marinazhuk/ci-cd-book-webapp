package com.zma.highload.course;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BookWebappApiTest {

    private final String url = "http://ec2-16-170-211-2.eu-north-1.compute.amazonaws.com:8080/books";

    @Test
    public void getBookNotFound() throws IOException {
        // Given
        final HttpUriRequest request = new HttpGet(url + "/1000");
        // When

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client
                     .execute(request)) {
            // Then
            assertThat(response.getCode(), equalTo(HttpStatus.SC_NOT_FOUND));
        }
    }

    @Test
    public void createBook() throws IOException {

        final String json =
                "{\"author\":\"John\", \"title\": \"title\", \"yearPublished\": \"2000\"}";
        final StringEntity entity = new StringEntity(json);

        // Given
        final HttpPost request = new HttpPost(url);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        // When
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {
            // Then
            assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
        }

        // Given
        final HttpUriRequest getRequest = new HttpGet(url + "/1");
        // When
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(getRequest)) {
            // Then
            assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
        }
    }
}
