package br.com.Kostylll.services;

import br.com.Kostylll.controllers.PersonController;
import br.com.Kostylll.data.dto.v1.PersonDTO;
import br.com.Kostylll.data.dto.v2.PersonDTOV2;
import br.com.Kostylll.exception.FileStoragreException;
import br.com.Kostylll.exception.ResourceNotFoundException;

import static br.com.Kostylll.mapper.ObjectMapper.parseListObject;
import static br.com.Kostylll.mapper.ObjectMapper.parseObject;

import br.com.Kostylll.file.exporter.MediaTypes;
import br.com.Kostylll.file.exporter.contract.FileExporter;
import br.com.Kostylll.file.exporter.factory.FileExporterFactory;
import br.com.Kostylll.file.importer.contract.FileImporter;
import br.com.Kostylll.file.importer.factory.FileImporterFactory;
import br.com.Kostylll.mapper.custom.PersonMapper;
import br.com.Kostylll.model.Person;
import br.com.Kostylll.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;

    @Autowired
    FileImporterFactory importer;

    @Autowired
    FileExporterFactory exporter;

    @Autowired
    PersonMapper converter;

    private final AtomicLong counter = new AtomicLong();
    private org.slf4j.Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID!"));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public Page<PersonDTO> findAll(Pageable pageable) {
        logger.info("Finding all People");

        var people = repository.findAll(pageable);
        var peopleWithLinks = people.map(person -> {
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        return peopleWithLinks;
    }

    public List<PersonDTO> findAllEnabled() {
        logger.info("Finding all enabled People");
        var persons = parseListObject(repository.findByEnabledTrue(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);
        return persons;
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating Person");
        var entity = parseObject(person, Person.class);
        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public List<PersonDTO> massCreation(MultipartFile file){
        logger.info("Importing People from File");

        if (file.isEmpty()) {
            throw new ResourceNotFoundException("File is empty");
        }
        try (InputStream inputStream = file.getInputStream()) {

            String fileName = Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new ResourceNotFoundException("File is empty"));
            FileImporter importer = this.importer.getFileImporter(fileName);

            List<Person> entities = importer.importFile(inputStream).stream()
                    .map(dto -> repository.save(parseObject(dto, Person.class))).toList();
            {
                return entities.stream()
                        .map(entity -> {
                            var dto = parseObject(entity, PersonDTO.class);
                            addHateoasLinks(dto);
                            return dto;
                        }).toList();


            }
        } catch (Exception e) {
            throw new FileStoragreException("Error Processing File!");
        }
    }

    public Resource exportPage(Pageable pageable, String acceptHeader) throws IOException {
        logger.info("Finding all People");

        var people = repository.findAll(pageable).map(person ->
                parseObject(person, PersonDTO.class)).getContent();

        FileExporter exporter = this.exporter.getFileImporter(acceptHeader);
        return exporter.exportFIle(people);
    }

    public PersonDTOV2 createv2(PersonDTOV2 person) {
        logger.info("Creating Person V2");
        var entity = converter.convertDTOtoEntity(person);
        return converter.convertEntitytoDTO(repository.save(entity));
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating Person");
        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {
        logger.info("Disabling Person");

        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID!"));
        repository.disablePerson(id);

        var entity = repository.findById(id).get();
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }


    public void delete(Long id) {
        logger.info("Deleting Person");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID!"));

        repository.delete(entity);
    }

    public Page<PersonDTO> findByName(String name, Pageable pageable) {

        logger.info("Finding a Person");
        var entity = repository.findPeopleByName(name, pageable);

        var peopleWithLinks = entity.map(person -> {
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        return peopleWithLinks;
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

        dto.add(linkTo(methodOn(PersonController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(PersonController.class)).slash("massCreation").withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));

        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
    }
}
