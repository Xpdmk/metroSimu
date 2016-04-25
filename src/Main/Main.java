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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Main extends Application implements Initializable {

    static Leiri leiri;
    static int lisaaMetsatyotTyontekijoita;
    static int lisaaMetsastysTyontekijoita;
    static int lisaaKaivosTyontekijoita;
    static Slider s1;
    static Slider s2;
    static Slider s3;
    static TextField k1;
    static TextField k2;
    static TextField k3;
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
        lisaaMetsatyotTyontekijoita = 0;
        lisaaMetsastysTyontekijoita = 0;
        lisaaKaivosTyontekijoita = 0;
        
        //Leirin valmistelu
        leiri = new Leiri();
        lisaaMetsatyotTyontekijoita = 0;
        lisaaMetsastysTyontekijoita = 0;
        lisaaKaivosTyontekijoita = 0;
        leiri.lisaaTyontekijat(new Tyontekija[]{new Tyontekija(0.2, 2.4, 1, 1, 20, 0)});
        
        //Main.fxml tiedoston lataus samasta kansiosta
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        //Slidereiden valmistelu
        s1 = (Slider) root.lookup("#metsatyotSlider");
        s2 = (Slider) root.lookup("#metsastysSlider");
        s3 = (Slider) root.lookup("#kaivosSlider");
        
        //Kenttien valmistelu
        k1 = (TextField) root.lookup("#lisaaKentta1");
        k2 = (TextField) root.lookup("#lisaaKentta2");
        k3 = (TextField) root.lookup("#lisaaKentta3");
        
        //Tekstien valmistelu
        metsatyotTyontekijat = (Text) root.lookup("#metsatyotTyontekijat");
        metsastysTyontekijat = (Text) root.lookup("#metsastysTyontekijat");
        kaivosTyontekijat = (Text) root.lookup("#kaivosTyontekijat");
        
        //Ikkunan valmistelut ja näyttö
        paivita();
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
    }

    @FXML
    private void paivita() {
        int metsatyotMaara = leiri.palautaTyontekijoidenMaaraTyopaikkaindeksilla(1);
        int metsastysMaara = leiri.palautaTyontekijoidenMaaraTyopaikkaindeksilla(2);
        int kaivosMaara = leiri.palautaTyontekijoidenMaaraTyopaikkaindeksilla(3);
        int maxTickShow = 20;
        
        metsatyotTyontekijat.setText("Työntekijät: " + metsatyotMaara);
        metsastysTyontekijat.setText("Työntekijät: " + metsastysMaara);
        kaivosTyontekijat.setText("Työntekijät: " + kaivosMaara);
        
        k1.setPromptText("Montako lisätään. Slider: " + lisaaMetsatyotTyontekijoita);
        k2.setPromptText("Montako lisätään. Slider: " + lisaaMetsastysTyontekijoita);
        k3.setPromptText("Montako lisätään. Slider: " + lisaaKaivosTyontekijoita);
        
        s1.setMin(0);
        s2.setMin(0);
        s3.setMin(0);
        
        s1.setMax(metsatyotMaara*3);
        s2.setMax(metsastysMaara*3);
        s3.setMax(kaivosMaara*3);
        
        s1.setValue(lisaaMetsatyotTyontekijoita);
        s2.setValue(lisaaMetsastysTyontekijoita);
        s3.setValue(lisaaKaivosTyontekijoita);
        
        s1.setBlockIncrement(1);
        s2.setBlockIncrement(1);
        s3.setBlockIncrement(1);
        
        s1.setSnapToTicks(true);
        s2.setSnapToTicks(true);
        s3.setSnapToTicks(true);
        
        if (metsatyotMaara > maxTickShow) {
            s1.setShowTickMarks(false);
            s1.setShowTickLabels(false);
        }
        if (metsastysMaara > maxTickShow) {
            s2.setShowTickMarks(false);
            s2.setShowTickLabels(false);
        }
        if (kaivosMaara > maxTickShow) {
            s3.setShowTickMarks(false);
            s2.setShowTickLabels(false);
        }
    }

}
