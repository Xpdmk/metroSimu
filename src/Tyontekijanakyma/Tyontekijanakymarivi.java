package Tyontekijanakyma;

import javafx.scene.control.CheckBox;

public class Tyontekijanakymarivi {
    private final int tyontekijakoodi;
    private final int resurssit;
    private final double palkka;
    private final int toheloinnit;
    private final CheckBox valintaruutu;
    
    public Tyontekijanakymarivi(int tyontekijakoodi, int resurssit, double palkka, int toheloinnit) {
        this.tyontekijakoodi = tyontekijakoodi;
        this.resurssit = resurssit;
        this.palkka = palkka;
        this.toheloinnit = toheloinnit;
        this.valintaruutu = new CheckBox();
        
        this.valintaruutu.setSelected(true);
    }

    public int getToheloinnit() {
        return toheloinnit;
    }

    public int getResurssit() {
        return resurssit;
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
    
    public void vaihdaValintaruutu(boolean asd) {
        this.valintaruutu.setSelected(asd);
    }
    
}
