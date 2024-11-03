public abstract class Savas_Araclari {
    private int seviye_puani;

    private int dayaniklilik;
    private int vurus;

    private String sinif;

    public Savas_Araclari() {
    }

    public Savas_Araclari(int seviye_puani, int dayaniklilik, int vurus, String sinif) {
        this.seviye_puani = seviye_puani;
        this.dayaniklilik = dayaniklilik;
        this.vurus = vurus;
        this.sinif = sinif;
    }

    public void setSeviye_puani(int seviye_puani) {
        this.seviye_puani = seviye_puani;
    }

    public void setDayaniklilik(int dayaniklilik) {
        this.dayaniklilik = dayaniklilik;
    }

    public int getSeviye_puani() {
        return seviye_puani;
    }

    public int getDayaniklilik() {
        return dayaniklilik;
    }

    public int getVurus() {
        return vurus;
    }

    public String getSinif() {
        return sinif;
    }
}
