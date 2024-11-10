package Veri_Modelleri.Savas_Araclari_Modeli.Hava_Araclari_Modeli;

public class Siha extends Hava_araclari{
    private static int sihaSayisi = 0;

    public Siha() {
        super(20, 15, 10,10,10,"siha"+(++sihaSayisi));
    }

    @Override
    public void kartPuanGoster() {
        System.out.print(getKartID()+"|");
        System.out.println("Dayaniklik:"+getDayaniklilik());
    }

    @Override
    public void durumGuncelle(int hasar) {
        this.setDayaniklilik(Math.max(this.getDayaniklilik() - hasar, 0));
    }


}
