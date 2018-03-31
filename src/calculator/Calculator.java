/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.util.ArrayList;
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
             {"C", "D", "4", "5", "6", "-"},
             {"E", "F", "1", "2", "3", "+"}, 
             {"(", ")", "±", "0", ".", "="}
    };
    
    public String input = "";
    public Equation equation = new Equation();
    public String output = "";
    
    
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
                button.setOnAction(handle(label));
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
    
    public EventHandler<ActionEvent> handle(String s) {

//        Numeric key action
        if (tryParseInt(s)) {
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                equation.pushNumeral(s);
                input = equation.toString();
                m.setText(input);
            };
            return handle;
        }
        
//        Operator key action
        if (tryParseOp(s)) {
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                equation.pushOperation(s);
                input = equation.toString();
                m.setText(input);
            };
            return handle;
        }
        
//        Equals action
        if (s.equals("=")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                solve(equation);
                input = equation.toString();
                m.setText(input);
            };
            
            return handle;
        }
        
//        Backspace action
        if (s.equals("⌫")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                equation.backspace();
                input = equation.toString();
                m.setText(input);
            };
            return handle;
        } 
        
//        Decimal point action
        if (s.equals(".")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                equation.pushDecimal();
                input = equation.toString();
                m.setText(input);
            };
            return handle;
            
        }
        
//        Clear action: clears the entire input
        if (s.equals("C")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                equation.clear();
                input = equation.toString();
                m.setText(input); 
            };
            return handle;
            
        }
        
//        Clear Entry action: clears only the current number        
        if (s.equals("CE")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                equation.clearEntry();
                input = equation.toString();
                m.setText(input);
            };
            return handle;
            
        }
        
//        Sign Switch action: changes the sign of the current number
        if (s.equals("±")) {
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                equation.switchSign();
                input = equation.toString();
                m.setText(input);
            };          
            return handle;
        }
        
        if (s.equals("(")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                equation.openBrace();
                input = equation.toString();
                m.setText(input);
            };
            return handle;
        }
        
        if (s.equals(")")){
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                equation.closeBrace();
                input = equation.toString();
                m.setText(input);
            };
            return handle;
        }
        else {
            EventHandler<ActionEvent> handle = (ActionEvent e) -> {
                System.out.println("'" + s + "' " + "Button onAction undefined");
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
    boolean tryParseInt(char c) {  
       try {  
            Integer.parseInt(Character.toString(c));
            return true;
        } catch (NumberFormatException e) {  
            return false;  
        }
    }
    boolean tryParseOp(String s) {
        String[] operators = {"+", "-", "×", "÷"};
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
    }private Operation parseOp(char c){
        if (c == '+'){
            return Operation.ADD;
        } else if (c == '-'){
            return Operation.SUBTRACT;
        } else if (c == '×'){
            return Operation.MULTIPLY;
        } else if (c == '÷'){
            return Operation.DIVIDE;
        } else 
            return Operation.ADD;
    }
    private Operation parseOp(String s){
        if (s.isEmpty()) System.out.println("parseOp: String was empty");
        char[] arr = s.toCharArray();
        for (char c : arr){
            if (c == '+'){
                return Operation.ADD;
            } else if (c == '-'){
                return Operation.SUBTRACT;
            } else if (c == '×'){
                return Operation.MULTIPLY;
            } else if (c == '÷'){
                return Operation.DIVIDE;
            }
        }
        return Operation.ADD;
    }
    private double solve(Equation equation){
        Equation fx, gx;
        Operation op;
        int numberOfOperations = 0;
        int open = 0, closed = 0;
        String s = equation.toString();
        ArrayList<Operation> operations = new ArrayList<Operation>();
        if (tryParseInt(s))
//            Base case
            return Double.parseDouble(s);
        else {
//            find all operations
            for (char c: s.toCharArray()){
                if (tryParseOp(c)){
                    operations.add(parseOp(c));
                }
            }
//            Sort the array of operators by precedence (i.e. P.E.M.D.A.S.) and choose the lowest
            System.out.println(operations.toString());
            quickSort(operations,0,operations.size()-1);
            System.out.println(operations.toString());
            //operate(fx, op, gx);
        }
        return 0.0;
    }
    
    private double operate(Equation x, Operation op, Equation y){
        System.out.println(x +" "+ op +" "+ y);
        if (op == Operation.ADD)
            return x + y;
        if (op == Operation.SUBTRACT)
            return x - y;
        if (op == Operation.MULTIPLY)
            return x * y;
        if (op == Operation.DIVIDE)
            return x / y;
        else return 0;
    }
    
    public static void quickSort (ArrayList<Operation> arr, int left, int right){
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
    }
    public static int partition (ArrayList<Operation> arr, int left, int right){
        int i = left, j = right;
        Operation tmp;
        Operation pivot = arr.get((left + right) / 2);

        while (i <= j) {
            while (arr.get(i).compareTo(pivot) > 0)
                i++;
            while (arr.get(j).compareTo(pivot) < 0)
                j--;
            if (i <= j) {
                tmp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, tmp);
                i++;
                j--;
            }
        };
        return i;
    }
}