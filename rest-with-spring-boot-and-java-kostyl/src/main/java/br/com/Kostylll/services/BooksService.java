package br.com.Kostylll.services;

import static br.com.Kostylll.mapper.ObjectMapper.parseListObject;
import static br.com.Kostylll.mapper.custom.BooksMapper.*;
import static br.com.Kostylll.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.Kostylll.controllers.BooksController;
import br.com.Kostylll.controllers.PersonController;
import br.com.Kostylll.data.dto.v1.BooksDTO;
import br.com.Kostylll.data.dto.v1.PersonDTO;
import br.com.Kostylll.mapper.custom.BooksMapper;
import br.com.Kostylll.model.Books;
import br.com.Kostylll.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class BooksService {

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    BooksMapper mapper;

    public Page<BooksDTO> getAllBooks(Pageable pageable) {
       var books = booksRepository.findAll(pageable);

       var booksWithLinks = books.map(book -> {
           var dto = parseObject(books, BooksDTO.class);
           addHateoasLinks(dto);
           return dto;
       });

       return booksWithLinks;
    }

    public BooksDTO getBookById(Long id) {
        var book = booksRepository.findById(id).orElse(null);
        var dto = parseObject(book, BooksDTO.class);
        return dto;
    }

    public BooksDTO createBook(BooksDTO dto) {
        var book = parseObject(dto, Books.class);
        booksRepository.save(book);
        var dtoBook = parseObject(book, BooksDTO.class);
        addHateoasLinks(dtoBook);
        return dtoBook;
    }

    public BooksDTO updateBook(BooksDTO dto) {
        var book = booksRepository.findById(dto.getId()).orElse(null);

        book.setAuthor(dto.getAuthor());
        book.setTitle(dto.getTitle());
        book.setPrice(dto.getPrice());
        book.setLaunch_date(dto.getLaunch_date());

        var dtoBook = parseObject(booksRepository.save(book), BooksDTO.class);
        addHateoasLinks(dtoBook);
        return dtoBook;
    }

    public void deleteBook(Long id) {
        var book = booksRepository.findById(id).orElse(null);
        booksRepository.delete(book);
    }


    private void addHateoasLinks(BooksDTO dto) {
        dto.add(linkTo(methodOn(BooksController.class).findBookById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(BooksController.class).deleteBook(dto.getId())).withRel("delete").withType("DELETE"));

        dto.add(linkTo(methodOn(BooksController.class).findAllBooks(1,12,"asc")).withRel("findAll").withType("GET"));

        dto.add(linkTo(methodOn(BooksController.class).createBook(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(BooksController.class).updateBook(dto)).withRel("update").withType("PUT"));
    }


}
