package br.com.Kostylll.file.exporter.factory;


import br.com.Kostylll.file.exporter.MediaTypes;
import br.com.Kostylll.file.exporter.contract.FileExporter;
import br.com.Kostylll.file.exporter.impl.CsvExporter;
import br.com.Kostylll.file.exporter.impl.XlsxExporter;
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
public class FileExporterFactory {


    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileExporter getFileImporter(String acceptHeader) throws IOException {

        if (acceptHeader.equalsIgnoreCase("text/csv")) {
            return context.getBean("csvExporter", CsvExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX)) {
            return context.getBean("xlsxExporter", XlsxExporter.class);
        } else {
            throw new FileNotFoundException(acceptHeader);
        }
    }

}
