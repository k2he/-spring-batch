package com.demo.batch.springbatch.batch.csv;

import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.util.ReportUtil;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class OrderReportWriter extends FlatFileItemWriter<Order> {

    private static final String CSV_DELIMITER = ";";

    @Autowired
    public OrderReportWriter(AppProperties appProperties) {
        // Plus one minute to make sure it's right date.
        var date = LocalDateTime.now().plusMinutes(1).toLocalDate();
        var fileName = ReportUtil.getFileName(appProperties.getReport().getFileName(), date);
        // Set output file location
        var filePath = appProperties.getReport().getOutputPath() + fileName;
        Resource outputResource = new FileSystemResource(filePath);

        this.setResource(outputResource);
        this.setEncoding(StandardCharsets.UTF_8.name());
        this.setShouldDeleteIfExists(appProperties.getReport().isDeleteIfFileExists());

        // All job repetitions should "append" to same output file, enable this if business requirement
        // changed.
        // this.setAppendAllowed(true);

        // Create Header
        var headers = appProperties.getReport().getCsvHeader();
        String headerCommaSeparated = String.join(CSV_DELIMITER, headers);
        this.setHeaderCallback(new FlatFileHeaderCallback() {
            public void writeHeader(Writer writer) throws IOException {
                // set csv header
                writer.write(headerCommaSeparated);
            }
        });

        // Convert list to array.
        var javaFields = appProperties.getReport().getJavaField();
        String[] javaFieldsArray = javaFields.toArray(String[]::new);

        // Name field values sequence based on object properties
        this.setLineAggregator(new DelimitedLineAggregator<Order>() {
            {
                setDelimiter(CSV_DELIMITER);
                setFieldExtractor(new BeanWrapperFieldExtractor<Order>() {
                    {
                        // set csv each column should map to which field in java object.
                        setNames(javaFieldsArray);
                    }
                });
            }
        });
    }
}
