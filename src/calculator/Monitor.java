/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.scene.text.Text;

/**
 *
 * @author souten
 */
public class Monitor extends Text implements ActionListener {
    
    
    public Monitor() {
        
        this.setText("Hello World!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setText(e.getActionCommand());
    }
    
    public static void update(ActionEvent event){
        
    }
    
    

}
