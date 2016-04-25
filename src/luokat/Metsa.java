package luokat;

public class Metsa {
  private int puidenMaara;
  private double puidenKasvu;
  private double palkka;
  public Metsa() {
    this.puidenMaara = 500;
    this.puidenKasvu = 10.0;
    this.palkka = 10;
  }

    public int getPuidenMaara() {
        return puidenMaara;
    }

    public double getPuidenKasvu() {
        return puidenKasvu;
    }

    public double getPalkka() {
        return palkka;
    }

    public void setPuidenMaara(int puidenMaara) {
        this.puidenMaara = puidenMaara;
    }

    public void setPuidenKasvu(double puidenKasvu) {
        this.puidenKasvu = puidenKasvu;
    }

    public void setPalkka(double palkka) {
        this.palkka = palkka;
    }
  
}
