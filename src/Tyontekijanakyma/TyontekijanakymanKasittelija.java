package Tyontekijanakyma;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import luokat.Tyontekija;
import javax.swing.JOptionPane;

public class TyontekijanakymanKasittelija implements Initializable {

    private Stage ikkuna;
    private Boolean muutoksetTallennetaan;
    private TableColumn<Tyontekijanakymarivi, Integer> tyontekijakoodiSarake;
    private TableColumn<Tyontekijanakymarivi, Double> tehokkuusSarake;
    private TableColumn<Tyontekijanakymarivi, Double> palkkaSarake;
    private TableColumn<Tyontekijanakymarivi, Integer> toheloinnitSarake;
    private TableColumn<Tyontekijanakymarivi, CheckBox> valintaruutuSarake;
    private AnchorPane asettelu;
    private Button hyvaksy;
    private Button peruuta;
    private TableView taulukko;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    private void valmistele(String ikkunanOtsikko){
        //Käsittelyn valmistelu
        ikkuna = new Stage();
        muutoksetTallennetaan = true;
        ikkuna.setResizable(false);
        ikkuna.setTitle(ikkunanOtsikko);
        
        //Ikkuna asetetaan ei-ohitettavaksi
        ikkuna.initModality(Modality.APPLICATION_MODAL);

        //Valmistellaan ikkuna
        ikkuna.setOnCloseRequest(e -> {
            e.consume();
            JOptionPane.showMessageDialog(null, "Paina Hyväksy tai Peruuta palataksesi pääikkunaan");
        });
        
        //Tuodaan asettelu-muuttujaan asettelu sijainnista
        try {
            asettelu = FXMLLoader.load(this.getClass().getResource("/Tyontekijanakyma/Tyontekijanakyma.fxml"));
        } catch (Exception e) {
            System.out.println(getClass().getName() + " ei löytänyt asettelua");
            System.exit(0);
        }
        
        //Haetaan asettelusta napit ja talukko
        hyvaksy = (Button) asettelu.lookup("#hyvaksy");
        peruuta = (Button) asettelu.lookup("#peruuta");
        taulukko = (TableView) asettelu.lookup("#tTaulukko");

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
        tyontekijakoodiSarake = new TableColumn("Tyontekijakoodi");
        tyontekijakoodiSarake.setMinWidth(100);
        tyontekijakoodiSarake.setCellValueFactory(new PropertyValueFactory<>("tyontekijakoodi"));

        //Työntekijän tehokkuus -sarake
        tehokkuusSarake = new TableColumn("Tehokkuus");
        tehokkuusSarake.setMinWidth(100);
        tehokkuusSarake.setCellValueFactory(new PropertyValueFactory<>("tehokkuus"));
        
        //Työntekijän palkka -sarake
        palkkaSarake = new TableColumn("Palkka");
        palkkaSarake.setMinWidth(100);
        palkkaSarake.setCellValueFactory(new PropertyValueFactory<>("palkka"));

        //Työntekijän tohelointien määrä -sarake
        toheloinnitSarake = new TableColumn<>("Toheloinnit");
        toheloinnitSarake.setMinWidth(100);
        toheloinnitSarake.setCellValueFactory(new PropertyValueFactory<>("toheloinnit"));

        //Sarake valintaruutuja varten
        valintaruutuSarake = new TableColumn<>("Pidä työntekijä");
        valintaruutuSarake.setMinWidth(30);
        valintaruutuSarake.setCellValueFactory(new PropertyValueFactory<>("valintaruutu"));
        
        //Lisätään sarakkeet taulukkoon
        taulukko.getColumns().addAll(tyontekijakoodiSarake, tehokkuusSarake, palkkaSarake, toheloinnitSarake, valintaruutuSarake);
    }

    public HashMap<Integer, Boolean> nayta(ArrayList<Tyontekija> tyontekijat, String ikkunanOtsikko) {
        valmistele(ikkunanOtsikko);
        //Taulukon valmistelu
        ArrayList<Tyontekijanakymarivi> rivit = new ArrayList<>();
        for (Tyontekija tyontekija : tyontekijat) {
            rivit.add(new Tyontekijanakymarivi(tyontekija.getTyontekijakoodi(), tyontekija.getTehokkuus(), tyontekija.getPalkka(), tyontekija.getToheloinnit()));
        }
        
        return avaaIkkuna(rivit);
    }
    
    public HashMap<Integer, Boolean> naytaMuunneltu(ArrayList<Tyontekija> tyontekijat, ArrayList<Integer> muunnelmat, String ikkunanOtsikko) {
        valmistele(ikkunanOtsikko);
        //Taulukon valmistelu
        ArrayList<Tyontekijanakymarivi> rivit = new ArrayList<>();
        for (Tyontekija tyontekija : tyontekijat) {
            Tyontekijanakymarivi rivi = new Tyontekijanakymarivi(tyontekija.getTyontekijakoodi(), tyontekija.getTehokkuus(), tyontekija.getPalkka(), tyontekija.getToheloinnit());
            for (Integer koodi : muunnelmat) {
                if (koodi.equals(rivi.getTyontekijakoodi())) {
                    rivi.vaihdaValintaruutu(false);
                }
            }
            rivit.add(rivi);
        }
        
        return avaaIkkuna(rivit);
    }
    
    private HashMap<Integer, Boolean> avaaIkkuna(ArrayList<Tyontekijanakymarivi> rivit) {
        ObservableList<Tyontekijanakymarivi> OBTyontekijanakymarivit = FXCollections.observableArrayList(rivit);
        
        //Lisätään lista riveistä
        taulukko.setItems(OBTyontekijanakymarivit);
        
        ikkuna.setScene(new Scene(asettelu, 600, 700));
        ikkuna.showAndWait();
        
        HashMap<Integer, Boolean> tyontekijaMuutokset = new HashMap<>();
        for (Tyontekijanakymarivi rivi : rivit) {
            tyontekijaMuutokset.put(rivi.getTyontekijakoodi(), rivi.getValintaruutu().isSelected());
        }
        
        return tyontekijaMuutokset;
    }

}
