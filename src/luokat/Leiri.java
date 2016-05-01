package luokat;

import java.util.ArrayList;
import java.util.Random;

public class Leiri {

    private Metsa metsa;
    private Luonto luonto;
    //private Kaivos kaivos;
    //private Kauppa kauppa;
    private ArrayList<Tyontekija> tyontekijat;
    private int aterioidenMaara;
    private double raha;
    private int puu;
    private int oletusPalkka;
    private ArrayList<Integer> vuoronToheloijienKoodit;
    private int vuoronSaadutAteriat;
    private int vuoronSaatuPuu;
    private int onnettomuusCooldown;
    //private Map<String, int> mineraalienMaara;
    private ArrayList<Raportti> raportit;

    public Leiri() {
        this.metsa = new Metsa();
        this.luonto = new Luonto();
        this.tyontekijat = new ArrayList<>();
        this.aterioidenMaara = 10;
        this.raha = 100;
        this.puu = 0;
        this.raportit = new ArrayList();
        this.oletusPalkka = 10;
        this.vuoronToheloijienKoodit = new ArrayList<>();
        this.vuoronSaadutAteriat = 0;
        this.vuoronSaatuPuu = 0;
    }

    public int getAterioidenMaara() {
        return aterioidenMaara;
    }

    public double getRaha() {
        return raha;
    }

    public int getPuu() {
        return puu;
    }

    public void kasittele() {
        /**
         * metodi ottaa huomioon leirilla olevien tyontekijat, niiden maaran
         * seka tyopaikan, mihin ne on sijoitettu, ja laskee tyopaikoilla
         * tapahtuneet mahdolliset onnettomuudet, resurssien, kuten ruoan, puun
         * ja mineraalien, tulon maaran, maksaa palkat ja ruokkii tyontekijat.
         * Naista tehdaan uusi Raportti-olio raporttilistaan.
         */

        vuoronToheloijienKoodit = new ArrayList<>();
        vuoronSaadutAteriat = 0;
        vuoronSaatuPuu = 0;

        kasitteleCooldown();
        laskeVelat();
        laskeTyontekijat();
        laskeResurssit();

        raportit.add(new Raportti(vuoronSaadutAteriat, vuoronSaatuPuu, vuoronToheloijienKoodit, 0));

    }

    private void kasitteleCooldown() {
        if (onnettomuusCooldown > 0) {
            onnettomuusCooldown -= 1;
        } else {
            for (Tyontekija tyontekija : tyontekijat) {
                tyontekija.setPalkka(oletusPalkka);
            }
        }
    }

    private void laskeTyontekijat() {
        for (Tyontekija tyontekija : tyontekijat) {

            if (tyontekija.toheloiko()) {
                onnettomuusCooldown += 4;
                tyontekija.setPalkka(tyontekija.getPalkka() * 1.1);

                vuoronToheloijienKoodit.add(tyontekija.getTyontekijakoodi());

            }
        }
    }

    private void laskeVelat() {
        for (Tyontekija tyontekija : tyontekijat) {
            raha -= tyontekija.getPalkka();
            aterioidenMaara -= 1;
        }
    }

    private void laskeResurssit() {
        for (Tyontekija tyontekija : tyontekijat) {
            if (tyontekija.getTyopaikkaindeksi() == 2) {
                double tehokkuus = tyontekija.getTehokkuus();
                vuoronSaadutAteriat += tehokkuus;
                aterioidenMaara += tehokkuus;
            }
            if (tyontekija.getTyopaikkaindeksi() == 1) {
                double tehokkuus = tyontekija.getTehokkuus();
                puu += tehokkuus;
                vuoronSaatuPuu += tehokkuus;
            }
        }
    }

    public Raportti viimeisinRaportti() {
        if (!raportit.isEmpty()) {
            return raportit.get(raportit.size() - 1);
        } else {
            return new Raportti(0, 0, new ArrayList<Integer>(), 0);
        }

    }

    public void palkkaaTyontekija(int tyopaikkaindeksi) {
        int seuraavaTyontekijakoodi = 1;
        Random randomaattori = new Random();
        ALKU:
        while (true) {
            for (Tyontekija tyontekija : tyontekijat) {
                if (tyontekija.getTyontekijakoodi() == seuraavaTyontekijakoodi) {
                    seuraavaTyontekijakoodi++;
                    continue ALKU;
                }
            }
            break;
        }
        
        Tyontekija uusi;
        //Testataanko, onko uusi tyontekija Jarno
        if (new Random().nextDouble() < (1/1000)) {
            uusi = new Tyontekija(0.8, 3, seuraavaTyontekijakoodi, tyopaikkaindeksi, oletusPalkka, 0);
        } else {
            uusi = new Tyontekija(randomaattori.nextDouble()*0.5, randomaattori.nextDouble()*20, seuraavaTyontekijakoodi, tyopaikkaindeksi, oletusPalkka, 0);
        }
        tyontekijat.add(uusi);
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
    
    public void setAterioidenMaara(int aterioidenMaara) {
        this.aterioidenMaara = aterioidenMaara;
    }

    public void setRaha(double raha) {
        this.raha = raha;
    }

    public void setPuu(int puu) {
        this.puu = puu;
    }

    public void setOletusPalkka(int oletusPalkka) {
        this.oletusPalkka = oletusPalkka;
    }
}
