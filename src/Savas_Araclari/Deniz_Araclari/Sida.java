package Savas_Araclari.Deniz_Araclari;

public class Sida extends Deniz_araclari{
    private static int sidaSayisi = 0;

    public Sida() {
        super(20, 15, 10,10,10,"sida"+(++sidaSayisi));
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