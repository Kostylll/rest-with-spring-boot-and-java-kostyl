package br.com.Kostylll.services;

import br.com.Kostylll.data.dto.v1.PersonDTO;
import br.com.Kostylll.model.Person;
import br.com.Kostylll.repository.PersonRepository;
import br.com.Kostylll.unitTests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertEquals(person.getId(), result.getId());
    }


    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);
        PersonDTO dto = input.mockDTO(1);
        when(personRepository.save(person)).thenReturn((persisted));
        var result = service.create(dto);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertEquals(person.getId(), result.getId());
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);
        PersonDTO dto = input.mockDTO(1);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn((persisted));
        var result = service.update(dto);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertEquals(person.getId(), result.getId());
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        service.delete(1L);

    }

    //@Test
    // void findAll() {
    // List<Person> list = input.mockEntityList();
    // when(personRepository.findAll()).thenReturn(list);
    //var result = service.findAll();
    //assertNotNull(result);
    // }
}