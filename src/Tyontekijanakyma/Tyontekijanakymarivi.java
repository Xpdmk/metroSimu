package Tyontekijanakyma;

import javafx.scene.control.CheckBox;

public class Tyontekijanakymarivi {
    private final int tyontekijakoodi;
    private final double tehokkuus;
    private final double palkka;
    private final int toheloinnit;
    private final CheckBox valintaruutu;
    
    public Tyontekijanakymarivi(int tyontekijakoodi, double tehokkuus, double palkka, int toheloinnit) {
        this.tyontekijakoodi = tyontekijakoodi;
        this.tehokkuus = tehokkuus;
        this.palkka = palkka;
        this.toheloinnit = toheloinnit;
        this.valintaruutu = new CheckBox();
        
        this.valintaruutu.setSelected(true);
    }

    public double getTehokkuus() {
        return tehokkuus;
    }

    public int getToheloinnit() {
        return toheloinnit;
    }

    public double getPalkka() {
        return palkka;
    }

    public CheckBox getValintaruutu() {
        return valintaruutu;
    }

    public int getTyontekijakoodi() {
        return tyontekijakoodi;
    }
    
}
