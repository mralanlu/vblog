package com.northbund.vblog.utils.mybatis;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class JsonCurrencyFormat extends JsonSerializer<Double> {

    // 注意 "0.00" 和 "#.00" 的区别
    private DecimalFormat format = new DecimalFormat("0.00");

    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // 使用 writeNumber() 而非 writeString
        jsonGenerator.writeNumber(format.format(value));
    }

    public static void main(String[] args) {
        double value = 35445.009439;
        System.out.println(new DecimalFormat("0.00").format(value));

    }
}
