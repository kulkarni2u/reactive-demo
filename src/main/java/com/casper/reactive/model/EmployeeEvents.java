package com.casper.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class EmployeeEvents {
    private Employee employee;
    private Date date;
}
