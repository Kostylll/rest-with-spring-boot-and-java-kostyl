package br.com.Kostylll.services;

import br.com.Kostylll.data.dto.v1.PersonDTO;
import br.com.Kostylll.data.dto.v2.PersonDTOV2;
import br.com.Kostylll.exception.ResourceNotFoundException;
import static  br.com.Kostylll.mapper.ObjectMapper.parseListObject;
import static  br.com.Kostylll.mapper.ObjectMapper.parseObject;

import br.com.Kostylll.mapper.custom.PersonMapper;
import br.com.Kostylll.repository.PersonRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
         return parseObject(entity, PersonDTO.class);
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all People");
        return parseListObject(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating Person");
        var entity = parseObject(person, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
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

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting Person");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID!"));

        repository.delete(entity);
    }


}
