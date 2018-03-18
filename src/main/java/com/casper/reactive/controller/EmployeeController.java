package com.casper.reactive.controller;

import com.casper.reactive.model.Employee;
import com.casper.reactive.model.EmployeeEvents;
import com.casper.reactive.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;
    public EmployeeController(@Autowired EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public Flux<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Employee> getEmployee(@PathVariable("id") String employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvents> getEvents(@PathVariable("id") String employeeId) {
        return employeeRepository
                .findById(employeeId)
                .flatMapMany((Employee employee) -> {
                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

                    Flux<EmployeeEvents> eventsFlux = Flux.fromStream(
                            Stream.generate(() -> new EmployeeEvents(employee, new Date()))
                    );

                    return Flux.zip(interval, eventsFlux)
                            .map(Tuple2::getT2);
                });
    }


}
