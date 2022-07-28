package com.syrros.etl.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultFilter implements Filter {

    private String key;
    private String operator;
    private Object value;

    @Override
    public String evaluate() {
        if (value instanceof String) {
            return String.join(" ", key, operator, "'" + value + "'");
        }
        else {
            return String.join(" ", key, operator, value.toString());
        }
    }
}
