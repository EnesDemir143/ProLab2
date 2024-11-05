public abstract class Deniz_araclari extends Savas_Araclari {

    private int hava_vurus_avantaji;
    private int kara_vurus_avantaji;

    public Deniz_araclari(int seviye_puani, int dayaniklilik, int vurus,int hava_vurus_avantaji, int kara_vurus_avantaji,String kartID) {
        super(seviye_puani, dayaniklilik, vurus, "Deniz",kartID);
        this.hava_vurus_avantaji=hava_vurus_avantaji;
        this.kara_vurus_avantaji=kara_vurus_avantaji;
    }

    public int getHava_vurus_avantaji() {
        return hava_vurus_avantaji;
    }

    public int getKara_vurus_avantaji() {
        return kara_vurus_avantaji;
    }
}
