public abstract class Hava_araclari extends Savas_Araclari{

    private int kara_vurus_avantaji;
    private int deniz_vurus_avantaji;

    public Hava_araclari(int seviye_puani, int dayaniklilik, int vurus,int kara_vurus_avantaji, int deniz_vurus_avantaji) {
        super(seviye_puani, dayaniklilik, vurus, "Hava");
        this.deniz_vurus_avantaji = deniz_vurus_avantaji;
        this.kara_vurus_avantaji = kara_vurus_avantaji;
    }

    public int getDeniz_vurus_avantaji() {
        return deniz_vurus_avantaji;
    }

    public int getKara_vurus_avantaji() {
        return kara_vurus_avantaji;
    }
}
