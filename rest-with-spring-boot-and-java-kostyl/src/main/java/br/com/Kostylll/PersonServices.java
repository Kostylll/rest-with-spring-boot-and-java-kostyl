package br.com.Kostylll;

import br.com.Kostylll.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(String id) {
        logger.info("Finding one Person");
        Person person = new Person();

        person.setId(counter.incrementAndGet());
        person.setFirstName("John");
        person.setLastName("Smith");
        person.setGender("Male");
        person.setAdress("Avenida Teste");

        return person;
    }


    public List<Person> findAll() {
        logger.info("Finding one Person");

        List<Person> persons = new ArrayList<Person>();

        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        };
        return persons;
    }

    private Person mockPerson(int i) {

        Person person = new Person();

        person.setId(counter.incrementAndGet());
        person.setFirstName("Person" + i);
        person.setLastName("LastName" + i);
        person.setGender("Male" + i);
        person.setAdress("Avenida Teste" + i);

        return person;
    }


}
