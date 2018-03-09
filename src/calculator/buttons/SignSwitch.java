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
public class SignSwitch extends Button {
//    Switches the sign on the number currently being entered
    //    No-arg constructor
    public SignSwitch() {
        this.setText("±");
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                Handle the event as an sign switch button
            }
        });
    }
    
}
