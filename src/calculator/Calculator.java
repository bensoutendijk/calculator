/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 *
 * @author souten
 */
public class Calculator extends Application {
    
    public static final double MIN_HEIGHT = 525;
    public static final double MIN_WIDTH = 350;
    public static final double DEFAULT_GAP = 6;
    
    @Override
    public void start(Stage primaryStage) {
//        Window settings
        primaryStage.setTitle("Calculator");
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
        
//        container is the base content node
//        - it contains 1 column and 3 rows
        GridPane container = new GridPane();
        container.setAlignment(Pos.CENTER);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        container.getColumnConstraints().add(column1);
        RowConstraints[] rows = new RowConstraints[3];
        for (int i = 0; i < rows.length; i++)
            rows[i] = new RowConstraints();
        rows[0].setPercentHeight(40);
        rows[1].setPercentHeight(10);
        rows[2].setPercentHeight(50);
        container.getRowConstraints().addAll(rows);
        container.setHgap(0);
        container.setVgap(DEFAULT_GAP);
        
//        row 0: the monitor, outputs the calculations in hex, dec, oct, and bin

        Monitor m = new Monitor();
        GridPane.setFillWidth(m, true);
        GridPane.setFillHeight(m, true);
        
        container.add(m, 0, 0);
        
//        row 1: contains misc buttons i.e. bit-toggler, bit measurement, and memory buttons
        
//        row 2: the bottom row, keypadContainer contains all the buttons that create the calculation/function

        Keypad keypad = new Keypad();
        
        
        
        container.setGridLinesVisible(true);
        keypad.setGridLinesVisible(true);
        
        container.add(keypad, 0, 2);
        
        GridPane.setFillWidth(keypad, true);
        GridPane.setFillHeight(keypad, true);
        keypad.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        

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
    
}
