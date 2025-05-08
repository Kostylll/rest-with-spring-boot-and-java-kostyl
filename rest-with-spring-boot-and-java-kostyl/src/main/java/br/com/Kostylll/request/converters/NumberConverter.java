package br.com.Kostylll.request.converters;

import br.com.Kostylll.exception.UnsupportedMathOperationException;

public class NumberConverter {

    public static Double convertToDouble(String strNumber) {
        if(strNumber == null || strNumber.isEmpty()) throw new UnsupportedMathOperationException("Please set a numeric value");;
        String number = strNumber.replaceAll(",", ".");
        return Double.parseDouble(number);
    }

    public static  boolean isNumeric(String strNumber)  throws IllegalArgumentException {
        if(strNumber == null || strNumber.isEmpty()) throw new IllegalArgumentException();
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[+-]?[0-9]*\\.?[0-9]+") ;
    }
}
