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

    static Leiri leiri;
    static int lisaaMetsatyotTyontekijoita;
    static int lisaaMetsastysTyontekijoita;
    static int lisaaKaivosTyontekijoita;
    static Parent root;

    //Main.fxml -tiedoston elementtien id:den määritys. 
    //Ei tarvitse määritellä, riittää, että se vastaa fxml-tiedoston id:tä
    @FXML
    private Text metsatyotTyontekijat;
    @FXML
    private Text metsastysTyontekijat;
    @FXML
    private Text kaivosTyontekijat;
    @FXML
    private Button bt1;
    @FXML
    private Button bt2;
    @FXML
    private Button bt3;

    public static void main(String[] args) {
        //Pääikkunan valmistelu ja avaus
        launch(args);

    }

    //Javafx suorittaa tämän metodien, kun main-metodin launch(args) suoritetaan
    @Override
    public void start(Stage primaryStage) throws Exception {
        Boolean kaynnissa = true;
        lisaaMetsatyotTyontekijoita = 0;
        lisaaMetsastysTyontekijoita = 0;
        lisaaKaivosTyontekijoita = 0;
        
        //Leirin valmistelu
        leiri = new Leiri();
        leiri.lisaaTyontekijat(new Tyontekija[]{new Tyontekija(0.2,2.4,1,1)});
        
        //Main.fxml tiedoston lataus samasta kansiosta
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        //Ikkunan valmistelut ja näyttö
        primaryStage.setTitle("Resurssienkeruusimulaattori");
        paivitaLuvut();
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
    public void tutkiTyolaisia(ActionEvent event) throws Exception {
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
        ArrayList<Integer> poistettavat = new TyontekijanakymanKasittelija().nayta(leiri.palautaTyontekijatTyopaikkaindksilla(numero), ikkunanOtsikko);

        leiri.poistaTyontekijat(poistettavat);

        paivitaLuvut();
    }

    @FXML
    private void paivitaLuvut() {
        ((Text) root.lookup("#metsatyotTyontekijat")).setText("" + leiri.palautaTyontekijoidenMaaraTyopaikkaindeksilla(1) + "+" + lisaaMetsatyotTyontekijoita);
        ((Text) root.lookup("#metsastysTyontekijat")).setText("" + leiri.palautaTyontekijoidenMaaraTyopaikkaindeksilla(2) + "+" + lisaaMetsastysTyontekijoita);
        ((Text) root.lookup("#kaivosTyontekijat")).setText("" + leiri.palautaTyontekijoidenMaaraTyopaikkaindeksilla(3) + "+" + lisaaKaivosTyontekijoita);
    }

}
