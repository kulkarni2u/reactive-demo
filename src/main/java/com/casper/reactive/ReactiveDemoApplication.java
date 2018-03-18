package com.casper.reactive;

import com.casper.reactive.model.Employee;
import com.casper.reactive.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveDemoApplication {

	@Bean
	CommandLineRunner employeeRunner(EmployeeRepository employeeRepository) {
		return args -> employeeRepository
                .deleteAll()
                .subscribe(null, null, () -> {
                    Stream.of(
                            new Employee(UUID.randomUUID().toString(), "Anirudh", 128000.00d),
                            new Employee(UUID.randomUUID().toString(), "Sravan", 138000.00),
                            new Employee(UUID.randomUUID().toString(), "Vishnu", 128000.00),
                            new Employee(UUID.randomUUID().toString(), "Aravid", 138000.00)
                    ).forEach(employee -> {
                        employeeRepository.save(employee).subscribe(System.out::println);
                    });
                });
	}

	public static void main(String[] args) {
		SpringApplication.run(ReactiveDemoApplication.class, args);
	}
}
