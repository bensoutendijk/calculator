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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 *
 * @author souten
 */
public class Calculator extends Application {
    
    public ArithmeticExpression expression = new ArithmeticExpression();    
    
    public enum Operation {
        ADD, SUBTRACT, MULTIPLY, MOD, DIVIDE, OR, XOR, AND;
        public String toString() {
            switch(this) {
              case ADD: return "+";
              case SUBTRACT: return "−";
              case MULTIPLY: return "×";
              case DIVIDE: return "÷";
              case MOD: return "Mod";
              case OR: return "Or";
              case XOR: return "Xor";
              case AND: return "And";
              default: throw new IllegalArgumentException();
            }
        }
        public char toChar() {
            switch(this) {
              case ADD: return '+';
              case SUBTRACT: return '−';
              case MULTIPLY: return '×';
              case DIVIDE: return '÷';
              case MOD: return '%';
              case OR: return '‖';
              case XOR: return '^';
              case AND: return '&';
              default: throw new IllegalArgumentException();
            }
        }
        public int operate(int x, int y){
            switch(this) {
              case ADD: return x+y;
              case SUBTRACT: return x-y;
              case MULTIPLY: return x*y;
              case DIVIDE: return x/y;
              case MOD: return x%y;
              case OR: return x|y;
              case XOR: return x^y;
              case AND: return x&y;
              default: throw new IllegalArgumentException();
            }
        }
    };
    
    public static final double MIN_HEIGHT = 525;
    public static final double MIN_WIDTH = 350;
    
    public static GridPane container = new GridPane();
    
    public static GridPane keypad = new GridPane();
    
    public static GridPane monitor = new GridPane();
    public static Label displayExpression = new Label();
    public static Label displayCurrentNumber = new Label("0");
    public static Label displayHex = new Label("0");
    public static Label displayDec = new Label("0");
    public static Label displayOct = new Label("0");
    public static Label displayBin = new Label("0");
    
    public static Label[] integerDisplays = 
        {displayCurrentNumber, displayHex, displayDec, displayOct, displayBin};
    
    String[][] labels = {
        {"Lsh", "Rsh", "Or", "Xor", "Not", "And"},
           {"↑", "Mod", "CE", "Ｃ", "⌫", "÷"},
             {"A", "B", "7", "8", "9", "×"},     
             {"C", "D", "4", "5", "6", "−"},
             {"E", "F", "1", "2", "3", "+"}, 
             {"(", ")", "±", "0", ".", "="}
    };
    Button[][] buttons = new Button[labels.length][labels[0].length];
    
    @Override
    public void start(Stage primaryStage) {
//        Configure Stage
        primaryStage.setTitle("Calculator");
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
        
//        Configure GridPane root container
        container.setGridLinesVisible(true);
        container.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(100);
        container.getColumnConstraints().add(c1);
//        Upper Half
        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(100);
        container.getRowConstraints().add(r1);
//        Lower Half
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(100);
        container.getRowConstraints().add(r2);
        
//        Configure Monitor        
        displayExpression.setMaxWidth(Double.MAX_VALUE);
        displayExpression.setAlignment(Pos.CENTER_RIGHT);
        displayExpression.setPadding(new Insets(10,0,10,0));
        displayCurrentNumber.setMaxWidth(Double.MAX_VALUE);
        displayCurrentNumber.setAlignment(Pos.CENTER_RIGHT);
        displayExpression.setPadding(new Insets(0,0,10,0));
        displayCurrentNumber.setStyle("-fx-font: 24 arial;");
        
        monitor.add(displayExpression, 1, 0, 2, 1);
        monitor.add(displayCurrentNumber, 1, 1, 2, 1);
        monitor.add(displayHex, 1, 2);
        monitor.add(displayDec, 1, 3);
        monitor.add(displayOct, 1, 4);
        monitor.add(displayBin, 1, 5);
        monitor.add(new Label("HEX"), 0, 2);
        monitor.add(new Label("DEC"), 0, 3);
        monitor.add(new Label("OCT"), 0, 4);
        monitor.add(new Label("BIN"), 0, 5);
        ColumnConstraints m1 = new ColumnConstraints();
        m1.setPercentWidth(10);
        monitor.getColumnConstraints().add(m1);
        ColumnConstraints m2 = new ColumnConstraints();
        m2.setPercentWidth(90);
        monitor.getColumnConstraints().add(m2);
        monitor.setAlignment(Pos.BOTTOM_RIGHT);
                
//        Configure keypad
        keypad.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        for(int i = 0; i < labels.length; i++) {
            ColumnConstraints c = new ColumnConstraints();
            c.setPercentWidth(100);
            keypad.getColumnConstraints().add(i,c);
            RowConstraints r = new RowConstraints();
            r.setPercentHeight(100);
            keypad.getRowConstraints().add(i, r);
            for(int j = 0; j < labels[i].length; j++) {
                String label = labels[i][j];
                Button button = new Button(label);
                button.setId(label);
                button.setOnAction(handleActionEvent(button));
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                buttons[i][j] = button;
                keypad.add(button, j, i);
            }
        }
        
//        Scene initialization
        container.add(monitor, 0, 0);
        container.add(keypad, 0, 1);
                
        Scene scene = new Scene(container, MIN_WIDTH, MIN_HEIGHT);
        primaryStage.setScene(scene);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode().equals(KeyCode.MINUS))
                handleKeyEvent("−");
            if (e.getCode().equals(KeyCode.ENTER))
                handleKeyEvent("=");
            if (e.getCode().equals(KeyCode.DIGIT8) && e.isShiftDown())
                handleKeyEvent("×");
            if (e.getCode().equals(KeyCode.ASTERISK))
                handleKeyEvent("×");
            if (e.getCode().equals(KeyCode.SLASH))
                handleKeyEvent("÷");
            if (e.getCode().equals(KeyCode.BACK_SPACE))
                handleKeyEvent("⌫");
            if (e.getCode().equals(KeyCode.ESCAPE))
                handleKeyEvent("CE");
        });
        scene.setOnKeyTyped(e -> {
            handleKeyEvent(e.getCharacter());
        });
        scene.getStylesheets().add
          (Calculator.class.getResource("Calculator.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public EventHandler<ActionEvent> handleActionEvent(Button b) {
        String s = b.getText();
        EventHandler<ActionEvent> handle;
//        Numeric key action
        if (tryParseInt(s)) {
            handle = (ActionEvent e) -> {
                expression.pushNumeral(s);
                refresh(integerDisplays);
            };
        }
        
//        Operator key action
        else if (tryParseOp(s)) {
            handle = (ActionEvent e) -> {
                expression.pushOperation(parseOp(s));
                refresh(displayExpression);
            };
        }
        
//        Equals action
        else if (s.equals("=")){
            handle = (ActionEvent e) -> {
                expression = new ArithmeticExpression(solve(expression));
                refresh(integerDisplays);
                expression = new ArithmeticExpression();
                refresh(displayExpression);
            };
        }
        
//        Backspace action
        else if (s.equals("⌫")){
            handle = (ActionEvent e) -> {
                expression.backspace();
                refresh(integerDisplays);
                refresh(displayExpression);
            };
        } 
        
//        Decimal point action ***DISABLED***
        else if (s.equals(".")){
            handle = (ActionEvent e) -> {
//                disabled in programming mode
            };
        }
        
//        Clear action: clears the entire input
        else if (s.equals("Ｃ")){
            handle = (ActionEvent e) -> {
                expression.clear();
                refresh(integerDisplays);
                refresh(displayExpression);
            };            
        }
        
//        Clear Entry action: clears only the current number        
        else if (s.equals("CE")){
            handle = (ActionEvent e) -> {
                expression.clearEntry();
                refresh(integerDisplays);
            };            
        }
        
//        Sign Switch action: changes the sign of the current number
        else if (s.equals("±")) {
            handle = (ActionEvent e) -> {
                expression.switchSign();
                refresh(integerDisplays);
            };          
        }
        
//        Open Brace action
        else if (s.equals("(")){
            handle = (ActionEvent e) -> {
                expression.openBrace();
                refresh(integerDisplays);
                refresh(displayExpression);
            };
        }
        
//        Close brace action
        else if (s.equals(")")){
            handle = (ActionEvent e) -> {
                expression.closeBrace();
                refresh(integerDisplays);
                refresh(displayExpression);
            };
        }
        
//        Not action
        else if (s.equals("Not")){
            handle = (ActionEvent e) -> {
                expression.pushNot();
                refresh(integerDisplays);
                refresh(displayExpression);
            };
        }
        
//        Undefined Action
        else {
            handle = (ActionEvent e) -> {
                System.out.println("'" + s + "' " + "Button onAction undefined");
            };
        }
        return handle;
    }
    public void handleKeyEvent(String s) {
//        Numeric key action
        if (tryParseInt(s)) {
            expression.pushNumeral(s);
            refresh(integerDisplays);
        }
        
//        Operator key action
        else if (tryParseOp(s)) {
            expression.pushOperation(parseOp(s));
            refresh(displayExpression);
        }
        
//        Equals action
        else if (s.equals("=")){
            expression = new ArithmeticExpression(solve(expression));
            refresh(integerDisplays);
            expression = new ArithmeticExpression();
            refresh(displayExpression);
        }
        
//        Backspace action
        else if (s.equals("⌫")){
            expression.backspace();
            refresh(integerDisplays);
            refresh(displayExpression);
        } 
        
//        Decimal point action ***DISABLED***
        else if (s.equals(".")){
//            disabled in programming mode
        }
        
//        Clear action: clears the entire input
        else if (s.equals("Ｃ")){
            expression.clear();
            refresh(integerDisplays);
            refresh(displayExpression);
        }
        
//        Clear Entry action: clears only the current number        
        else if (s.equals("CE")){
            expression.clearEntry();
            refresh(integerDisplays);
        }
        
//        Sign Switch action: changes the sign of the current number
        else if (s.equals("±")) {
            expression.switchSign();
            refresh(integerDisplays);
        }
        
//        Open Brace action
        else if (s.equals("(")){
            expression.openBrace();
            refresh(integerDisplays);
            refresh(displayExpression);
        }
        
//        Close brace action
        else if (s.equals(")")){
            expression.closeBrace();
            refresh(integerDisplays);
            refresh(displayExpression);
        }
        
//        Not action
        else if (s.equals("Not")){
            expression.pushNot();
            refresh(integerDisplays);
            refresh(displayExpression);
        }
        
//        Undefined Action
        else {
            System.out.println("'" + s + "' " + "Button onAction undefined");
        }
    }
    
    static boolean tryParseInt(String s) {  
       try {  
            Integer.parseInt(s);  
            return true;  
        } catch (NumberFormatException e) {  
            return false;  
        }
    }
    static boolean tryParseInt(char c) {  
       try {  
            Integer.parseInt(Character.toString(c));
            return true;
        } catch (NumberFormatException e) {  
            return false;  
        }
    }
    static boolean tryParseOp(String s) {
        for (Operation o: Operation.values()){
            if (s.equals(o.toString())){
                return true;
            }
        }
        for (Operation o: Operation.values()){
            char op = o.toChar();
            for (char c: s.toCharArray()){
                if (c == op){
                    return true;
                }
            }
        }
        return false;
    }
    static boolean tryParseOp(char c) {
        for (Operation o: Operation.values()){
            char op = o.toChar();
            if (c == op){
                return true;
            }
        }
        return false;
    }
    static Operation parseOp(char c){
        for (Operation o: Operation.values()){
            if (o.toChar() == c){
                return o;
            }
        }
        return Operation.ADD;
    }
    static Operation parseOp(String s){
        for (Operation o: Operation.values()){
            if (s.equals(o.toString())){
                return o;
            }
        }
        char[] arr = s.toCharArray();
        for (char c : arr){
            for (Operation o: Operation.values()){
                if (o.toChar() == c){
                    return o;
                }
            }
        }
        return Operation.ADD;
    }
    
    private void refresh(Label... displays){
        int i = 0;
        String fullExpression = expression.toString();
        String currentNumber = expression.currentNumber();
        
        if(tryParseInt(currentNumber)){
            i = Integer.parseInt(currentNumber);
        }
        String hex = Integer.toHexString(i);
        String dec = Integer.toString(i);
        String oct = Integer.toOctalString(i);
        String bin = Integer.toBinaryString(i);
        
        
        for (Label l: displays){
            if (l == displayHex)
                l.setText(hex);
            else if (l == displayDec)
                l.setText(dec);
            else if (l == displayOct)
                l.setText(oct);
            else if (l == displayBin)
                l.setText(bin);
            else if (l == displayCurrentNumber)
                l.setText(currentNumber);
            else if (l == displayExpression)
                l.setText(fullExpression);
        }
    }
    
    private ArrayList<Operation> findOperations(String s){
        ArrayList<Operation> operations = new ArrayList<Operation>();
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (c == '('){
                int j = findClose(s.substring(i));
                i += j;
            }
            if (tryParseOp(c)){
                operations.add(parseOp(c));
            }
        }
        return operations;
    }
    public static int findClose(String s){
        int open = 0, j = 0;
        for (j = 0; j < s.length(); j++){
            char c = s.charAt(j);
            if (c == '(')
                open++;
            if (c == ')')
                open--;
            if (open == 0){
                break;
            }
        }
        return j;
    }

    private int solve(ArithmeticExpression expression){
        expression.simplify();
        String s = expression.toString();
        if (tryParseInt(s)){
            return Integer.parseInt(s);
        } else {
            ArrayList<Operation> operations = findOperations(s);
            quickSort(operations,0 ,operations.size()-1);
            Operation op = operations.get(0);
            ArithmeticExpression fx = null, gx = null;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '('){
                    int j = findClose(s.substring(i));
                    i += j;
                }
                if (c == op.toChar()){
                    fx = new ArithmeticExpression(s.substring(0, i-1));
                    gx = new ArithmeticExpression(s.substring(i+2));
                }
            }
            return op.operate(solve(fx), solve(gx));
        }
    }
    
    private static void quickSort (ArrayList<Operation> arr, int left, int right){
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
    }
    private static int partition (ArrayList<Operation> arr, int left, int right){
        int i = left, j = right;
        Operation tmp;
        Operation pivot = arr.get((left + right) / 2);
        while (i <= j) {
            while (arr.get(i).compareTo(pivot) < 0)
                i++;
            while (arr.get(j).compareTo(pivot) > 0)
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