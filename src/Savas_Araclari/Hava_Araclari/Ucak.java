package Savas_Araclari.Hava_Araclari;

public class Ucak extends Hava_araclari{
    private static int ucakSayisi = 0;

    public Ucak() {
        super(0, 20, 10,10,0,"ucak"+(++ucakSayisi));
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
