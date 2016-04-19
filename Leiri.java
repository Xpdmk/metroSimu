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
    this.tyontekijat = new Arraylist();
    this.aterioidenMaara = 10;
    this.raha = 100;
    this.puu = 0;
    this.raportit = new ArrayList();
  }

  public void kasittele(int[] muutokset) {
    /**metodi ottaa huomioon leirilla olevien
    tyontekijat, niiden maaran seka tyopaikan,
    mihin ne on sijoitettu, ja laskee
    tyopaikoilla tapahtuneet mahdolliset
    onnettomuudet, resurssien, kuten
    ruoan, puun ja mineraalien, tulon maaran, 
    maksaa palkat ja ruokkii tyontekijat.
    Naista tehdaan uusi Raportti-olio
    raporttilistaan.
    */
  }

  public Raportti viimeisinRaportti() {
    return raportit[raportit.length-1];
  }
}
