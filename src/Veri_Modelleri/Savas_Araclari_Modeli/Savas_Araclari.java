package Veri_Modelleri.Savas_Araclari_Modeli;

public abstract class Savas_Araclari {
    private int seviye_puani;
    private String kartID;

    private int dayaniklilik;
    private int vurus;

    private int hava_vurus_avantaji;
    private int kara_vurus_avantaji;
    private int deniz_vurus_avantaji;

    private String sinif;

    public Savas_Araclari() {
    }

    public Savas_Araclari(int seviye_puani, int dayaniklilik, int vurus, String sinif,String kartID) {
        this.seviye_puani = seviye_puani;
        this.dayaniklilik = dayaniklilik;
        this.vurus = vurus;
        this.sinif = sinif;
        this.kartID = kartID;
    }

    public abstract void kartPuanGoster();
    public abstract void durumGuncelle(int hasar);

    public void setDayaniklilik(int dayaniklilik) {
        this.dayaniklilik = dayaniklilik;
    }

    public String getSinif() {
        return sinif;
    }

    public int getVurus() {
        return vurus;
    }

    public int getDayaniklilik() {
        return dayaniklilik;
    }

    public int getSeviye_puani() {
        return seviye_puani;
    }

    public String getKartID() {
        return kartID;
    }
}
