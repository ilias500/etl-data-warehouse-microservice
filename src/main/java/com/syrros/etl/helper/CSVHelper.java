package com.syrros.etl.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.syrros.etl.model.Etl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
  public static String TYPE = "text/csv";
  static String[] HEADERS = { "Datasource", "Campaign", "Daily", "Clicks", "Impressions" };

  private static final String PATTERN_FORMAT = "MM/dd/yy";

  public static boolean hasCSVFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<Etl> csvToEtlRows(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<Etl> etlList = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT);
      for (CSVRecord csvRecord : csvRecords) {
        Etl etl = new Etl();
        etl.setDatasource(csvRecord.get(HEADERS[0]));
        etl.setCampaign(csvRecord.get(HEADERS[1]));
        etl.setDaily(LocalDate.parse(csvRecord.get(HEADERS[2]), formatter));
        etl.setClicks(Integer.valueOf(csvRecord.get(HEADERS[3])));
        etl.setImpressions(Integer.valueOf(csvRecord.get(HEADERS[4])));
        etlList.add(etl);

      }

      return etlList;
    } catch (Exception e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream etlsToCSV(List<Etl> etls) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (Etl etl : etls) {
        List<String> data = Arrays.asList(
              String.valueOf(etl.getDatasource()),
              etl.getCampaign(),
              etl.getDaily().toString(),
              String.valueOf(etl.getClicks()),
                String.valueOf(etl.getImpressions())
            );

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }

}
