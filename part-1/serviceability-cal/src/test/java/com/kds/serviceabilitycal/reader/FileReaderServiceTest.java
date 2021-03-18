package com.kds.serviceabilitycal.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kds.serviceabilitycal.model.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FileReaderServiceTest {

    private FileReaderService service;
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        service = new FileReaderService(objectMapper);
    }

    @Test
    void readApplication() throws IOException {
        File resource = new ClassPathResource("application.json").getFile();
        String absolutePath = resource.getAbsolutePath();
        Application application = service.readApplication(absolutePath);

        assertEquals(2, application.getIncomes().size());
        assertEquals(2, application.getExpenses().size());
    }
}