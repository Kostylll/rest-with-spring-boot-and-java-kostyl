package br.com.Kostylll.math;

import br.com.Kostylll.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class SimpleMath {

    public Double sum (Double number1, Double number2) {
        return number1 + number2;
    }

    public Double sub (Double number1, Double number2) {
        return number1 - number2;
    }

    public Double multi (Double number1, Double number2) {
        return number1 * number2;
    }

    public Double div (Double number1, Double number2) {
        return (number1 + number2) / 2;
    }

    public Double squareRoot (Double number1) {
        return Math.sqrt(number1);
    }
}
