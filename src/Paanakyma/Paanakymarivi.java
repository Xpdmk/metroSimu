/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paanakyma;

/**
 *
 * @author royja
 */
public class Paanakymarivi {
    private String kohde;
    private String tieto;

    public Paanakymarivi(String kohde, String tieto) {
        this.kohde = kohde;
        this.tieto = tieto;
    }

    public String getKohde() {
        return kohde;
    }

    public String getTieto() {
        return tieto;
    }

    public void setKohde(String kohde) {
        this.kohde = kohde;
    }

    public void setTieto(String tieto) {
        this.tieto = tieto;
    }
}
