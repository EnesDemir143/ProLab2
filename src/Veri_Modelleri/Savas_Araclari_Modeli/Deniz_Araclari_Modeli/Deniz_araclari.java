package Veri_Modelleri.Savas_Araclari_Modeli.Deniz_Araclari_Modeli;

import Veri_Modelleri.Savas_Araclari_Modeli.EkstraVurusOzellikleri;
import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;

public abstract class Deniz_araclari extends Savas_Araclari implements EkstraVurusOzellikleri {

    private int hava_vurus_avantaji;
    private int kara_vurus_avantaji;

    public Deniz_araclari(int seviye_puani, int dayaniklilik, int vurus,int hava_vurus_avantaji, int kara_vurus_avantaji,String kartID) {
        super(seviye_puani, dayaniklilik, vurus, "Deniz",kartID);
        this.hava_vurus_avantaji=hava_vurus_avantaji;
        this.kara_vurus_avantaji=kara_vurus_avantaji;
    }

    @Override
    public int getHava_vurus_avantaji() {
        return hava_vurus_avantaji;
    }

    @Override
    public int getKara_vurus_avantaji() {
        return kara_vurus_avantaji;
    }
}
