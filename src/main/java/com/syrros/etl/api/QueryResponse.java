package com.syrros.etl.api;

import java.util.List;
import lombok.Data;
import lombok.Singular;

@Data
public class QueryResponse {

    @Singular private List<String> aliases;

    @Singular private List<List<Object>> records;

    public QueryResponse(List<String> aliases, List<List<Object>> records) {
        this.aliases = aliases;
        this.records = records;
    }

    public static QueryResponse of(QueryResult result) {
        return new QueryResponse(result.getAliases(), result.getRecords());
    }
}