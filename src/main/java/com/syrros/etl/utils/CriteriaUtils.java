package com.syrros.etl.utils;

import com.syrros.etl.api.DefaultFilter;
import com.syrros.etl.api.Metric;
import com.syrros.etl.api.Query;
import com.syrros.etl.api.QueryResult;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CriteriaUtils {

    public static QueryResult query(EntityManager em, Query query) {
        Map<String, Object> paramaterMap = new HashMap<>();
        List<String> whereCause = new ArrayList<>();
        Pattern regex = Pattern.compile("\\((.*?)\\)");
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select ");
        queryBuilder.append(query.getMetrics().stream().map(m -> {
            String result = "";
            String ev = m.evaluate();
            String al = m.alias();
            if (m.evaluate().contains("(")) {
                Matcher regexMatcher = regex.matcher(ev);
                while (regexMatcher.find()) {
                    String t = regexMatcher.group(1);
                    ev = ev.replace(t, "t." + t);
                    result = ev;
                }
            }
            else {
                result = "t." + ev;
            }
            result = result + " as " + al;
            return result;
        }).collect(Collectors.joining(",")));
        queryBuilder.append(" from Etl t ");
        AtomicInteger i = new AtomicInteger();
        query.getFilters().stream().forEach(f -> {
            DefaultFilter df = ((DefaultFilter) f);
            StringBuilder whereBuilder = new StringBuilder();
            whereBuilder.append(" ").append("t.").append(df.getKey()).append(" ").append(df.getOperator()).append(" ").append(":").append(df.getKey() + i.toString());
            whereCause.add(whereBuilder.toString());
            paramaterMap.put(df.getKey() + i.toString(), df.getKey().equals("daily") ? LocalDate.parse(df.getValue().toString()) : df.getValue());
            i.getAndIncrement();
        });
        if (!whereCause.isEmpty())
            queryBuilder.append(" where " + String.join(" and ", whereCause));
        if (!query.getGroups().isEmpty()) {
            queryBuilder.append(" group by ").append(query.getGroups().stream().map(g -> "t." + g.evaluate()).collect(Collectors.joining(",")));
        }

        javax.persistence.Query jpaQuery = em.createQuery(queryBuilder.toString());

        for (String key : paramaterMap.keySet()) {
            jpaQuery.setParameter(key, paramaterMap.get(key));
        }
        return toQueryResult(query, jpaQuery.getResultList());
    }

    private static QueryResult toQueryResult(Query query, List<Object[]> result) {
        return QueryResult.builder()
                .aliases(query.getMetrics().stream().map(Metric::alias).collect(Collectors.toList()))
                .records(result.stream().map(Arrays::asList).collect(Collectors.toList()))
                .build();
    }

}
