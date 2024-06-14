package com.ihomeCabinet.crm.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihomeCabinet.crm.model.CustomerFile;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Converter
public class JsonConverter implements AttributeConverter<List<CustomerFile>, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CustomerFile> attribute) {
        if (attribute == null) {
            attribute = new ArrayList<>();
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CustomerFile> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, CustomerFile.class));
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
