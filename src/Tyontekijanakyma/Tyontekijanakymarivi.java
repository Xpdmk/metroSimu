package Tyontekijanakyma;

import javafx.scene.control.CheckBox;

public class Tyontekijanakymarivi {
    private final int tyontekijakoodi;
    private final double tehokkuus;
    private final int toheloinnit;
    private final CheckBox valintaruutu;
    
    public Tyontekijanakymarivi(int tyontekijakoodi, double tehokkuus, int toheloinnit) {
        this.tyontekijakoodi = tyontekijakoodi;
        this.tehokkuus = tehokkuus;
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

    public CheckBox getValintaruutu() {
        return valintaruutu;
    }

    public int getTyontekijakoodi() {
        return tyontekijakoodi;
    }
    
}
