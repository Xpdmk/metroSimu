package luokat;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

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
    private ArrayList<Tyontekija> vuoronKuolleet;
    private int onnettomuusCooldown;
    private int todnakOnnettomuuskuolema; //Prosentti
    private Random randomaattori;
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
        this.todnakOnnettomuuskuolema = 10;
        this.randomaattori = new Random();
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
        vuoronKuolleet = new ArrayList<>();

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
                vuoronToheloijienKoodit.add(tyontekija.getTyontekijakoodi());
                if (randomaattori.nextDouble()*100 > 100-todnakOnnettomuuskuolema) {
                    kuoletaTyontekija(tyontekija.getTyopaikkaindeksi());
                }

            }
        }
        for (Tyontekija tyontekija : tyontekijat) {
            tyontekija.setPalkka(tyontekija.getPalkka() * Math.pow(1.1, vuoronToheloijienKoodit.size()));
        }
    }
    
    private void kuoletaTyontekija(int tyopaikkaindeksi) {
        ArrayList<Tyontekija> vaihtoehtoiset = new ArrayList<>();
        for (Tyontekija tyontekija : tyontekijat) {
            if (tyopaikkaindeksi == tyontekija.getTyopaikkaindeksi()) {
                vaihtoehtoiset.add(tyontekija);
            }
        }
        int randomTyontekijakoodi = randomaattori.nextInt(vaihtoehtoiset.size()-1);
        vuoronKuolleet.add(vaihtoehtoiset.get(randomTyontekijakoodi));
        tyontekijat.remove(vaihtoehtoiset.get(randomTyontekijakoodi));
    }

    private void laskeVelat() {
        for (Tyontekija tyontekija : tyontekijat) {
            raha -= tyontekija.getPalkka();
            aterioidenMaara -= 1;
        }
    }

    private void laskeResurssit() {
        for (Tyontekija tyontekija : tyontekijat) {
            int tehokkuus = tyontekija.getTehokkuus();
            int vuoronKeratyt = 0;
            int yritys = 0;
            while (yritys < 5 && vuoronKeratyt < 4) {
                vuoronKeratyt = new Random().nextInt(tehokkuus);
                yritys++;
            }
            if (tyontekija.getTyopaikkaindeksi() == 2) {

                vuoronSaadutAteriat += vuoronKeratyt;
                aterioidenMaara += vuoronKeratyt;
            }
            if (tyontekija.getTyopaikkaindeksi() == 1) {
                puu += vuoronKeratyt;
                vuoronSaatuPuu += vuoronKeratyt;
            }
            tyontekija.setKeratytResurssit(tyontekija.getKeratytResurssit() + vuoronKeratyt);
        }
    }
    
    public double palautaPalkkoihinMenevaRaha(ArrayList<Integer> potkittavat) {
        double palkat = 0;
        for (Tyontekija tyontekija : tyontekijat) {
            if (!potkittavat.contains(tyontekija.getTyontekijakoodi())) {
                palkat += tyontekija.getPalkka();
            }
            
        }
        return palkat;
    }

    public Raportti viimeisinRaportti() {
        if (!raportit.isEmpty()) {
            return raportit.get(raportit.size() - 1);
        } else {
            return new Raportti(0, 0, new ArrayList<>(), 0);
        }

    }

    public void palkkaaTyontekija(int tyopaikkaindeksi) {
        int seuraavaTyontekijakoodi = 1;
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
        if (new Random().nextDouble() < (1 / 1000)) {
            uusi = new Tyontekija(0.8, 3, seuraavaTyontekijakoodi, tyopaikkaindeksi, oletusPalkka, 0);
            System.out.println("Jarno iskee!");
        } else {
            double tehokkuus = 0;
            while (tehokkuus < 2) {
                tehokkuus = Math.round(randomaattori.nextDouble() * 10);
            }
            uusi = new Tyontekija(randomaattori.nextDouble() * 0.2, (int) tehokkuus, seuraavaTyontekijakoodi, tyopaikkaindeksi, oletusPalkka, 0);
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
            if (tyontekija.getTyopaikkaindeksi() == i) {
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
