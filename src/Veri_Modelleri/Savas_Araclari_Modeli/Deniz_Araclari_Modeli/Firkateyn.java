package Veri_Modelleri.Savas_Araclari_Modeli.Deniz_Araclari_Modeli;

public class Firkateyn extends Deniz_araclari{
    private static int firkateynSayisi = 0;

    public Firkateyn() {
        super(0, 25, 10,5,0,"firkateyn"+(++firkateynSayisi));
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
