package br.com.Kostylll.controllers;


import br.com.Kostylll.model.User;
import br.com.Kostylll.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "api/user/v1")
public class UserController {

    @Autowired
    UserServices userServices;


    @GetMapping(value = "/{user_name}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> getUser(@PathVariable("user_name") String user_name) {
        return (ResponseEntity<User>) this.userServices.loadUserByUsername(user_name);
    }


}
