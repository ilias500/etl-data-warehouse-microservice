package com.syrros.etl.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultGroup implements Group{

    private String column;

    @Override
    public String evaluate() {
        return column;
    }
}
