package br.com.Kostylll.repository;

import br.com.Kostylll.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Long> {
}
