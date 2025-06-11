package br.com.Kostylll.file.importer.factory;


import br.com.Kostylll.file.importer.contract.FileImporter;
import br.com.Kostylll.file.importer.impl.CsvImporter;
import br.com.Kostylll.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class FileImporterFactory {


    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileImporter getFileImporter(String fileName) throws IOException {

        if (fileName.endsWith(".csv")) {
            return context.getBean("csvImporter", CsvImporter.class);
        } else if (fileName.endsWith(".xlsx")) {
            return context.getBean("xlsxImporter", XlsxImporter.class);
        } else {
            throw new FileNotFoundException(fileName);
        }
    }

}
