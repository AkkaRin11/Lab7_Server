package org.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
public class Response implements Serializable {
    private StatusCode statusCode;
    private String message;
}
