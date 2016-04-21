package Paanakyma;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class PaanakymanKasittelija implements Initializable {
    
    @FXML
    private Text metsatyotTyontekijat;
    private Text metsastysTyontekijat;
    private Text kaivosTyontekijat;
    
    private Button bt1;
    private Button bt2;
    private Button bt3;
    
    //Metodi täältä: http://stackoverflow.com/questions/25409044/javafx-multiple-buttons-to-same-handler
    @FXML
    private void tutkiTyolaisia(ActionEvent event) {
        Button kutsuja = (Button) event.getSource();
        if (kutsuja.getId().equals("bt1")) {
            //Pyydetään leiriltä lista työntekijöistä, joilla työpaikkaindeksi 1
        } 
        if (kutsuja.getId().equals("bt2")) {
            //Pyydetään leiriltä lista työntekijöistä, joilla työpaikkaindeksi 2
        }
        if (kutsuja.getId().equals("bt3")) {
            //Pyydetään leiriltä lista työntekijöistä, joilla työpaikkaindeksi 3
        }
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
