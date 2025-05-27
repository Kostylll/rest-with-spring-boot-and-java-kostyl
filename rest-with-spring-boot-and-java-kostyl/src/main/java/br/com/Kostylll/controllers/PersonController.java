package br.com.Kostylll.controllers;

import br.com.Kostylll.data.dto.v1.PersonDTO;
import br.com.Kostylll.data.dto.v2.PersonDTOV2;
import br.com.Kostylll.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/person/v1")
@RestController
@Tag(name = "", description = "Endpoints for Managing People")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Find All People",
            description = "Finds All People",
            tags = "People",
            responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class))
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)

    })
    public ResponseEntity<Page<PersonDTO>> findAll(@RequestParam(value = "page" , defaultValue = "0") Integer page, @RequestParam(value = "size" , defaultValue = "12") Integer size,@RequestParam(value = "direction" , defaultValue = "asc") String direction) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortDirection,"firstName"));
        return  ResponseEntity.ok(service.findAll(pageable));
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PersonDTO findById(@PathVariable("id") Long id) {
        var person = service.findById(id);
        person.setBirthDay(new Date());
        //person.setPhoneNumber("+5512996771474");
        person.setPhoneNumber("");
        person.setSensitiveData("ba");
        return person;
    }

    @GetMapping(value = "findByName/{name}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PersonDTO>> findByName(@PathVariable("name") String name, @RequestParam(value = "page" , defaultValue = "0") Integer page, @RequestParam(value = "size" , defaultValue = "12") Integer size) {
        Pageable pageable = PageRequest.of(page,size,Sort.by("firstName"));
        return ResponseEntity.ok(service.findByName(name,pageable));
    }


    @GetMapping(value = "/enabled", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<PersonDTO> getEnabled() {
        return service.findAllEnabled();
    };

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PersonDTO create(@RequestBody PersonDTO person) {
        return service.create(person);
    }

    @PostMapping(value = "/v2/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PersonDTOV2 createv2(@RequestBody PersonDTOV2 person) {
        return service.createv2(person);
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PersonDTO update(@RequestBody PersonDTO dto) {
        return service.update(dto);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO disablePerson(@PathVariable("id") long id) {
        return service.disablePerson(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
