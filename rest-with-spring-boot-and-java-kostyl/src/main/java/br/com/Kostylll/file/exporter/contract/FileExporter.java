package br.com.Kostylll.file.exporter.contract;

import br.com.Kostylll.data.dto.v1.PersonDTO;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileExporter {

    Resource exportFIle (List<PersonDTO> people) throws IOException;

}
