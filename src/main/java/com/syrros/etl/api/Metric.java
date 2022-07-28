package com.syrros.etl.api;

public interface Metric extends Expression {

    String alias();

    static Metric of(String expression) {
        return of(expression, expression);
    }

    static Metric of(String expression, String alias) {
        return new DefaultMetric(expression, alias);
    }
}