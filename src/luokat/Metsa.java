package Luokat;

public class Metsa {
  private int puidenMaara;
  private double puidenKasvu;
  private double palkka;
  public Metsa() {
    this.puidenMaara = 10000;
    this.puidenKasvu = 1.05;
  }

    public int getPuidenMaara() {
        return puidenMaara;
    }

    public double getPuidenKasvu() {
        return puidenKasvu;
    }

    public void setPuidenMaara(int puidenMaara) {
        this.puidenMaara = puidenMaara;
    }

    public void setPuidenKasvu(double puidenKasvu) {
        this.puidenKasvu = puidenKasvu;
    }

    public void vahennaPuita(int maara) {
      puidenMaara -= maara;
    }

    public void kasva() {
      puidenMaara = (int) Math.round(puidenMaara*puidenKasvu);
    }


}
