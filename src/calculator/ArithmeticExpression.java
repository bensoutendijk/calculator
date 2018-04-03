/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.util.ArrayList;

/**
 *
 * @author bensoutendijk
 */

public class ArithmeticExpression {
    
    private StringBuilder expression = new StringBuilder("");
    
    public ArithmeticExpression (){
//        no arg constructor
    }
    public ArithmeticExpression (String s){
        this.expression = new StringBuilder(s);
    }
    public ArithmeticExpression (Double x){
        this.expression = new StringBuilder(Double.toString(x));
    }
    
    public void pushOperation(String op){
        if (expression.length() != 0){
            if (Calculator.tryParseInt(Character.toString(expression.charAt(expression.length()-1)))){
                expression.append(" " + op + " ");
            } else if (expression.charAt(expression.length() - 1) == ')') {
                expression.append(" " + op + " ");
            }
        }
    }
    public void pushNumeral(String s){
        expression.append(s);
    }
    public void openBrace(){
        if (expression.length() == 0){
            expression.append("(");
        }
        else if (Calculator.tryParseInt(expression.charAt(expression.length() - 1))){
            expression.append("(");
        }
    }
    public void closeBrace(){
        int open = 0, closed = 0;
        String[] arr = new String[expression.length()];
        for (int i = 0; i < arr.length; i++){
            arr[i] = Character.toString(expression.charAt(i));
            if (arr[i].equals("("))
                open++;
            else if (arr[i].equals(")"))
                closed++;
        }
        if (expression.length() > 0){
            if (open > closed){
                if (Calculator.tryParseInt(Character.toString(expression.charAt(expression.length() - 1)))) {
                    expression.append(")");
                }
            }
        }
    }
    public void pushDecimal(){
        if (expression.length() > 0 && !currentNumber().contains(".")){
            if (Calculator.tryParseInt(expression.charAt(expression.length()-1))){
                expression.append(".");
            } 
        }
    }
    public void backspace(){
        if (expression.length() > 0){
            char c = expression.charAt(expression.length() - 2);
            if (Calculator.tryParseOp(c))
                for (int i = 0; i < 3; i++)
                    expression.deleteCharAt(expression.length() - 1);
            else 
                expression.deleteCharAt(expression.length() - 1);
        }
    }
    public void clear(){
        this.expression.delete(0, expression.length());
    }
    public void clearEntry(){
        for (int i = currentNumber().length(); i > 0 ; i--) {
            expression.deleteCharAt(expression.length() - 1);
        }
    }
    public void switchSign(){
        if (!currentNumber().equals("")){
            String switchedNumber = currentNumber();
            if (currentSign(currentNumber()) == Calculator.Sign.POSITIVE) {
                switchedNumber = new StringBuilder(switchedNumber).insert(0, "-").toString();
                this.clearEntry();
                expression.append(switchedNumber);
            } else {
                switchedNumber = new StringBuilder(switchedNumber).deleteCharAt(0).toString();
                this.clearEntry();
                expression.append(switchedNumber);
            }
        }
    }

    private String currentNumber() {
        String res = "";
        if (expression.length() == 0) return res;
        String[] arr = new String[expression.length()];
        
        for (int i = expression.length() - 1; i >= 0; i--) {
            arr[expression.length() - i - 1] = Character.toString(expression.charAt(i));
        }
        
        for (String s : arr) {
            if (Calculator.tryParseInt(s) || s.equals(".") || s.equals("-")) {
                res += s;
            } else if (s.equals(" ")) break;
        }
        
        res = new StringBuilder(res).reverse().toString();
        
        return res;
    }
    private Calculator.Sign currentSign(String s){
        if (s.charAt(0) == '-') {
            return Calculator.Sign.NEGATIVE;
        } else return Calculator.Sign.POSITIVE;
    }
    
    @Override 
    public String toString(){
        String res = "";
        for (int i = 0; i < expression.length(); i++){
            res += expression.charAt(i);
        }
        return res;
    } 
    
}
