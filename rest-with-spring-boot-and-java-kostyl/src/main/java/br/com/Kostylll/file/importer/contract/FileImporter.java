package br.com.Kostylll.file.importer.contract;

import br.com.Kostylll.data.dto.v1.PersonDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile (InputStream inputStream) throws IOException;

}
