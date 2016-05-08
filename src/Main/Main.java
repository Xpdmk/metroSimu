package Main;

import Tyontekijanakyma.*;
import luokat.Raportti;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import luokat.Leiri;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Main extends Application implements Initializable {

    static Leiri leiri;
    static TyontekijanakymanKasittelija tk;
    static ArrayList<Integer> lisaaTyontekjoita;
    static ArrayList<Slider> sliderit;
    static ArrayList<TextField> kentat;
    static ArrayList<Integer> maarat;
    static ArrayList<Label> tekstit;
    static ArrayList<Integer> potkittavat;
    static ArrayList<TableView> taulukot;
    static Raportti viimeisinRaportti;
    static boolean voidaanSulkea;
    static int kaytettavienTyopaikkojenMaara;
    static int tyopaikkojenMaara;
    static int maxTickShow;
    static Parent root;

    public static void main(String[] args) {
        //Pääikkunan valmistelu ja avaus
        tyopaikkojenMaara = 3;
        voidaanSulkea = true;
        kaytettavienTyopaikkojenMaara = 2;
        maxTickShow = 5;
        maarat = new ArrayList<>(Collections.nCopies(kaytettavienTyopaikkojenMaara, 0));
        tk = new TyontekijanakymanKasittelija();

        //Leirin valmistelu
        leiri = new Leiri();

        launch(args);

    }

    //Javafx suorittaa tämän metodien, kun main-metodin launch(args) suoritetaan
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Main.fxml tiedoston lataus samasta kansiosta
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        lisaaTyontekjoita = new ArrayList<>(Collections.nCopies(kaytettavienTyopaikkojenMaara, 0));
        potkittavat = new ArrayList<>();
        sliderit = new ArrayList<>();
        kentat = new ArrayList<>();
        tekstit = new ArrayList<>();
        taulukot = new ArrayList<>();

        //Valmistele taulukot
        for (int i = 0; i < 3; i++) {
            taulukot.add((TableView) root.lookup("#taulukko" + i));
        }

        viimeisinRaportti = leiri.viimeisinRaportti();
        valmisteleTaulukot();
        paivitaTaulukot();

        //Slidereiden, kenttien ja tekstien valmistelu
        for (int i = 0; i < tyopaikkojenMaara; i++) {
            if (i >= kaytettavienTyopaikkojenMaara) {
                root.lookup("#editVBox" + i).setDisable(true);
                root.lookup("#tyontekijatTeksti" + i).setDisable(true);
            } else {
                //Haetaan Slider-elementti
                Slider slider = (Slider) root.lookup("#slider" + i);
                sliderit.add(slider);
                ////Lisätään kuuntelija, joka muuttaa lisaaTyontekijoita-listan arvoa, kun Slideria liikutetaan
                slider.valueProperty().addListener((ObservableValue<? extends Number> arvo, Number vanha, Number uusi) -> {
                    if (vanha.intValue() != uusi.intValue()) {
                        lisaaTyontekjoita.set(sliderit.indexOf(slider), (int) arvo.getValue().intValue());
                        paivitaKentta(sliderit.indexOf(slider));
                        paivitaTaulukot();
                    }
                });

                //Valmistellaan tekstikenttä-elementti
                TextField kentta = (TextField) root.lookup("#lisaaKentta" + i);
                kentat.add(kentta);
                kentta.setPromptText("Montako palkataan?");
                ////Lisätään kuuntelija, joka tarkistaa, onko syöte numero
                kentta.focusedProperty().addListener((arvo, vanha, uusi) -> {
                    if (vanha && !kentta.getText().isEmpty()) {
                        if (tarkistaKentat(true)) {
                            voidaanSulkea = true;
                        } else {
                            voidaanSulkea = false;
                        }
                    }
                });
                kentta.textProperty().addListener((teksti, vanha, uusi) -> {
                    if (!vanha.equals(uusi)) {
                        int uusiArvo = 0;
                        if (!uusi.isEmpty() && tarkistaKentat(false)) {
                            uusiArvo = Integer.parseInt(kentta.getText());
                        }
                        lisaaTyontekjoita.set(kentat.indexOf(kentta), uusiArvo);
                        paivitaTaulukot();
                    }
                });
                tekstit.add((Label) root.lookup("#tyontekijatTeksti" + i));
            }

        }
        //Nappien toimintojen määritteleminen
        Button suoritaNappi = (Button) root.lookup("#suorita");
        suoritaNappi.setOnAction(e -> {
            if (voidaanSulkea) {
                //Suljetaan ikkuna
                primaryStage.close();

                //Katsotaan, riittääkö raha työntekijöiden palkkaamiseen
                if (leiri.getRaha() < leiri.palautaPalkkoihinMenevaRaha(potkittavat, lisattavienMaara())) {
                    JOptionPane.showMessageDialog(null, "GAME OVER, liian vähän rahaa");
                    System.exit(0);
                }

                //Potkitaan ulos tyontekijat
                leiri.poistaTyontekijat(potkittavat);

                //Palkataan työntekijät käyttäjän syöttöjen mukaan
                for (int i = 0; i < lisaaTyontekjoita.size(); i++) {

                    for (int k = 0; k < lisaaTyontekjoita.get(i); k++) {
                        leiri.palkkaaTyontekija(i + 1);
                    }
                }

                leiri.kasittele();
                try {
                    start(new Stage());
                } catch (Exception error) {
                    primaryStage.close();
                }
            }

        });

        //Ikkunan valmistelut ja näyttö
        paivitaTyontekijatekstit();
        paivitaLisaaelementit();
        primaryStage.setTitle("Resurssienkeruusimulaattori");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    private boolean tarkistaKentat(boolean ilmoitetaan) { //Palauttaa true, jos OK
        for (TextField kentta : kentat) {
            if (!kentta.getText().isEmpty()) {
                try {
                    lisaaTyontekjoita.set(kentat.indexOf(kentta), Integer.parseInt(kentta.getText()));
                } catch (Exception e) {
                    if (ilmoitetaan) {
                        JOptionPane.showMessageDialog(null, "Syötteen täytyy olla numero tai tyhjä");
                        kentta.requestFocus();
                    }

                    return false;
                }
            }

        }
        return true;
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
        kasittelePotkittavat(tk.naytaMuunneltu(leiri.palautaTyontekijatTyopaikkaindksilla(numero), potkittavat, ikkunanOtsikko));
        paivitaTaulukot();
    }

    private void kasittelePotkittavat(HashMap<Integer, Boolean> muutetut) {
        for (Map.Entry<Integer, Boolean> pidetaanko : muutetut.entrySet()) {
            if (pidetaanko.getValue()) {
                for (Integer potkittava : potkittavat) {
                    if (pidetaanko.getKey().equals(potkittava)) {
                        potkittavat.remove(potkittava);
                    }
                    if (potkittavat.isEmpty()) {
                        break;
                    }
                }
            } else {
                potkittavat.add(pidetaanko.getKey());
            }
        }
    }

    private void paivitaTaulukot() {
        //Täytetään vuoron aikana tapahtumien näyttävä taulukko (ylin)
        ArrayList<taulukkoTietue> vuoronTapahtumat = new ArrayList<>();
        vuoronTapahtumat.add(new taulukkoTietue("Hakattu puu", "" + viimeisinRaportti.getSaatuPuu()));
        vuoronTapahtumat.add(new taulukkoTietue("Metsästetty aterioita", "" + viimeisinRaportti.getSaadutAteriat()));
        vuoronTapahtumat.add(new taulukkoTietue("Myyntitulot", "" + viimeisinRaportti.getMyyntitulot()));
        ObservableList<taulukkoTietue> OBtapahtumat = FXCollections.observableArrayList(vuoronTapahtumat);
        taulukot.get(0).setItems(OBtapahtumat);

        //Täytetään nykyisen tilanteen näyttävä taulukko (keskimmäinen)
        ArrayList<taulukkoTietue> nykyinenTilanne = new ArrayList<>();
        nykyinenTilanne.add(new taulukkoTietue("Metsän puiden määrä", "" + leiri.getMetsanKoko()));
        nykyinenTilanne.add(new taulukkoTietue("Puu", "" + leiri.getPuu()));
        nykyinenTilanne.add(new taulukkoTietue("Ateriat", "" + leiri.getAterioidenMaara()));
        nykyinenTilanne.add(new taulukkoTietue("Raha", "" + leiri.getRaha()));
        ObservableList<taulukkoTietue> OBnykyinen = FXCollections.observableArrayList(nykyinenTilanne);
        taulukot.get(1).setItems(OBnykyinen);

        //Täytetään oletettavien tapahtumien näyttävä taulukko (alin)
        ArrayList<taulukkoTietue> tulevat = new ArrayList<>();
        double maksettavaPalkka = leiri.palautaPalkkoihinMenevaRaha(potkittavat, lisattavienMaara());
        tulevat.add(new taulukkoTietue("Palkkoihin menevä raha", "" + maksettavaPalkka));
        ObservableList<taulukkoTietue> OBtulevat = FXCollections.observableArrayList(tulevat);
        taulukot.get(2).setItems(OBtulevat);
    }

    private void valmisteleTaulukot() {

        for (TableView taulukko : taulukot) {
            TableColumn<taulukkoTietue, String> kohdeSarake = new TableColumn<>("Kohde");
            kohdeSarake.setMinWidth(200);
            kohdeSarake.setCellValueFactory(new PropertyValueFactory<>("kohde"));

            TableColumn<taulukkoTietue, String> tietoSarake = new TableColumn<>("Tietoa");
            tietoSarake.setMinWidth(200);
            tietoSarake.setCellValueFactory(new PropertyValueFactory<>("tieto"));
            taulukko.getColumns().addAll(kohdeSarake, tietoSarake);
        }

    }

    private void paivitaKentat() {
        for (int i = 0; i < kentat.size(); i++) {
            kentat.get(i).setText("" + lisaaTyontekjoita.get(i));

        }
    }

    private void paivitaKentta(int i) {
        kentat.get(i).setText("" + lisaaTyontekjoita.get(i));
    }

    private void paivitaTyontekijatekstit() {
        for (int i = 0; i < maarat.size(); i++) {
            maarat.set(i, leiri.palautaTyontekijoidenMaaraTyopaikkaindeksilla(i + 1));
            if (!tekstit.get(i).isDisabled()) {
                tekstit.get(i).setText("Työntekijät: " + maarat.get(i));
            }
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

    private Integer lisattavienMaara() {
        int maara = 0;
        for (Integer lisattava : lisaaTyontekjoita) {
            maara += lisattava;
        }
        return maara;
    }

}
