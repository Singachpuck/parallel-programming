package com.kpi.multithreading.client_server;

import com.kpi.multithreading.matrix.service.MultiplyMatrix;
import com.kpi.multithreading.matrix.service.StringMultiplyMatrix;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
public class Lab8Application {

	@Bean
	public Map<Integer, MultiplyMatrix> matrixMultiplies() {
		return Map.of(
				2, new StringMultiplyMatrix(2),
				4, new StringMultiplyMatrix(4),
				6, new StringMultiplyMatrix(6),
				8, new StringMultiplyMatrix(8),
				10, new StringMultiplyMatrix(10),
				12, new StringMultiplyMatrix(12),
				14, new StringMultiplyMatrix(14),
				20, new StringMultiplyMatrix(20)
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(Lab8Application.class, args);
	}

}
