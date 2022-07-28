package com.syrros.etl.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultMetric implements Metric {

    private String expression;
    private String alias;

    @Override
    public String evaluate() {
        return expression;
    }

    @Override
    public String alias() {
        return alias;
    }
}