package br.com.Kostylll.controllers;

import br.com.Kostylll.data.dto.v1.EmailRequestDTO;
import br.com.Kostylll.mail.EmailSender;
import br.com.Kostylll.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email/v1")
public class EmailController {

    @Autowired
    EmailService emailService;


    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {
        emailService.sendSimpleEmail(emailRequestDTO);
        return new ResponseEntity<>("", HttpStatus.OK);
    }



}
