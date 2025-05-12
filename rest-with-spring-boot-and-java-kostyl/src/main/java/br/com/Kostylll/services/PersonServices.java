package br.com.Kostylll.services;

import br.com.Kostylll.exception.ResourceNotFoundException;
import br.com.Kostylll.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(Long id) {
        logger.info("Finding one Person");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID!"));
    }

    public List<Person> findAll() {
        logger.info("Finding all People");
        return repository.findAll();
    }

    public Person create(Person person) {
        logger.info("Creating Person");
        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating Person");
        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAdress(person.getAdress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Deleting Person");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID!"));

        repository.delete(entity);
    }


}
