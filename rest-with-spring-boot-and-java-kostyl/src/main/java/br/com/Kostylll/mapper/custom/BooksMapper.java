package br.com.Kostylll.mapper.custom;

import br.com.Kostylll.data.dto.v1.BooksDTO;
import br.com.Kostylll.model.Books;
import org.springframework.stereotype.Service;

@Service
public class BooksMapper {

    public BooksDTO convertEntityToDTO(Books entity) {
        BooksDTO dto = new BooksDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        return dto;
    };

    public Books convertDTOToEntity(BooksDTO dto) {
        Books entity = new Books();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        return entity;
    }

}
