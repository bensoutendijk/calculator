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
                equation.solve();
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
    boolean tryParseOp(String s) {
        String[] operators = {"+", "−", "×", "÷"};
        for (String op : operators) {
            if (s.equals(op)) 
                return true;
        }
        return false;
    }
}