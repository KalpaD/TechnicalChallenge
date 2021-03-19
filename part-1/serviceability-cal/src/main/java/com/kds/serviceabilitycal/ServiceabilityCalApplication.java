package com.kds.serviceabilitycal;

import com.kds.serviceabilitycal.calculator.ServiceabilityCalculator;
import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.reader.FileReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class ServiceabilityCalApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceabilityCalApplication.class);

	private ServiceabilityCalculator serviceabilityCalculator;
	private FileReaderService fileReaderService;

	public static void main(String[] args) {
		SpringApplication.run(ServiceabilityCalApplication.class, args);
	}

	public ServiceabilityCalApplication(ServiceabilityCalculator serviceabilityCalculator,
										FileReaderService fileReaderService) {
		this.serviceabilityCalculator = serviceabilityCalculator;
		this.fileReaderService = fileReaderService;
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Serviceability Calculator Running");

		Scanner scanner = new Scanner(System.in);
		LOGGER.info("Enter the absolute path for the application.json file: ");

		String filePath = scanner.nextLine();

		Application application = fileReaderService.readApplication(filePath);
		Optional<BigDecimal> serviceability = serviceabilityCalculator.calculate(application);

		if (serviceability.isPresent()) {
			LOGGER.info("Serviceability for the given application : {}", serviceability);
		}
		else {
			LOGGER.info("Serviceability for the given application cannot be calculated," +
					" Please check the validity of the given loan application for non-empty" +
					"income and expenses list.");
		}
	}

}
