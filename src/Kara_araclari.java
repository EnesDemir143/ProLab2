public abstract class Kara_araclari extends Savas_Araclari{

    private int deniz_vurus_avantaji;
    private int hava_vurus_avantaji;

    public Kara_araclari(int seviye_puani, int dayaniklilik, int vurus,int deniz_vurus_avantaji, int hava_vurus_avantaji) {
        super(seviye_puani, dayaniklilik, vurus, "Kara");
        this.deniz_vurus_avantaji = deniz_vurus_avantaji;
        this.hava_vurus_avantaji = hava_vurus_avantaji;
    }

}
