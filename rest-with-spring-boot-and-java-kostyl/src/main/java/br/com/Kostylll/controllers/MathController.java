package br.com.Kostylll.controllers;

import br.com.Kostylll.exception.UnsupportedMathOperationException;
import br.com.Kostylll.math.SimpleMath;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.UnsupportedAddressTypeException;

import static br.com.Kostylll.request.converters.NumberConverter.convertToDouble;
import static br.com.Kostylll.request.converters.NumberConverter.isNumeric;


@RestController
@RequestMapping("/math")
public class MathController {

    private SimpleMath math = new SimpleMath();


    @RequestMapping("/sum/{number1}/{number2}")
    public Double sum (@PathVariable("number1") String number1, @PathVariable("number2") String number2) {

        if(!isNumeric(number1) || !isNumeric(number2) ) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.sum(convertToDouble(number1), convertToDouble(number2));

    }

    @RequestMapping("/sub/{number1}/{number2}")
    public Double sub (@PathVariable("number1") String number1, @PathVariable("number2") String number2) {
        if(!isNumeric(number1) || !isNumeric(number2) ) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.sub(convertToDouble(number1), convertToDouble(number2));
    }

    @RequestMapping("/mult/{number1}/{number2}")
    public Double mult (@PathVariable("number1") String number1, @PathVariable("number2") String number2) {
        if(!isNumeric(number1) || !isNumeric(number2) ) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.multi(convertToDouble(number1), convertToDouble(number2));
    }

    @RequestMapping("/media/{number1}/{number2}")
    public Double media (@PathVariable("number1") String number1, @PathVariable("number2") String number2) {
        if(!isNumeric(number1) || !isNumeric(number2) ) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.div(convertToDouble(number1), convertToDouble(number2));
    }

    @RequestMapping("/squareRoot/{number1}")
    public Double squareRoot (@PathVariable("number1") String number1) {
        if(!isNumeric(number1)) throw new UnsupportedMathOperationException("Please set a numeric value");
        return math.squareRoot(convertToDouble(number1));
    }



}
