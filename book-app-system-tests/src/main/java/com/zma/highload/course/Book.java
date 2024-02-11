package com.zma.highload.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Book(int id, String author, String title, int yearPublished) {
}
