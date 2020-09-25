package com.northbund.vblog.utils.mybatis;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonLocalDateFormat extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    public static void main(String[] args) {
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));;
    }
}