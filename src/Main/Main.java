package Main;

import Tyontekijanakyma.TyontekijanakymanKasittelija;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class Main extends Application implements Initializable {

    static Leiri leiri;
    static ArrayList<Integer> lisaaTyontekjoita;
    static ArrayList<Slider> sliderit;
    static ArrayList<TextField> kentat;
    static ArrayList<Integer> maarat;
    static ArrayList<Label> tekstit;
    static int kaytettavienTyopaikkojenMaara;
    static int tyopaikkojenMaara;
    static int maxTickShow;
    static Parent root;

    public static void main(String[] args) {
        //Pääikkunan valmistelu ja avaus
        launch(args);

    }

    //Javafx suorittaa tämän metodien, kun main-metodin launch(args) suoritetaan
    @Override
    public void start(Stage primaryStage) throws Exception {
        tyopaikkojenMaara = 3;
        kaytettavienTyopaikkojenMaara = 2;
        maxTickShow = 20;
        lisaaTyontekjoita = new ArrayList<>(Collections.nCopies(kaytettavienTyopaikkojenMaara, 0));
        sliderit = new ArrayList<>();
        kentat = new ArrayList<>();
        maarat = new ArrayList<>(Collections.nCopies(kaytettavienTyopaikkojenMaara, 0));
        tekstit = new ArrayList<>();

        //Leirin valmistelu
        leiri = new Leiri();
        leiri.lisaaTyontekijat(new Tyontekija[]{new Tyontekija(0.2, 2.4, 1, 1, 20, 0)});

        //Main.fxml tiedoston lataus samasta kansiosta
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        //Slidereiden, kenttien ja tekstien valmistelu
        for (int i = 0; i < tyopaikkojenMaara; i++) {
            if (i >= kaytettavienTyopaikkojenMaara)  {
                root.lookup("#editVBox" + i).setDisable(true);
                root.lookup("#tyontekijatTeksti" + i).setDisable(true);
            } else {
                //Haetaan Slider-elementti
                Slider slider = (Slider) root.lookup("#slider" + i);
                //Määritetään, mitä tehdään, kun sliderin arvo muuttuu
                slider.valueProperty().addListener((ObservableValue<? extends Number> arvo, Number vanha, Number uusi) -> {
                    if (vanha.intValue() != uusi.intValue()) {
                        lisaaTyontekjoita.set(sliderit.indexOf(slider), (int) arvo.getValue().intValue());
                        paivitaKentat();
                    }
                });
                sliderit.add(slider);
                kentat.add((TextField) root.lookup("#lisaaKentta" + i));
                tekstit.add((Label) root.lookup("#tyontekijatTeksti" + i));
            }

        }

        //Ikkunan valmistelut ja näyttö
        paivita();
        primaryStage.setTitle("Resurssienkeruusimulaattori");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

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

    private void paivita() {
        paivitaTyontekijoidenMaarat();

        for (int i = 0; i < tekstit.size(); i++) {
            if (!tekstit.get(i).isDisabled()) {
                tekstit.get(i).setText("Työntekijät: " + maarat.get(i));
            }
        }

        paivitaKentat();
        paivitaLisaaelementit();

    }

    private void paivitaKentat() {
        for (int i = 0; i < kentat.size(); i++) {
            kentat.get(i).setPromptText("Montako lisätään. Slider: " + lisaaTyontekjoita.get(i));
        }
    }

    private void paivitaTyontekijoidenMaarat() {
        for (int i = 0; i < maarat.size(); i++) {
            maarat.set(i, leiri.palautaTyontekijoidenMaaraTyopaikkaindeksilla(i + 1));

        }
    }

    private void paivitaLisaaelementit() {

        for (int i = 0; i < maarat.size(); i++) {
            int maara = maarat.get(i);
            Slider slider = sliderit.get(i);
            slider.setMin(0);
            slider.setBlockIncrement(1);
            slider.setSnapToTicks(true);
            if (maara > maxTickShow) {
                slider.setShowTickMarks(false);
                slider.setShowTickLabels(false);
            }
            if (maara < 1) {
                sliderit.get(i).setMax(3);
            } else {
                sliderit.get(i).setMax(maara * 3);
            }
        }
    }

}
