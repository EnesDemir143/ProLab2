public abstract class Kara_araclari extends Savas_Araclari implements EkstraVurusOzellikleri{

    private int deniz_vurus_avantaji;
    private int hava_vurus_avantaji;

    public Kara_araclari(int seviye_puani, int dayaniklilik, int vurus,int deniz_vurus_avantaji, int hava_vurus_avantaji,String kartID) {
        super(seviye_puani, dayaniklilik, vurus, "Kara",kartID);
        this.deniz_vurus_avantaji = deniz_vurus_avantaji;
        this.hava_vurus_avantaji = hava_vurus_avantaji;
    }

    @Override
    public int getHava_vurus_avantaji() {
        return hava_vurus_avantaji;
    }

    @Override
    public int getDeniz_vurus_avantaji() {
        return deniz_vurus_avantaji;
    }
}
