
import Paanakyma.Paanakymarivi;
import Kauppanakyma.*;
import Tyontekijanakyma.*;
import Luokat.*;
import Ilmoittaja.Ilmoittaja;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class Main extends Application implements Initializable {

    static Leiri leiri;
    static Kauppa kauppa;
    static Ilmoittaja ilmoittaja;
    static TyontekijanakymanKasittelija tk;
    static ArrayList<Slider> sliderit;
    static ArrayList<TextField> kentat;
    static ArrayList<Label> tekstit;
    static ArrayList<TableView> taulukot;
    static TextArea ilmoituskentta;
    static Raportti viimeisinRaportti;
    static Label paivalaskuriteksti;
    static int kaytettavienTyopaikkojenMaara;
    static int tyopaikkojenMaara;
    static int maxTickShow;
    static boolean voidaanHavita;
    static Parent root;

    public static void main(String[] args) {
        //Paaikkunan valmistelu ja avaus
        tyopaikkojenMaara = 3;
        kaytettavienTyopaikkojenMaara = 2;
        maxTickShow = 5;
        voidaanHavita = true;
        tk = new TyontekijanakymanKasittelija();
        ilmoittaja = new Ilmoittaja();

        //Olioiden valmistelu
        leiri = new Leiri();
        kauppa = new Kauppa();

        launch(args);

    }

    //JavaFX suorittaa taman metodien, kun main-metodin launch(args) suoritetaan
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (root == null) {
            //Main.fxml tiedoston lataus samasta kansiosta
            root = FXMLLoader.load(getClass().getResource("Main.fxml"));

            sliderit = new ArrayList<>();
            kentat = new ArrayList<>();
            tekstit = new ArrayList<>();
            taulukot = new ArrayList<>();
            
            //Paivalaskuritekstin haku
            paivalaskuriteksti = (Label) root.lookup("#paivalaskuriteksti");

            //Valmistele taulukot
            for (int i = 0; i < 3; i++) {
                taulukot.add((TableView) root.lookup("#taulukko" + i));
            }

            //Haetaan ilmoituskentta-elementti
            ilmoituskentta = (TextArea) root.lookup("#ilmoitusTeksti");

            //Slidereiden, kenttien ja tekstien valmistelu
            for (int i = 0; i < tyopaikkojenMaara; i++) {
                if (i >= kaytettavienTyopaikkojenMaara) {
                    root.lookup("#editVBox" + i).setDisable(true);
                    root.lookup("#tyontekijatTeksti" + i).setDisable(true);
                } else {
                    //Haetaan Slider-elementti
                    Slider slider = (Slider) root.lookup("#slider" + i);
                    sliderit.add(slider);
                    ////Lisataan kuuntelija, joka muuttaa lisaaTyontekijoita-listan arvoa, kun Slideria liikutetaan
                    slider.valueProperty().addListener((ObservableValue<? extends Number> arvo, Number vanha, Number uusi) -> {
                        if (vanha.intValue() != uusi.intValue()) {
                            leiri.setPalkattavienMaaraTyonpaikkaindeksilla((sliderit.indexOf(slider) + 1), (int) arvo.getValue().intValue());
                            paivitaKentta(sliderit.indexOf(slider));
                            paivitaTaulukot();
                        }
                    });

                    //Valmistellaan tekstikentta-elementti
                    TextField kentta = (TextField) root.lookup("#lisaaKentta" + i);
                    kentat.add(kentta);
                    kentta.setPromptText("Montako palkataan?");
                    ////Lisataan kuuntelija, joka asettaa palkattavien maaran, jos teksti vaihtui
                    kentta.textProperty().addListener((teksti, vanha, uusi) -> {
                        if (!vanha.equals(uusi)) {
                            int uusiArvo = 0;
                            if (!uusi.isEmpty() && tarkistaKentat() == null) {
                                uusiArvo = Integer.parseInt(kentta.getText());
                            }
                            leiri.setPalkattavienMaaraTyonpaikkaindeksilla(kentat.indexOf(kentta) + 1, uusiArvo);
                            paivitaTaulukot();

                        }
                    });
                    tekstit.add((Label) root.lookup("#tyontekijatTeksti" + i));
                }

            }

            //Nappien toimintojen maaritteleminen
            Button suoritaNappi = (Button) root.lookup("#suorita");
            suoritaNappi.setOnAction(e -> {
                Integer virheellinenKentta = tarkistaKentat();
                if (virheellinenKentta == null) {
                    //Suljetaan ikkuna
                    primaryStage.close();

                    //Katsotaan, riittaako raha tyontekijoiden palkkaamiseen
                    if (voidaanHavita && leiri.getRaha() < leiri.palkkoihinMenevaRaha()) {
                        ilmoittaja.nayta("Simualatio ohi", "Liian vähän rahaa");
                        System.exit(0);
                    }
                    leiri.kasittele();
                    ilmoituskentta.setText("");
                    paivitaPaivalaskuri();
                    viimeisinRaportti = leiri.viimeisinRaportti();
                    paivitaTaulukot();
                    asetaLisaaElementitNollaan();
                    paivitaLisaaelementit();
                    paivitaTyontekijatekstit();
                    primaryStage.show();
                } else {
                    ilmoittaja.nayta("Syötteessä vika", "Syotteen täytyy olla numero tai tyhjä");
                    kentat.get(virheellinenKentta).requestFocus();
                }

            });

            Button myyNappi = (Button) root.lookup("#myyNappi");
            myyNappi.setOnAction(e -> {
                HashMap<String, Integer> tuotteet = new HashMap<>();
                tuotteet.put("Puu", leiri.getPuu());
                double saatuRaha = leiri.kasitteleMyydyt(kauppa.avaa(tuotteet));
                if (saatuRaha > 0) {
                    lisaaIlmoituksiin("Myyntitulot: " + saatuRaha);
                    paivitaTaulukot();
                }
            });
        }
        
        paivitaPaivalaskuri();

        viimeisinRaportti = leiri.viimeisinRaportti();
        valmisteleTaulukot();
        paivitaTaulukot();

        //Ikkunan valmistelut ja naytto
        paivitaTyontekijatekstit();
        paivitaLisaaelementit();
        primaryStage.setTitle("Resurssienkeruusimulaattori");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    private Integer tarkistaKentat() { //Palauttaa true, jos OK
        for (TextField kentta : kentat) {
            if (!kentta.getText().isEmpty()) {
                try {
                    leiri.setPalkattavienMaaraTyonpaikkaindeksilla(kentat.indexOf(kentta) + 1, Integer.parseInt(kentta.getText()));
                } catch (Exception e) {
                    return kentat.indexOf(kentta);
                }
            }

        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //Paaikkunassa napit, joissa lukee "Tutki tyontekijoita", kutsuvat tata metodia.
    //Metodiin haettu apua taalta: http://stackoverflow.com/questions/25409044/javafx-multiple-buttons-to-same-handler
    @FXML
    public void tutkiTyolaisia(ActionEvent event) throws Exception {
        Button kutsuja = (Button) event.getSource();
        int numero;
        String ikkunanOtsikko;
        //Selvitetaan, mita bt-nappia painettiin
        if (kutsuja.getId().equals("bt1")) {
            numero = 1;
            ikkunanOtsikko = "Metsätyoläiset";
        } else if (kutsuja.getId().equals("bt2")) {
            numero = 2;
            ikkunanOtsikko = "Metsästäjät";
        } else if (kutsuja.getId().equals("bt3")) {
            numero = 3;
            ikkunanOtsikko = "Kaivostyoläiset";
        } else {
            return;
        }
        leiri.kasittelePotkittavat(tk.naytaMuunneltu(leiri.tyontekijatTyopaikkaindksilla(numero), leiri.getPotkittavat(), ikkunanOtsikko));
        paivitaTaulukot();
    }

    private void paivitaTaulukot() {
        //Taytetaan vuoron aikana tapahtumien nayttava taulukko (ylin)
        ArrayList<Paanakymarivi> vuoronTapahtumat = new ArrayList<>();
        vuoronTapahtumat.add(new Paanakymarivi("Hakattu puu", "" + viimeisinRaportti.getSaatuPuu()));
        vuoronTapahtumat.add(new Paanakymarivi("Metsästetty aterioita", "" + viimeisinRaportti.getSaadutAteriat()));
        if (viimeisinRaportti.getToheloijat().size() > 0) {
            vuoronTapahtumat.add(new Paanakymarivi("Toheloinnit", "" + viimeisinRaportti.getToheloijat().size()));
        }
        ObservableList<Paanakymarivi> OBtapahtumat = FXCollections.observableArrayList(vuoronTapahtumat);
        taulukot.get(0).setItems(OBtapahtumat);

        //Taytetaan nykyisen tilanteen nayttava taulukko (keskimmainen)
        ArrayList<Paanakymarivi> nykyinenTilanne = new ArrayList<>();
        nykyinenTilanne.add(new Paanakymarivi("Metsän puiden määrä", "" + leiri.getMetsanKoko()));
        nykyinenTilanne.add(new Paanakymarivi("Puu", "" + leiri.getPuu()));
        nykyinenTilanne.add(new Paanakymarivi("Ateriat", "" + leiri.getAterioidenMaara()));
        nykyinenTilanne.add(new Paanakymarivi("Raha", "" + leiri.getRaha()));
        ObservableList<Paanakymarivi> OBnykyinen = FXCollections.observableArrayList(nykyinenTilanne);
        taulukot.get(1).setItems(OBnykyinen);

        //Taytetaan oletettavien tapahtumien nayttava taulukko (alin)
        ArrayList<Paanakymarivi> tulevat = new ArrayList<>();
        double maksettavaPalkka = leiri.palkkoihinMenevaRaha();
        tulevat.add(new Paanakymarivi("Palkkoihin menevä raha", "" + maksettavaPalkka));
        ObservableList<Paanakymarivi> OBtulevat = FXCollections.observableArrayList(tulevat);
        taulukot.get(2).setItems(OBtulevat);
    }

    private void valmisteleTaulukot() {

        for (TableView taulukko : taulukot) {
            TableColumn<Paanakymarivi, String> kohdeSarake = new TableColumn<>("Kohde");
            kohdeSarake.setMinWidth(200);
            kohdeSarake.setCellValueFactory(new PropertyValueFactory<>("kohde"));

            TableColumn<Paanakymarivi, String> tietoSarake = new TableColumn<>("Tietoa");
            tietoSarake.setMinWidth(200);
            tietoSarake.setCellValueFactory(new PropertyValueFactory<>("tieto"));
            taulukko.getColumns().addAll(kohdeSarake, tietoSarake);
        }

    }

    private void paivitaKentta(int i) {
        kentat.get(i).setText("" + leiri.getPalkattavienMaaraTyonpaikkaindeksilla(i + 1));
    }

    private void paivitaTyontekijatekstit() {
        ArrayList<Integer> maarat = leiri.tyontekijoidenMaarat();
        for (int i = 0; i < kaytettavienTyopaikkojenMaara; i++) {
            if (!tekstit.get(i).isDisabled()) {
                int maara = maarat.get(i + 1);
                tekstit.get(i).setText("Tyontekijät: " + maara);
            }
        }
    }

    private void paivitaLisaaelementit() {
        ArrayList<Integer> maarat = leiri.tyontekijoidenMaarat();
        for (int i = 0; i < kaytettavienTyopaikkojenMaara; i++) {
            int maara = maarat.get(i + 1);
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

    private void lisaaIlmoituksiin(String lisattava) {
        if (ilmoituskentta.getText().isEmpty()) {
            ilmoituskentta.setText(lisattava);
            return;
        }
        ilmoituskentta.setText(ilmoituskentta.getText() + "\n" + lisattava);
    }

    private void asetaLisaaElementitNollaan() {
        for (int i = 0; i < sliderit.size(); i++) {
            sliderit.get(i).setValue(0);
            kentat.get(i).setText("");
        }
    }
    
    private void paivitaPaivalaskuri() {
        paivalaskuriteksti.setText("Päivä: " + leiri.getPaiva());
    }

}
