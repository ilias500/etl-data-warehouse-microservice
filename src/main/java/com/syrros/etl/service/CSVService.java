package com.syrros.etl.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syrros.etl.helper.CSVHelper;
import com.syrros.etl.model.Etl;
import com.syrros.etl.repository.EtlRepository;

@Service
@AllArgsConstructor
public class CSVService {

  private final EtlRepository repository;

  public void save(MultipartFile file) {
    try {
      List<Etl> etlList = CSVHelper.csvToEtlRows(file.getInputStream());
      repository.saveAll(etlList);
    }
    catch (Exception e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<Etl> etlRows = repository.findAll();
    ByteArrayInputStream in = CSVHelper.etlsToCSV(etlRows);
    return in;
  }

  public List<Etl> getAllEtlRows() {
    return repository.findAll();
  }

}
