package br.com.Kostylll.repository;

import br.com.Kostylll.services.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
