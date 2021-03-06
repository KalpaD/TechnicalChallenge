package com.kds.serviceabilitycal.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kds.serviceabilitycal.exception.ApplicationException;
import com.kds.serviceabilitycal.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileReaderService {

    private ObjectMapper objectMapper;

    @Autowired
    public FileReaderService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Application readApplication(String path) {
        try {
            String content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
            return objectMapper.readValue(content, Application.class);
        } catch (IOException e) {
            throw new ApplicationException("Invalid application : Error while reading application", e);
        }
    }
}
