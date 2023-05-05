package com.kpi.multithreading.client_server;

import com.kpi.multithreading.client_server.service.MatrixService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.kpi.multithreading.client_server.GenerateMatrixTest.dstDirectory;

@SpringBootTest
class Lab8ApplicationTests {

	@Autowired
	MatrixService ms;

	@Test
	void contextLoads() {
	}

	@Test
	void generateTest() throws IOException {
		ms.calculateMatrix(6, Files.readAllBytes(Paths.get(dstDirectory + "\\m1_500")),
				Files.readAllBytes(Paths.get(dstDirectory + "\\m2_500")));
	}

}
