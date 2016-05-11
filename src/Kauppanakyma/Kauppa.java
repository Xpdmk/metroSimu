package Kauppanakyma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Kauppa {

    private double puunHinta;
    private double annettavatRahat;
    private boolean muutoksetTallennetaan;
    private ArrayList<Kauppatuoterivi> tuoterivit;
    private HashMap<String, Double> tuotteidenHinnat;
    private ArrayList<String> tuotteidenNimet;

    private Stage ikkuna;
    private Parent asettelu;
    private Button myy;
    private Button peruuta;
    private Label tulot;
    private TableView taulukko;

    public Kauppa() {
        puunHinta = 10;
        annettavatRahat = 0;
    }

    public HashMap<String, double[]> avaa(HashMap<String, Integer> tuotteidenMaarat) {
        //Alustetaan muuttujat
        HashMap<String, double[]> palautus = new HashMap<>();
        tuotteidenHinnat = new HashMap<>();
        tuotteidenNimet = new ArrayList<>();
        muutoksetTallennetaan = true;

        //Ikkunan Valmistelu
        ikkuna = new Stage();
        ikkuna.setTitle("Kauppa");
        ikkuna.initModality(Modality.APPLICATION_MODAL);

        //Yritetaan hakea FXML-tiedosto
        try {
            asettelu = FXMLLoader.load(this.getClass().getResource("/Kauppanakyma/Kauppanakyma.fxml"));
        } catch (Exception e) {
            System.out.println(getClass().getName() + " ei loytänyt asettelua");
            System.exit(0);
        }

        //Haetaan elementit
        myy = (Button) asettelu.lookup("#myyNappi");
        peruuta = (Button) asettelu.lookup("#peruutaNappi");
        tulot = (Label) asettelu.lookup("#tulotTeksti");
        taulukko = (TableView) asettelu.lookup("#tuotteetTaulukko");

        //Asetetaan Peruuta-napille toiminto
        peruuta.setOnAction(e -> {
            ikkuna.close();
            muutoksetTallennetaan = false;
        });

        //Asetetaan Myy-napille toiminto
        myy.setOnAction(e -> {
            int tarkistettavaRivi = tarkistaMyytavienMaarat();
            if (tarkistettavaRivi >= 0) {
                JOptionPane.showMessageDialog(null, "Tarkista tuotteen " + tuotteidenNimet.get(tarkistettavaRivi) + " myytävien määrä.");
            } else {
                ikkuna.close();
            }
        });

        //Valmistellaan sarakkeet
        TableColumn<Kauppatuoterivi, String> nimiSarake = new TableColumn("Tuote");
        nimiSarake.setMinWidth(100);
        nimiSarake.setCellValueFactory(new PropertyValueFactory<>("tuotteenNimi"));

        TableColumn<Kauppatuoterivi, Integer> maaraSarake = new TableColumn("Määrä");
        maaraSarake.setMinWidth(100);
        maaraSarake.setCellValueFactory(new PropertyValueFactory<>("tuotteenMaara"));

        TableColumn<Kauppatuoterivi, Double> hintaSarake = new TableColumn("Hinta");
        hintaSarake.setMinWidth(100);
        hintaSarake.setCellValueFactory(new PropertyValueFactory<>("tuotteenHinta"));

        TableColumn<Kauppatuoterivi, TextField> syoteSarake = new TableColumn("Myydään");
        syoteSarake.setMinWidth(200);
        syoteSarake.setCellValueFactory(new PropertyValueFactory<>("syote"));

        //Asetetaan sarakkeet taulukkoon
        taulukko.getColumns().addAll(nimiSarake, maaraSarake, hintaSarake, syoteSarake);

        //Haetaan tuotteiden nimet
        for (Map.Entry<String, Integer> tuotteenMaara : tuotteidenMaarat.entrySet()) {
            tuotteidenNimet.add(tuotteenMaara.getKey());
        }

        //Luodaan rivit
        tuoterivit = new ArrayList<>();
        for (int i = 0; i < tuotteidenNimet.size(); i++) {
            if (tuotteidenMaarat.get(tuotteidenNimet.get(i)) > 0) {
                String tuotteenNimi = tuotteidenNimet.get(i);
                double hinta;
                switch (tuotteenNimi) {
                    case "Puu":
                        hinta = puunHinta;
                        break;
                    default:
                        hinta = 0;
                }
                //Lisataan hinta muistiin
                tuotteidenHinnat.put(tuotteenNimi, hinta);

                Kauppatuoterivi uusiRivi = new Kauppatuoterivi(tuotteenNimi, tuotteidenMaarat.get(tuotteenNimi), hinta);
                uusiRivi.getSyote().textProperty().addListener((arvo, vanha, uusi) -> {
                    if (!vanha.equals(uusi)) {
                        double tulevaRaha = 0;
                        if (tarkistaMyytavienMaarat() < 0) {
                            for (Kauppatuoterivi rivi : tuoterivit) {
                                tulevaRaha += muutaTekstiKokonaisluvuksi(rivi.getSyote().getText()) * rivi.getTuotteenHinta();
                            }
                            tulot.setText("Tulot: " + tulevaRaha);
                        } else {
                            tulot.setText("Tulot: ???");
                        }

                    }
                });
                tuoterivit.add(uusiRivi);
            }
        }
        //Tehdaan ArrayListista ObservableList, jonka TableView osaa nayttaa
        ObservableList<Kauppatuoterivi> OBtuoterivit = FXCollections.observableArrayList(tuoterivit);
        //Asetetaan taulukon rivit ObservableListilla
        taulukko.setItems(OBtuoterivit);

        //Ikkunan viimeistely ja avaus
        ikkuna.setScene(new Scene(asettelu));
        ikkuna.showAndWait();

        //Varmistetaan, tallennetaanko kayttajan muutokset
        if (muutoksetTallennetaan) {
            for (int i = 0; i < tuotteidenNimet.size(); i++) {
                double[] maaraJaHinta = new double[2];
                maaraJaHinta[0] = muutaTekstiKokonaisluvuksi(tuoterivit.get(i).getSyote().getText());
                maaraJaHinta[1] = tuotteidenHinnat.get(tuotteidenNimet.get(i));
                palautus.put(tuotteidenNimet.get(i), maaraJaHinta);
            }
        }

        return palautus;
    }

    private Integer tarkistaMyytavienMaarat() {
        for (int i = 0; i < tuoterivit.size(); i++) {
            Kauppatuoterivi rivi = tuoterivit.get(i);
            Integer numero = muutaTekstiKokonaisluvuksi(rivi.getSyote().getText());
            if (numero == null) {
                return i;
            }

            if (rivi.getTuotteenMaara() < numero) {
                return i;
            }
        }

        return -1;
    }

    private Integer muutaTekstiKokonaisluvuksi(String teksti) {
        int numero;
        if (!teksti.isEmpty()) {
            try {
                numero = Integer.parseInt(teksti);
            } catch (Exception e) {
                return null;
            }
        } else {
            numero = 0;
        }
        return numero;
    }
}
