package br.com.Kostylll.file.exporter.impl;

import br.com.Kostylll.data.dto.v1.PersonDTO;
import br.com.Kostylll.file.exporter.contract.FileExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter implements FileExporter {

    @Override
    public Resource exportFIle(List<PersonDTO> people) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID", "First Name","Last Name","Address","Gender","Enabled")
                .setSkipHeaderRecord(false)
                .build();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer,csvFormat)){
               for (PersonDTO person : people) {
                   csvPrinter.printRecord(
                           person.getId(),
                           person.getFirstName(),
                           person.getLastName(),
                           person.getAddress(),
                           person.getGender(),
                           person.getEnabled()
                   );
               }
        } catch (Exception e){
            e.printStackTrace();
        }


        return new ByteArrayResource(outputStream.toByteArray());
    }

}
