public class Ucak extends Hava_araclari{
    private static int ucakSayisi = 0;

    public Ucak() {
        super(0, 20, 10,10,0,"ucak"+(++ucakSayisi));
    }

    @Override
    public void kartPuanGoster() {
        System.out.println("Kart Puan Goster:"+getSeviye_puani());
        System.out.println("Dayaniklik:"+getDayaniklilik());
    }

    @Override
    public void durumGuncelle() {

    }


}
