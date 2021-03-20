package com.kds.serviceabilitycal;

import com.kds.serviceabilitycal.calculator.ServiceabilityCalculator;
import com.kds.serviceabilitycal.exception.ApplicationException;
import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.reader.FileReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
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
	public void run(String... args) {
		LOGGER.info("Serviceability Calculator Running");

		String filePath;
		if (args.length < 1) {
			LOGGER.info("Enter the absolute path for the application.json file: ");
			Scanner scanner = new Scanner(System.in);
			filePath = scanner.nextLine();
		}
		else {
			filePath = args[0];
		}

		try {
			Application application = fileReaderService.readApplication(filePath);
			BigDecimal serviceability = serviceabilityCalculator.calculate(application);
			LOGGER.info("Serviceability for the given application : {}", serviceability);
		} catch (ApplicationException e) {
			LOGGER.error("Error : ", e);
		}
	}

}
