package br.com.Kostylll.controllers;

import br.com.Kostylll.data.dto.v1.BooksDTO;
import br.com.Kostylll.model.Books;
import br.com.Kostylll.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/books/v1")
@RestController
public class BooksController {

    @Autowired
    BooksService booksService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<BooksDTO> findAllBooks() {
        return booksService.getAllBooks();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public BooksDTO findBookById(@PathVariable("id") long id) {
        return booksService.getBookById(id);
    }

    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public BooksDTO createBook(@RequestBody BooksDTO booksDTO) {
        return booksService.createBook(booksDTO);
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public BooksDTO updateBook(@RequestBody BooksDTO booksDTO) {
        return booksService.updateBook(booksDTO);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") long id) {
        booksService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
