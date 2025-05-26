package br.com.Kostylll.services;

import br.com.Kostylll.controllers.PersonController;
import br.com.Kostylll.data.dto.v1.PersonDTO;
import br.com.Kostylll.data.dto.v2.PersonDTOV2;
import br.com.Kostylll.exception.ResourceNotFoundException;
import static  br.com.Kostylll.mapper.ObjectMapper.parseListObject;
import static  br.com.Kostylll.mapper.ObjectMapper.parseObject;

import br.com.Kostylll.mapper.custom.PersonMapper;
import br.com.Kostylll.model.Person;
import br.com.Kostylll.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;

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

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

        dto.add(linkTo(methodOn(PersonController.class).findAll(1,12,"asc")).withRel("findAll").withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));

        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
    }
}
