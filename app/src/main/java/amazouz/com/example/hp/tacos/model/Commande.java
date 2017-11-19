package amazouz.com.example.hp.tacos.model;

/**
 * Created by HP on 09/11/2017.
 */

public class Commande {
    private String pain="";
    private String sauce="";
    private String viande="";
    private String boisson="";
    public Commande(String pain,String sauce,String viande,String boisson){
        this.pain=pain;
        this.sauce=sauce;
        this.viande=viande;
        this.boisson=boisson;

    }

    public String getPain() {
        return pain;
    }

    public String getSauce() {
        return sauce;
    }

    public String getViande() {
        return viande;
    }

    public String getBoisson() {
        return boisson;
    }

    public void setPain(String pain) {
        this.pain = pain;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public void setViande(String viande) {
        this.viande = viande;
    }

    public void setBoisson(String boisson) {
        this.boisson = boisson;
    }
}
