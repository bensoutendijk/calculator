/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author souten
 */
public class IntegerButton extends Button{
    int value;
    
//    No-arg constructor
    public IntegerButton() {
        this.value = 1;
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                Handle the event as an integer
            }
        });
    }
    
    public IntegerButton(int value) {
        this.value = value;
        this.setText(Integer.toString(value));
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                Handle the event as an integer
                
            }
        });
    }
    
}
