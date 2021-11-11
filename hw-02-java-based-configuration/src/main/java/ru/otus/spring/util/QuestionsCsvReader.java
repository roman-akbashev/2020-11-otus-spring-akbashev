package ru.otus.spring.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.spring.exceptions.IncorrectRecordFormatException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class QuestionsCsvReader implements QuestionReader {
    private final Resource questionsResource;
    private final String resourceName;

    public QuestionsCsvReader(@Value("${csv-file}") String resourceName) {
        this.questionsResource = new ClassPathResource(resourceName);
        this.resourceName = resourceName;
    }


    public List<List<String>> getRecords() {
        return readRecordsFromCsv();
    }

    private List<List<String>> readRecordsFromCsv() {
        List<List<String>> records = new ArrayList<>();
        try (Reader reader = new InputStreamReader(questionsResource.getInputStream())) {
            CSVParser csvParser = CSVFormat.EXCEL.withDelimiter(";".charAt(0)).parse(reader);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                List<String> record = new ArrayList<>();
                for (String recordString : csvRecord) {
                    record.add(recordString);
                }
                records.add(record);
            }
            checkCsvRecords(records);
            return records;
        } catch (IOException e) {
            throw new IncorrectRecordFormatException(e);
        }
    }

    private void checkCsvRecords(List<List<String>> records) {
        if (records.size() == 0) {
            throw new IncorrectRecordFormatException("Resource file " + resourceName + " contains no records");
        }
        records.forEach(recordStrings -> {
            int recordSize = recordStrings.size();
            if (recordSize < 4) {
                throw new IncorrectRecordFormatException("The record must contain a question, " +
                        "two answers, and the number of the correct question. Elements must be separated by a symbol \";\"");
            }
            String lastRecordElement = recordStrings.get(recordSize - 1);
            if (!isNumber(lastRecordElement)) {
                throw new IncorrectRecordFormatException("The record does not contain the number of the answer to the question");
            }
        });
    }

    private boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
