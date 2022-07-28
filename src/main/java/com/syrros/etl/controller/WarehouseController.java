package com.syrros.etl.controller;

import com.syrros.etl.api.QueryRequest;
import com.syrros.etl.api.QueryResponse;
import com.syrros.etl.api.QueryResult;
import com.syrros.etl.exception.DataWarehouseException;
import com.syrros.etl.utils.CriteriaUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

@RestController
@AllArgsConstructor
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final EntityManager entityManager;

    @PostMapping("/queries")
    public QueryResponse query(@RequestBody QueryRequest request) {
        QueryResult result;
        try {
          result  = CriteriaUtils.query(entityManager, request.toQuery());
        }
        catch (Exception ex) {
            throw new DataWarehouseException("Unable to perform query", ex);
        }
        return QueryResponse.of(result);
    }

}
