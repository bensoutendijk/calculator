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
    public ArithmeticExpression (int x){
        this.expression = new StringBuilder(Integer.toString(x));
    }
    
    public void pushOperation(Calculator.Operation op){
        if (expression.length() != 0){
            if (Calculator.tryParseInt(expression.charAt(expression.length()-1))){
                expression.append(" " + op.toChar() + " ");
            } else if (expression.charAt(expression.length() - 1) == ')') {
                expression.append(" " + op.toChar() + " ");
            }
        }
    }
    public void pushNumeral(String s){
        expression.append(s);
    }
    public void openBrace(){
        expression.append("(");
    }
    public void closeBrace(){
        int open = 0, closed = 0;
        for (int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);
            if (c == '(')
                open++;
            if (c == ')')
                closed++;
        }
        if (expression.length() > 0){
            if (expression.charAt(expression.length() - 1) == ' ');
            else if (open > closed)
                expression.append(")");
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
        for (int i = currentNumber(expression).length(); i > 0 ; i--) {
            expression.deleteCharAt(expression.length() - 1);
        }
    }
    public void switchSign(){
        String s = currentNumber(expression);
        if (!s.equals("")){
            if (s.charAt(0) != '-') {
                s = new StringBuilder(s).insert(0, "-").toString();
                this.clearEntry();
                expression.append(s);
            } else {
                s = new StringBuilder(s).deleteCharAt(0).toString();
                this.clearEntry();
                expression.append(s);
            }
        }
    }

    private String currentNumber(StringBuilder expression ) {
        String res = "";
        if (expression.length() == 0) return res;
        String s = new StringBuilder(expression).reverse().toString();
        
        for (char c : s.toCharArray()) {
            if (c == ' ') break;
            res = res + c;
        }
        
        return new StringBuilder(res).reverse().toString();
    }
    
    public void simplify() {
        int open = 0, closed = 0;
        for (int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);
            if (c == '('){
                int j = Calculator.findClose(expression.substring(i));
                if (i == 0 && j == expression.length()-1){
                    expression = new StringBuilder(expression.substring(i+1,j));
                    i = 0;
                }
                open++;
                if (i > 0){
                    if (Calculator.tryParseInt(expression.charAt(i-1))){
                        expression.insert(i," " + "×" + " ");
                    } else if (expression.charAt(i-1) == ')'){
                        expression.insert(i," " + "×" + " ");
                    }
                }
            }
            if (c == ')'){
                if (i < expression.length()-1){
                    if (Calculator.tryParseInt(expression.charAt(i+1)))
                        expression.insert(i+1," " + "×" + " ");
                }
                closed++;
            }
        }
        if (expression.charAt(expression.length()-1) == ' '){
            expression.append(currentNumber(expression.delete(expression.length()-3, expression.length()-1)));
        }
        for (; closed < open; closed++){
            this.closeBrace();
        }
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
