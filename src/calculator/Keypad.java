/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import calculator.buttons.IntegerButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 *
 * @author souten
 */
public class Keypad extends GridPane{
    String[][] labels = {
        {"Lsh", "Rsh", "Or", "Xor", "Not", "And"},
           {"↑", "Mod", "CE", "C", "⌫", "÷"},
             {"A", "B", "7", "8", "9", "×"},     
             {"C", "D", "4", "5", "6", "−"},
             {"E", "F", "1", "2", "3", "+"}, 
             {"(", ")", "±", "0", ".", "="}
    };
    
    Button[][] buttons = new Button[6][6];
    
    
    public Keypad(){
        //        initialize buttons
        buttons[5][3] = new IntegerButton(0);
        buttons[4][2] = new IntegerButton(1);
        buttons[4][3] = new IntegerButton(2);
        buttons[4][4] = new IntegerButton(3);
        buttons[3][2] = new IntegerButton(4);
        buttons[3][3] = new IntegerButton(5);
        buttons[3][4] = new IntegerButton(6);
        buttons[2][2] = new IntegerButton(7);
        buttons[2][3] = new IntegerButton(8);
        buttons[2][4] = new IntegerButton(9);
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++) {
            if(buttons[i][j] != null)
//                GridPane.setFillWidth(buttons[i][j], true);
//                GridPane.setFillHeight(buttons[i][j], true);
//                buttons[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                this.add(buttons[i][j], j, i);
            }
        }

        
    }
    
}
