package Main;


import Tyontekijanakyma.TyontekijanakymanKasittelija;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import luokat.Leiri;
import luokat.Tyontekija;
import static javafx.application.Application.launch;


public class Main extends Application implements Initializable {
    //Omat oliot
    private Leiri leiri;
    
    //Main.fxml -tiedoston elementtien id:den määritys. 
    //Ei tarvitse määritellä, riittää, että se vastaa fxml-tiedoston id:tä
    @FXML private Text metsatyotTyontekijat;
    @FXML private Text metsastysTyontekijat;
    @FXML private Text kaivosTyontekijat;
    @FXML private Button bt1;
    @FXML private Button bt2;
    @FXML private Button bt3;
    
    public static void main(String[] args) {
        //TODO: this.leiri = new Leiri();
        
        //Pääikkunan valmistelu ja avaus
        launch(args);
        
    }
    
    //Javafx suorittaa tämän metodien, kun main-metodin launch(args) suoritetaan
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Main.fxml tiedoston lataus samasta sijainnista
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        leiri = new Leiri();
        //Ikkunan valmistelut ja näyttö
        primaryStage.setTitle("Resurssienkeruusimulaattori");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    //Omien olioiden määrittely
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    //Pääikkunassa napit, joissa lukee "Tutki työntekijöitä", kutsuvat tätä metodia.
    //Metodiin haettu apua täältä: http://stackoverflow.com/questions/25409044/javafx-multiple-buttons-to-same-handler
    @FXML
    private void tutkiTyolaisia(ActionEvent event) throws Exception {
        Button kutsuja = (Button) event.getSource();
        int numero;
        String ikkunanOtsikko;
        //Selvitetään, mitä bt-nappia painettiin
        if (kutsuja.getId().equals("bt1")) {
            numero = 1;
            ikkunanOtsikko = "Metsätyöläiset";
        } else if (kutsuja.getId().equals("bt2")) {
            numero = 2;
            ikkunanOtsikko = "Metsästäjät";
        } else if (kutsuja.getId().equals("bt3")) {
            numero = 3;
            ikkunanOtsikko = "Kaivostyöläiset";
        } else {
            return;
        }
        ArrayList<Integer> poistettavienTyontekijoidenkoodit = new TyontekijanakymanKasittelija().nayta(palautaTyontekijatKoodilla(numero), ikkunanOtsikko);
        //TODO: Käske leiri-oliota poistamaan työntekijäkoodin perusteella työntekijät
    }
    
    //Apumetodi työntekijöiden hakuun Työntekijän instanssimuuttuja tyontekijakoodin perusteella
    private ArrayList<Tyontekija> palautaTyontekijatKoodilla(int indeksi) {
        ArrayList<Tyontekija> tyontekijat = new ArrayList();
        
        //TODO: Hae oikeat työntekijät
        
        return tyontekijat;
    }
    
    
    
}
