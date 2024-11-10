package Veri_Modelleri.Savas_Araclari_Modeli;

public interface EkstraVurusOzellikleri {
    default int getHava_vurus_avantaji() { return 0; }
    default int getKara_vurus_avantaji() { return 0; }
    default int getDeniz_vurus_avantaji() { return 0; }
}
