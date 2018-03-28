/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import static calculator.Calculator.m;

/**
 *
 * @author bensoutendijk
 */
public class Equation {
    StringBuilder equation = new StringBuilder("");
    private double x, y, res;
    private int level, depth;
    
    public Equation (){
//        no arg constructor
    }
    
    public Equation (double x, String op, double y){
        this.x = x;
        this.y = y;
        this.pushNumeral(Double.toString(x));
        this.pushOperation(op);
        this.pushNumeral(Double.toString(y));
    }
    
    public void pushOperation(String op){
        if (equation.length() != 0){
            if (tryParseInt(Character.toString(equation.charAt(equation.length()-1)))){
                equation.append(" " + op + " ");
            } else if (equation.charAt(equation.length() - 1) == ')') {
                equation.append(" " + op + " ");
            }
        }
    }
    public void pushNumeral(String s){
//        if (equation.length() == 0)
//            equation.append(s);
//        else if (equation.charAt(equation.length() - 1) == ')')
//            equation.append(s);
//        else if (equation.charAt(equation.length() - 1) == '(')
//            equation.append(s);
//        else if (tryParseInt(equation.charAt(equation.length() - 1)))
//            equation.append(s);
//        else if ((equation.charAt(equation.length() - 1)) == ' ')
//            equation.append(s);
        equation.append(s);
    }
    public void openBrace(){
        if (tryParseOp(equation.charAt(equation.length() - 2)))
            equation.append("(");
//        else if (tryParseInt(equation.charAt(equation.length() - 1)))
//            equation.append("(");
    }
    public void closeBrace(){
        int open = 0, closed = 0;
        String[] arr = new String[equation.length()];
        for (int i = 0; i < arr.length; i++){
            arr[i] = Character.toString(equation.charAt(i));
            if (arr[i].equals("("))
                open++;
            else if (arr[i].equals(")"))
                closed++;
        }
        if (open > closed && tryParseInt(Character.toString(equation.charAt(equation.length() - 1)))){
            equation.append(")");
        }
    }
    public void pushDecimal(){
        if (equation.length() > 0 && !currentNumber().contains(".")){
            if (tryParseInt(equation.charAt(equation.length()-1))){
                equation.append(".");
            } 
        }
    }
    public void backspace(){
        if (equation.length() > 0){
            char c = equation.charAt(equation.length() - 2);
            if (tryParseOp(c))
                for (int i = 0; i < 3; i++)
                    equation.deleteCharAt(equation.length() - 1);
            else 
                equation.deleteCharAt(equation.length() - 1);
        }
    }
    public void clear(){
        this.equation.delete(0, equation.length());
    }
    public void clearEntry(){
        for (int i = currentNumber().length(); i > 0 ; i--) {
            equation.deleteCharAt(equation.length() - 1);
        }
    }
    public void switchSign(){
        if (!currentNumber().equals("")){
            String switchedNumber = currentNumber();
            if (currentSign(currentNumber()) == Calculator.Sign.POSITIVE) {
                switchedNumber = new StringBuilder(switchedNumber).insert(0, "-").toString();
                this.clearEntry();
                equation.append(switchedNumber);
            } else {
                switchedNumber = new StringBuilder(switchedNumber).deleteCharAt(0).toString();
                this.clearEntry();
                equation.append(switchedNumber);
            }
        }
    }
    
    public void solve(){
        int left = 0, right = 0;
        String s;
        for (int i = 0; i < equation.length(); i++){
            if (tryParseInt(equation.charAt(i))){
                left = i;
                break;
            }
        }
        for (int i = left; i < equation.length(); i++){
            if (equation.charAt(i) == ' '){
                right = i;
                break;
            }
        }
        s = equation.substring(left, right);
        x = Double.parseDouble(s);
        
        for (int i = right; i < equation.length(); i++){
            if (tryParseInt(equation.charAt(i))){
                left = i;
                break;
            }
        }
        for (int i = left; i < equation.length(); i++){
            if (tryParseInt(equation.charAt(i))){
                right = i + 1;
            }
        }
        s = equation.substring(left, right);
        y = Double.parseDouble(s);
        
        equation = new StringBuilder(Double.toString(operate(parseOp(equation.charAt(equation.length() - 3)))));
    }

    private double operate(Calculator.Operation op){
        if (op == Calculator.Operation.ADD)
            return x + y;
        if (op == Calculator.Operation.SUBTRACT)
            return x - y;
        if (op == Calculator.Operation.MULTIPLY)
            return x * y;
        if (op == Calculator.Operation.DIVIDE)
            return x / y;
        else return 0;
    }
    
    private String currentNumber() {
        String res = "";
        if (equation.length() == 0) return res;
        String[] arr = new String[equation.length()];
        
        for (int i = equation.length() - 1; i >= 0; i--) {
            arr[equation.length() - i - 1] = Character.toString(equation.charAt(i));
        }
        
        for (String s : arr) {
            if (tryParseInt(s) || s.equals(".") || s.equals("-")) {
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
    private Calculator.Operation parseOp(char c){
        if (c == '+'){
            return Calculator.Operation.ADD;
        } else if (c == '−'){
            return Calculator.Operation.SUBTRACT;
        } else if (c == '×'){
            return Calculator.Operation.MULTIPLY;
        } else if (c == '÷'){
            return Calculator.Operation.DIVIDE;
        } else return Calculator.Operation.ADD;
    }

    boolean tryParseInt(String s) {  
       try {  
            Integer.parseInt(s);  
            return true;  
        } catch (NumberFormatException e) {  
            return false;  
        }
    }
    boolean tryParseInt(char c) {  
       try {  
            Integer.parseInt(Character.toString(c));
            return true;
        } catch (NumberFormatException e) {  
            return false;  
        }
    }
    boolean tryParseOp(String s) {
        String[] operators = {"+", "−", "×", "÷"};
        for (String op : operators) {
            if (s.equals(op)) 
                return true;
        }
        return false;
    }
    boolean tryParseOp(char c) {
        char[] operators = {'+', '-', '×', '÷'};
        for (char op  : operators) {
            if (c == op) 
                return true;
        }
        return false;
    }
    
    @Override 
    public String toString(){
        String res = "";
        for (int i = 0; i < equation.length(); i++){
            res += equation.charAt(i);
        }
        return res;
    } 
    
}
