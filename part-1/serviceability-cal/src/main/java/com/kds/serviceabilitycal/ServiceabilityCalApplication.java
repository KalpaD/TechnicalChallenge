package com.kds.serviceabilitycal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kds.serviceabilitycal.calculator.ServiceabilityCalculator;
import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.reader.FileReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

@SpringBootApplication
public class ServiceabilityCalApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceabilityCalApplication.class);

	private ServiceabilityCalculator serviceabilityCalculator;
	private FileReaderService fileReaderService;
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(ServiceabilityCalApplication.class, args);
	}

	public ServiceabilityCalApplication(ServiceabilityCalculator serviceabilityCalculator,
										FileReaderService fileReaderService,
										ObjectMapper objectMapper) {
		this.serviceabilityCalculator = serviceabilityCalculator;
		this.fileReaderService = fileReaderService;
		this.objectMapper = objectMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Serviceability Calculator Running");

		Scanner scanner = new Scanner(System.in);
		LOGGER.info("Enter the absolute path for the application.json file: ");

		String filePath = scanner.nextLine();

		Application application = fileReaderService.readApplication(filePath);
		BigDecimal serviceability = serviceabilityCalculator.calculate(application);

		LOGGER.info("Serviceability for the given application : {}", serviceability);
	}

}
