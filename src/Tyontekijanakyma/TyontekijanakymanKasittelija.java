package Tyontekijanakyma;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import luokat.Tyontekija;
import javax.swing.JOptionPane;

public class TyontekijanakymanKasittelija implements Initializable {

    private Stage ikkuna;
    private Boolean muutoksetTallennetaan;

    @FXML
    private Label ohje;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public ArrayList<Integer> nayta(ArrayList<Tyontekija> tyontekijat, String ikkunanOtsikko) throws Exception {
        //Käsittelyn valmistelu
        ikkuna = new Stage();
        muutoksetTallennetaan = true;
        
        //Tuodaan asettelu-muuttujaan asettelu sijainnista
        AnchorPane asettelu = FXMLLoader.load(getClass().getResource("/Tyontekijanakyma/Tyontekijanakyma.fxml"));
        
        //Haetaan asettelusta napit ja talukko
        Button hyvaksy = (Button) asettelu.lookup("#hyvaksy");
        Button peruuta = (Button) asettelu.lookup("#peruuta");
        TableView taulukko = (TableView) asettelu.lookup("#tTaulukko");

        //Määritetään, mitä tehdään, kun nappeja painetaan
        hyvaksy.setOnAction(e -> {
            ikkuna.close();
        });
        peruuta.setOnAction(e -> {
            ikkuna.close();
            muutoksetTallennetaan = false;
        });

        //Sarakkeet luodaan näyttöä varten
        //Työntekijäkoodi -sarake
        TableColumn<Tyontekijanakymarivi, Integer> tyontekijakoodiSarake = new TableColumn("Tyontekijakoodi");
        tyontekijakoodiSarake.setMinWidth(100);
        tyontekijakoodiSarake.setCellValueFactory(new PropertyValueFactory<>("tyontekijakoodi"));

        //Työntekijän tehokkuus -sarake
        TableColumn<Tyontekijanakymarivi, Double> tehokkuusSarake = new TableColumn("Tehokkuus");
        tehokkuusSarake.setMinWidth(100);
        tehokkuusSarake.setCellValueFactory(new PropertyValueFactory<>("tehokkuus"));
        
        //Työntekijän palkka -sarake
        TableColumn<Tyontekijanakymarivi, Double> palkkaSarake = new TableColumn("Palkka");
        palkkaSarake.setMinWidth(100);
        palkkaSarake.setCellValueFactory(new PropertyValueFactory<>("palkka"));

        //Työntekijän tohelointien määrä -sarake
        TableColumn<Tyontekijanakymarivi, Integer> toheloinnitSarake = new TableColumn<>("Toheloinnit");
        toheloinnitSarake.setMinWidth(100);
        toheloinnitSarake.setCellValueFactory(new PropertyValueFactory<>("toheloinnit"));

        //Sarake valintaruutuja varten
        TableColumn<Tyontekijanakymarivi, CheckBox> valintaruutuSarake = new TableColumn<>("Pidä työntekijä");
        valintaruutuSarake.setMinWidth(30);
        valintaruutuSarake.setCellValueFactory(new PropertyValueFactory<>("valintaruutu"));

        //Lisätään sarakkeet taulukkoon
        taulukko.getColumns().addAll(tyontekijakoodiSarake, tehokkuusSarake, palkkaSarake, toheloinnitSarake, valintaruutuSarake);

        //Taulukon valmistelu
        ObservableList<Tyontekijanakymarivi> OBTyontekijanakymarivit = FXCollections.observableArrayList();
        for (Tyontekija tyontekija : tyontekijat) {
            OBTyontekijanakymarivit.add(new Tyontekijanakymarivi(tyontekija.getTyontekijakoodi(), tyontekija.getTehokkuus(), tyontekija.getPalkka(), tyontekija.getToheloinnit()));
        }

        //Lisätään lista riveistä
        taulukko.setItems(OBTyontekijanakymarivit);

        //Ikkuna asetetaan ei-ohitettavaksi
        ikkuna.initModality(Modality.APPLICATION_MODAL);

        //Valmistellaan ikkuna
        ikkuna.setOnCloseRequest(e -> {
            e.consume();
            JOptionPane.showMessageDialog(null, "Paina Hyväksy tai Peruuta palataksesi pääikkunaan");
        });
        ikkuna.setResizable(false);
        ikkuna.setScene(new Scene(asettelu, 600, 700));
        ikkuna.setTitle(ikkunanOtsikko);
        ikkuna.showAndWait();

        //Kerätään tyontekijakoodit, joita vastaa pois valittu valintaruutu
        ArrayList<Integer> poistettavatTyontekijat = new ArrayList<>();
        if (muutoksetTallennetaan) {
            for (Tyontekijanakymarivi rivi : OBTyontekijanakymarivit) {
                CheckBox valintaruutu = rivi.getValintaruutu();
                if (!valintaruutu.isSelected()) {
                    poistettavatTyontekijat.add(rivi.getTyontekijakoodi());
                    System.out.println(rivi.getTyontekijakoodi());
                }
            }
        }

        return poistettavatTyontekijat;
    }

}
