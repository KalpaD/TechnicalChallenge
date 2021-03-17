package com.kds.serviceabilitycal.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kds.serviceabilitycal.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
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

    public Application readApplication(String path) throws IOException {
        String content = readFile(path, StandardCharsets.UTF_8);
        return objectMapper.readValue(content, Application.class);
    }

    private String readFile(String path, Charset encoding) throws IOException {
        return Files.readString(Paths.get(path), encoding);
    }
}
