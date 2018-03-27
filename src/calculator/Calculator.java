/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 *
 * @author souten
 */
public class Calculator extends Application {
    
    enum Sign {POSITIVE, NEGATIVE};
    enum Operation {ADD, SUBTRACT, MULTIPLY, DIVIDE};
    
    public static final double MIN_HEIGHT = 525;
    public static final double MIN_WIDTH = 350;
    
    public static Label m = new Label("");
    
    String[][] labels = {
        {"Lsh", "Rsh", "Or", "Xor", "Not", "And"},
           {"↑", "Mod", "CE", "C", "⌫", "÷"},
             {"A", "B", "7", "8", "9", "×"},     
             {"C", "D", "4", "5", "6", "−"},
             {"E", "F", "1", "2", "3", "+"}, 
             {"(", ")", "±", "0", ".", "="}
    };
    
    private String input = "";
    private String output = "";
    
    
    @Override
    public void start(Stage primaryStage) {
//        Stage settings
        primaryStage.setTitle("Calculator");
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);


//        Three row structure
        GridPane container = new GridPane();
        container.setAlignment(Pos.CENTER);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        container.getColumnConstraints().add(column1);
        RowConstraints[] rows = new RowConstraints[3];
        for (int i = 0; i < rows.length; i++)
            rows[i] = new RowConstraints();
        rows[0].setPercentHeight(40);
        rows[1].setPercentHeight(5);
        rows[2].setPercentHeight(50);
        container.getRowConstraints().addAll(rows);
        container.setHgap(0);
        
//        row 0: the monitor, outputs the calculations in hex, dec, oct, and bin

        
        container.add(m, 0, 0);
        
//        row 1: contains misc buttons i.e. bit-toggler, bit measurement, and memory buttons
        
//        row 2: the bottom row, keypadContainer contains all the buttons that create the calculation/function

        GridPane keypad = new GridPane();
        for(int i = 0; i < labels.length; i++) {
            for(int j = 0; j < labels[i].length; j++) {
                String label = labels[i][j];
                Button button = new Button(label);
                if (tryParseInt(label))
                    button.setOnAction(handleInt(label));
                else if (tryParseOp(label))
                    button.setOnAction(handleOp(label));
//                else if (tryParseVar(label))
//                    button.setOnAction(handleVar(label));
                else button.setOnAction(handleRest(label));
                keypad.add(button, j, i);
            }
        }
        
        container.add(keypad, 0, 2);


//        Scene initialization
        Scene scene = new Scene(container, MIN_WIDTH, MIN_HEIGHT);
        primaryStage.setScene(scene);
        scene.getStylesheets().add
          (Calculator.class.getResource("Calculator.css").toExternalForm());
        primaryStage.show();
    }
    
    

    /** 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public EventHandler<ActionEvent> handleInt(String s) {
        EventHandler<ActionEvent> handle = (ActionEvent e) -> {
            if (this.input.equals("") || this.input.charAt(this.input.length() - 1) != ')')
                this.input += s;
            System.out.println(this.input);
            m.setText(this.input);
        };
        return handle;
    }
    
    public EventHandler<ActionEvent> handleOp(String s) {
        EventHandler<ActionEvent> handle = (ActionEvent e) -> {
            if (input.length() != 0){
                if (tryParseInt(Character.toString(this.input.charAt(this.input.length()-1))) || this.input.charAt(this.input.length() - 1) == ')'){
                    this.input += " " + s + " ";
                    System.out.println(this.input);
                    m.setText(this.input);
                }
            }
        };
        return handle;
    }
    
    public double solve(String equation){
        int level = 0;
        int depth = 0;
        int priorityStart = 0;
        int priorityEnd = equation.length() - 1;
        double value = 0;
        for (int i = 0; i < equation.length(); i++){
            if (equation.charAt(i) == '('){
                level++;
            } else if (equation.charAt(i) == ')'){
                level--;
            }
            if (level > depth){
                depth = level;
            }
        }
        for (int i =0; i < equation.length(); i++){
            if (equation.charAt(i) == '('){
                level++;
            } else if (equation.charAt(i) == ')'){
                level--;
            }
            if (level == depth){
                priorityStart = i + 1;
                break;
            }
        }
        for (int i =0; i < equation.length(); i++){
            if (equation.charAt(i) == '('){
                level++;
            } else if (equation.charAt(i) == ')'){
                level--;
            }
            if (level == depth){
                priorityEnd = i;
            }
        }
        if(depth == 0){
            value = operate(equation);
            System.out.println("returning: " + value);
            return value;
        }
        return solve(equation.substring(priorityStart, priorityEnd));
    }
    
    public double operate(String equation){
        double value = 0;
        double x;
        double y;
        for (int i = 0; i < equation.length(); i++){
            System.out.print("'" + equation.substring(i, i+1) + "'");
        }
        return value;
    }
    
    public EventHandler<ActionEvent> handleRest(String s) {
//        Equals action
        if (s.equals("=")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                solve(this.input);
            };
            return handle;
        }
//        Backspace action
        if (s.equals("⌫")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                if (this.input.length() > 1){
                    String prevChar = Character.toString(this.input.charAt(this.input.length()-1));
                    if (tryParseInt(prevChar) || prevChar.equals(".")){
                        this.input = this.input.substring(0, this.input.length()-1);
                        System.out.println(this.input);
                        m.setText(this.input);
                    } else if (tryParseOp(Character.toString(this.input.charAt(this.input.length()-2)))){
                        this.input = this.input.substring(0, this.input.length()-3);
                        System.out.println(this.input);
                        m.setText(this.input);
                    }
                } else if (this.input.length() == 1) {
                    this.input = "";
                    m.setText(this.input);
                }
            };
            return handle;
        } 
        
//        Decimal point action
        if (s.equals(".")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                if (this.input.length() > 0 && !rightOfDecimal(currentNumber(this.input))){
                    if (tryParseInt(Character.toString(this.input.charAt(this.input.length()-1)))){
                        this.input += s;
                        System.out.println(this.input);
                        m.setText(this.input); 
                    } 
                } else if (this.input.length() == 0) {
//                    do nothing
                }
            };
            return handle;
            
        }
        
//        Clear action: clears the entire input
        if (s.equals("C")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                this.input = "";
                m.setText(this.input); 
            };
            return handle;
            
        }
        
//        Clear Entry action: clears only the current number        
        if (s.equals("CE")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                for (int i = currentNumber(this.input).length(); i > 0 ; i--) {
                    this.input = new StringBuilder(this.input).deleteCharAt(this.input.length() - 1).toString();
                }
                m.setText(this.input);
            };
            return handle;
            
        }
        
//        Sign Switch action: changes the sign of the current number
        if (s.equals("±")) {
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                if (!currentNumber(this.input).equals("")){
                    String switchedNumber = currentNumber(this.input);
                    if (currentSign(currentNumber(this.input)) == Sign.POSITIVE) {
//                        change to negative
                        switchedNumber = new StringBuilder(switchedNumber).insert(0, "-").toString();
                        for (int i = currentNumber(this.input).length(); i > 0 ; i--) {
                            this.input = new StringBuilder(this.input).deleteCharAt(this.input.length() - 1).toString();
                        }
                        this.input += switchedNumber;

                    } else {
//                        change to positive
                        switchedNumber = new StringBuilder(switchedNumber).deleteCharAt(0).toString();
                        for (int i = currentNumber(this.input).length(); i > 0 ; i--) {
                            this.input = new StringBuilder(this.input).deleteCharAt(this.input.length() - 1).toString();
                        }
                        this.input += switchedNumber;
                    }
                    m.setText(this.input);
                }
            };          
            
            return handle;
        }
        
        if (s.equals("(")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                if (tryParseOp(Character.toString(this.input.charAt(this.input.length() - 2)))){
                    this.input = this.input + s;
                }
                m.setText(this.input);
            };
            return handle;
        }
        
        if (s.equals(")")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                int open = 0, closed = 0;
                String[] arr = new String[this.input.length()];
                for (int i = 0; i < arr.length; i++){
                    arr[i] = Character.toString(this.input.charAt(i));
                    if (arr[i].equals("("))
                        open++;
                    else if (arr[i].equals(")"))
                        closed++;
                }
//                System.out.println("open: " + open + "\nclosed: " + closed);
                if (open > closed && tryParseInt(Character.toString(this.input.charAt(this.input.length() - 1)))){
                    this.input = this.input + s;
                }
                m.setText(this.input); 
            };
            return handle;
        }
        
        else {
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
//                this.input += s;
                System.out.println("'" + s + "' " + "Button onAction undefined");
//                m.setText(this.input);
            };
            return handle;
        } 
    }
    
    boolean tryParseInt(String s) {  
       try {  
            Integer.parseInt(s);  
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
    
    boolean rightOfDecimal(String input) {
        String[] arr = new String[input.length()];
        for (int i = input.length() - 1; i >= 0; i--) {
            arr[i] = Character.toString(input.charAt(i));
        }
        for (String s : arr) {
            if (s.equals(".")) return true;
        }
        return false;
    }

    String currentNumber(String input) {
        String res = "";
        if (input.length() == 0) return res;
        String[] arr = new String[input.length()];
        
        for (int i = input.length() - 1; i >= 0; i--) {
            arr[input.length() - i - 1] = Character.toString(input.charAt(i));
        }
        
        for (String s : arr) {
            if (tryParseInt(s) || s.equals(".") || s.equals("-")) {
                res += s;
            } else if (s.equals(" ")) break;
        }
        
        res = new StringBuilder(res).reverse().toString();
        
        return res;
    }
    
    Sign currentSign(String input){
        if (input.charAt(0) == '-') {
            return Sign.NEGATIVE;
        } else return Sign.POSITIVE;
    }
    
    Operation parseOperation(String s){
        if (s.equals("+")){
            return Operation.ADD;
        } else if (s.equals("−")){
            return Operation.SUBTRACT;
        } else if (s.equals("×")){
            return Operation.MULTIPLY;
        } else if (s.equals("÷")){
            return Operation.DIVIDE;
        } else return Operation.ADD;
    }
}
