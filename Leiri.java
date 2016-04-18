import java.util.ArrayList;
public class Leiri {
  private Metsä metsä;
  private Luonto luonto;
  //private Kaivos kaivos;
  //private Kauppa kauppa;
  private ArrayList<Työntekijä> työntekijät;
  private int aterioidenMäärä;
  private double raha;
  private int puu;
  //private Map<String, int> mineraalienMäärä;
  private ArrayList<Raportti> raportit;

  public Leiri() {
    this.metsä = new Metsä();
    this.luonto = new Luonto();
    this.työntekijät = new Arraylist();
    this.aterioidenMäärä = 10;
    this.raha = 100;
    this.puu = 0;
    this.raportit = new ArrayList();
  }

  public void käsittele(int[] muutokset) {
    /**metodi ottaa huomioon leirillä olevien
    työntekijät, niiden määrän sekä työpaikan,
    mihin ne on sijoitettu, ja laskee
    työpaikoilla tapahtuneet mahdolliset
    onnettomuudet, resurssien, kuten
    ruoan, puun ja mineraalien, tulon määrän, 
    maksaa palkat ja ruokkii työntekijät.
    Näistä tehdään uusi Raportti-olio
    raporttilistaan.
    */
  }

  public Raportti viimeisinRaportti() {
    return raportit[raportit.length-1];
  }
}
