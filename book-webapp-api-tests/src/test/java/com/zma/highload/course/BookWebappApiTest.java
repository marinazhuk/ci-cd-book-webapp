package com.zma.highload.course;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookWebappApiTest {

    private final String url = "http://ec2-16-170-211-2.eu-north-1.compute.amazonaws.com:8080/books";

    @Test
    @Order(1)
    public void getBookNotFound() throws IOException {
        // Given
        final HttpUriRequest request = new HttpGet(url + "/1");
        // When

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client
                     .execute(request)) {
            // Then
            assertThat(response.getCode(), equalTo(HttpStatus.SC_NOT_FOUND));
        }
    }

    @Test
    @Order(2)
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
    }

    @Test
    @Order(3)
    public void getBookFound() throws IOException {
        // Given
        final HttpUriRequest request = new HttpGet(url + "/1");
        // When

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client
                     .execute(request)) {
            // Then
            assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    @Order(4)
    public void getAllBooks() throws IOException, ParseException {

        // Given
        final HttpUriRequest request = new HttpGet(url);
        // When

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client
                     .execute(request)) {
            // Then
            assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
            assertThat(EntityUtils.toString(response.getEntity(), "UTF-8"),
                    equalTo("[{\"id\":1,\"author\":\"John\",\"title\":\"title\",\"yearPublished\":2000}]"));
        }
    }

    @Test
    @Order(5)
    public void deleteBook() throws IOException {
        // Given
        final HttpUriRequest request = new HttpDelete(url + "/1");
        // When

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client
                     .execute(request)) {
            // Then
            assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
        }
    }
}
