package br.com.Kostylll.controllers;

import br.com.Kostylll.services.PersonServices;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestLogController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());


    @GetMapping("/test")
    public String TestLog() {

        logger.debug("This is a debug log");
        logger.warn("This is a warning log");
        logger.error("This is an error log");
        logger.info("This is a info log");


        return "Log Generated";

    }


}
