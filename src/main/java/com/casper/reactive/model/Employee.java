package com.casper.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Employee {
    @Id
    private String id;
    private String name;
    private Double salary;
}
