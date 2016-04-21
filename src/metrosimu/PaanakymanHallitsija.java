/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrosimu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class PaanakymanHallitsija implements Initializable {
    
    @FXML
    private Text metsatyotTyontekijat;
    private Text metsastysTyontekijat;
    private Text kaivosTyontekijat;
    
    private Button bta1;
    private Button bta2;
    private Button bta3;
    
    //http://stackoverflow.com/questions/14085076/javafx-pass-fxid-to-controller-or-parameter-in-fxml-onaction-method
    
    //private Nappiohjain nappiohjain = new Nappiohjain();
    
    @FXML
    private void tutkiTyolaisia(ActionEvent event) {
        //nappiohjain.handle(event);
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    /**
    private class Nappiohjain implements EventHandler<ActionEvent>{
        //@Override
        public void handle(ActionEvent event) {
            if (event.getSource().equals(bta1)) {
              System.out.println("1");
            } else if (event.getSource().equals(bta2)) {
              System.out.println("2");
            }
        }
    }
    **/
    
}
