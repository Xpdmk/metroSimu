package Luokat;

public class Luonto {
  private int elaintenMaara;
  private double elaintenkasvu;

    public Luonto() {
        this.elaintenMaara = 50;
        this.elaintenkasvu = 10.0;
    }

    public int getElaintenMaara() {
        return elaintenMaara;
    }

    public double getElaintenkasvu() {
        return elaintenkasvu;
    }

    public void setElaintenMaara(int elaintenMaara) {
        this.elaintenMaara = elaintenMaara;
    }

    public void setElaintenkasvu(double elaintenkasvu) {
        this.elaintenkasvu = elaintenkasvu;
    }

}
