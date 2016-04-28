package luokat;

import java.util.ArrayList;

public class Leiri {

    private Metsa metsa;
    private Luonto luonto;
    //private Kaivos kaivos;
    //private Kauppa kauppa;
    private ArrayList<Tyontekija> tyontekijat;
    private int aterioidenMaara;
    private double raha;
    private int puu;
    //private Map<String, int> mineraalienMaara;
    private ArrayList<Raportti> raportit;

    public Leiri() {
        this.metsa = new Metsa();
        this.luonto = new Luonto();
        this.tyontekijat = new ArrayList<>();
        this.aterioidenMaara = 10;
        this.raha = 100;
        this.puu = 0;
        this.raportit = new ArrayList<>();
    }

    public void kasittele() {
        /**
         * metodi ottaa huomioon leirilla olevien tyontekijat, niiden maaran
         * seka tyopaikan, mihin ne on sijoitettu, ja laskee tyopaikoilla
         * tapahtuneet mahdolliset onnettomuudet, resurssien, kuten ruoan, puun
         * ja mineraalien, tulon maaran, maksaa palkat ja ruokkii tyontekijat.
         * Naista tehdaan uusi Raportti-olio raporttilistaan.
         */
    }

    public Raportti viimeisinRaportti() {
        return raportit.get(raportit.size() - 1);
    }

    public void lisaaTyontekijat(Tyontekija[] lisattavat) {
        for (Tyontekija tyontekija : lisattavat) {
            this.tyontekijat.add(tyontekija);
        }
    }

    public void poistaTyontekijat(ArrayList<Integer> tyontekijakoodit) {
        for (Integer koodi : tyontekijakoodit) {
            for (int i = 0; i < tyontekijat.size(); i++) {
                if (tyontekijat.get(i).getTyontekijakoodi() == koodi) {
                    tyontekijat.remove(i);
                }
            }
        }
    }

    public Integer palautaTyontekijoidenMaaraTyopaikkaindeksilla(int i) {
        int maara = 0;
        for (Tyontekija tyontekija : tyontekijat) {
            if (tyontekija.getTyopaikkaindeksi() == i) {
                maara++;
            }
        }
        return maara;
    }

    public ArrayList<Tyontekija> palautaTyontekijatTyopaikkaindksilla(int i) {
        ArrayList<Tyontekija> ahertajat = new ArrayList<>();
        for (Tyontekija tyontekija : tyontekijat) {
            if (tyontekija.getTyontekijakoodi() == i) {
                ahertajat.add(tyontekija);
            }
        }
        return ahertajat;
    }
}
